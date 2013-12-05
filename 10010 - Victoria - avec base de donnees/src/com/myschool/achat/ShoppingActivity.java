package com.myschool.achat;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Category;
import com.myschool.game.model.Store;

public class ShoppingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		MyApplication myApplication = (MyApplication) getApplication();
		Toast.makeText(getApplicationContext(), "Nom du personnage " + myApplication.person.getName(), Toast.LENGTH_SHORT).show();
		
		// Initialisation de la database
		DatabaseHelper databaseHelper = new DatabaseHelper(myApplication);
		
		databaseHelper.createCategory("Weapon");
		databaseHelper.createCategory("Armor");

		databaseHelper.createCategory("Dress");
		databaseHelper.createCategory("Pant");
		List<Category> categoryList = databaseHelper.getAllCategories();

		for (Category category : categoryList) {
			Log.d("Alain", "Category " + category.getName());
		}


		Store store = new Store("Jules Wears");
		long id  = databaseHelper.createStore(store);
		Log.i("Alain", "Store id = " + store.getId() + " return id=" + id );
		
		store = new Store("Kevin Weapons");
		id  = databaseHelper.createStore(store);
		Log.i("Alain", "Store id = " + store.getId() + " return id=" + id );
		
		List<Store> storeList = databaseHelper.getAllStores();

		for (Store shop : storeList) {
			Log.d("Alain", "Shop " + shop.getName());
		}


		//Product product =  new Product("Bow", 10);
/*		StoreDatabaseHelper storeDatabaseHelper = new databaseHelper(myApplication);
		Cursor cursor = storeDatabaseHelper.selectAll(null);
		Log.i("Alain",  "Nomber of rows : " + cursor.getCount());

		while (cursor.moveToNext()) {
			Log.i("Alain","Name " +  cursor.getString(0));
		}*/
		//Log.i("Alain", toto);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
