package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectDateActivity extends AppCompatActivity {
    private String loggedInUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        loggedInUserEmail = getIntent().getStringExtra("user_email");

        CalendarView calendarView = findViewById(R.id.calendarView);
        Button btnManage = findViewById(R.id.btnManage);

        btnManage.setOnClickListener(v -> {
            Intent intent = new Intent(SelectDateActivity.this, ManageReservationActivity.class);
            intent.putExtra("user_email", loggedInUserEmail);
            startActivity(intent);

        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

            Intent intent = new Intent(SelectDateActivity.this, SelectTimeActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("user_email", loggedInUserEmail);
            startActivity(intent);
        });
    }
}
