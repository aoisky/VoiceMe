package me.imvoice.app;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class NewArticleActivity extends Activity {

	private EditText articleTitle;
	private EditText articleContent;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_article);
		// Show the Up button in the action bar.
		setupActionBar();
		articleTitle = (EditText) findViewById(R.id.editTitle);
		articleContent = (EditText) findViewById(R.id.editContent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		ColorDrawable background = new ColorDrawable(Color.parseColor("#00A9FF"));
		background.setAlpha(150);
		getActionBar().setBackgroundDrawable(background);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_article, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
			
	    case R.id.action_article_send:
	    	SQLHandler sql = new SQLHandler(this);
	    	Bundle bundle = new Bundle();
	    	bundle.putLong(SQLHandler.USER_ID, 1);
	    	bundle.putString(SQLHandler.ARTICLE_TITLE, articleTitle.getText().toString());
	    	bundle.putString(SQLHandler.ARTICLE_CONTENT, articleContent.getText().toString());
	    	sql.addArticle(bundle);
	    	sql.close();
	    	sql = null;
	    	this.setResult(RESULT_OK);
	    	this.finish();
	    	return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
