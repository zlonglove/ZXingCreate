package cn.zlonglove.zxingcreate;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	EditText input_editText;
	Button qr_button;
	ImageView zxing_display;
	CreateZxing createZxing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		init();
	}
	
	private void findViews()
	{
		input_editText=(EditText) findViewById(R.id.input_zxing);
		qr_button=(Button) findViewById(R.id.qr_button);
		zxing_display=(ImageView) findViewById(R.id.zxing_display);
	}
	private void init()
	{
		createZxing=new CreateZxing();
		qr_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String input=input_editText.getText().toString();
				if (TextUtils.isEmpty(input)) {
					return;
				}
				createZxing.showInView(zxing_display, input);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
