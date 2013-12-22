package com.myschool.game.shop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.myschool.achat.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Product;

public class ProductDetailActivity extends Activity {

	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		mBundle = getIntent().getBundleExtra("game_bundle");
		int productId = mBundle.getInt("product_id");
		DatabaseHelper dbh = new DatabaseHelper(this);
		Product product = dbh.getProduct(productId);
		Log.d("Alain", "Product Id = " + product.getId());

		if (product.getId() == 0 ) {
			Toast.makeText(this, "No product", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else {
			((TextView) findViewById(R.id.act_product_detail_text_product_name)).setText(product.getName());
			((TextView) findViewById(R.id.act_product_detail_text_product_price)).setText("" + product.getPrice());
			((TextView) findViewById(R.id.act_product_detail_text_product_count)).setText("" + product.getCount());
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

}
