package me.imvoice.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Local database
 * @author Yudong Yang
 *
 */
public class SQLHandler extends SQLiteOpenHelper{

	private static final String LOG_TAG = "SQLHandler";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "VoiceMeSQL.db";
	private static final String USER_TABLE_NAME = "users";
	private static final String ARTICLE_TABLE_NAME = "articles";
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "username";
	public static final String USER_EMAIL = "email";
	public static final String USER_AGE = "userage";
	public static final String USER_GENDER = "gender";
	public static final String USER_PASSWORD = "password";
	public static final String USER_AVATAR_PATH = "useravatarpath";
	public static final String USER_TIMESTAMP = "user_timestamp";
	
	public static final String ARTICLE_ID = "articleid";
	public static final String ARTICLE_TITLE = "articletitle";
	public static final String ARTICLE_CONTENT = "articlecontent";
	public static final String ARTICLE_TIMESTAMP = "article_timestamp";
	
	private static final String USER_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
	private static final String ARTICLE_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ARTICLE_TABLE_NAME;
	
	private static final String ARTICLE_TABLE_CREATE = "CREATE TABLE " + ARTICLE_TABLE_NAME
			+ " (" 
			+ USER_ID + INTEGER_TYPE + ","
			+ ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ ARTICLE_TITLE + TEXT_TYPE + ","
			+ ARTICLE_CONTENT + TEXT_TYPE + ","
			+ ARTICLE_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
			+ " )";
	

	private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME
			+ " (" 
			+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ USER_NAME + TEXT_TYPE + ","
			+ USER_EMAIL + TEXT_TYPE + ","
			+ USER_AGE + INTEGER_TYPE + ","
			+ USER_PASSWORD + TEXT_TYPE + ","
			+ USER_GENDER + INTEGER_TYPE + ","
			+ USER_AVATAR_PATH + TEXT_TYPE + ","
			+ USER_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
			+ " )";
	
	
	public SQLHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(USER_TABLE_CREATE);
		db.execSQL(ARTICLE_TABLE_CREATE);
		Log.d(LOG_TAG, "Created user and article tables in the database");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		Log.d(LOG_TAG, "Upgrade from version " + oldversion + " to " + newversion + ", Previous database will be deleted.");
        db.execSQL(USER_SQL_DELETE_ENTRIES);
        db.execSQL(ARTICLE_SQL_DELETE_ENTRIES);
        
        onCreate(db);
	}

	public long addArticle(Bundle bundle){
		if(bundle == null) 
			return -1;
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(USER_ID, bundle.getLong(USER_ID));
		values.put(ARTICLE_TITLE, bundle.getString(ARTICLE_TITLE));
		values.put(ARTICLE_CONTENT, bundle.getString(ARTICLE_CONTENT));

		long rowid = db.insert(ARTICLE_TABLE_NAME, null, values);
		Log.d(LOG_TAG, "An article added to the database: " + bundle.getString(ARTICLE_TITLE));

		return rowid;
	}
	
	public Bundle getArticleById(long rowid){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(ARTICLE_TABLE_NAME, new String[] { USER_ID, ARTICLE_ID, ARTICLE_TITLE, ARTICLE_CONTENT, ARTICLE_TIMESTAMP}, ARTICLE_ID + " = ?", new String[]{String.valueOf(rowid)}, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}else{
			return null;
		}
		
		Bundle bundle = new Bundle();
		bundle.putLong(USER_ID, cursor.getLong(0));
		bundle.putLong(ARTICLE_ID, cursor.getLong(1));
		bundle.putString(ARTICLE_TITLE, cursor.getString(2));
		bundle.putString(ARTICLE_CONTENT, cursor.getString(3));
		bundle.putString(ARTICLE_TIMESTAMP, cursor.getString(4));
		Log.d(LOG_TAG, "Article Id: " + rowid + "Article title: " + cursor.getString(2));
		return bundle;
	}
	
	public List<Bundle> getAllArticlesByUserId(long rowid){
		List<Bundle> articleList = new ArrayList<Bundle>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(ARTICLE_TABLE_NAME, new String[] { USER_ID, ARTICLE_ID, ARTICLE_TITLE, ARTICLE_CONTENT, ARTICLE_TIMESTAMP}, USER_ID + " = ?", new String[]{String.valueOf(rowid)}, null, null, null);
		
	       if (cursor.moveToFirst()) {
	            do {
	            	Bundle bundle = new Bundle();
	            	
	        		bundle.putLong(USER_ID, cursor.getLong(0));
	        		bundle.putLong(ARTICLE_ID, cursor.getLong(1));
	        		bundle.putString(ARTICLE_TITLE, cursor.getString(2));
	        		bundle.putString(ARTICLE_CONTENT, cursor.getString(3));
	        		bundle.putString(ARTICLE_TIMESTAMP, cursor.getString(4));	

	        		articleList.add(bundle);
	            } while (cursor.moveToNext());
	        }
	       
	       return articleList;
	}
	
		

}