package me.imvoice.app;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity class for comment page(s).
 * @author deenliu
 *
 */

public class CommentActivity extends Activity {

	// Assume using articleID to fetch comments of a specific article.
	// UserInfo used to add comment.
	// should be passed by ArticleActivity.
	private int articleID;
	private UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_page);
		
		//Get action bar and setting attribute
		ActionBar actionBar = getActionBar();
		setActionBar(actionBar);
		
		//Default articles fragment to show on the main
		setDefaultComment();
	}
	
	public void clickHandler(View target) {
		switch(target.getId()) {
		
			case R.id.comment_button:
	        	EditText et = (EditText)findViewById(R.id.comment_content);
	        	String comment = et.getText().toString();
	        	Toast.makeText(this, comment, Toast.LENGTH_LONG).show();
	        	break;
	        	
			default:
				break;
		
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
	
	/**
	 * Show the comments.
	 */
	private void setDefaultComment(){
		FragmentManager fragmentMgr = this.getFragmentManager();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();
		
		CommentFragment comments = new CommentFragment();
		
		transaction.add(R.id.comment_container, comments);
		transaction.commit();
	}
	
	private void setActionBar(ActionBar actionBar){
		actionBar.setTitle("Comments");

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
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}

}
