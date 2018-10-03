package com.example.gustavo.demoapp.client.services;

import com.example.gustavo.demoapp.foodList.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodService {
    @GET("food?fields=documents(fields%2Cname)")
    Call<List<Food>> obtainFoodList();
    @GET("/food/{foodId}/detail?fields=documents(fields%2Cname)")
    Call<Food> obtainFoodDetail(String foodId);
}
