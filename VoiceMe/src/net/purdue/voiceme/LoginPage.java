package net.purdue.voiceme;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;

public class LoginPage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_menu, menu);
		return true;
	}
	
	/**
	 * @author Yudong Yang
	 * 10/21/2013
	 * Check the network avaliablity and return network state
	 * @return boolean networkAvaliability
	 */
	private boolean isNetworkAvaliable(){
		ConnectivityManager connectMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo netInfo = connectMgr.getActiveNetworkInfo();
		
		if(netInfo != null && netInfo.isConnected()){
			return true;
		} else{
			return false;
		}
	}
	
}
