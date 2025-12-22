package com.example.resapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity

class SelectDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val btnManage = findViewById<Button>(R.id.btnManage)

        btnManage.setOnClickListener {
            val intent = Intent(this, ManageReservationActivity::class.java)
            startActivity(intent)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            val intent = Intent(this, SelectTimeActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        }
    }
}
