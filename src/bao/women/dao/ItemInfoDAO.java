package bao.women.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bao.women.model.AppConstant;
import bao.women.model.ItemInfo;


public class ItemInfoDAO
{
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public ItemInfoDAO(Context context)
	{
		helper = new DBOpenHelper(context);
	}

	/**
	 * 
	 * @param tableName 要插入信息到哪个表
	 * @param ItemInfo 要插入的信息
	 */
	public void add(ItemInfo itemInfo,String tableName)
	{
		db = helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("id", itemInfo.getId());
		values.put("fromAndTime", itemInfo.getFromAndTime());
		values.put("titleString", itemInfo.getTitleString());
		values.put("titleImageName", itemInfo.getTitleImageName());
		values.put("contentHTMLName", itemInfo.getContentHTMLName());
		db.insert(tableName, "id", values);
	}
	
	/**
	 * 
	 * @param tableName 要插入信息到哪个表
	 * @param ItemInfo 要插入的信息
	 */
	public void add(List<ItemInfo> itemInfos,String tableName)
	{
		db = helper.getWritableDatabase();
		for (Iterator<ItemInfo> iterator = itemInfos.iterator(); iterator.hasNext();) {
			ItemInfo itemInfo = (ItemInfo) iterator.next();
			ContentValues values=new ContentValues();
			values.put("id", itemInfo.getId());
			values.put("fromAndTime", itemInfo.getFromAndTime());
			values.put("titleString", itemInfo.getTitleString());
			values.put("titleImageName", itemInfo.getTitleImageName());
			values.put("contentHTMLName", itemInfo.getContentHTMLName());
			db.insert(tableName, "id", values);
		}

	}


	/**
	 * 根据ID查找ItemInfo对象
	 * 
	 * @param id 要查找的id
	 * @param tableName 要查找的表的名称
	 * @return
	 */
	public ItemInfo find(int id,String tableName)
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.query(tableName, AppConstant.DATABASESTRING.ALLCOLUMNS, "id=?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor.moveToNext())
		{
			return new ItemInfo(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
		}
		return null;
	}

	/**
	 * 
	 * @param id 要删除的id
	 */
	public void detele(String tableName,Integer... ids)
	{
		if (ids.length > 0)
		{
			StringBuffer sb = new StringBuffer();
			String[] strPid=new String[ids.length];
			for (int i = 0; i < ids.length; i++)
			{
				sb.append('?').append(',');
				strPid[i]=String.valueOf(ids[i]);
			}
			sb.deleteCharAt(sb.length() - 1);
			db = helper.getWritableDatabase();
			db.delete(tableName, "id in ("+sb+")", strPid);
		}
	}

	/**
	 * 获取表中的指定对象
	 * @param start 开始的id
	 * @param count 结束的id
	 * @param tableName 表的名称
	 * @return List<ItemInfo>
	 */
	public List<ItemInfo> getScrollData(int start, int count,String tableName)
	{
		List<ItemInfo> itemInfos=new ArrayList<ItemInfo>();
		db = helper.getWritableDatabase();
		//按ID的降序排列
		Cursor cursor=db.query(tableName, AppConstant.DATABASESTRING.ALLCOLUMNS, null, null, null, null, "id desc",start+","+count);
		while (cursor.moveToNext())
		{
			itemInfos.add(new ItemInfo(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4)));
		}
		return itemInfos;
	}

	/**
	 * 获得数据库的指定表中的数据的个数
	 * @return
	 */
	public int getCount(String tableName)
	{
		db = helper.getWritableDatabase();
		//返回记录数
		Cursor cursor = db.query(tableName , new String[]{"count(*)"}, null, null, null, null, null);
		if (cursor.moveToNext())
		{
			return (int) cursor.getLong(0);
		}
		return 0;
	}
}
