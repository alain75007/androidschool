package com.myschool.game.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database helper class contains all the methods to perform database operations
 * like opening connection, closing connection, insert, update, read, delete and
 * other things. As this class is helper class, place this under helper package.
 * 
 * @author alain
 * 
 */
public class databaseHelper extends SQLiteOpenHelper {

	/*
	 * Database Name
	 */
	private static final String DATABASE_NAME = "store.db";

	/*
	 * Database Version
	 */
	private static final int DATABASE_VERSION = 1;

	// Table Names
	private static final String TABLE_SHOP = "shops";
	private static final String TABLE_SHOP_PRODUCT = "shop_products";
	private static final String TABLE_PRODUCT = "products";
	private static final String TABLE_PRODUCT_CATEGORY = "product_categories";

	// Common column names
	private static final String KEY_ID = "id";

	// SHOPS Table - column names
	private static final String KEY_SHOP_NAME = "name";

	// SHOP_PRODUCTS table - column names
	private static final String KEY_SHOP_ID = "shop_id";
	private static final String KEY_PRODUCT_ID = "product_id";
	private static final String KEY_PRODUCT_COUNT = "count";
	private static final String KEY_PRODUCT_PRICE = "price";

	// PRODUCTS table - column names
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_CATEGORY_ID = "category_id";	
	
	// PRODUCTS_CATEGORIES - column names
	private static final String KEY_CATEGORY_NAME = "name";
	

	// Table Create
	private static final String CREATE_TABLE_SHOPS = "CREATE TABLE "
			+ TABLE_SHOP + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_SHOP_NAME + "TEXT" + ")";

	private static final String CREATE_TABLE_SHOP_PRODUCTS = "CREATE TABLE "
			+ TABLE_SHOP_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_SHOP_ID + " INTEGER," + KEY_PRODUCT_ID + "INTEGER"
			+ KEY_PRODUCT_COUNT + " INTEGER," + KEY_PRODUCT_PRICE + " INTEGER" + ")";


	private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "
			+ TABLE_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PRODUCT_NAME + " TEXT," + KEY_PRODUCT_CATEGORY_ID + " INTEGER" + ")";
	
	private static final String CREATE_TABLE_PRODUCT_CATEGORY = "CREATE TABLE "
			+ TABLE_PRODUCT_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CATEGORY_NAME + " TEXT" + ")";

	public databaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SHOPS);
		db.execSQL(CREATE_TABLE_SHOP_PRODUCTS);
		db.execSQL(CREATE_TABLE_PRODUCT_CATEGORY);
		db.execSQL(CREATE_TABLE_PRODUCTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	     // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP_PRODUCT);
 
        // create new tables
        onCreate(db);

	}

}
