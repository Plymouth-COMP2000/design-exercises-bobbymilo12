package com.example.resapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageMenuActivity extends AppCompatActivity implements MenuAdapter.OnMenuActionListener {

    private RecyclerView rvMenu;
    private TextView tvEmptyMenu;
    private Button btnAddMenu;

    private DatabaseHelper dbHelper;
    private MenuAdapter adapter;
    private final List<MenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        rvMenu = findViewById(R.id.rvManageMenu);
        tvEmptyMenu = findViewById(R.id.tvEmptyMenu);
        btnAddMenu = findViewById(R.id.btnAddMenu);

        dbHelper = new DatabaseHelper(this);

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(menuItems, this, true, this); // staff mode = true, listener = this
        rvMenu.setAdapter(adapter);

        btnAddMenu.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditMenuItemActivity.class));
        });

        loadMenuFromDb();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMenuFromDb(); // refresh after editing/adding
    }

    private void loadMenuFromDb() {
        menuItems.clear();

        Cursor cursor = dbHelper.getAllMenuItems();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_NAME));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_PRICE));
                    String allergens = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_ALLERGENS));
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MENU_IMAGE));

                    menuItems.add(new MenuItem(id, name, price, allergens, imageUrl));
                }
            } finally {
                cursor.close();
            }
        }

        adapter.notifyDataSetChanged();

        boolean empty = menuItems.isEmpty();
        tvEmptyMenu.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvMenu.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onEdit(MenuItem item) {
        Intent intent = new Intent(this, AddEditMenuItemActivity.class);
        intent.putExtra("menu_item_id", item.getId()); // IMPORTANT
        startActivity(intent);
    }

    @Override
    public void onDelete(MenuItem item) {
        dbHelper.deleteMenuItem(item.getId());
        loadMenuFromDb();
    }
}
