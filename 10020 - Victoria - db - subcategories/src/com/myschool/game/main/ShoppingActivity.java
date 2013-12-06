package com.myschool.game.main;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Product;
import com.myschool.game.model.Store;

public class ShoppingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		MyApplication myApplication = (MyApplication) getApplication();
		Toast.makeText(getApplicationContext(),
				"Nom du personnage " + myApplication.person.getName(),
				Toast.LENGTH_SHORT).show();

		// Initialisation de la database
		DatabaseHelper databaseHelper = new DatabaseHelper(myApplication);

		Store store;
		Product product;
		store = new Store("Kevin Weapons");
		databaseHelper.createStore(store);

		product = new Product(store, "Arc", "Armes", "Armes de projection",
				100, 10);
		databaseHelper.createProduct(product);

		product = new Product(store, "Epée", "Armes", "Armes blanches", 80, 10);
		databaseHelper.createProduct(product);

		product = new Product(store, "Poignard", "Armes", "Armes blanches", 50,
				10);
		databaseHelper.createProduct(product);

		store = new Store("Jules Wear");
		databaseHelper.createStore(store);

		product = new Product(store, "Armure", "Protection", "Armure", 120, 10);
		databaseHelper.createProduct(product);

		product = new Product(store, "Casque", "Protection", "Armure", 120, 10);
		databaseHelper.createProduct(product);

		product = new Product(store, "Jambière", "Protection", "Armure", 120,
				10);
		databaseHelper.createProduct(product);

		product = new Product(store, "Cotte de mailles", "Protection",
				"Armure", 120, 10);
		databaseHelper.createProduct(product);

		List<Store> storeList = databaseHelper.getAllStores();
		for (Store shop : storeList) {
			Log.d("Alain", "Shop " + shop.getName());
			List<Product> productList = databaseHelper.getAllProducts(shop);
			for (Product p : productList) {
				Log.d("Alain",
						"  Product " + p.getName() + " category: "
								+ p.getCategory() + " subCategory: "
								+ p.getSubCategory() + " price: "
								+ p.getPrice() + " count: " + p.getCount());

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
