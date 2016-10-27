package edu.hhu.baiduMap.tangsj;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import edu.hhu.baiduMap.tangsj.domain.Current;
import edu.hhu.baiduMap.tangsj.domain.User;
import edu.hhu.baiduMap.tangsj.util.SqlService;

public class LoginActivity extends Activity{

	
	private SqlService service = new SqlService(this);
	private List<Current> list;
	
	private TextView textView1,textView2,textView3;
	private CheckBox check1,check2;
	private EditText user,pass;
	private Button loginbutton,registbutton,btnLogin;
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
		btnLogin = (Button)findViewById(R.id.btnLogin);
		check1 = (CheckBox)findViewById(R.id.check1);
		check2 = (CheckBox)findViewById(R.id.check2);
		user = (EditText)findViewById(R.id.user);
		pass = (EditText)findViewById(R.id.pass);
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
		
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String priority="";
				boolean flag1 = check1.isChecked();
				boolean flag2 = check2.isChecked();
				String username = user.getText().toString().trim();
				String password = pass.getText().toString().trim();
				if(list.size()==0){
					if(flag1==false&&flag2==false){
						service.saveCurrent(username, password, "0");
					}else if(flag1==true&&flag2==false){
						service.saveCurrent(username, password, "1");
					}else{
						service.saveCurrent(username, password, "2");
					}
				}else{
					if(flag1==false&&flag2==false){
						service.deleteCurrent();
						service.saveCurrent(username, password, "0");
					}else if(flag1==true&&flag2==false){
						service.deleteCurrent();
						service.saveCurrent(username, password, "1");
					}else{
						service.deleteCurrent();
						service.saveCurrent(username, password, "2");
					}
				}
				//TODO 开启地图页面
			}
		});
		
		init();
	}
	
	private void init(){
		list=service.getCurrent();
		if(list.size()>0){
			Current current = list.get(0);
			if(!current.getPriority().equals("0")){
				user.setText(current.getUsername());
				if(current.getPriority().equals("2")){
					pass.setText(current.getPassword());
				}
			}
		}
	}
}
