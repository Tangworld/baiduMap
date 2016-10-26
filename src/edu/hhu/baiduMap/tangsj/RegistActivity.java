package edu.hhu.baiduMap.tangsj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity {

	private Context context;
	private Button cancelbutton;
	private EditText editfavor;
	private EditText editbirth;
	private TextView registtheme;
	private TextView use;
	private TextView pas;
	private TextView con;
	private TextView gender;
	private TextView phone;
	private TextView mail;
	private TextView birth;
	private TextView area;
	private TextView favor;
	private TextView motto;
	private TextView line;
	private Spinner province;
	private Spinner city;
	private int provinceIndex;
	private int cityIndex;
	private String[] hobbyArray = {"运动","旅游","编程","音乐","电影","美食","摄影","书法","绘画"};
	private boolean[] initStatus = {false,false,false,false,false,false,false,false,false};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.regist);
		init();
		addAction();
	}

	private void init() {
		context = this;
		cancelbutton = (Button) findViewById(R.id.cancelbutton);
		editfavor = (EditText) findViewById(R.id.editfavor);
		editbirth = (EditText) findViewById(R.id.editbirth);
		registtheme = (TextView) findViewById(R.id.registtheme);
		use = (TextView) findViewById(R.id.use);
		use.setText("用  户  名");
		pas = (TextView) findViewById(R.id.pas);
		pas.setText("密        码");
		con = (TextView) findViewById(R.id.con);
		gender = (TextView) findViewById(R.id.gender);
		gender.setText("性        别");
		phone = (TextView) findViewById(R.id.phone);
		phone.setText("电        话");
		mail = (TextView) findViewById(R.id.mail);
		mail.setText("邮        箱");
		birth = (TextView) findViewById(R.id.birth);
		birth.setText("生        日");
		area = (TextView) findViewById(R.id.area);
		area.setText("籍        贯");
		favor = (TextView) findViewById(R.id.favor);
		favor.setText("兴        趣");
		motto = (TextView) findViewById(R.id.motto);
		line = (TextView) findViewById(R.id.line);
		province = (Spinner)findViewById(R.id.province);
		city = (Spinner)findViewById(R.id.city);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,ProvinceCityUtil.PROVINCE_ARRAY);
		province.setAdapter(arrayAdapter);
		
	}

	private void addAction() {
		// 取消按钮监听
		cancelbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		//生日
		dealBirthday();
		//籍贯
		dealArea();
		// 兴趣
		dealHobby();
	}
	
	private void dealBirthday(){
		// 生日
		editbirth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("yes");
				DatePickerDialog dialog = new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker arg0, int year,
									int month, int day) {
								// Toast.makeText(context,
								// arg1+"-"+arg2+1+"-"+arg3,
								// Toast.LENGTH_LONG).show();
								month++;
								String fill = "";
								fill += year;
								if (month < 10) {
									fill += ("-0" + month);
								} else {
									fill += ("-" + month);
								}
								if (day < 10) {
									fill += ("-0" + day);
								} else {
									fill += ("-" + day);
								}
								long millis = System.currentTimeMillis();
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date=null;
						        try {
									date=sdf.parse(fill+" 00:00:00");
								} catch (ParseException e) {
									e.printStackTrace();
								}
						        long checked = (long)date.getTime();
						        System.out.println("系统时间："+millis+"   选择的时间："+checked);
								if(checked>millis){
									System.out.println("超过");
									Toast.makeText(context, "生日超过当前日期!", Toast.LENGTH_LONG).show();
								}else{
									System.out.println(date);
									editbirth.setText(fill);
								}
								
							}
						}, 1990, 0, 1);
				dialog.show();
			}
		});
	}
	
	private void dealArea(){
		province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				provinceIndex = position;
				if(position<4 || position == 31 || position == 32){
					line.setVisibility(View.INVISIBLE);
					city.setVisibility(View.INVISIBLE);
					cityIndex = -1;
				}else{
					line.setVisibility(View.VISIBLE);
					city.setVisibility(View.VISIBLE);
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,ProvinceCityUtil.CITY_ARRAY[provinceIndex]);
					city.setAdapter(arrayAdapter);
					
				}
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				cityIndex = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	private void dealHobby(){
		editfavor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.println("enter");
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("请选择兴趣");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String interest = "";
						for(int i=0;i<initStatus.length;i++){
							if(initStatus[i]==true){
								if(interest.equals("")){
									interest += hobbyArray[i];
								}else{
									interest += ","+hobbyArray[i];
								}
							}
						}
						editfavor.setText(interest);
					}
				});
				builder.setNegativeButton("取消", null);
				builder.setMultiChoiceItems(hobbyArray, initStatus, new DialogInterface.OnMultiChoiceClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int which,boolean checked) {
						initStatus[which] =checked;
					}
				});
				builder.create().show();
			}
		});
	}
}
