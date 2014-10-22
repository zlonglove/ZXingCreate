package cn.zlonglove.zxingcreate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @author zhanglong
 * 
 */
public class CreateZxing {
	int QR_HEIGHT = 300;
	int QR_WIDRH = 300;

	private Bitmap createQRImage(String url) {
		Bitmap bitmap = null;
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			BitMatrix bitMatrix = new QRCodeWriter().encode(url,
					BarcodeFormat.QR_CODE, QR_WIDRH, QR_HEIGHT, hints);
			int[] pisels = new int[QR_WIDRH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDRH; x++) {
					if (bitMatrix.get(x, y)) {
						pisels[y * QR_WIDRH + x] = 0xff000000;
					} else {
						pisels[y * QR_WIDRH + x] = 0xffffffff;
					}
				}
			}
			bitmap = Bitmap.createBitmap(QR_WIDRH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pisels, 0, QR_WIDRH, 0, 0, QR_WIDRH, QR_HEIGHT);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public void showInView(ImageView view, String url) {
		Bitmap bitmap = createQRImage(url);
		if (bitmap == null) {
			return;
		}
		view.setImageBitmap(bitmap);

	}

	public void savetoSdcard(String url) {
		Bitmap bitmap = createQRImage(url);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File f = new File(path + "/zxing" + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
