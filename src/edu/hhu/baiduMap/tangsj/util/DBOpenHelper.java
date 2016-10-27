package edu.hhu.baiduMap.tangsj.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	/**
	 * 
	 * @param context �����Ķ���
	 * @param name    ��ݿ����
	 * @param factory   ���ù�
	 * @param version   ��ݿ�汾��
	 */
	public DBOpenHelper(Context context) {
		super(context, "baiduMap2.db", null, 1);
	}

	/**
	 * ������ݿ�
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		db.execSQL("DROP TABLE person");
		db.execSQL("CREATE TABLE users(user varchar(20),pwd varchar(20),gen varchar(20),phone varchar(20),mail varchar(20),birth varchar(20),area varchar(20),hobby varchar(20),motto varchar(20))");
//		db.execSQL("DROP TABLE file");
		db.execSQL("CREATE TABLE current(username varchar(20),password varchar(20),priority varchar(20))");
		}

	/**
	 * �޸���ݿ��ṹ�ķ���
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
