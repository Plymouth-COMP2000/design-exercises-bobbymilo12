package com.example.resapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MenuItem> sampleMenu = Arrays.asList(
                new MenuItem("Double Cheese Bacon Burger", "£12.99", R.drawable.burger,
                        "Beef, bun, cheese, ketchup, bacon", "Dairy, gluten"),
                new MenuItem("Peperoni Pizza", "£12.99", R.drawable.pizza,
                        "Cheese, bread, peperroini, tomato, olive oil", "Dairy, gluten"),
                new MenuItem("Spaghetti Bolognase", "£9.50", R.drawable.spag,
                        "Pasta, beef, tomato, cheese", "Dairy, gluten"),
                new MenuItem("Chicken Curry", "£13.00", R.drawable.curry,
                        "Chicken, Rice, Sauce", "Dairy"),
                new MenuItem("Cheesy Chips", "£4.50", R.drawable.chips,
                        "Chips, cheese", "Dairy")
        );

        recyclerView.setAdapter(new MenuAdapter(sampleMenu));

        Button btnBookManage = findViewById(R.id.btnBookManage);

        btnBookManage.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, GuestLoginActivity.class);
            startActivity(intent);
        });
    }
}
