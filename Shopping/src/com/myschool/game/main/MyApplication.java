package com.myschool.game.main;

import android.app.Application;

import com.myschool.game.character.Character;

public class MyApplication extends Application {
	static Character person;
	public static Character getPerson() {
		return person;
	}
	public static void setPerson(Character person) {
		MyApplication.person = person;
	}
}
