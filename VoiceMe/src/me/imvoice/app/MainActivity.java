package me.imvoice.app;
/**
 * Launcher activity
 * Use a swipe tab and fragments to show articles and sidebar
 * @author Yudong Yang
 */


import java.util.List;
import java.util.Locale;

import me.imvoice.app.ArticleFragment.myBundleArticleAdapter;
import me.imvoice.app.MyArticleActivity.DummySectionFragment;


import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity{
	private boolean sidebarFlag = false;
	private SidebarFragment sidebar;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mainViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.main_pageadapter);
		
		//Get action bar and setting attribute
		ActionBar actionBar = getActionBar();
		setActionBar(actionBar);
		
		mainViewPager = (ViewPager)findViewById(R.id.mainpager);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mainViewPager.setAdapter(mSectionsPagerAdapter);
		mSectionsPagerAdapter.notifyDataSetChanged();
		//Default articles fragment to show on the main
		//setDefaultArticles();
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
	    		
	    	case R.id.action_logout:
	    		Intent login = new Intent(this,LoginActivity.class);
	    		startActivity(login);
	    		finish();
	    		return true;
	    		
	        case R.id.action_settings:
	        	Intent settings = new Intent(this,SettingsActivity.class);
	        	startActivity(settings);
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	//A sample to use notificationMgr
	private void notifyMsg(NotificationMgr notifyMgr){

		Bundle args = new Bundle();
		args.putString("title", "New Headline");
		args.putString("content", "You got 5 new articles");
		args.putInt("type", NotificationMgr.NEW_ARTICLE_NOTIFICATION);
		int notifyNum = notifyMgr.createNotify(args);
		notifyMgr.showNotify(notifyNum);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				if(requestCode == SidebarFragment.POST_ARTICLE){
					if(resultCode == RESULT_OK){
					Log.d("MainActivity", "OnResult");
		    		  ViewPager viewPager = (ViewPager) findViewById(R.id.mainpager);
		    		  ArticleFragment friendFragment = (ArticleFragment)viewPager.getAdapter().instantiateItem(viewPager, 1);
		    		  ListView listView = (ListView) friendFragment.getView().findViewById(android.R.id.list);
		  	    	SQLHandler sql = new SQLHandler(this);
			    	List<Bundle> allArticles = sql.getAllArticlesByUserId(1);
			    	
			    	sql.close();
			    	if(listView.getAdapter() instanceof ArticleFragment.myBundleArticleAdapter){
			    		ArticleFragment.myBundleArticleAdapter adapter = (ArticleFragment.myBundleArticleAdapter)listView.getAdapter();
				    	adapter.clear();
				    	adapter.addAll(allArticles);
				    	adapter.notifyDataSetChanged();
				    	
			    	}else{
			    		ArticleFragment.myBundleArticleAdapter bundleArticleAdapter = friendFragment.new myBundleArticleAdapter(this, android.R.id.list, allArticles);
				        listView.setAdapter(bundleArticleAdapter);
				        bundleArticleAdapter.notifyDataSetChanged();
			    	}
					}
				}

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
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			switch(position){
				//Add different articles for different sections, now it's just show one article fragment
				case 0:
					ArticleFragment headlineFragment = new ArticleFragment();
					Bundle bundle = new Bundle();
					bundle.putBoolean("isHeadline", true);
					headlineFragment.setArguments(bundle);
					return headlineFragment;
				
				case 1:
						ArticleFragment friendFragment = new ArticleFragment();
						Bundle friendbundle = new Bundle();
						friendbundle.putBoolean("isHeadline", false);
						friendFragment.setArguments(friendbundle);
						return friendFragment;

				case 2:
					return new ArticleFragment();
				
			}
			
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
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

		transaction.replace(android.R.id.content, sidebar);
		transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.addToBackStack(null);
		transaction.commit();
		
		sidebarFlag = true;
		
	}
	
	//Add a dummy fragment to show something when no article
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_my_article_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(getArguments().getString("pageName"));
			return rootView;
		}
	}
}


