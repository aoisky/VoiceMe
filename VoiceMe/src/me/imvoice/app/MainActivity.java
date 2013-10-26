package me.imvoice.app;
/**
 * Launcher activity, used for UI debug now.
 * @author yudong, deenliu
 */
import me.imvoice.example.classes.SiderbarTester;
import me.imvoice.example.classes.TabTester;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button loginButton = (Button) findViewById(R.id.login);
		loginButton.setOnClickListener(this);
		Button exitButton = (Button)findViewById(R.id.exit);
		exitButton.setOnClickListener(this);
		
		//Test use.
		Button testButton = (Button)findViewById(R.id.sidebar_test);
		testButton.setOnClickListener(this);
		Button tabsButton = (Button)findViewById(R.id.tabs_test);
		tabsButton.setOnClickListener(this);
		
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int buttonId = arg0.getId();
		switch(buttonId) {
			case R.id.login:
				Intent login = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(login);
				break;
			case R.id.sidebar_test:
				Intent sidebarTest = new Intent(this, SiderbarTester.class);
				startActivity(sidebarTest);
				break;
			case R.id.tabs_test:
				Intent tabsTest = new Intent(this, MainNotLoginActivity.class);
				startActivity(tabsTest);
				break;
			case R.id.exit:
				finish();
		}
	}
	
	//A sample to use notificationMgr
	private void notifyMsg(NotificationMgr notifyMgr){

		Bundle args = new Bundle();
		args.putString("title", "Test");
		args.putString("content", "Test content");
		args.putInt("type", NotificationMgr.NEW_ARTICLE_NOTIFICATION);
		int notifyNum = notifyMgr.createNotify(args);
		notifyMgr.showNotify(notifyNum);
	}

}
