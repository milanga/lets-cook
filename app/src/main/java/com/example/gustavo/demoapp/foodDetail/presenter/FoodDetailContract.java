package com.example.gustavo.demoapp.foodDetail.presenter;

import com.example.gustavo.demoapp.foodDetail.FoodDetail;

public interface FoodDetailContract {
    interface View {
        void startLoading();

        void stopLoading();

        void showConnectionError();

        void showFoodDetail(FoodDetail foodDetail);

        void showApiError();
    }
    interface Presenter{
        void start();
    }
}
