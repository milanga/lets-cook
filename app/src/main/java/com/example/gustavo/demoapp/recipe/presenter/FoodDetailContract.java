package com.example.gustavo.demoapp.recipe.presenter;

import com.example.gustavo.demoapp.recipe.Recipe;

public interface FoodDetailContract {
    interface View {
        void startLoading();

        void stopLoading();

        void showConnectionError();

        void showFoodDetail(Recipe recipe);

        void showApiError();
    }
    interface Presenter{
        void start();
    }
}
