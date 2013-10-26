package me.imvoice.example.classes;
/**
 * Example used for tab button (using actionbar).
 * @author deenliu
 */
import me.imvoice.app.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;

public class TabTester extends Activity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i=1; i <= 3; i++) {
			Tab tab = bar.newTab();
			tab.setText("Tab " + i);
			tab.setTabListener(this);
			bar.addTab(tab);
		}
		setContentView(R.layout.tab_tester);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_tester, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
