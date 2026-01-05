package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MenuItemActivity extends AppCompatActivity {

    private ImageView imgLarge;
    private TextView tvName, tvIngredients;
    private Button btnBack;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        imgLarge = findViewById(R.id.imgLarge);
        tvName = findViewById(R.id.tvItemName);
        tvIngredients = findViewById(R.id.tvIngredients);
        btnBack = findViewById(R.id.btnBackToMenu);

        dbHelper = new DatabaseHelper(this);

        int menuItemId = getIntent().getIntExtra("menuItemId", -1);
        if (menuItemId != -1) {
            loadMenuItem(menuItemId);
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadMenuItem(int id) {
        Cursor cursor = null;

        try {
            cursor = dbHelper.getMenuItemById(id);

            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE));
                String allergens = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS));

                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));

                tvName.setText(name + " - £" + price);
                tvIngredients.setText("Allergens: " + allergens);

                Glide.with(this)
                        .load(TextUtils.isEmpty(imageUrl) ? null : imageUrl)
                        .placeholder(R.drawable.burger)
                        .error(R.drawable.burger)
                        .into(imgLarge);
            }

        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
