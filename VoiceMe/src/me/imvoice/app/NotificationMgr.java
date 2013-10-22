package me.imvoice.app;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 
 * @author Yudong Yang
 * Manage multiple notifications, and update notifications by using this utility class
 */

public class NotificationMgr extends Service{

	NotificationManager mNotifyManager;
	public final int NEW_ARTICLE_NOTIFICATION = 1; 
	public final int NEW_VERSION_NOTIFICATION = 2;
	
	
	/**
	 * Obtain the notifyManager from context and create NotificationMgr object
	 * @param notifyManager
	 */
	public NotificationMgr(NotificationManager notifyManager){
		//Get notify Manager from context
		mNotifyManager = notifyManager;
		
	}
	
	/**
	 * Create a new notification
	 * @param notifyType
	 * @param title
	 * @param content
	 * @param extra
	 */
	public int createNotify(int notifyType, String title, String content, int extra){
		
		return 0;
	}
	
	/**
	 * Update a notification
	 * @param title
	 * @param content
	 * @param extra
	 */
	public void updateNotify(int notifyNum, String title, String content, int extra){
		
	}
	
	/**
	 * Remove a notification
	 * @param notifyNum
	 */
	public void removeNotify(int notifyNum){
		
	}
	
	public void showNotify(){
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
