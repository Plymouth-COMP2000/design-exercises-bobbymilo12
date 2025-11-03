package com.example.resapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SelectTimeActivity : AppCompatActivity() {

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_time)

        val tvDate = findViewById<TextView>(R.id.tvSelectedDate)

        selectedDate = intent.getStringExtra("selectedDate")
        tvDate.text = "Selected date: $selectedDate"

        val t1 = findViewById<Button>(R.id.btnTime1)
        val t2 = findViewById<Button>(R.id.btnTime2)
        val t3 = findViewById<Button>(R.id.btnTime3)
        val t4 = findViewById<Button>(R.id.btnTime4)

        t1.setOnClickListener { openBooking("17:00") }
        t2.setOnClickListener { openBooking("17:30") }
        t3.setOnClickListener { openBooking("18:00") }
        t4.setOnClickListener { openBooking("18:30") }
    }

    private fun openBooking(time: String) {
        val intent = Intent(this, BookingDetailsActivity::class.java)

        intent.putExtra("selectedTime", time)
        intent.putExtra("selectedDate", selectedDate)

        startActivity(intent)
    }
}
