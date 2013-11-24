package me.imvoice.app;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ArticleActivity - used to display a single article.
 * Child activity of MainActivity and parent activity of comment activity.
 * 
 * @author deenliu
 *
 */

public class ArticleActivity extends Activity {
	
	private String title;
	private String article_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_page);
		
		try {
			title = getIntent().getExtras().getString("title");
		}
		catch(Exception e) {
			title = "Untitled";
		}
		
		try {
			article_title = getIntent().getExtras().getString("article_title");
		}
		catch (Exception e) {
			article_title = "Untitled";
		}
			
		//Get action bar and setting attribute
		ActionBar actionBar = getActionBar();
		setActionBar(actionBar);
		setButtonColor();
		setDefaultArticleContent();
	}

	public void clickHandler(View target) {
		switch(target.getId()) {
		
			case R.id.like_btn:
	        	break;
	        	
			case R.id.comment_btn:
				Intent comment = new Intent(this, CommentActivity.class);
				startActivity(comment);
				break;
				
			case R.id.share_btn:
				break;
	        	
			default:
				break;
		
		}
	}
	
	private void setDefaultArticleContent(){
		FragmentManager fragmentMgr = this.getFragmentManager();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();
		
		ArticleContentFragment comments = new ArticleContentFragment();
		
		transaction.add(R.id.article_container, comments);
		transaction.commit();
	}
	
	private void setActionBar(ActionBar actionBar){
		actionBar.setTitle(title);
		
		// Set the background of action Bar
		ColorDrawable background = new ColorDrawable(Color.parseColor("#00A9FF"));
		background.setAlpha(150);
		actionBar.setBackgroundDrawable(background);

		actionBar.show();
	}
	
	private void setButtonColor(){
		Button like_btn = (Button)findViewById(R.id.like_btn);
		Button comment_btn = (Button)findViewById(R.id.comment_btn);
		Button share_btn = (Button)findViewById(R.id.share_btn);
		like_btn.setBackgroundResource(R.drawable.aritcle_btns);
		comment_btn.setBackgroundResource(R.drawable.aritcle_btns);
		share_btn.setBackgroundResource(R.drawable.aritcle_btns);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    		
	        case R.id.action_settings:
	        	Intent settings = new Intent(this,SettingsActivity.class);
	        	startActivity(settings);
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article, menu);
		return true;
	}

}
