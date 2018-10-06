package com.example.gustavo.demoapp.recipe.presenter;

import com.example.gustavo.demoapp.recipe.model.Recipe;

public interface FoodDetailContract {
    interface View {

        void showConnectionError();

        void showFoodRecipeAfterTransition(Recipe recipe);

        void showApiError();
    }
    interface Presenter{
        void start();
    }
}
