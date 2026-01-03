package com.example.resapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditMenuItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;

    private EditText etName, etPrice, etAllergens;
    private ImageView ivImage;
    private Button btnSave, btnSelectImage;

    private Uri selectedImageUri;
    private DatabaseHelper dbHelper;

    private int editingItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu_item);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etAllergens = findViewById(R.id.etAllergens);
        ivImage = findViewById(R.id.ivImage);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("menu_item_id")) {
            editingItemId = intent.getIntExtra("menu_item_id", -1);
            if (editingItemId != -1) {
                loadMenuItem(editingItemId);
            }
        }

        btnSelectImage.setOnClickListener(v -> openImageChooser());

        btnSave.setOnClickListener(v -> saveMenuItem());
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivImage.setImageURI(selectedImageUri);
        }
    }

    private void loadMenuItem(int id) {
        Uri imageUri;
        try (android.database.Cursor cursor = dbHelper.getMenuItemById(id)) {
            if (cursor != null && cursor.moveToFirst()) {
                etName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME)));
                etPrice.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE))));
                etAllergens.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS)));

                String imageUriStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));
                if (!TextUtils.isEmpty(imageUriStr)) {
                    selectedImageUri = Uri.parse(imageUriStr);
                    ivImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void saveMenuItem() {
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String allergens = etAllergens.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Name and Price are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriStr = selectedImageUri != null ? selectedImageUri.toString() : "";

        long result;
        if (editingItemId == -1) {
            result = dbHelper.addMenuItem(name, price, allergens, imageUriStr);
        } else {
            result = dbHelper.updateMenuItem(editingItemId, name, price, allergens, imageUriStr);
        }

        if (result != -1) {
            Toast.makeText(this, "Menu item saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving menu item", Toast.LENGTH_SHORT).show();
        }
    }
}
