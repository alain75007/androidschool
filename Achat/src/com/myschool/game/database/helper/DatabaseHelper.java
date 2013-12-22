package com.myschool.game.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myschool.game.model.Product;
import com.myschool.game.model.Store;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 4;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	// Table names
	private static final String TABLE_SHOP = "shops";
	private static final String TABLE_PRODUCT = "products";

	// Commun Columns name
	public static final String KEY_ID = "_id";

	// Table shops - columns name
	public static final String KEY_SHOP_NAME = "name";

	// Table products - columns name
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_SHOP_ID = "shop_id";
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
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ KEY_PRODUCT_NAME + " TEXT, " 
			+ KEY_PRODUCT_SHOP_ID + " INTEGER," 
			+ KEY_PRODUCT_PRICE + " INTEGER,"
			+ KEY_PRODUCT_COUNT + " INTEGER"
			+ ")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_PRODUCT);
		initializeDatabase(db);
	}
	private int insert(SQLiteDatabase db, String storeName) {
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, storeName);
		return (int) db.insert(TABLE_SHOP, null, values);
	}
	/**
	 * @param db SQLiteDatabase
	 * @param shopId
	 * @param productName
	 * @param price
	 * @param count : number of the product
	 * @return int : id of the product
	 */
	private int insert(SQLiteDatabase db, int shopId, String productName, int price, int count) {
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_SHOP_ID, shopId);
		values.put(KEY_PRODUCT_NAME, productName);
		values.put(KEY_PRODUCT_PRICE, price);
		values.put(KEY_PRODUCT_COUNT, count);
		return (int) db.insert(TABLE_PRODUCT, null, values);
	}

	private void initializeDatabase(SQLiteDatabase db) {
		int shopId;
		shopId = insert(db, "Jule Wear");
		insert(db, shopId, "Armure", 10000, 5);
		insert(db, shopId, "Casque", 5000, 5);
		insert(db, shopId, "Jambière", 20000, 5);
		insert(db, shopId, "Cote de mailles", 10000, 5);

		shopId = insert(db, "Ilan Weapons");
		insert(db, shopId, "Arc", 8000, 5);
		insert(db, shopId, "Epée", 5000, 5);
		insert(db, shopId, "Flèche", 10, 100);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We use DROP here, but you can use ALTER to change Structure 
		if (newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
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
	
	private Store getShop(Cursor cursor) {
		Store shop = new Store();
		shop.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		shop.setName(cursor.getString(cursor
				.getColumnIndex(KEY_SHOP_NAME)));
		return shop;
	}
	
	
	public Cursor getShops() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	
	public Store getShop(int shopId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP + " WHERE " + KEY_ID + " = " + shopId;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		return getShop(cursor);
	}
	public Cursor getProducts(int shopId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + KEY_PRODUCT_SHOP_ID + " = " + shopId;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		return cursor;
	}
	public Product getProduct(int productId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + KEY_ID + " = " + productId;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		Product product = new Product();
		product.setId(productId);
		product.setName(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME)));
		product.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_PRICE)));
		product.setCount(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_COUNT)));
		product.setShopId(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_SHOP_ID)));

		return product;
	}
}
