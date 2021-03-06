package me.imvoice.app;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * ArticleActivity - used to display a single article.
 * Child activity of MainActivity and parent activity of comment activity.
 * 
 * @author deenliu
 *
 */

public class ArticleActivity extends Activity {
	
	public static final String ARTICLE_TITLE = "articleTitle";
	public static final String ARTICLE_CONTENT = "articleContent";
	public static final String PIC_URL = "picUrl";
	
	private String title;
	private String articleTitle = null;
	private String articleContent = null;
	
	private ArticleContentFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_page);
		
		try {
			title = getIntent().getExtras().getString("title");
			articleTitle = getIntent().getExtras().getString(ARTICLE_TITLE);
			articleContent = getIntent().getExtras().getString(ARTICLE_CONTENT);
		}
		catch(Exception e) {
			title = "Article page";
		}
			
		ViewPager articleViewPager = (ViewPager)findViewById(R.id.article);
		ArticlePagerAdapter myPagerAdapter = new ArticlePagerAdapter(getFragmentManager());
		articleViewPager.setAdapter(myPagerAdapter);
		
		//Get action bar and setting attribute
		ActionBar actionBar = getActionBar();
		setActionBar(actionBar);
	}
	
	public class ArticlePagerAdapter extends FragmentPagerAdapter {

		public ArticlePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			fragment = new ArticleContentFragment();
			Bundle bundle = new Bundle();
			if(articleTitle != null){
				bundle.putString(ARTICLE_TITLE, articleTitle);
			}
			
			if(articleContent != null){
				bundle.putString(ARTICLE_CONTENT, articleContent);
			}
			
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 1;
		}
	}
	
	public void clickHandler(View target) {
		fragment.clickHandler(target);
	}
	
	private void setActionBar(ActionBar actionBar){
		actionBar.setTitle(title);
		
		// Set the background of action Bar
		ColorDrawable background = new ColorDrawable(Color.parseColor("#00A9FF"));
		background.setAlpha(150);
		actionBar.setBackgroundDrawable(background);

		actionBar.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    		
	        case R.id.action_settings:
	        	Intent settings = new Intent(this,SettingsActivity.class);
	        	startActivity(settings);
	            return true;
	            
	        case android.R.id.home:
	        	this.finish();
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
