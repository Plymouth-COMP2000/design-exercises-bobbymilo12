package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffDashboardActivity extends AppCompatActivity {

    private Button btnManageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        Button btnViewReservations = findViewById(R.id.btnViewReservations);

        btnManageMenu = findViewById(R.id.btnManageMenu);

        btnManageMenu.setOnClickListener(v ->
                startActivity(new Intent(this, ManageMenuActivity.class))
        );

        btnViewReservations.setOnClickListener(v -> {
            Intent intent = new Intent(
                    StaffDashboardActivity.this,
                    StaffReservationsActivity.class
            );
            startActivity(intent);
        });
    }
}
