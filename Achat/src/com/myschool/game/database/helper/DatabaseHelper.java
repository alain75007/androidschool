package com.myschool.game.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myschool.game.model.Store;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 3;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	// Table names
	private static final String TABLE_SHOP = "shops";
	private static final String TABLE_PRODUCT = "products";
	private static final String TABLE_CATEGORY = "categories";
	private static final String TABLE_SHOP_PRODUCT = "shop_products";

	// Commun Columns name
	private static final String KEY_ID = "_id";

	// Table shops - columns name
	public static final String KEY_SHOP_NAME = "name";

	// Table products - columns name
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_CATEGORY_ID = "category_id";

	// Table categories - columns
	private static final String KEY_CATEGORY_NAME = "name";

	// Table shop_products - column
	private static final String KEY_SHOP_ID = "shop_id";
	private static final String KEY_PRODUCT_ID = "product_id";
	private static final String KEY_PRODUCT_COUNT = "count";
	private static final String KEY_PRODUCT_PRICE = "price";

	// Create table shops
	private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
			+ TABLE_SHOP + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SHOP_NAME + " TEXT"
			+ ")";

	// Create table products
	private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
			+ TABLE_PRODUCT + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_NAME
			+ " TEXT, " + KEY_CATEGORY_ID + " INTEGER" + ")";

	// Create table categories
	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
			+ TABLE_CATEGORY + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_NAME
			+ " TEXT" + ")";

	// Create table shop_products
	private static final String CREATE_TABLE_SHOP_PRODUCT = "CREATE TABLE "
			+ TABLE_SHOP_PRODUCT + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SHOP_ID
			+ " INTEGER," + KEY_PRODUCT_ID + " INTEGER," + KEY_PRODUCT_COUNT
			+ " INTEGER," + KEY_PRODUCT_PRICE + " INTEGER" + ")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_SHOP_PRODUCT);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We use DROP here, but you can use ALTER to change Structure 
		if (newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP_PRODUCT);
			this.onCreate(db);
		}

	}
	
	
	// SHOP CRUD
	
	public long createStore(Store shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, shop.getName());
		long shop_id = db.insert(TABLE_SHOP, null, values);
		return shop_id;
	}
	
	public Cursor getShops() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	
	public void initializeShopDatabase() {
		
	}

}
