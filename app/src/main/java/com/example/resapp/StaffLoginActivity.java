package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class StaffLoginActivity extends AppCompatActivity {

    private static final String STAFF_EMAIL_DOMAIN = "@yummytummy.com";
    private static final String STAFF_PASSWORD = "12345678";

    private TextInputEditText etStaffEmail, etStaffPassword;
    private Button btnStaffLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        etStaffEmail = findViewById(R.id.etStaffEmail);
        etStaffPassword = findViewById(R.id.etStaffPassword);
        btnStaffLogin = findViewById(R.id.btnStaffLogin);

        btnStaffLogin.setOnClickListener(v -> {
            String email = etStaffEmail.getText().toString().trim();
            String password = etStaffPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.endsWith(STAFF_EMAIL_DOMAIN)) {
                Toast.makeText(this, "Invalid staff email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(STAFF_PASSWORD)) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, StaffDashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
