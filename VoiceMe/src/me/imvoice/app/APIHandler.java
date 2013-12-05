package me.imvoice.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Handle network connection and API transfer
 * @author Yudong Yang
 *
 */
public class APIHandler {
	private static final String userInfoStr = "UserInfoPref";
	private static final String logTag = "APIHandler";
	private static final String authURL = "http://imvoice.me/app_v2/functions.php";

	
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
		
		obj.put("user_password", password);
		obj.put("user_username", username);
		
		
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
	        
	        if(auth != null && auth.equals("No such user")){
	        	Log.d(logTag, "Auth: No such user, try to register an account" );
	        	loginConnect.disconnect();
	        	return null;
	        	
	        }
	        //If login succeed, fetch profile from server
	        if( auth != null && auth.equals("Authentication succeeded")){
	        	String returnedProfileStr = apiConnection("fetch_profile", userAuthInfo);
	        	Object profileJSON = parser.parse(returnedProfileStr);
		        JSONArray profileArray = (JSONArray)authJSON;
		        JSONObject profileInfo = (JSONObject)authArray.get(0);
		        
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
	
	/**
	 * Register a new user
	 * @param username
	 * @param password
	 * @return
	 */
	public static UserInfo registerUser(String username, String password){
	
		JSONObject obj = new JSONObject();
		
		obj.put("password", password);
		obj.put("username", username);
		
		
		String userAuthInfo = obj.toJSONString();
		Log.d(logTag,"JSON User Register Info: " + userAuthInfo);
		
		String returnInfo = apiConnection("registerUser", userAuthInfo);

		return null;
		
	}
	
	/**
	 * Fetch Article from the server
	 * @param uid
	 * @param pid
	 * @return
	 */
	public static String fetchArticle(String uid, String pid){
		JSONObject obj = new JSONObject();
		
		obj.put("uid", uid);
		obj.put("pid", pid);
		
		
		String userAuthInfo = obj.toJSONString();
		Log.d(logTag,"JSON Fetch Article Info: " + userAuthInfo);
		
		String returnInfo = apiConnection("fetch_content", userAuthInfo);

		return returnInfo;
		
	}
	/**
	 * Fetch comment from the server
	 * @param uid
	 * @param pid
	 * @param cid
	 * @return
	 */
	public static String fetchComment(String uid, String pid, String cid){
		JSONObject obj = new JSONObject();
		
		obj.put("uid", uid);
		obj.put("pid", pid);
		obj.put("cid", cid);
		
		String userAuthInfo = obj.toJSONString();
		Log.d(logTag,"JSON Fetch Comment Info: " + userAuthInfo);
		
		String returnInfo = apiConnection("fetch_comment", userAuthInfo);

		return returnInfo;
	}
	
	
	public static String checkVersion(){

		String returnInfo = apiConnection("fetch_version", null);

        JSONParser parser=new JSONParser();
        
        try {
			Object authJSON = parser.parse(returnInfo);
	        JSONArray authArray = (JSONArray)authJSON;
	        JSONObject versionInfo = (JSONObject)authArray.get(0);
	        return versionInfo.get("version").toString();
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	
	/**
	 * Connect to the server
	 * @param requestMethod
	 * @param JSONInfo
	 * @return JSON String
	 */
	private static String apiConnection(String requestMethod, String JSONInfo){
		URL url;
		try {
			Log.d(logTag, "Start API connection");
			url = new URL(authURL);
		
			HttpURLConnection registerConnect = (HttpURLConnection)url.openConnection();
			registerConnect.setReadTimeout(10000 /* milliseconds */);
			registerConnect.setConnectTimeout(10000 /* milliseconds */);
			registerConnect.setRequestProperty("METHOD", requestMethod);
			registerConnect.setRequestMethod("POST");
			registerConnect.setDoInput(true);
			registerConnect.setDoOutput(true);
	        
	        OutputStream sendData = registerConnect.getOutputStream();
	        BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(sendData));
	        if(JSONInfo != null)
	        	dataWriter.write(JSONInfo);
	        dataWriter.close();
	        
	        registerConnect.connect();
	        
	        InputStream userData = registerConnect.getInputStream();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(userData));
	        String userStr = reader.readLine();
	        reader.close();
	        registerConnect.disconnect();
	        
	        Log.d(logTag,"Returned JSON Info:" + userStr); //Log info for user
	        return userStr;
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(logTag, "API Connection Exception");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Create a new article on the server
	 * @param user
	 * @param articleInfo
	 * @return pid
	 */
	public static int createNewArticle(UserInfo user, Bundle articleInfo){

		if(articleInfo == null || user == null){
			//No article content or no userInfo
			return -1;
		}
		JSONObject contentObject = new JSONObject();
		String content = articleInfo.getString("content");
		
		if(content == null){
			//No content info in the bundle
			return -1;
		}
		
		contentObject.put("uid", user.getuid());
		contentObject.put("article_content", content);
		
		String articleStr = contentObject.toJSONString();
		Log.d(logTag, "Start posting article");
		
		String returnInfo = apiConnection("post_content", content);
		return 0;
	}
	
	
	/**
	 * Create new Comment
	 * @param user
	 * @param commentInfo
	 * @return
	 */
	public static int createNewComment(UserInfo user, Bundle commentInfo){
		if(user == null || commentInfo == null){
			return -1;
		}
		String comment = commentInfo.getString("comment");
		String rid = commentInfo.getString("rid");
		String pid = commentInfo.getString("pid");
		
		if(comment == null || rid == null || pid == null){
			return -1;
		}
		JSONObject commentObject = new JSONObject();
		
		commentObject.put("uid", user.getuid());
		commentObject.put("rid", rid);
		commentObject.put("pid", pid);
		commentObject.put("comment", comment);
		
		String commentStr = commentObject.toJSONString();
		Log.d(logTag, "Start posting comment");
		
		String returnInfo = apiConnection("post_comment", comment);
		
		return 0;
	}
	/**
	 * Save userInfo into preferences
	 * @param context
	 * @param userInfo
	 */
	public static void saveUserInfo(Context context, UserInfo userInfo){

		SharedPreferences userInfoPref = context.getSharedPreferences(userInfoStr, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userInfoPref.edit();
		editor.putString("UserName", userInfo.getUserName());
		editor.putInt("UID", userInfo.getuid());
		editor.putInt("Age", userInfo.getAge());
		editor.putString("Email", userInfo.getEmail());
		editor.putString("Password", userInfo.getmd5Password());
		editor.putBoolean("Gender", userInfo.getGender());
		editor.apply();
		try {
			FileOutputStream out = context.openFileOutput("UserAvatar.PNG", Context.MODE_PRIVATE);
			Bitmap userIcon = userInfo.getUserAvatar();
			userIcon.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * Read userInfo from preferences
	 * @param context
	 * @return userInfo
	 */
	public static UserInfo readUserInfo(Context context){

		SharedPreferences userInfoPref = context.getSharedPreferences(userInfoStr, Context.MODE_PRIVATE);
		try {
			FileInputStream in = context.openFileInput("UserAvatar.PNG");
			Bitmap userIcon = BitmapFactory.decodeStream(in);
			in.close();
			String userName = userInfoPref.getString("UserName","Not Login");
			int userAge = userInfoPref.getInt("Age", -1);
			int uid = userInfoPref.getInt("UID", -1);
			String email = userInfoPref.getString("Email", "NONE");
			String password = userInfoPref.getString("Password", "");
			boolean gender = userInfoPref.getBoolean("Gender", false);
			UserInfo userInfo = new UserInfo(userName, uid, userIcon, userAge, email, password, gender);
			return userInfo;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
