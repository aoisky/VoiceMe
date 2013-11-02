package me.imvoice.app;
/**
 * Launcher activity, used for UI debug now.
 * @author yudong, deenliu
 */


import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	private boolean sidebarFlag = false;
	private SidebarFragment sidebar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get action bar and setting attribute
		ActionBar actionBar = getActionBar();
		setActionBar(actionBar);
		
		//Default articles fragment to show on the main
		setDefaultArticles();
		//Usage of notificationMgr
		NotificationMgr notifyMgr = new NotificationMgr(this);
		notifyMsg(notifyMgr);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case android.R.id.home:
	    		setSideBar();
	    		return true;
	    		
	    	case R.id.action_login:
	    		Intent login = new Intent(this,LoginActivity.class);
	    		startActivity(login);
	    		return true;
	    		
	        case R.id.action_settings:
	        	Intent settings = new Intent(this,SettingsActivity.class);
	        	startActivity(settings);
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		
	}
	
	//A sample to use notificationMgr
	private void notifyMsg(NotificationMgr notifyMgr){

		Bundle args = new Bundle();
		args.putString("title", "Test");
		args.putString("content", "Test content");
		args.putInt("type", NotificationMgr.NEW_ARTICLE_NOTIFICATION);
		int notifyNum = notifyMgr.createNotify(args);
		notifyMgr.showNotify(notifyNum);
	}
	
	
	private void setActionBar(ActionBar actionBar){
		actionBar.setTitle("VoiceMe");

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_HOME);
		//Set the background of action Bar
		ColorDrawable background = new ColorDrawable(Color.parseColor("#00A9FF"));
		background.setAlpha(150);
		actionBar.setBackgroundDrawable(background);

		actionBar.show();
	}
	/**
	 * Show the articles on the main
	 */
	private void setDefaultArticles(){
		FragmentManager fragmentMgr = this.getFragmentManager();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();
		
		ArticleFragment articles = new ArticleFragment();
		
		transaction.add(R.id.article_container, articles);
		transaction.commit();
	}
	/**
	 * Set a side bar in the main activity by fragment
	 */
	private void setSideBar(){
		FragmentManager fragmentMgr = this.getFragmentManager();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();
		
		if(sidebarFlag == true){
			fragmentMgr.popBackStack();
			sidebarFlag = false;
			return;
		}
		
		sidebar = new SidebarFragment();

		
		transaction.replace(R.id.mainLayout, sidebar);
		
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.addToBackStack(null);
		transaction.commit();
		
		sidebarFlag = true;
		
	}
}
