package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageReservationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> reservations;
    private TextView tvPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rvReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvPlaceholder = findViewById(R.id.tvPlaceholder);

        reservations = new ArrayList<>();
        loadReservationsFromDB();

        adapter = new ReservationAdapter(reservations);
        recyclerView.setAdapter(adapter);

        if (reservations.isEmpty()) {
            recyclerView.setVisibility(RecyclerView.GONE);
            tvPlaceholder.setVisibility(TextView.VISIBLE);
        } else {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            tvPlaceholder.setVisibility(TextView.GONE);
        }
    }

    private void loadReservationsFromDB() {
        Cursor cursor = dbHelper.getAllReservations();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int guests = cursor.getInt(cursor.getColumnIndex("guests"));
                String requests = cursor.getString(cursor.getColumnIndex("requests"));

                reservations.add(new Reservation(name, date, time, guests, requests));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
