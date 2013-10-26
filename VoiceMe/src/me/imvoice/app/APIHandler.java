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
import org.json.simple.parser.JSONParser;

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
		
		obj.put("password", password);
		obj.put("username", username);
		
		
		String userAuthInfo = obj.toJSONString();
		Log.d(logTag,"JSON Auth Login Info: " + userAuthInfo);
		URL url;
		
		try {
			Log.d(logTag, "Start login connection");
			url = new URL(authURL);
		
			HttpURLConnection loginConnect = (HttpURLConnection)url.openConnection();
	        loginConnect.setReadTimeout(10000 /* milliseconds */);
	        loginConnect.setConnectTimeout(10000 /* milliseconds */);
	        loginConnect.setRequestProperty("METHOD", "checkLogin");
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
	        reader.close();
	        
	        Log.d(logTag,"User Info:" + userStr); //Log info for user
	        
	        JSONParser parser=new JSONParser();
	        
	        Object authJSON = parser.parse(userStr);
	        
	        JSONArray authArray = (JSONArray)authJSON;
	        JSONObject authInfo = (JSONObject)authArray.get(0);
	        
	        String auth = authInfo.get("auth").toString();
	        
	        if(auth.equals("No such user")){
	        	Log.d(logTag, "Auth: No such user, try to register an account" );
	        	loginConnect.disconnect();
	        	
	        	registerUser(username,password);
	        }
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(logTag, "Login Connection Exception");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static UserInfo registerUser(String username, String password){
		
		
		
		JSONObject obj = new JSONObject();
		
		obj.put("password", password);
		obj.put("username", username);
		
		
		String userAuthInfo = obj.toJSONString();
		Log.d(logTag,"JSON User Register Info: " + userAuthInfo);
		URL url;
		

		try {
			Log.d(logTag, "Start register connection");
			url = new URL(authURL);
		
			HttpURLConnection registerConnect = (HttpURLConnection)url.openConnection();
			registerConnect.setReadTimeout(10000 /* milliseconds */);
			registerConnect.setConnectTimeout(10000 /* milliseconds */);
			registerConnect.setRequestProperty("METHOD", "registerUser");
			registerConnect.setRequestMethod("POST");
			registerConnect.setDoInput(true);
			registerConnect.setDoOutput(true);
	        
	        OutputStream sendData = registerConnect.getOutputStream();
	        BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(sendData));
	        dataWriter.write(userAuthInfo);
	        dataWriter.close();
	        
	        registerConnect.connect();
	        
	        InputStream userData = registerConnect.getInputStream();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(userData));
	        String userStr = reader.readLine();
	        reader.close();
	        registerConnect.disconnect();
	        
	        Log.d(logTag,"Registered User Info:" + userStr); //Log info for user
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(logTag, "Register Connection Exception");
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
