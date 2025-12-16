package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class GuestLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        TextInputEditText email = findViewById(R.id.etEmail);
        TextInputEditText password = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreate = findViewById(R.id.btnCreateAccount);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(GuestLoginActivity.this, SelectDateActivity.class);
            startActivity(intent);
        });

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(GuestLoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }
}
