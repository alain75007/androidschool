package com.myschool.achat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class ShoppingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		MyApplication myApplication = (MyApplication) getApplication();
		Toast.makeText(getApplicationContext(), "Nom du personnage " + myApplication.person.getName(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
