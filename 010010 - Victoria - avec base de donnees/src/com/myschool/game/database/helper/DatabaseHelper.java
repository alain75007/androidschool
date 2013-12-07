package com.myschool.game.database.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myschool.game.model.Category;
import com.myschool.game.model.Product;
import com.myschool.game.model.Store;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 1;

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
	private static final String KEY_ID = "id";

	// Table shops - columns name
	private static final String KEY_SHOP_NAME = "name";

	// Table products - columns name
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_CATEGORY_ID = "category_id";

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
			+ " TEXT, " + KEY_PRODUCT_CATEGORY_ID + " INTEGER" + ")";

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
		long id = db.insert(TABLE_SHOP, null, values);
		// TODO test if Activity Shop has id
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
	public long createProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_NAME, product.getName());
		long id = db.insert(TABLE_SHOP, null, values);
		return id;
	}

	public Product getProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP + " WHERE id " + id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return getProduct(cursor);
	}

	private Product getProduct(Cursor cursor) {
		Product product = new Product();
		product.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		product.setName(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_NAME)));
		product.setCategory_id(cursor.getColumnIndex(KEY_PRODUCT_CATEGORY_ID));
		product.setPrice(cursor.getColumnIndex(KEY_PRODUCT_PRICE));
		product.setCount(cursor.getColumnIndex(KEY_PRODUCT_COUNT));
		return product;
	}

	public int updateStore(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, product.getName());
		return db.update(TABLE_PRODUCT, values,
				KEY_ID + " = " + product.getId(), null);
	}

	public void deleteStore(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCT, KEY_ID + " = " + product.getId(), null);
	}

	// CRUD category
	public long createCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, category.getName());
		long id = db.insert(TABLE_CATEGORY, null, values);
		category.setId(id);
		return id;
	}

	/**
	 * Create a category object by name and insert it in the database
	 * 
	 * @param name
	 *            : name of the category
	 * @return Category category
	 * @author alain Alain Beauvois
	 * 
	 */
	public Category createCategory(String name) {
		Category category = new Category();
		category.setName(name);
		category.setId(this.createCategory(category));
		return category;
	}

	public Store getCategory(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_CATEGORY + " WHERE id " + id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Store store = new Store();
		store.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		store.setName(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));
		return store;
	}

	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_CATEGORY;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				Category category = new Category();
				category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				category.setName(cursor.getString(cursor
						.getColumnIndex(KEY_CATEGORY_NAME)));
				categories.add(category);
			} while (cursor.moveToNext());
		}
		return categories;
	}

	public int updateCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, category.getName());
		return db.update(TABLE_CATEGORY, values,
				KEY_ID + " = " + category.getId(), null);
	}

	public void deleteCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CATEGORY, KEY_ID + " = " + category.getId(), null);
	}

	public List<Product> getAllStoreProducts(Store shop) {
		List<Product> products = new ArrayList<Product>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				products.add(getProduct(cursor));
			} while (cursor.moveToNext());
		}
		return products;
	}

}
