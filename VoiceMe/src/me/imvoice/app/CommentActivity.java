package me.imvoice.app;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

/**
 * Activity class for comment page(s).
 * @author deenliu
 *
 */

public class CommentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_page);
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
//	private void setSideBar(){
//		FragmentManager fragmentMgr = this.getFragmentManager();
//		FragmentTransaction transaction = fragmentMgr.beginTransaction();
//		
//		if(sidebarFlag == true){
//			fragmentMgr.popBackStack();
//			sidebarFlag = false;
//			return;
//		}
//		
//		sidebar = new SidebarFragment();
//
//		
//		transaction.replace(R.id.mainLayout, sidebar);
//		transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
//		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//		transaction.addToBackStack(null);
//		transaction.commit();
//		
//		sidebarFlag = true;
//		
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}

}
