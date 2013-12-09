package com.myschool.game.main;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.shopping.ShopChooserFragment;

public class GameActivity extends FragmentActivity {

	private MyApplication mMyApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		mMyApplication = (MyApplication) getApplicationContext();

		((TextView) findViewById(R.id.act_game_text_charname))
				.setText(mMyApplication.getCharNameAndType());

		DatabaseHelper databaseHelper = new DatabaseHelper(mMyApplication);
		databaseHelper.initializeDatabase();

	}

	public void onShopChooserButtonClick(View v) {
		DialogFragment dialog = new ShopChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "NoticeDialogFragment");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
