package com.myschool.game.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myschool.game.R;
import com.myschool.game.character.Character;
import com.myschool.game.character.Warrior;
import com.myschool.game.fragments.CharTypeChooserFragment;
import com.myschool.game.fragments.CharTypeChooserFragment.CharTypeChooserListener;

public class MainActivity extends FragmentActivity implements
		CharTypeChooserListener {

	private static final String tag = "Alain";
	private int mCharTypeNum = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onCharTypeButtonClick(View v) {
		DialogFragment dialog = new CharTypeChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "NoticeDialogFragment");
	}

	public void onCreateCharacterClick(View v) {
		Log.d(tag, "Coucou");
		EditText viewCharname = (EditText) findViewById(R.id.charname);
		String charname = viewCharname.getText().toString();
		Context context = getApplicationContext();
		if (charname.matches("")) {
			Toast.makeText(getApplicationContext(),
					"Le nom du personnage est a renseigner", Toast.LENGTH_SHORT)
					.show();
		} else {
			// TODO améliorer ce code
			switch (mCharTypeNum) {
			case 0:
				MyApplication.setPerson((Character) new Character(charname));
				break;

			case 1:
				MyApplication.setPerson((Character) new Warrior(charname));
				break;
			default:
				Toast.makeText(getApplicationContext(),
						"Le nom du personnage est a renseigner",
						Toast.LENGTH_SHORT).show();
				break;
			}
			Intent intent = new Intent(this, ShoppingActivity.class);
			startActivity(intent);
		}

	}

	public void onFinishCharTypeChooser(int which) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "" + which, Toast.LENGTH_SHORT).show();
		this.mCharTypeNum = which;
	}

}
