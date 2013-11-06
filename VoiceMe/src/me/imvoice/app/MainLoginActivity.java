package me.imvoice.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_login, menu);
		return true;
	}

}
