// Fichet Game.java
package com.myschool.game.core;

public class Game {
	private static Game instance = null;
	protected Game() {
	    // Exists only to defeat instantiation.
	}
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
}