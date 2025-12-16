package com.example.resapp;

public class MenuItem {

    private final String name;
    private final String price;
    private final int image;
    private final String ingredients;
    private final String allergens;

    public MenuItem(String name, String price, int image, String ingredients, String allergens) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getAllergens() {
        return allergens;
    }
}
