package com.example.gustavo.demoapp.client.services;

import com.example.gustavo.demoapp.recipe.Recipe;
import com.example.gustavo.demoapp.foodList.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodService {
    @GET("food?fields=documents(fields%2Cname)")
    Call<List<Food>> obtainFoodList();
    @GET("food/{foodId}/detail?fields=documents(fields%2Cname)")
    Call<Recipe> obtainFoodDetail(@Path("foodId") String foodId);
}
