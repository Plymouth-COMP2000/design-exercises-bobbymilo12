package com.example.resapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.rvMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sampleMenu = listOf(
            MenuItem("Double Cheese Bacon Burger", "£12.99", R.drawable.burger, "Beef, bun, cheese, ketchup, bacon", "Dairy, gluten"),
            MenuItem("Peperoni Pizza", "£12.99", R.drawable.pizza, "Cheese, bread, peperroini, tomato, olive oil", "Dairy, gluten"),
            MenuItem("Spaghetti Bolognase", "£9.50", R.drawable.spag, "Pasta, beef, tomato, cheese" , "Dairy, gluten"),
            MenuItem("Chicken Curry", "£13.00", R.drawable.curry, "Chicken, Rice, Sauce", "Dairy"),
            MenuItem("Cheesy Chips", "£4.50", R.drawable.chips, "Chips, cheese", "Dairy")
        )

        recyclerView.adapter = MenuAdapter(sampleMenu)

        val btnBookManage = findViewById<Button>(R.id.btnBookManage)

        btnBookManage.setOnClickListener {
            val intent = Intent(this, GuestLoginActivity::class.java)
            startActivity(intent)
        }

    }
}
