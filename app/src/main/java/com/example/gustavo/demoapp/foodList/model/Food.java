package com.example.gustavo.demoapp.foodList.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavo on 04/04/18.
 */

@Parcel
public class Food {

    String name;
    String imageUrl;
    String id;
    boolean favorite;

    public static String FOOD_KEY = "food_key";

    public Food(){}

    public Food(String id, String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

}
