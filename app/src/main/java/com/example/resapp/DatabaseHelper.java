package com.example.resapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "resapp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RESERVATIONS = "reservations";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_GUESTS = "guests";
    private static final String COLUMN_REQUESTS = "requests";

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        createNotificationChannel();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESERVATIONS_TABLE =
                "CREATE TABLE " + TABLE_RESERVATIONS + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_DATE + " TEXT,"
                        + COLUMN_TIME + " TEXT,"
                        + COLUMN_GUESTS + " INTEGER,"
                        + COLUMN_REQUESTS + " TEXT"
                        + ")";
        db.execSQL(CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }


    // create reservation

    public long addReservation(String name, String date, String time, int guests, String requests) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_GUESTS, guests);
        values.put(COLUMN_REQUESTS, requests);

        long result = db.insert(TABLE_RESERVATIONS, null, values);
        db.close();

        if (result != -1) {
            sendNotification();
        }

        return result;
    }


    // view reservations

    public Cursor getAllReservations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RESERVATIONS, null);
    }


    // delete reservations
    public void deleteReservation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESERVATIONS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // noti function
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "reservations",
                    "Reservations",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("New reservation notifications");

            NotificationManager manager =
                    context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendNotification() {
        // permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                return; // Permission denied → exit safely
            }
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "reservations")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("New Reservation")
                        .setContentText("A new reservation has been made")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(context)
                .notify((int) System.currentTimeMillis(), builder.build());
    }
}
