package me.imvoice.app;


import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

/**
 * 
 * @author Yudong Yang
 * Manage multiple notifications, and update notifications by using this utility class
 */

public class NotificationMgr{
	
	
	Context context;
	NotificationManager mNotifyManager;
	public final int NEW_ARTICLE_NOTIFICATION = 1; 
	public final int NEW_VERSION_NOTIFICATION = 2;
	private ArrayList<Notification.Builder> notifys;
	private int notifyNum;
	
	/**
	 * Obtain the notifyManager from context and create NotificationMgr object
	 * @param notifyManager
	 */
	public NotificationMgr(Context context){
		this.context = context;
		//Get notify Manager from context
		mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;
		notifys = new ArrayList<Notification.Builder>();
		notifyNum = 0;
	}
	
	/**
	 * Create a new notification
	 * @param notifyType
	 * @param title
	 * @param content
	 * @param extra
	 */
	public int createNotify(Bundle args){
		String title;
		String content;
		int notifyType;
		Notification.Builder mBuilder;
		
		if(args != null){
			title = args.getString("title");
			content = args.getString("content");
			notifyType = args.getInt("type");
			
			if(notifyType == this.NEW_ARTICLE_NOTIFICATION){
				mBuilder = new Notification.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setContentText(content);
				
				notifys.add(mBuilder);
				this.notifyNum++;
			}
			
			if(notifyType == this.NEW_VERSION_NOTIFICATION){
				mBuilder = new Notification.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("New Version Found: " + title)
				.setContentText(content);
				notifys.add(mBuilder);
				this.notifyNum++;
			}
		}
		return notifyNum - 1;
	}
	
	/**
	 * Update a notification
	 * @param title
	 * @param content
	 * @param extra
	 */
	public void updateNotify(Bundle args){
		
	}
	
	/**
	 * Remove a notification from the notifys arrayList
	 * @param notifyNum
	 */
	public void removeNotify(int notifyNum){
		
	}
	
	/**
	 * Cancel a notification
	 * @param notifyNum
	 */
	public void cancelNotify(int notifyNum){
		
	}
	
	/**
	 * Show a notification from notifys arrayList
	 * @param notifyNum
	 */
	public void showNotify(int notifyNum){
		mNotifyManager.notify(notifyNum, notifys.get(notifyNum).getNotification());
	}

	
}
