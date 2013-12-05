package com.myschool.game.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class StoreProvider extends ContentProvider {
	private StoreDatabaseHelper storeDatabaseHelper;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// Initialize your provider. The Android system calls this method
	// immediately after it creates your provider. Notice that your provider is
	// not created until a ContentResolver object tries to access it.
	public boolean onCreate() {
		storeDatabaseHelper = new StoreDatabaseHelper(null);
		return false;
	}

	@Override
	// Retrieve data from your provider. Use the arguments to select the table
	// to query, the rows and columns to return, and the sort order of the
	// result. Return the data as a Cursor object.
	public Cursor query(Uri uri, String[] projection, String selection,
		String[] selectionArgs, String sortOrder) {
		Cursor cursor = storeDatabaseHelper.selectAll(null);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
