package com.example.resapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "resapp.db";
    private static final int DB_VERSION = 3;

    //reservations
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String COL_ID = "id";
    public static final String COL_EMAIL = "email";
    public static final String COL_NAME = "name";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_GUESTS = "guests";
    public static final String COL_REQUESTS = "requests";

    // users
    public static final String TABLE_USERS = "users";
    public static final String USER_COL_ID = "id";
    public static final String USER_COL_EMAIL = "email";
    public static final String USER_COL_PASSWORD = "password";
    public static final String USER_COL_FULLNAME = "fullname";

    // menu
    public static final String TABLE_MENU = "menu";
    public static final String MENU_ID = "id";
    public static final String MENU_NAME = "name";
    public static final String MENU_PRICE = "price";
    public static final String MENU_ALLERGENS = "allergens";
    public static final String MENU_IMAGE = "image_uri";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // reservation table
        db.execSQL("CREATE TABLE " + TABLE_RESERVATIONS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EMAIL + " TEXT, "
                + COL_NAME + " TEXT, "
                + COL_DATE + " TEXT, "
                + COL_TIME + " TEXT, "
                + COL_GUESTS + " INTEGER, "
                + COL_REQUESTS + " TEXT)");

        // user table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_COL_EMAIL + " TEXT UNIQUE, "
                + USER_COL_PASSWORD + " TEXT, "
                + USER_COL_FULLNAME + " TEXT)");

        // menu table
        db.execSQL("CREATE TABLE " + TABLE_MENU + " ("
                + MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MENU_NAME + " TEXT, "
                + MENU_PRICE + " REAL, "
                + MENU_ALLERGENS + " TEXT, "
                + MENU_IMAGE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        Log.i("DatabaseHelper", "Upgrading DB " + oldV + " → " + newV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldV, int newV) {
        onUpgrade(db, oldV, newV);
    }

    public Cursor getUserReservations(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_RESERVATIONS,
                new String[]{COL_ID, COL_NAME, COL_DATE, COL_TIME, COL_GUESTS, COL_REQUESTS},
                COL_EMAIL + "=?",
                new String[]{email},
                null, null,
                COL_DATE + " ASC"
        );
    }

    public long addReservation(String email, String name, String date, String time, int guests, String requests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, email);
        cv.put(COL_NAME, name);
        cv.put(COL_DATE, date);
        cv.put(COL_TIME, time);
        cv.put(COL_GUESTS, guests);
        cv.put(COL_REQUESTS, requests);
        return db.insert(TABLE_RESERVATIONS, null, cv);
    }

    public int deleteReservation(int id) {
        return getWritableDatabase().delete(
                TABLE_RESERVATIONS, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllReservations() {
        return getReadableDatabase().query(TABLE_RESERVATIONS,
                null, null, null, null, null, COL_DATE + " ASC");
    }

    public long addUser(String email, String password, String fullName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_COL_EMAIL, email);
        cv.put(USER_COL_PASSWORD, password);
        cv.put(USER_COL_FULLNAME, fullName);
        return db.insertWithOnConflict(TABLE_USERS, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public long addMenuItem(String name, double price, String allergens, String imageUri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MENU_NAME, name);
        cv.put(MENU_PRICE, price);
        cv.put(MENU_ALLERGENS, allergens);
        cv.put(MENU_IMAGE, imageUri);
        return db.insert(TABLE_MENU, null, cv);
    }

    public Cursor getAllMenuItems() {
        return getReadableDatabase().query(
                TABLE_MENU, null, null, null, null, null, MENU_NAME + " ASC");
    }

    public Cursor getMenuItemById(int id) {
        return getReadableDatabase().query(
                TABLE_MENU, null, MENU_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
    }

    public int updateMenuItem(int id, String name, double price, String allergens, String imageUri) {
        ContentValues cv = new ContentValues();
        cv.put(MENU_NAME, name);
        cv.put(MENU_PRICE, price);
        cv.put(MENU_ALLERGENS, allergens);
        cv.put(MENU_IMAGE, imageUri);

        return getWritableDatabase().update(
                TABLE_MENU, cv, MENU_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteMenuItem(int id) {
        return getWritableDatabase().delete(
                TABLE_MENU, MENU_ID + "=?", new String[]{String.valueOf(id)});
    }

    public boolean isMenuEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MENU, null);
        boolean empty = true;
        if (c.moveToFirst()) {
            empty = c.getInt(0) == 0;
        }
        c.close();
        return empty;
    }

    public void seedMenuIfEmpty() {
        if (!isMenuEmpty()) return;

        addMenuItem("Double Cheese Bacon Burger", 12.99, "Dairy, Gluten", null);
        addMenuItem("Pepperoni Pizza", 12.99, "Dairy, Gluten", null);
        addMenuItem("Spaghetti Bolognese", 9.50, "Gluten", null);
        addMenuItem("Chicken Curry", 13.00, "Dairy", null);
        addMenuItem("Cheesy Chips", 4.50, "Dairy", null);
    }

}
