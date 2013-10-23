package me.imvoice.app;


import java.util.ArrayList;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * 
 * @author Yudong Yang
 * Manage multiple notifications, and update notifications by using this utility class
 */

public class NotificationMgr{
	
	private final Context context;
	private final NotificationManager mNotifyManager;
	public final static int NEW_ARTICLE_NOTIFICATION = 1; 
	public final static int NEW_VERSION_NOTIFICATION = 2;
	public final static int NEW_MESSAGE_NOTIFICATION = 3;
	
	private ArrayList<NotificationCompat.Builder> notifys;
	private int notifyNum;
	
	/**
	 * Obtain the notifyManager from context and create NotificationMgr object
	 * @param notifyManager
	 */
	public NotificationMgr(Context context){
		this.context = context;
		//Get notify Manager from context
		mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;
		notifys = new ArrayList<NotificationCompat.Builder>();
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
		boolean autoCancel;
		NotificationCompat.Builder mBuilder;
		
		if(args != null){
			title = args.getString("title");
			content = args.getString("content");
			notifyType = args.getInt("type");
			autoCancel = args.getBoolean("auto",true);
			
			if(notifyType == NEW_ARTICLE_NOTIFICATION){
				mBuilder = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setContentText(content);
				
				Intent intent = new Intent(context,MainActivity.class);
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				stackBuilder.addParentStack(MainActivity.class);
				stackBuilder.addNextIntent(intent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				
				mBuilder.setContentIntent(resultPendingIntent);
				mBuilder.setAutoCancel(autoCancel);
				notifys.add(mBuilder);
				this.notifyNum++;
			}
			
			if(notifyType == NEW_VERSION_NOTIFICATION){
				
				mBuilder = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("VoiceMe New Version" + title)
				.setContentText(content);
				
				Intent intent = new Intent(context,MainActivity.class);
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				stackBuilder.addParentStack(MainActivity.class);
				stackBuilder.addNextIntent(intent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultPendingIntent);
				
				mBuilder.setAutoCancel(autoCancel);
				notifys.add(mBuilder);
				this.notifyNum++;
			}
			
			if(notifyType == NEW_MESSAGE_NOTIFICATION){
				int messageNum = args.getInt("messageNum", 1);
				mBuilder = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("VoiceMe" + title)
				.setContentText("You have new messages.")
				.setNumber(messageNum);
				
				Intent intent = new Intent(context,MainActivity.class);
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				stackBuilder.addParentStack(MainActivity.class);
				stackBuilder.addNextIntent(intent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultPendingIntent);
				
				mBuilder.setAutoCancel(autoCancel);
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
		int notifyNum;
		String title;
		String content;
		int type;
		
		if(args != null){
			notifyNum = args.getInt("notifyNum");
			title = args.getString("title");
			content = args.getString("content");
			type = args.getInt("type");
		}
	}
	
	/**
	 * Remove a notification from the notifys arrayList
	 * @param notifyNum
	 */
	public void removeNotify(int notifyNum){
		notifys.set(notifyNum, null);
	}
	
	/**
	 * Cancel a notification
	 * @param notifyNum
	 */
	public void cancelNotify(int notifyNum){
		NotificationCompat.Builder mBuilder;
		if((mBuilder = notifys.get(notifyNum))!= null){
		
		}
	}
	
	/**
	 * Show a notification from notifys arrayList
	 * @param notifyNum
	 */
	public void showNotify(int notifyNum){
		NotificationCompat.Builder builder;
		if((builder = notifys.get(notifyNum))!= null){
			mNotifyManager.notify(notifyNum, builder.build());  //In the new version, it will call build in this function, so it doesn't matter
		}
	}

	
}
