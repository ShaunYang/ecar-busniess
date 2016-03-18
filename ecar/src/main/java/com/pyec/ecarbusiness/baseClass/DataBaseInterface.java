package com.pyec.ecarbusiness.baseClass;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DataBaseInterface
{
	public static DBOpenHelper dbOpenHelper;
	public static SQLiteDatabase db;
	private String _type;

	public DataBaseInterface(Context context, String type)
	{
		if (dbOpenHelper != null)
			dbOpenHelper.close();
		if (db != null)
			db.close();
		dbOpenHelper = new DBOpenHelper(context);
		this._type = type;
		openDb();
	}

	public static void closeOpenHelper()
	{
		if (dbOpenHelper != null)
		{
			dbOpenHelper.close();
		}
	}

	public static void closeDb()
	{
		db.close();
	}

	public void openDb()
	{
		try
		{
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex)
		{
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	
	
	/**************************************
	 * 保存到数据库
	 * 
	 * @param appData
	 ***************************************/

	public boolean saveCarData(String carDetailData)
	{
		boolean isSuccess = false;
		openDb();
		Cursor cursorSort = null;
		try {
			cursorSort = db.rawQuery("select * from car_detail_data where data_name = ?",new String[]{"carData"});
			if (cursorSort.moveToFirst() == false){
				db.execSQL("insert into car_detail_data(data_name , data_content) values (?,?)",
						new String[] {"carData" , carDetailData});
				}
			else
			{
				db.execSQL("update car_detail_data set data_content = ? where data_name = ?",
						new String[] {carDetailData ,"carData" });
			}
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		closeDb();
		return isSuccess;
	}

	
	public String getCarData(String sName) {
		String content = "";
		openDb();
		Cursor cursorSort = null;
		try {
			cursorSort = db.rawQuery("select data_content from car_detail_data where data_name = ?",new String[]{sName});
			if (cursorSort.moveToFirst() == false){
				closeDb();
				return "";
			}
			else
			{
				content = cursorSort.getString(cursorSort.getColumnIndex("data_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDb();
		return content;
	}
	
	
	public boolean delCarData(String sName) {
		openDb();
		boolean state = false;
		try {
			db.rawQuery("delete  from car_detail_data where data_name = ?",new String[]{sName});
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDb();
		return state;
	}
	

	/**
	 * 
	 * @return
	 */
	public boolean deleteSearchRecord(){
		openDb();
	    String sql = "DELETE FROM " + "search_history" +";";
	    db.execSQL(sql);
		closeDb();	
		return true;
	}
}
