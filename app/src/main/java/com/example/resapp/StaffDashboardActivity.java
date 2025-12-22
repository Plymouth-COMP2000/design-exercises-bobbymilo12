package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        Button btnViewReservations = findViewById(R.id.btnViewReservations);

        btnViewReservations.setOnClickListener(v -> {
            Intent intent = new Intent(
                    StaffDashboardActivity.this,
                    StaffReservationsActivity.class
            );
            startActivity(intent);
        });
    }
}
