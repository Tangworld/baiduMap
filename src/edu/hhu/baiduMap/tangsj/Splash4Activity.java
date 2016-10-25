package edu.hhu.baiduMap.tangsj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Splash4Activity extends Activity{

	private Button button;
//	private Handler handler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			Intent intent  = new Intent(Splash4Activity.this,Splash3Activity.class);
//			startActivity(intent);
//			finish();
//		}
//		
//	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash4);
		button = (Button)findViewById(R.id.enter);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Splash4Activity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
//		handler.sendEmptyMessageDelayed(1, 1500);
	}
}
