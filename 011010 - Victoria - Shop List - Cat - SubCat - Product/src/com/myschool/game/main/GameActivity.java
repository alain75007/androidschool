package com.myschool.game.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.myschool.game.R;

public class GameActivity extends Activity {

	private MyApplication mMyApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		mMyApplication = (MyApplication) getApplicationContext();

		TextView view = (TextView) findViewById(R.id.act_game_text_charname);
		view.setText(mMyApplication.getCharNameAndType());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
