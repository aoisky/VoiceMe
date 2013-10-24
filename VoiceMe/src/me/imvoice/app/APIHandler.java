package me.imvoice.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import org.json.simple.*;

/**
 * Handle network connection and API transfer
 * @author Yudong Yang
 *
 */
public class APIHandler {

	private static final String logTag = "APIHandler";
	private static final String authURL = "http://puuca.org/app_conn/auth.php";

	
	private APIHandler(){
		
	}
	
	/**
	 * Log into user account and return user information into the UserInfo object
	 * @param username
	 * @param password
	 * @return UserInfo object
	 */
	public static UserInfo authLogin(String username, String password){
		JSONObject obj = new JSONObject();
		
		obj.put("username", username);
		obj.put("password", password);
		
		String userAuthInfo = obj.toJSONString();
		URL url;
		
		//If the device is not connected to the Internet
		/*
		 * Check the network Availability outside the function
		if(!isNetworkAvaliable()){
			Log.d(logTag, "No network Available");
			return null;
		}
		*/
		try {
			Log.d(logTag, "Start login connection");
			url = new URL(authURL);
		
			HttpURLConnection loginConnect = (HttpURLConnection)url.openConnection();
	        loginConnect.setReadTimeout(10000 /* milliseconds */);
	        loginConnect.setConnectTimeout(10000 /* milliseconds */);
	        loginConnect.setRequestMethod("POST");
	        loginConnect.setDoInput(true);
	        loginConnect.setDoOutput(true);
	        
	        OutputStream sendData = loginConnect.getOutputStream();
	        BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(sendData));
	        dataWriter.write(userAuthInfo);
	        dataWriter.close();
	        loginConnect.connect();
	        
	        InputStream userData = loginConnect.getInputStream();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(userData));
	        String userStr = reader.readLine();
	        
	        Log.d(logTag,"User Info:" + userStr); //Log info for user
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Debug", "Login Connection Exception");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Utility function
	 * Check the network availability of this device
	 * @return network availability
	 */
	public static boolean isNetworkAvaliable(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	public static int createNewArticle(UserInfo user, Bundle articleInfo){
		
		return 0;
	}
	
	public static int createNewComment(UserInfo user, Bundle commentInfo){
		
		
		return 0;
	}
}
