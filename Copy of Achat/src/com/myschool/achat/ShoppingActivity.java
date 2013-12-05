package com.myschool.achat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.myschool.game.database.StoreDatabaseHelper;

public class ShoppingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		MyApplication myApplication = (MyApplication) getApplication();
		Toast.makeText(getApplicationContext(), "Nom du personnage " + myApplication.person.getName(), Toast.LENGTH_SHORT).show();

		//Product product =  new Product("Bow", 10);
		StoreDatabaseHelper storeDatabaseHelper = new StoreDatabaseHelper(myApplication);
		Cursor cursor = storeDatabaseHelper.selectAll(null);
		Log.i("Alain",  "Nomber of rows : " + cursor.getCount());

		while (cursor.moveToNext()) {
			Log.i("Alain","Name " +  cursor.getString(0));
		}
		//Log.i("Alain", toto);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
