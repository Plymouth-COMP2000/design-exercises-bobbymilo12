package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.util.Log;

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

        String userEmail = getIntent().getStringExtra("user_email");
        Toast.makeText(this, "Email: " + userEmail, Toast.LENGTH_LONG).show();
        Log.d("ManageReservation", "Querying reservations for email: " + userEmail);

        if (userEmail == null || userEmail.isEmpty()) {
            tvPlaceholder.setText("Please log in to view your reservations");
            tvPlaceholder.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
            return;
        }

        reservations = new ArrayList<>();

        loadReservationsFromDB(userEmail);

        adapter = new ReservationAdapter(reservations);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (reservations.isEmpty()) {
            recyclerView.setVisibility(RecyclerView.GONE);
            tvPlaceholder.setVisibility(TextView.VISIBLE);
        } else {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            tvPlaceholder.setVisibility(TextView.GONE);
        }
    }

    private void loadReservationsFromDB(String userEmail) {
        // use try-with-resources to ensure cursors are closed
        Cursor cursor = null;
        try {
            cursor = dbHelper.getUserReservations(userEmail);

            Toast.makeText(
                    this,
                    "Reservations found: " + (cursor != null ? cursor.getCount() : 0),
                    Toast.LENGTH_SHORT
            ).show();

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
                            cursor.getString(nameIndex),
                            cursor.getString(dateIndex),
                            cursor.getString(timeIndex),
                            cursor.getInt(guestsIndex),
                            cursor.getString(requestsIndex)
                    ));
                } while (cursor.moveToNext());
            }
        } catch (IllegalArgumentException e) {
            Log.e("ManageReservation", "Expected column missing in cursor: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
