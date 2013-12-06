package com.myschool.game.main;

import android.app.Application;

import com.myschool.game.character.Character;
import com.myschool.game.character.Warrior;

// J'étends la classe Application pour stocker des variables "globales" à toutes mes activités
public class MyApplication  extends Application {
	public Character person;
}
