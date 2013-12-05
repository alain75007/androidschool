package com.myschool.game.database;

import android.database.sqlite.SQLiteDatabase;

public class Store {
	private static final String CREATE_DATABASE = "CREATE TABLE store (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)";

	static void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DATABASE);
	}

	static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		if (newVersion > oldVersion ) {
			// TODO UPGRADE table
		}
	}
}
