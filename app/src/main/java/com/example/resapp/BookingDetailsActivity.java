package com.example.resapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//test
import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String selectedDate, selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        dbHelper = new DatabaseHelper(this);

        selectedDate = getIntent().getStringExtra("selectedDate");
        selectedTime = getIntent().getStringExtra("selectedTime");

        EditText etName = findViewById(R.id.etName);
        EditText etGuests = findViewById(R.id.etGuests);
        EditText etRequests = findViewById(R.id.etRequests);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        TextView tvDate = findViewById(R.id.tvSelectedDate);
        TextView tvTime = findViewById(R.id.tvSelectedTime);
        tvDate.setText("Selected date: " + selectedDate);
        tvTime.setText("Selected time: " + selectedTime);

        btnConfirm.setOnClickListener(v -> {
            String name = etName.getText().toString();
            int guests = 1;

            try {
                guests = Integer.parseInt(etGuests.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid guest number, defaulting to 1.", Toast.LENGTH_SHORT).show();
            }

            String requests = etRequests.getText().toString();

            long result = dbHelper.addReservation(name, selectedDate, selectedTime, guests, requests);
            if (result != -1) {
                Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving reservation.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
