package com.example.resapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuItemActivity extends AppCompatActivity {

    private ImageView imgLarge;
    private TextView tvName, tvIngredients;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        imgLarge = findViewById(R.id.imgLarge);
        tvName = findViewById(R.id.tvItemName);
        tvIngredients = findViewById(R.id.tvIngredients);
        btnBack = findViewById(R.id.btnBackToMenu);

        int menuItemId = getIntent().getIntExtra("menuItemId", -1);
        if (menuItemId != -1) {
            loadMenuItem(menuItemId);
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadMenuItem(int id) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getMenuItemById(id);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE));
            String allergens = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS));
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));

            tvName.setText(name + " - £" + price);
            tvIngredients.setText("Allergens: " + allergens);

            if (imageUri != null && !imageUri.isEmpty()) {
                imgLarge.setImageURI(Uri.parse(imageUri));
            } else {
                imgLarge.setImageResource(R.drawable.burger); // fallback
            }

            cursor.close();
        }
    }
}
