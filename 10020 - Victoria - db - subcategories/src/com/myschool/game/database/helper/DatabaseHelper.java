package com.myschool.game.database.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myschool.game.model.Product;
import com.myschool.game.model.Store;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 7;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Table names
	private static final String TABLE_SHOP = "shops";
	private static final String TABLE_PRODUCT = "products";

	// Commun Columns name
	private static final String KEY_ID = "id";

	// Table shops - columns name
	private static final String KEY_SHOP_NAME = "name";

	// Table products - columns name
	private static final String KEY_PRODUCT_SHOP_ID = "shop_id";
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_CATEGORY = "category";
	private static final String KEY_PRODUCT_SUBCATEGORY = "subcategory";
	private static final String KEY_PRODUCT_PRICE = "price";
	private static final String KEY_PRODUCT_COUNT = "count";

	// Create table shops
	private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
			+ TABLE_SHOP + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SHOP_NAME + " TEXT"
			+ ")";

	// Create table products
	private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
			+ TABLE_PRODUCT + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_SHOP_ID
			+ " INTEGER," + KEY_PRODUCT_NAME + " TEXT," + KEY_PRODUCT_CATEGORY
			+ " TEXT," + KEY_PRODUCT_SUBCATEGORY + " TEXT," + KEY_PRODUCT_PRICE
			+ " INTEGER," + KEY_PRODUCT_COUNT + " INTEGER" + ")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_PRODUCT);
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
	public int createStore(Store shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, shop.getName());
		int id = (int) db.insert(TABLE_SHOP, null, values);
		shop.setId(id);
		return id;
	}

	public Store getStore(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP + " WHERE id " + id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Store store = new Store();
		store.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		store.setName(cursor.getString(cursor.getColumnIndex(KEY_SHOP_NAME)));
		return store;
	}

	public List<Store> getAllStores() {
		List<Store> stores = new ArrayList<Store>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain", "count = " + cursor.getCount());
		if (cursor.moveToFirst()) {
			do {
				Store store = new Store();
				store.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				store.setName(cursor.getString(cursor
						.getColumnIndex(KEY_SHOP_NAME)));
				stores.add(store);
			} while (cursor.moveToNext());
		}
		return stores;
	}

	public int updateStore(Store shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, shop.getName());
		return db.update(TABLE_SHOP, values, KEY_ID + " = " + shop.getId(),
				null);
	}

	public void deleteStore(Store shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SHOP, KEY_ID + " = " + shop.getId(), null);
	}

	// PRODUCT CRUD
	public int createProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_SHOP_ID, product.getStoreId());
		values.put(KEY_PRODUCT_NAME, product.getName());
		values.put(KEY_PRODUCT_CATEGORY, product.getCategory());
		values.put(KEY_PRODUCT_SUBCATEGORY, product.getSubCategory());
		values.put(KEY_PRODUCT_PRICE, product.getPrice());
		values.put(KEY_PRODUCT_COUNT, product.getCount());
		int id = (int) db.insert(TABLE_PRODUCT, null, values);
		product.setId(id);
		return id;
	}

	public Product getProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE id " + id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return getProduct(cursor);
	}

	public List<Product> getAllProducts(Store shop) {
		List<Product> products = new ArrayList<Product>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shop.getId();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				products.add(getProduct(cursor));
			} while (cursor.moveToNext());
		}
		return products;
	}

	private Product getProduct(Cursor cursor) {
		Product product = new Product();
		product.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		product.setStore_id(cursor.getInt(cursor
				.getColumnIndex(KEY_PRODUCT_SHOP_ID)));
		product.setName(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_NAME)));
		product.setCategory(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_CATEGORY)));
		product.setSubCategory(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_SUBCATEGORY)));
		product.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_PRICE)));
		product.setCount(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_COUNT)));
		return product;
	}

	public int updateProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_NAME, product.getName());
		values.put(KEY_PRODUCT_SHOP_ID, product.getStoreId());
		values.put(KEY_PRODUCT_CATEGORY, product.getCategory());
		values.put(KEY_PRODUCT_SUBCATEGORY, product.getSubCategory());
		values.put(KEY_PRODUCT_PRICE, product.getPrice());
		values.put(KEY_PRODUCT_COUNT, product.getCount());

		return db.update(TABLE_PRODUCT, values,
				KEY_ID + " = " + product.getId(), null);
	}

	public void deleteProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCT, KEY_ID + " = " + product.getId(), null);
	}

}
