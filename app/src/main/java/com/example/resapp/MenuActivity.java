package com.example.resapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<MenuItem> menuItems;

    private Button btnBookManage, btnStaffLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBookManage = findViewById(R.id.btnBookManage);
        btnStaffLogin = findViewById(R.id.btnStaffLogin);

        dbHelper = new DatabaseHelper(this);
        dbHelper.seedMenuIfEmpty();

        menuItems = new ArrayList<>();

        adapter = new MenuAdapter(
                menuItems,
                this,
                false,
                null
        );
        recyclerView.setAdapter(adapter);

        btnBookManage.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
        });

        btnStaffLogin.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, StaffLoginActivity.class));
        });

        refreshMenuFromDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMenuFromDB();
    }

    private void refreshMenuFromDB() {
        menuItems.clear();

        Cursor cursor = null;
        try {
            cursor = dbHelper.getAllMenuItems();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE));
                    String allergens = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS));
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));

                    menuItems.add(new MenuItem(id, name, price, allergens, imageUrl));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
