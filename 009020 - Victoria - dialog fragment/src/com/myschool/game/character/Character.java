package com.myschool.game.character;

import android.content.Context;
import com.myschool.game.main.MyApplication;


public class Character {
	private String name;

	/**
	 * Le nom du personnage
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	public Character(String name) {
		this.name = name;
	}

}
