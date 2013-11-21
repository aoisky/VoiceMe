package me.imvoice.app;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.os.*;
import android.view.*;
import android.widget.*;

/* Author Chenlin zhou
 * to display the user page
 * User's own page only currently
 */

public class UserInfoActivity extends Activity {


		// Assume using articleID to fetch comments of a specific article.
		// UserInfo used to add comment.
		// should be passed by ArticleActivity.
		private int articleID;
		private UserInfo userInfo;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_user_info);
			userInfo = (UserInfo)getIntent().getExtras().get("UserInfo");  //Get User Info data
			
			//Get action bar and setting attribute
			ActionBar actionBar = getActionBar();
			setActionBar(actionBar);
			

		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    	case android.R.id.home:
		    		this.finish();
		    		return true;
		    		
		        case R.id.action_settings:
		        	Intent settings = new Intent(this,SettingsActivity.class);
		        	startActivity(settings);
		            return true;

		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
		
		
		
		private void setActionBar(ActionBar actionBar){
			actionBar.setTitle("My VoiceMe");

			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_HOME);
			//Set the background of action Bar
			ColorDrawable background = new ColorDrawable(Color.parseColor("#00A9FF"));
			background.setAlpha(150);
			actionBar.setBackgroundDrawable(background);

			actionBar.show();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.user, menu);
			return true;
		}



}
