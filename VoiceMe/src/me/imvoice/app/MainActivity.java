package me.imvoice.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{

	Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(this);
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
		if(arg0.getId() == R.id.button1){
			Intent login = new Intent(MainActivity.this,LoginActivity.class);
			startActivity(login);
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
