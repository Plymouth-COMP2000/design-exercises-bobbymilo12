package com.example.resapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val etName = findViewById<EditText>(R.id.etName)
        val etRequests = findViewById<EditText>(R.id.etRequests)
        val tvTime = findViewById<TextView>(R.id.tvSelectedTime)
        val tvDate = findViewById<TextView>(R.id.tvSelectedDate)
        val btnBook = findViewById<Button>(R.id.btnBook)
        val btnSignOut = findViewById<Button>(R.id.btnSignOut)


        val time = intent.getStringExtra("selectedTime")
        val date = intent.getStringExtra("selectedDate")

        tvTime.text = time
        tvDate.text = date

        btnBook.setOnClickListener {
        }

        btnSignOut.setOnClickListener {
            finish()
        }
    }
}
