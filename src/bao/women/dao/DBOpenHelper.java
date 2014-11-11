package bao.women.dao;

import bao.women.model.AppConstant;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final int VERSION = 1;
	private static final String DBNAME = "bao_women.db";
	
	public DBOpenHelper(Context context)
	{
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.HYYM+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.FLZS+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.XFJZ+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.YSJT+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.ZSDL+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.FAVORITES+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
		db.execSQL("create table "+AppConstant.DATABASETABLENAME.TESTTABLE+" (id integer primary key,fromAndTime TEXT,titleString TEXT,titleImageName TEXT,contentHTMLName TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.i("TAG", "UpGrade!");
	}

}
