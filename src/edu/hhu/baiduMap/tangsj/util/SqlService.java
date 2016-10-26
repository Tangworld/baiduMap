package edu.hhu.baiduMap.tangsj.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.hhu.baiduMap.tangsj.domain.User;

public class SqlService {

	private Context context;

	public SqlService(Context context) {
		this.context = context;
	}

	public void savezc(String username, String password, String gender, String phone,
			String mail, String birthday, String area, String hobby,
			String motto) {
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		String sql = "insert into users (user,pwd,gen,phone,mail,birth,area,hobby,motto) " +
				"values('" + username + "','"+ password + "','" + gender + "','"+ phone + "','"+ mail
				+ "','"+ birthday + "','"+ area + "','"+ hobby + "','"+ motto + "')";
		database.execSQL(sql);
	}

	public List<User> findbysql(String sql) {
		List<User> list = new ArrayList<User>();
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person ", null);

		while (cursor.moveToNext()) {

			String username = cursor.getString(cursor.getColumnIndex("user"));
			String password = cursor.getString(cursor.getColumnIndex("pwd"));
			String gen = cursor.getString(cursor.getColumnIndex("gen"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			String mail = cursor.getString(cursor.getColumnIndex("mail"));
			String birth = cursor.getString(cursor.getColumnIndex("birth"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String hobby = cursor.getString(cursor.getColumnIndex("hobby"));
			String motto = cursor.getString(cursor.getColumnIndex("motto"));
			User personlist = new User(username, password,gen,phone,mail,birth,
					area,hobby,motto);
			list.add(personlist);
		}

		return list;

	}

}
