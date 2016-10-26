package edu.hhu.baiduMap.tangsj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity{

	private TextView textView1,textView2,textView3;
	private Button loginbutton,registbutton;
	private Typeface typeFace1,typeFace2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		textView1 = (TextView)findViewById(R.id.myappname);
		textView2 = (TextView)findViewById(R.id.use);
		textView3 = (TextView)findViewById(R.id.pas);
		typeFace1 =Typeface.createFromAsset(getAssets(),"fonts/liuxingjianti.ttf");
		typeFace2 =Typeface.createFromAsset(getAssets(),"fonts/nuojiya.ttf");
		registbutton = (Button)findViewById(R.id.btnReg);
		textView3.setText("密  码");
		textView1.setTypeface(typeFace1);
		textView2.setTypeface(typeFace2);
		textView3.setTypeface(typeFace2);
		registbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
				startActivity(intent);
			}
		});
	}
}
