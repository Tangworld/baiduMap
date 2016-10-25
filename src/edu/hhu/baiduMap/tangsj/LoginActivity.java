package edu.hhu.baiduMap.tangsj;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LoginActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		TextView textView1 = (TextView)findViewById(R.id.myappname);
		TextView textView2 = (TextView)findViewById(R.id.use);
		TextView textView3 = (TextView)findViewById(R.id.pas);
		Typeface typeFace1 =Typeface.createFromAsset(getAssets(),"fonts/liuxingjianti.ttf");
		Typeface typeFace2 =Typeface.createFromAsset(getAssets(),"fonts/nuojiya.ttf");
		textView3.setText("ÃÜ  Âë");
		textView1.setTypeface(typeFace1);
		textView2.setTypeface(typeFace2);
		textView3.setTypeface(typeFace2);
	}
}
