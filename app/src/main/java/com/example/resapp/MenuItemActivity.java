package com.example.resapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        ImageView img = findViewById(R.id.imgLarge);
        TextView name = findViewById(R.id.tvItemName);
        TextView ingredients = findViewById(R.id.tvIngredients);
        TextView allergens = findViewById(R.id.tvAllergens);
        Button backButton = findViewById(R.id.btnBackToMenu);

        img.setImageResource(getIntent().getIntExtra("image", 0));
        name.setText(getIntent().getStringExtra("name"));
        ingredients.setText("Ingredients: " + getIntent().getStringExtra("ingredients"));
        allergens.setText("Allergens: " + getIntent().getStringExtra("allergens"));

        backButton.setOnClickListener(v -> finish());
    }
}
