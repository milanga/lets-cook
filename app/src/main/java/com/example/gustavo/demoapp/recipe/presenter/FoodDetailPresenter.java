package com.example.gustavo.demoapp.recipe.presenter;

import com.example.gustavo.demoapp.client.ServiceGenerator;
import com.example.gustavo.demoapp.client.services.FoodService;
import com.example.gustavo.demoapp.recipe.Recipe;

import org.parceler.Parcel;
import org.parceler.Transient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Parcel
public class FoodDetailPresenter implements FoodDetailContract.Presenter{
    @Transient
    private FoodDetailContract.View foodDetailContract;
    Recipe recipe;
    String foodId;

    //only for parcel
    FoodDetailPresenter() {
    }

    public FoodDetailPresenter(String foodId){
        this.foodId = foodId;
    }

    public void attach(FoodDetailContract.View foodDetailContract){
        this.foodDetailContract = foodDetailContract;
    }

    public void start(){
        if (recipe !=null){
            foodDetailContract.showFoodDetail(recipe);
        }else {
            obtainFoodDetail();
        }
    }

    private void obtainFoodDetail() {
        foodDetailContract.startLoading();
        ServiceGenerator.createService(FoodService.class).obtainFoodDetail(foodId)
                .enqueue(new Callback<Recipe>() {
                    @Override
                    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                        foodDetailContract.stopLoading();
                        if (response.isSuccessful()) {
                            recipe = response.body();
                            if (recipe != null)
                                foodDetailContract.showFoodDetail(recipe);
                            else
                                foodDetailContract.showApiError();
                        } else {
                            foodDetailContract.showApiError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Recipe> call, Throwable t) {
                        foodDetailContract.stopLoading();
                        foodDetailContract.showConnectionError();
                    }
                });
    }
}