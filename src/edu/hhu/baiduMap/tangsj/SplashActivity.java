package edu.hhu.baiduMap.tangsj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent intent  = new Intent(SplashActivity.this,Splash2Activity.class);
			startActivity(intent);
			finish();
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		无标题栏：
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		全屏：
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		handler.sendEmptyMessageDelayed(1, 3000);
	}
}
