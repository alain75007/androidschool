package com.myschool.game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class StoreDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "store.db";
	private static final int database_version = 1;

	public StoreDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, database_version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Store.onCreate(db);
		initializeDatabase(db);
	}

	private void initializeDatabase(SQLiteDatabase db) {
		Log.d("Alain", "Initialize Database");
		ContentValues contentValues = new ContentValues(2);
		contentValues.put("name", "Bastien Store");
		db.insert("Store", null, contentValues );
		contentValues.put("name", "Jules Wears");
		db.insert("Store", null, contentValues );		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Store.onUpgrade(db, oldVersion, newVersion);
	}
	
	public Cursor selectAll(Uri uri) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = { "name" };
		Cursor cursor = db.query("Store", columns, null, null, null, null, null);
		//cursor.setNotificationUri(getContext().getContentResolver(),URI_PERSONS);
		return cursor;
	}

}
