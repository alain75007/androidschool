package com.myschool.achat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myschool.achat.CharChooserFragment.CharTypeChooserListener;
import com.myschool.game.character.Warrior;
import com.myschool.game.character.Character;


public class MainActivity extends FragmentActivity implements CharTypeChooserListener {

    private static final String tag = "Alain";
	private String mCharType;
	private String mCharName;
	private MyApplication mMyApplication;

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
    
    public void onCharTypeChooserClick(View view) {
		DialogFragment dialog = new CharChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "CharTypeChooserDialogFragment");
    }
    
    // Called by Fragment
	@Override
	public void onFinishCharTypeChooser(int which) {
		// Change text in button
		Button view = (Button) findViewById(R.id.act_main_btn_char_chooser);
		CharSequence[] items = getResources().getStringArray(R.array.character_type_array);
		mCharType = (String) items[which];
		view.setText(items[which]);		
	}

    
    public void onCreateCharacterClick(View v) {
    	Intent intent = new Intent(this, GameActivity.class);
    	EditText charNameView = (EditText) findViewById(R.id.charname);
    	mCharName = charNameView.getText().toString();
       	if (mCharName.matches("")) {
    		Toast.makeText(this, "Le nom du persoonage est à renseigner", Toast.LENGTH_SHORT).show();
    	}
    	else {
    		if (mCharType == null) {
        		Toast.makeText(this, "Le type de personnage est à renseigner", Toast.LENGTH_SHORT).show();
    		}
    		else {
    			mMyApplication = (MyApplication) getApplication();
    			if (mCharType.matches("Guerrier")) {
        			mMyApplication.person = new Warrior(mCharName);

    				 //person = new Warrior(charname.getText().toString());
    			}
    			else {
    				mMyApplication.person = new Character(mCharName);
    			}
        	    	Bundle bundle = new Bundle();
        	    	bundle.putString("char_type", mCharType);
        	    	bundle.putString("char_name", mCharName);
        	    	intent.putExtra("game_bundle", bundle);
        			startActivity(intent);
    		}
    	}
    }

    
/*    public void onCreateCharacterClick(View v) {
    	Log.d("Alain", "Coucou");
    	EditText charname = (EditText) findViewById(R.id.charname);
    	Spinner chartype = (Spinner) findViewById(R.id.chartype);
		Context context = getApplicationContext();
    	if (charname.getText().toString().matches("")) {
    		Toast.makeText(context, "Le nom du persoonage est à renseigner", Toast.LENGTH_SHORT).show();
    	}
    	else {
    		if (chartype.getSelectedItem().toString().matches("")) {
        		Toast.makeText(context, "Le type de personnage est à renseigner", Toast.LENGTH_SHORT).show();
    		}
    		else {
    			// TODO améliorer ce code
    			MyApplication myApplication = (MyApplication) getApplication();
    			//Character person;getApplicationContext()
    			if (chartype.getSelectedItem().toString().matches("Guerrier")) {
        			myApplication.person = new Warrior(charname.getText().toString());

    				 //person = new Warrior(charname.getText().toString());
    			}
    			else {
    				myApplication.person = new Character(charname.getText().toString());
    			}
    			Intent intent = new Intent(this, ShopActivity.class);
    			
    			startActivity(intent);
    		}
    	}
    	
    }*/


}
