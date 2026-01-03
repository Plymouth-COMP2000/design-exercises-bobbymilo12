package com.example.resapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ManageMenuActivity extends AppCompatActivity {

    private RecyclerView rvMenu;
    private Button btnAddMenu;
    private TextView tvEmptyMenu;

    private DatabaseHelper dbHelper;
    private List<MenuItem> menuItems;
    private MenuAdapter adapter;

    private static final int ADD_EDIT_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        rvMenu = findViewById(R.id.rvManageMenu);
        btnAddMenu = findViewById(R.id.btnAddMenu);
        tvEmptyMenu = findViewById(R.id.tvEmptyMenu);

        dbHelper = new DatabaseHelper(this);
        menuItems = new ArrayList<>();

        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MenuAdapter(menuItems, this, true, new MenuAdapter.OnMenuActionListener() {
            @Override
            public void onEdit(MenuItem item) {
                Intent intent = new Intent(ManageMenuActivity.this, AddEditMenuItemActivity.class);
                intent.putExtra("menuId", item.getId());
                startActivityForResult(intent, ADD_EDIT_REQUEST);
            }

            @Override
            public void onDelete(MenuItem item) {
                dbHelper.deleteMenuItem(item.getId());
                loadMenuItems();
            }
        });

        rvMenu.setAdapter(adapter);

        btnAddMenu.setOnClickListener(v -> {
            Intent intent = new Intent(ManageMenuActivity.this, AddEditMenuItemActivity.class);
            startActivityForResult(intent, ADD_EDIT_REQUEST);
        });

        loadMenuItems();
    }

    private void loadMenuItems() {
        menuItems.clear();
        Cursor cursor = dbHelper.getAllMenuItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE));
                String allergens = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));

                menuItems.add(new MenuItem(id, name, price, allergens, imageUri));
            } while (cursor.moveToNext());
            cursor.close();
        }

        tvEmptyMenu.setVisibility(menuItems.isEmpty() ? TextView.VISIBLE : TextView.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EDIT_REQUEST && resultCode == RESULT_OK) {
            loadMenuItems();
        }
    }
}
