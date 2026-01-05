package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaffReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> reservations;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservations);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        recyclerView = findViewById(R.id.reservationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        reservations = new ArrayList<>();

        loadAllReservations();

        adapter = new ReservationAdapter(
                this,
                reservations,
                true,
                dbHelper,
                null
        );

        recyclerView.setAdapter(adapter);
    }

    private void loadAllReservations() {
        Cursor cursor = dbHelper.getAllReservations();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {

                    int idIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID);
                    int emailIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL);
                    int nameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME);
                    int dateIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE);
                    int timeIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME);
                    int guestsIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GUESTS);
                    int requestsIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_REQUESTS);

                    do {
                        reservations.add(new Reservation(
                                cursor.getInt(idIndex),
                                cursor.getString(emailIndex),
                                cursor.getString(nameIndex),
                                cursor.getString(dateIndex),
                                cursor.getString(timeIndex),
                                cursor.getInt(guestsIndex),
                                cursor.getString(requestsIndex)
                        ));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
    }
}
