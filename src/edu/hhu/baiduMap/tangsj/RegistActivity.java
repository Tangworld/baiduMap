package edu.hhu.baiduMap.tangsj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.hhu.baiduMap.tangsj.domain.User;
import edu.hhu.baiduMap.tangsj.util.SqlService;

public class RegistActivity extends Activity {

	private String arearesult="";
	private SqlService service = new SqlService(this);
	
	private Context context;
	private RadioGroup group;
	private RadioButton checked;
	private Button registbutton;
	private Button cancelbutton;
	private EditText editfavor;
	private EditText editbirth;
	private EditText edituser;
	private EditText editpass;
	private EditText editconfirm;
	private EditText editphone;
	private EditText editmail;
	private EditText editmotto;
	private TextView registtheme;
	private TextView use;
	private TextView pas;
	private TextView con;
	private TextView gen;
	private TextView pho;
	private TextView mai;
	private TextView bir;
	private TextView are;
	private TextView fav;
	private TextView mot;
	private TextView line;
	private Spinner province;
	private Spinner city;
	private int provinceIndex;
	private int cityIndex;
	private Typeface typeFace1,typeFace2;
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
		group = (RadioGroup)findViewById(R.id.genderRadioGroupId);
//		
//		private EditText editconfirmpass;
//		private EditText editfavor;
//		private EditText editbirth;
//		private EditText edituser;
//		private EditText editpass;
//		private EditText editconfirm;
//		private EditText editphone;
//		private EditText editmail;
//		private EditText editmotto;
		
		cancelbutton = (Button)findViewById(R.id.cancelbutton);
		registbutton = (Button)findViewById(R.id.registbutton);
		editfavor = (EditText) findViewById(R.id.editfavor);
		editbirth = (EditText) findViewById(R.id.editbirth);
		edituser = (EditText) findViewById(R.id.user);
		editpass = (EditText) findViewById(R.id.pass);
		editphone = (EditText) findViewById(R.id.editphone);
		editmail = (EditText) findViewById(R.id.editmail);
		editmotto = (EditText) findViewById(R.id.editmotto);
		editconfirm = (EditText)findViewById(R.id.confirmpass);
		registtheme = (TextView)findViewById(R.id.registtheme);
		use = (TextView) findViewById(R.id.use);
		use.setText("用 户 名");
		pas = (TextView) findViewById(R.id.pas);
		pas.setText("密    码");
		con = (TextView) findViewById(R.id.con);
		gen = (TextView) findViewById(R.id.gender);
		gen.setText("性    别");
		pho = (TextView) findViewById(R.id.phone);
		pho.setText("电    话");
		mai = (TextView) findViewById(R.id.mail);
		mai.setText("邮    箱");
		bir = (TextView) findViewById(R.id.birth);
		bir.setText("生    日");
		are = (TextView) findViewById(R.id.area);
		are.setText("籍    贯");
		fav = (TextView) findViewById(R.id.favor);
		fav.setText("兴    趣");
		mot = (TextView) findViewById(R.id.motto);
		line = (TextView) findViewById(R.id.line);
		province = (Spinner)findViewById(R.id.province);
		city = (Spinner)findViewById(R.id.city);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,ProvinceCityUtil.PROVINCE_ARRAY);
		province.setAdapter(arrayAdapter);
		
		typeFace1 =Typeface.createFromAsset(getAssets(),"fonts/liuxingjianti.ttf");
		typeFace2 =Typeface.createFromAsset(getAssets(),"fonts/nuojiya.ttf");
		registtheme.setTypeface(typeFace1);
		use.setTypeface(typeFace2);
		pas.setTypeface(typeFace2);
		con.setTypeface(typeFace2);
		gen.setTypeface(typeFace2);
		pho.setTypeface(typeFace2);
		mai.setTypeface(typeFace2);
		bir.setTypeface(typeFace2);
		are.setTypeface(typeFace2);
		fav.setTypeface(typeFace2);
		mot.setTypeface(typeFace2);
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
		//注册按钮
		dealRegist();
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
				if(cityIndex<0){
					arearesult += ProvinceCityUtil.PROVINCE_ARRAY[provinceIndex];
				}
				arearesult += ProvinceCityUtil.PROVINCE_ARRAY[provinceIndex]+"-"+ProvinceCityUtil.CITY_ARRAY[provinceIndex][cityIndex];
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
	
	private void dealRegist(){
		registbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.println("clicked!");
				registbutton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
				
				
				 String username = edituser.getText().toString().trim();
				 String password = editpass.getText().toString().trim();
				 String confirmpassword = editconfirm.getText().toString().trim();
				 String gender = "";
				 if(registbutton!=null)registbutton.getText().toString().trim();
				 String phone = editphone.getText().toString().trim();
				 String mail = editmail.getText().toString().trim();
				 String birthday = editbirth.getText().toString().trim();
				 String area = arearesult;
				 String hobby = editfavor.getText().toString().trim();
				 String motto = editmotto.getText().toString().trim();
				 
				 List<User> list=service.findbysql("");
				 if(list.size()>0){
					 boolean flag = false;
					 for(User u:list){
						 if(username.equals(u.getUsername())){
							 flag = true;
							 break;
						 }
					 }
					 if(flag == true){
						 Toast.makeText(context, "已经存在的用户名", Toast.LENGTH_LONG).show();
					 }
				 }
				 
				 if(username.equals("")){
					 Toast.makeText(context, "用户名不能为空", Toast.LENGTH_LONG).show();
				 }else if(password.equals("")){
					 Toast.makeText(context, "密码不能为空", Toast.LENGTH_LONG).show();
				 }else if(!password.equals(confirmpassword)){
					 Toast.makeText(context, "两次密码不一致", Toast.LENGTH_LONG).show();
					 System.out.println("密码不一致");
				 }else if(confirmpassword.equals("")){
					 Toast.makeText(context, "确认密码不能为空", Toast.LENGTH_LONG).show();
				 }else if(!isMobile(phone)){
					 Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_LONG).show();
				 }else if(!isEmail(mail)){
					 Toast.makeText(context, "邮箱格式不正确", Toast.LENGTH_LONG).show();
				 }else{
					 service.savezc(username, confirmpassword, gender, phone, mail, birthday, area, hobby, motto);
					 Toast.makeText(context, "注册成功！", Toast.LENGTH_LONG).show();
					 finish();
				 }
			}
		});
	}
	
	
	 private boolean isMobile(String number) {
		    /*
		    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		    联通：130、131、132、152、155、156、185、186
		    电信：133、153、180、189、（1349卫通）
		    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		    */
		        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		        if (TextUtils.isEmpty(number)) {
		            return false;
		        } else {
		            //matches():字符串是否在给定的正则表达式匹配
		            return number.matches(num);
		        }
	 }
	 
	 private boolean isEmail(String email) {
		 String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		 Pattern p = Pattern.compile(str);
		 Matcher m = p.matcher(email);
		 return m.matches();
		}
}
