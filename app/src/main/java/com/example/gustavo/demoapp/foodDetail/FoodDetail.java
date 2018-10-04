package com.example.gustavo.demoapp.foodDetail;

import java.util.HashMap;
import java.util.List;

public class FoodDetail {
    private String name;
    private String imageUrl;
    private List<String> directions;
    private HashMap<String,String> nutrition;
    private List<String> ingredients;

    public FoodDetail(String name, String imageUrl, List<String> ingredients, List<String> directions, HashMap<String,String> nutrition) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.directions = directions;
        this.nutrition = nutrition;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getDirections() {
        return directions;
    }

    public HashMap<String, String> getNutrition() {
        return nutrition;
    }
}
