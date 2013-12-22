package com.myschool.achat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.myschool.achat.ShopChooserFragment.ShopChooserListener;
import com.myschool.game.shop.ShopActivity;

public class GameActivity extends FragmentActivity implements ShopChooserListener {

	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mBundle =   getIntent().getBundleExtra("game_bundle");
		TextView tv = (TextView) findViewById(R.id.act_game_text_charname_and_type);
		String text = mBundle.getString("char_name") + "(" + mBundle.getString("char_type") + ")";
		tv.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	
	public void onShopChooserBtnClick(View view) {
		DialogFragment dialog = new ShopChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "ShopChooserDialogFragment");
	}

	@Override
	public void onFinishCharTypeChooser(int shopId) {
		Intent intent = new Intent(this, ShopActivity.class);
		mBundle.putInt("shop_id", shopId);
		intent.putExtra("game_bundle", mBundle);
		startActivity(intent);
	}

}
