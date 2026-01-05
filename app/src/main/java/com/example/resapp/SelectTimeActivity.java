package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTimeActivity extends AppCompatActivity {

    private String selectedDate;
    private String loggedInUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        TextView tvDate = findViewById(R.id.tvSelectedDate);
        loggedInUserEmail = getIntent().getStringExtra("user_email");

        selectedDate = getIntent().getStringExtra("selectedDate");
        tvDate.setText("Selected date: " + selectedDate);

        Button t1 = findViewById(R.id.btnTime1);
        Button t2 = findViewById(R.id.btnTime2);
        Button t3 = findViewById(R.id.btnTime3);
        Button t4 = findViewById(R.id.btnTime4);

        t1.setOnClickListener(v -> openBooking("17:00"));
        t2.setOnClickListener(v -> openBooking("17:30"));
        t3.setOnClickListener(v -> openBooking("18:00"));
        t4.setOnClickListener(v -> openBooking("18:30"));
    }

    private void openBooking(String time) {
        Intent intent = new Intent(SelectTimeActivity.this, BookingDetailsActivity.class);
        intent.putExtra("selectedTime", time);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("user_email", loggedInUserEmail);
        startActivity(intent);
    }
}
