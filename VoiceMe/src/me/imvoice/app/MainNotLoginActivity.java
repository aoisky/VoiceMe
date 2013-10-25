package me.imvoice.app;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainNotLoginActivity extends Activity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Voice Me");
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab newsTab = bar.newTab();
		newsTab.setText("News");
		newsTab.setTabListener(this);
		bar.addTab(newsTab);
		
		Tab recomandTab = bar.newTab();
		recomandTab.setText("Recommand");
		recomandTab.setTabListener(this);
		bar.addTab(recomandTab);
		
		Tab discoverTab = bar.newTab();
		discoverTab.setText("Discover");
		discoverTab.setTabListener(this);
		bar.addTab(discoverTab);
		
		setContentView(R.layout.tab_tester);
		setContentView(R.layout.main_not_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_not_login, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch(itemId) {
			case R.id.menu_login:
				Intent login = new Intent(this, LoginActivity.class);
				startActivity(login);
				return true;
			case R.id.menu_signup:
				return true;
		}
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
