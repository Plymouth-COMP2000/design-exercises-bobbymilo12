package com.example.resapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
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

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rvReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvPlaceholder = findViewById(R.id.tvPlaceholder);


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        userEmail = getIntent().getStringExtra("user_email");
        Log.d("ManageReservation", "Querying reservations for email: " + userEmail);

        if (userEmail == null || userEmail.trim().isEmpty()) {
            tvPlaceholder.setText("Please log in to view your reservations");
            tvPlaceholder.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
            return;
        }

        reservations = new ArrayList<>();
        loadReservationsFromDB(userEmail);

        adapter = new ReservationAdapter(
                this,
                reservations,
                false,
                dbHelper,
                userEmail
        );

        recyclerView.setAdapter(adapter);

        if (reservations.isEmpty()) {
            recyclerView.setVisibility(RecyclerView.GONE);
            tvPlaceholder.setVisibility(TextView.VISIBLE);
        } else {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            tvPlaceholder.setVisibility(TextView.GONE);
        }
    }

    private void loadReservationsFromDB(String userEmail) {
        Cursor cursor = null;

        try {
            cursor = dbHelper.getUserReservations(userEmail);

            int count = (cursor != null) ? cursor.getCount() : 0;
            Toast.makeText(this, "Reservations found: " + count, Toast.LENGTH_SHORT).show();

            if (cursor == null) return;

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME);
                int dateIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE);
                int timeIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME);
                int guestsIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GUESTS);
                int requestsIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_REQUESTS);

                do {
                    reservations.add(new Reservation(
                            cursor.getInt(idIndex),
                            userEmail,
                            cursor.getString(nameIndex),
                            cursor.getString(dateIndex),
                            cursor.getString(timeIndex),
                            cursor.getInt(guestsIndex),
                            cursor.getString(requestsIndex)
                    ));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("ManageReservation", "Error loading reservations: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
        }
    }
}
