package com.example.resapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_item)

        val img = findViewById<ImageView>(R.id.imgLarge)
        val name = findViewById<TextView>(R.id.tvItemName)
        val ingredients = findViewById<TextView>(R.id.tvIngredients)
        val allergens = findViewById<TextView>(R.id.tvAllergens)
        val backButton = findViewById<Button>(R.id.btnBackToMenu)

        img.setImageResource(intent.getIntExtra("image", 0))
        name.text = intent.getStringExtra("name")
        ingredients.text = "Ingredients: " + intent.getStringExtra("ingredients")
        allergens.text = "Allergens: " + intent.getStringExtra("allergens")

        backButton.setOnClickListener {
            finish()
        }
    }
}
