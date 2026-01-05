package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditReservationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private int reservationId = -1;
    private String userEmail = null;

    private EditText etName, etDate, etTime, etGuests, etRequests;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etResName);
        etDate = findViewById(R.id.etResDate);
        etTime = findViewById(R.id.etResTime);
        etGuests = findViewById(R.id.etResGuests);
        etRequests = findViewById(R.id.etResRequests);
        btnSave = findViewById(R.id.btnSaveReservation);

        reservationId = getIntent().getIntExtra("reservation_id", -1);
        userEmail = getIntent().getStringExtra("user_email");

        if (reservationId == -1 || TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Invalid reservation / user", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (!loadReservationIfOwnedByUser(reservationId, userEmail)) {
            Toast.makeText(this, "You can only edit your own reservations.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnSave.setOnClickListener(v -> saveChanges());
    }

    private boolean loadReservationIfOwnedByUser(int id, String userEmail) {
        Cursor cursor = dbHelper.getReservationById(id);
        if (cursor == null) return false;

        try {
            if (!cursor.moveToFirst()) return false;

            String ownerEmail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL));
            if (ownerEmail == null || !ownerEmail.equalsIgnoreCase(userEmail)) {
                return false;
            }

            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
            etDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE)));
            etTime.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME)));
            etGuests.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GUESTS))));
            etRequests.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_REQUESTS)));

            return true;

        } finally {
            cursor.close();
        }
    }

    private void saveChanges() {
        String name = etName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String guestsStr = etGuests.getText().toString().trim();
        String requests = etRequests.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time) || TextUtils.isEmpty(guestsStr)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int guests;
        try {
            guests = Integer.parseInt(guestsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Guests must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        int rows = dbHelper.updateReservationForUser(
                reservationId,
                userEmail,
                name,
                date,
                time,
                guests,
                requests
        );

        if (rows > 0) {
            Toast.makeText(this, "Reservation updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed (not your reservation?)", Toast.LENGTH_SHORT).show();
        }
    }
}
