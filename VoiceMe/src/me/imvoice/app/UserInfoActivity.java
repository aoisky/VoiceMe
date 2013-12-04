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


		private UserInfo userInfo;
		private ImageView userIcon;
		private TextView nickname;
		private TextView age;
		private TextView email;
		private TextView gender;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_user_info);
			userInfo = APIHandler.readUserInfo(this);
			//Get two views by id
			userIcon = (ImageView)findViewById(R.id.user_headicon);
			nickname = (TextView) findViewById(R.id.user_nick);
			age = (TextView) findViewById(R.id.profile_age);
			email = (TextView) findViewById(R.id.profile_email);
			gender = (TextView) findViewById(R.id.profile_gender);
			
			//Get action bar and setting attribute
			ActionBar actionBar = getActionBar();
			setActionBar(actionBar);
			
			if(userInfo != null){
				//Set user avatar and username in the activity
				userIcon.setImageBitmap(userInfo.getUserAvatar());
				nickname.setText(userInfo.getUserName());
				age.setText("Age: " + userInfo.getAge());
				email.setText("Email: " + userInfo.getEmail());
				if(userInfo.getGender() == false){
					gender.setText("Male");
					gender.setTextColor(Color.BLUE);
				}else{
					gender.setText("Female");
					gender.setTextColor(Color.RED);
				}
			}
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
