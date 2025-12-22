package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaffReservationsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservations);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.reservationsRecyclerView);

        reservations = new ArrayList<>();
        loadReservations();

        adapter = new ReservationAdapter(reservations, true, dbHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadReservations() {
        reservations.clear();

        Cursor cursor = dbHelper.getAllReservations();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reservations.add(new Reservation(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("guests")),
                        cursor.getString(cursor.getColumnIndexOrThrow("requests"))
                ));
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}
