package edu.hhu.baiduMap.tangsj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import edu.hhu.baiduMap.tangsj.domain.User;
import edu.hhu.baiduMap.tangsj.util.SqlService;

public class BaiduMapActivity extends Activity implements OnMenuItemClickListener{
	
	private MapView mMapView;
	private RadioGroup mapTypeRadioGroup;
	private CheckBox trafficeCheckBox;
	private BaiduMap mBaiduMap;
	private Builder mBuilder;
	private Map map = new HashMap<String, String>();
	Context context = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(1024,1024);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_map);

		initView();
		addAction();
	}

	private void initView() {
		citylola();
		mMapView = (MapView) findViewById(R.id.mapView);
		mapTypeRadioGroup = (RadioGroup) findViewById(R.id.mapTypeRadioGroup);
		trafficeCheckBox = (CheckBox) findViewById(R.id.trafficeCheckBox);
		
		mBaiduMap = mMapView.getMap();
		// 定义一个地图状态的创建器
		mBuilder = new MapStatus.Builder();

		// 设置地图的默认地点(南京)
		LatLng point = new LatLng(32.057236, 118.778074);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
		// 将地图移动过去
		mBuilder.target(point);
		mBuilder.zoom(10);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
	}

	private void addAction() {
		mapTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.normalRadioButton:
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
					break;
				case R.id.satelliteRadioButton:
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
					break;
				default:
					break;
				}
			}
		});

		trafficeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mBaiduMap.setTrafficEnabled(true);
				} else {
					mBaiduMap.setTrafficEnabled(false);
				}
			}
		});
		
	}
	
	//添加菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		//groupId,itemId,orderId,名称
		menu.add(1,1,1,"经纬度定位").setOnMenuItemClickListener(this);
		menu.add(2,2,2,"城市定位").setOnMenuItemClickListener(this);
		menu.add(3,3,3,"公里数计算").setOnMenuItemClickListener(this);
		menu.add(4,4,4,"当前用户信息").setOnMenuItemClickListener(this);
		menu.add(5,5,5,"清除屏幕").setOnMenuItemClickListener(this);
		menu.add(6,6,6,"退出").setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

	//处理菜单点击事件
	public boolean onMenuItemClick(MenuItem item) {
		int itemId = item.getItemId();
		AlertDialog.Builder builder = new AlertDialog.Builder(BaiduMapActivity.this);
		builder.setIcon(R.drawable.applogo);
		switch(itemId){
		case 1:
			final View jwddwView = View.inflate(BaiduMapActivity.this,R.layout.dialog_jwddw, null);
			builder.setTitle("经纬度定位");
			builder.setView(jwddwView);
			final EditText jingduEditText = (EditText) jwddwView.findViewById(R.id.jingduEditTextId);
			final EditText weiduEditText = (EditText) jwddwView.findViewById(R.id.weiduEditTextId);
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String jingduString = jingduEditText.getText().toString().trim();
					String weiduString = weiduEditText.getText().toString().trim();
					double jindu = Double.parseDouble(jingduString);
					double weidu = Double.parseDouble(weiduString);
					// 定义Maker坐标点
					LatLng point = new LatLng(weidu, jindu);
					// 构建Marker图标
					BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark);
					// 构建MarkerOption，用于在地图上添加Marker
					OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
					// 在地图上添加Marker，并显示
					mBaiduMap.addOverlay(option);
					// 将地图移动过去
					mBuilder.target(point);
					mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
				}
			});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			break;
		case 2:
			final View csdwView = View.inflate(BaiduMapActivity.this,R.layout.dialog_csdw, null);
			builder.setTitle("城市定位");
			builder.setView(csdwView);
			final EditText csEditText = (EditText) csdwView.findViewById(R.id.csEditTextId);
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String city = csEditText.getText().toString().trim();
					String temp = (String)map.get(city);
					String[] longlati = temp.split("-");
					double jingdu = Double.parseDouble(longlati[0]);
					double weidu = Double.parseDouble(longlati[1]);
					LatLng point = new LatLng(weidu, jingdu);
					// 构建Marker图标
					BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark);
					// 构建MarkerOption，用于在地图上添加Marker
					OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
					// 在地图上添加Marker，并显示
					mBaiduMap.addOverlay(option);
					// 将地图移动过去
					mBuilder.target(point);
					mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
				}
			});
			
			
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			break;
		case 3:
			final View distanceView = View.inflate(BaiduMapActivity.this,R.layout.dialog_distance, null);
			builder.setTitle("公里数计算");
			builder.setView(distanceView);
			final EditText sourceEditTextId = (EditText) distanceView.findViewById(R.id.sourceEditTextId);
			final EditText destinationEditTextId = (EditText) distanceView.findViewById(R.id.destinationEditTextId);
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String source = sourceEditTextId.getText().toString().trim();
					String destination = destinationEditTextId.getText().toString().trim();
					String tempsource = (String)map.get(source);
					String[] longlatis = tempsource.split("-");
					double jingdus = Double.parseDouble(longlatis[0]);
					double weidus = Double.parseDouble(longlatis[1]);
					String tempdestination = (String)map.get(destination);
					String[] longlatid = tempdestination.split("-");
					double jingdud = Double.parseDouble(longlatid[0]);
					double weidud = Double.parseDouble(longlatid[1]);
					double result = Distance.GetLongDistance(jingdus, weidus, jingdud, weidud);
					long km = (int)(result/1000);
					Toast.makeText(context, "这两座城市间的距离约为："+km+"公里", Toast.LENGTH_LONG).show();
				}
			});
			
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			break;
		case 4:
			String currentusername = getIntent().getStringExtra("currentusername");
			SqlService service=new SqlService(getApplicationContext());
	    	List<User> list=service.findbysql("");
	    	User currentuser = new User();
	    	for(User u:list){
	    		if(u.getUsername().equals(currentusername)){
	    			currentuser = u;
	    		}
	    	}
	    	AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this).setTitle("个人信息");
	    	String result = "用户名："+currentuser.getUsername()+"\n";
	    	result += "性别："+currentuser.getGender()+"\n";
	    	result +="电话："+currentuser.getPhone()+"\n";
	    	result += "邮箱："+currentuser.getMail()+"\n";
	    	result += "生日："+currentuser.getBirthday()+"\n";
	    	result += "籍贯："+currentuser.getArea()+"\n";
	    	result += "兴趣："+currentuser.getHobby()+"\n";
	    	result += "人生格言："+currentuser.getMotto()+"\n";
	    	alertbuilder.setMessage(result);
	    	alertbuilder.setPositiveButton("是",null);
	    	alertbuilder.setNegativeButton("否",null).show();
			break;
		case 5:
			mBaiduMap.clear();
			break;
		case 6:
			builder.setIcon(R.drawable.applogo);
			builder.setTitle("退出提醒");
			builder.setMessage("你确认退出吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					BaiduMapActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			break;
		default:
			break;
		}
		return true;
	} 

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 是否触发按键为back键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(BaiduMapActivity.this);
			builder.setIcon(R.drawable.applogo);
			builder.setTitle("退出提醒");
			builder.setMessage("你确认退出吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					BaiduMapActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
	
	private void citylola(){
//		private Map map = new HashMap<String, String>();
		map.put("北京市","116.4-39.9");
		map.put("天津市","117.2-39.12");
		map.put("石家庄市","114.52-38.05");
		map.put("唐山市","118.2-39.63");
		map.put("秦皇岛市","119.6-39.93");
		map.put("邯郸市","114.48-36.62");
		map.put("邢台市","114.48-37.07");
		map.put("保定市","115.47-38.87");
		map.put("张家口市","114.88-40.82");
		map.put("承德市","117.93-40.97");
		map.put("沧州市","116.83-38.3");
		map.put("廊坊市","116.7-39.52");
		map.put("衡水市","115.68-37.73");
		map.put("太原市","112.55-37.87");
		map.put("大同市","113.3-40.08");
		map.put("阳泉市","113.57-37.85");
		map.put("长治市","113.12-36.2");
		map.put("晋城市","112.83-35.5");
		map.put("朔州市","112.43-39.33");
		map.put("晋中市","112.75-37.68");
		map.put("运城市","110.98-35.02");
		map.put("忻州市","112.73-38.42");
		map.put("临汾市","111.52-36.08");
		map.put("吕梁市","111.13-37.52");
		map.put("呼和浩特市","111.73-40.83");
		map.put("包头市","109.83-40.65");
		map.put("乌海市","106.82-39.67");
		map.put("赤峰市","118.92-42.27");
		map.put("通辽市","122.27-43.62");
		map.put("鄂尔多斯市","109.8-39.62");
		map.put("呼伦贝尔市","119.77-49.22");
		map.put("巴彦淖尔市","107.42-40.75");
		map.put("乌兰察布市","113.12-40.98");
		map.put("兴安盟","122.05-46.08");
		map.put("锡林郭勒盟","116.07-43.95");
		map.put("阿拉善盟","105.67-38.83");
		map.put("沈阳市","123.43-41.8");
		map.put("大连市","121.62-38.92");
		map.put("鞍山市","122.98-41.1");
		map.put("抚顺市","123.98-41.88");
		map.put("本溪市","123.77-41.3");
		map.put("丹东市","124.38-40.13");
		map.put("锦州市","121.13-41.1");
		map.put("营口市","122.23-40.67");
		map.put("阜新市","121.67-42.02");
		map.put("辽阳市","123.17-41.27");
		map.put("盘锦市","122.07-41.12");
		map.put("铁岭市","123.83-42.28");
		map.put("朝阳市","120.45-41.57");
		map.put("长春市","125.32-43.9");
		map.put("吉林市","126.55-43.83");
		map.put("四平市","124.35-43.17");
		map.put("辽源市","125.13-42.88");
		map.put("通化市","125.93-41.73");
		map.put("白山市","126.42-41.93");
		map.put("松原市","124.82-45.13");
		map.put("白城市","122.83-45.62");
		map.put("延边朝鲜族自治州","129.5-42.88");
		map.put("哈尔滨市","126.53-45.8");
		map.put("齐齐哈尔市","123.95-47.33");
		map.put("鸡西市","130.97-45.3");
		map.put("鹤岗市","130.27-47.33");
		map.put("双鸭山市","131.15-46.63");
		map.put("大庆市","125.03-46.58");
		map.put("伊春市","128.9-47.73");
		map.put("佳木斯市","130.37-46.82");
		map.put("七台河市","130.95-45.78");
		map.put("牡丹江市","129.6-44.58");
		map.put("黑河市","127.48-50.25");
		map.put("绥化市","126.98-46.63");
		map.put("大兴安岭地区","124.12-50.42");
		map.put("上海市","121.47-31.23");
		map.put("南京市","118.78-32.07");
		map.put("无锡市","120.3-31.57");
		map.put("徐州市","117.18-34.27");
		map.put("常州市","119.95-31.78");
		map.put("苏州市","120.58-31.3");
		map.put("南通市","120.88-31.98");
		map.put("连云港市","119.22-34.6");
		map.put("淮安市","119.02-33.62");
		map.put("盐城市","120.15-33.35");
		map.put("扬州市","119.4-32.4");
		map.put("镇江市","119.45-32.2");
		map.put("泰州市","119.92-32.45");
		map.put("宿迁市","118.28-33.97");
		map.put("杭州市","120.15-30.28");
		map.put("宁波市","121.55-29.88");
		map.put("温州市","120.7-28.0");
		map.put("嘉兴市","120.75-30.75");
		map.put("湖州市","120.08-30.9");
		map.put("绍兴市","120.57-30.0");
		map.put("金华市","119.65-29.08");
		map.put("衢州市","118.87-28.93");
		map.put("舟山市","122.2-30.0");
		map.put("台州市","121.43-28.68");
		map.put("丽水市","119.92-28.45");
		map.put("合肥市","117.25-31.83");
		map.put("芜湖市","118.38-31.33");
		map.put("蚌埠市","117.38-32.92");
		map.put("淮南市","117.0-32.63");
		map.put("马鞍山市","118.5-31.7");
		map.put("淮北市","116.8-33.95");
		map.put("铜陵市","117.82-30.93");
		map.put("安庆市","117.05-30.53");
		map.put("黄山市","118.33-29.72");
		map.put("滁州市","118.32-32.3");
		map.put("阜阳市","115.82-32.9");
		map.put("宿州市","116.98-33.63");
		map.put("巢湖市","117.87-31.6");
		map.put("六安市","116.5-31.77");
		map.put("亳州市","115.78-33.85");
		map.put("池州市","117.48-30.67");
		map.put("宣城市","118.75-30.95");
		map.put("福州市","119.3-26.08");
		map.put("厦门市","118.08-24.48");
		map.put("莆田市","119.0-25.43");
		map.put("三明市","117.62-26.27");
		map.put("泉州市","118.67-24.88");
		map.put("漳州市","117.65-24.52");
		map.put("南平市","118.17-26.65");
		map.put("龙岩市","117.03-25.1");
		map.put("宁德市","119.52-26.67");
		map.put("南昌市","115.85-28.68");
		map.put("景德镇市","117.17-29.27");
		map.put("萍乡市","113.85-27.63");
		map.put("九江市","116.0-29.7");
		map.put("新余市","114.92-27.82");
		map.put("鹰潭市","117.07-28.27");
		map.put("赣州市","114.93-25.83");
		map.put("吉安市","114.98-27.12");
		map.put("宜春市","114.38-27.8");
		map.put("抚州市","116.35-28.0");
		map.put("上饶市","117.97-28.45");
		map.put("济南市","116.98-36.67");
		map.put("青岛市","120.38-36.07");
		map.put("淄博市","118.05-36.82");
		map.put("枣庄市","117.32-34.82");
		map.put("东营市","118.67-37.43");
		map.put("烟台市","121.43-37.45");
		map.put("潍坊市","119.15-36.7");
		map.put("济宁市","116.58-35.42");
		map.put("泰安市","117.08-36.2");
		map.put("威海市","122.12-37.52");
		map.put("日照市","119.52-35.42");
		map.put("莱芜市","117.67-36.22");
		map.put("临沂市","118.35-35.05");
		map.put("德州市","116.3-37.45");
		map.put("聊城市","115.98-36.45");
		map.put("滨州市","117.97-37.38");
		map.put("牡丹区","115.43-35.25");
		map.put("郑州市","113.62-34.75");
		map.put("开封市","114.3-34.8");
		map.put("洛阳市","112.45-34.62");
		map.put("平顶山市","113.18-33.77");
		map.put("安阳市","114.38-36.1");
		map.put("鹤壁市","114.28-35.75");
		map.put("新乡市","113.9-35.3");
		map.put("焦作市","113.25-35.22");
		map.put("濮阳市","115.03-35.77");
		map.put("许昌市","113.85-34.03");
		map.put("漯河市","114.02-33.58");
		map.put("三门峡市","111.2-34.78");
		map.put("南阳市","112.52-33.0");
		map.put("商丘市","115.65-34.45");
		map.put("信阳市","114.07-32.13");
		map.put("周口市","114.65-33.62");
		map.put("驻马店市","114.02-32.98");
		map.put("武汉市","114.3-30.6");
		map.put("黄石市","115.03-30.2");
		map.put("十堰市","110.78-32.65");
		map.put("宜昌市","111.28-30.7");
		map.put("襄樊市","112.15-32.02");
		map.put("鄂州市","114.88-30.4");
		map.put("荆门市","112.2-31.03");
		map.put("孝感市","113.92-30.93");
		map.put("荆州市","112.23-30.33");
		map.put("黄冈市","114.87-30.45");
		map.put("咸宁市","114.32-29.85");
		map.put("随州市","113.37-31.72");
		map.put("恩施土家族苗族自治州","109.47-30.3");
		map.put("仙桃市","113.45-30.37");
		map.put("长沙市","112.93-28.23");
		map.put("株洲市","113.13-27.83");
		map.put("湘潭市","112.93-27.83");
		map.put("衡阳市","112.57-26.9");
		map.put("邵阳市","111.47-27.25");
		map.put("岳阳市","113.12-29.37");
		map.put("常德市","111.68-29.05");
		map.put("张家界市","110.47-29.13");
		map.put("益阳市","112.32-28.6");
		map.put("郴州市","113.02-25.78");
		map.put("永州市","111.62-26.43");
		map.put("怀化市","110.0-27.57");
		map.put("娄底市","112.0-27.73");
		map.put("湘西土家族苗族自治州","109.73-28.32");
		map.put("广州市","113.27-23.13");
		map.put("韶关市","113.6-24.82");
		map.put("深圳市","114.05-22.55");
		map.put("珠海市","113.57-22.27");
		map.put("汕头市","116.68-23.35");
		map.put("佛山市","113.12-23.02");
		map.put("江门市","113.08-22.58");
		map.put("湛江市","110.35-21.27");
		map.put("茂名市","110.92-21.67");
		map.put("肇庆市","112.47-23.05");
		map.put("惠州市","114.42-23.12");
		map.put("梅州市","116.12-24.28");
		map.put("汕尾市","115.37-22.78");
		map.put("河源市","114.7-23.73");
		map.put("阳江市","111.98-21.87");
		map.put("清远市","113.03-23.7");
		map.put("东莞市","113.75-23.05");
		map.put("中山市","113.38-22.52");
		map.put("潮州市","116.62-23.67");
		map.put("揭阳市","116.37-23.55");
		map.put("云浮市","112.03-22.92");
		map.put("南宁市","108.37-22.82");
		map.put("柳州市","109.42-24.33");
		map.put("桂林市","110.28-25.28");
		map.put("梧州市","111.27-23.48");
		map.put("北海市","109.12-21.48");
		map.put("防城港市","108.35-21.7");
		map.put("钦州市","108.62-21.95");
		map.put("贵港市","109.6-23.1");
		map.put("玉林市","110.17-22.63");
		map.put("百色市","106.62-23.9");
		map.put("贺州市","111.55-24.42");
		map.put("河池市","108.07-24.7");
		map.put("来宾市","109.23-23.73");
		map.put("崇左市","107.37-22.4");
		map.put("海口市","110.32-20.03");
		map.put("三亚市","109.5-18.25");
		map.put("五指山市","109.52-18.78");
		map.put("重庆市","106.55-29.57");
		map.put("成都市","104.07-30.67");
		map.put("自贡市","104.78-29.35");
		map.put("攀枝花市","101.72-26.58");
		map.put("泸州市","105.43-28.87");
		map.put("德阳市","104.38-31.13");
		map.put("绵阳市","104.73-31.47");
		map.put("广元市","105.83-32.43");
		map.put("遂宁市","105.57-30.52");
		map.put("内江市","105.05-29.58");
		map.put("乐山市","103.77-29.57");
		map.put("南充市","106.08-30.78");
		map.put("眉山市","103.83-30.05");
		map.put("宜宾市","104.62-28.77");
		map.put("广安市","106.63-30.47");
		map.put("达州市","107.5-31.22");
		map.put("雅安市","103.0-29.98");
		map.put("巴中市","106.77-31.85");
		map.put("资阳市","104.65-30.12");
		map.put("阿坝藏族羌族自治州","102.22-31.9");
		map.put("甘孜藏族自治州","101.97-30.05");
		map.put("凉山彝族自治州","102.27-27.9");
		map.put("贵阳市","106.63-26.65");
		map.put("六盘水市","104.83-26.6");
		map.put("遵义市","106.92-27.73");
		map.put("安顺市","105.95-26.25");
		map.put("铜仁地区","109.18-27.72");
		map.put("兴义市","104.9-25.08");
		map.put("毕节地区","105.28-27.3");
		map.put("黔东南苗族侗族自治州","107.97-26.58");
		map.put("黔南布依族苗族自治州","107.52-26.27");
		map.put("昆明市","102.72-25.05");
		map.put("曲靖市","103.8-25.5");
		map.put("玉溪市","102.55-24.35");
		map.put("保山市","99.17-25.12");
		map.put("昭通市","103.72-27.33");
		map.put("丽江市","100.23-26.88");
		map.put("墨江哈尼族自治县","101.68-23.43");
		map.put("临沧市","100.08-23.88");
		map.put("楚雄彝族自治州","101.55-25.03");
		map.put("红河哈尼族彝族自治州","103.4-23.37");
		map.put("文山壮族苗族自治州","104.25-23.37");
		map.put("西双版纳傣族自治州","100.8-22.02");
		map.put("大理白族自治州","100.23-25.6");
		map.put("德宏傣族景颇族自治州","98.58-24.43");
		map.put("怒江傈僳族自治州","98.85-25.85");
		map.put("迪庆藏族自治州","99.7-27.83");
		map.put("拉萨市","91.13-29.65");
		map.put("昌都地区","97.18-31.13");
		map.put("山南地区","91.77-29.23");
		map.put("日喀则地区","88.88-29.27");
		map.put("那曲地区","92.07-31.48");
		map.put("阿里地区","80.1-32.5");
		map.put("林芝地区","94.37-29.68");
		map.put("西安市","108.93-34.27");
		map.put("铜川市","108.93-34.9");
		map.put("宝鸡市","107.13-34.37");
		map.put("咸阳市","108.7-34.33");
		map.put("渭南市","109.5-34.5");
		map.put("延安市","109.48-36.6");
		map.put("汉中市","107.02-33.07");
		map.put("榆林市","109.73-38.28");
		map.put("安康市","109.02-32.68");
		map.put("商洛市","109.93-33.87");
		map.put("兰州市","103.82-36.07");
		map.put("嘉峪关市","98.27-39.8");
		map.put("金昌市","102.18-38.5");
		map.put("白银市","104.18-36.55");
		map.put("天水市","105.72-34.58");
		map.put("武威市","102.63-37.93");
		map.put("张掖市","100.45-38.93");
		map.put("平凉市","106.67-35.55");
		map.put("酒泉市","98.52-39.75");
		map.put("庆阳市","107.63-35.73");
		map.put("定西市","104.62-35.58");
		map.put("陇南市","104.92-33.4");
		map.put("临夏回族自治州","103.22-35.6");
		map.put("甘南藏族自治州","102.92-34.98");
		map.put("西宁市","101.78-36.62");
		map.put("海东地区","102.12-36.5");
		map.put("海北藏族自治州","100.9-36.97");
		map.put("黄南藏族自治州","102.02-35.52");
		map.put("海南藏族自治州","100.62-36.28");
		map.put("果洛藏族自治州","100.23-34.48");
		map.put("玉树藏族自治州","97.02-33.0");
		map.put("海西蒙古族藏族自治州","97.37-37.37");
		map.put("银川市","106.28-38.47");
		map.put("石嘴山市","106.38-39.02");
		map.put("吴忠市","106.2-37.98");
		map.put("固原市","106.28-36.0");
		map.put("中卫市","105.18-37.52");
		map.put("乌鲁木齐市","87.62-43.82");
		map.put("克拉玛依市","84.87-45.6");
		map.put("吐鲁番地区","89.17-42.95");
		map.put("哈密地区","93.52-42.83");
		map.put("昌吉回族自治州","87.3-44.02");
		map.put("博尔塔拉蒙古自治州","82.07-44.9");
		map.put("巴音郭楞蒙古自治州","86.15-41.77");
		map.put("阿克苏地区","80.27-41.17");
		map.put("阿图什市","76.17-39.72");
		map.put("喀什地区","75.98-39.47");
		map.put("和田地区","79.92-37.12");
		map.put("伊犁哈萨克自治州","81.32-43.92");
		map.put("塔城地区","82.98-46.75");
		map.put("阿勒泰地区","88.13-47.85");
		map.put("石河子市","86.03-44.3");



	}

}
