package com.myschool.game.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Product;
import com.myschool.game.model.Shop;

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

		Shop shop;
		Product product;
		shop = new Shop("Kevin Weapons");
		databaseHelper.createShop(shop);

		product = new Product(shop, "Arc", "Armes", "Armes de projection",
				100, 10);
		databaseHelper.createProduct(product);

		product = new Product(shop, "Epée", "Armes", "Armes blanches", 80, 10);
		databaseHelper.createProduct(product);

		product = new Product(shop, "Poignard", "Armes", "Armes blanches", 50,
				10);
		databaseHelper.createProduct(product);

		shop = new Shop("Jules Wear");
		databaseHelper.createShop(shop);

		product = new Product(shop, "Armure", "Protection", "Armure", 120, 10);
		databaseHelper.createProduct(product);

		product = new Product(shop, "Casque", "Protection", "Armure", 120, 10);
		databaseHelper.createProduct(product);

		product = new Product(shop, "Jambière", "Protection", "Armure", 120,
				10);
		databaseHelper.createProduct(product);

		product = new Product(shop, "Cotte de mailles", "Protection",
				"Armure", 120, 10);
		databaseHelper.createProduct(product);

		databaseHelper.logAllShops();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
