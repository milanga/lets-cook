package com.example.gustavo.demoapp.foodList.presenter;

import com.example.gustavo.demoapp.foodList.Food;

import java.util.List;

public interface FoodListContract {
    interface View {
        void startLoading();

        void stopLoading();

        void showConnectionError();

        void showFoodList(List<Food> foodList);

        void showApiError();
    }
    interface Presenter{
        void start();
    }
}
