package com.example.resapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class GuestLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_login)

        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val password = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnCreate = findViewById<Button>(R.id.btnCreateAccount)

        btnLogin.setOnClickListener {
            val intent = Intent(this, SelectDateActivity::class.java)
            startActivity(intent)
        }

        btnCreate.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}
