package com.example.resapp;

public class MenuItem {
    public int id;
    public String name;
    public double price;
    public String allergens;
    public String imageUri;

    public MenuItem(int id, String name, double price, String allergens, String imageUri) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.allergens = allergens;
        this.imageUri = imageUri;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getAllergens() { return allergens; }
    public String getImageUri() { return imageUri; }
}
