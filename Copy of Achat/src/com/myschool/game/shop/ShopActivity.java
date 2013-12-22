package com.myschool.game.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myschool.achat.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Store;

public class ShopActivity extends Activity {

	private Bundle mBundle;
	private ShopActivity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = getIntent().getBundleExtra("game_bundle");
		setContentView(R.layout.activity_shop);
		TextView tv = (TextView) findViewById(R.id.act_game_text_charname_and_type);
		String text = mBundle.getString("char_name") + "(" + mBundle.getString("char_type") + ")";
		
		// Set Store name
		DatabaseHelper dbh = new DatabaseHelper(this);
		Store shop = dbh.getShop(mBundle.getInt("shop_id"));
		tv.setText(text);
		tv = (TextView) findViewById(R.id.act_shop_text_shopname);
		tv.setText(shop.getName());
		
		mContext = this;
		Button button= (Button) findViewById(R.id.act_shop_btn_products);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Intent intent = new Intent(ShopActivity.this, ProductListActivity.class);
		    	intent.putExtra("game_bundle", mBundle);
		    	startActivity(intent);
		    }
		});
	}	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

}
