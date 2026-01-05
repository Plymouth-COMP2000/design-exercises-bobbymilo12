package com.example.resapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditMenuItemActivity extends AppCompatActivity {

    private EditText etName, etPrice, etAllergens, etImageUrl;
    private Button btnSave;

    private DatabaseHelper dbHelper;
    private int editingItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu_item);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etAllergens = findViewById(R.id.etAllergens);
        etImageUrl = findViewById(R.id.etImageUrl);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("menu_item_id")) {
            editingItemId = getIntent().getIntExtra("menu_item_id", -1);
            loadMenuItem(editingItemId);
        }

        btnSave.setOnClickListener(v -> saveMenuItem());
    }

    private void loadMenuItem(int id) {
        Cursor cursor = dbHelper.getMenuItemById(id);
        if (cursor != null && cursor.moveToFirst()) {

            etName.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME)));

            etPrice.setText(String.valueOf(cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE))));

            etAllergens.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS)));

            etImageUrl.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE)));

            cursor.close();
        }
    }

    private void saveMenuItem() {
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String allergens = etAllergens.getText().toString().trim();
        String imageUrl = etImageUrl.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Name and price required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        long result;
        if (editingItemId == -1) {
            result = dbHelper.addMenuItem(name, price, allergens, imageUrl);
        } else {
            result = dbHelper.updateMenuItem(editingItemId, name, price, allergens, imageUrl);
        }

        if (result != -1) {
            finish();
        } else {
            Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show();
        }
    }
}
