package com.example.resapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnCreate;
    private CheckBox cbRememberMe;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreate = findViewById(R.id.btnCreateAccount);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // auto fill if remembered
        String savedEmail = prefs.getString("email", null);
        String savedPassword = prefs.getString("password", null);

        if (savedEmail != null && savedPassword != null) {
            etEmail.setText(savedEmail);
            etPassword.setText(savedPassword);
            cbRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String emailInput = etEmail.getText().toString().trim();
            String passwordInput = etPassword.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }


            if (cbRememberMe.isChecked()) {
                prefs.edit()
                        .putString("email", emailInput)
                        .putString("password", passwordInput)
                        .apply();
            } else {
                prefs.edit().clear().apply();
            }

            Intent intent = new Intent(LoginActivity.this, SelectDateActivity.class);
            intent.putExtra("user_email", emailInput);
            startActivity(intent);
            finish();
        });

        btnCreate.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
    }
}
