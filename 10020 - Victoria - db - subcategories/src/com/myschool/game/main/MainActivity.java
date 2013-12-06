package com.myschool.game.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myschool.game.R;
import com.myschool.game.character.Character;
import com.myschool.game.character.Warrior;

public class MainActivity extends Activity {

    private static final String TAG = "Alain";

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
    
    public void onCreateCharacterClick(View v) {
    	Log.d(TAG, "Coucou");
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
    			Intent intent = new Intent(this, ShoppingActivity.class);
    			
    			startActivity(intent);
    		}
    	}
    	
    }
    
}
