package com.example.gustavo.demoapp.recipe.presenter;

import android.util.Log;

import com.example.gustavo.demoapp.client.ServiceGenerator;
import com.example.gustavo.demoapp.client.services.FoodService;
import com.example.gustavo.demoapp.recipe.model.Recipe;

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
            Log.e("presenter", "recipe not null");
            foodDetailContract.showFoodRecipeAfterTransition(recipe);
        }else {
            Log.e("presenter", "recipe null");
            obtainFoodDetail();
        }
    }

    private void obtainFoodDetail() {
        ServiceGenerator.createService(FoodService.class).obtainFoodDetail(foodId)
                .enqueue(new Callback<Recipe>() {
                    @Override
                    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                        if (response.isSuccessful()) {
                            recipe = response.body();
                            if (recipe != null)
                                foodDetailContract.showFoodRecipeAfterTransition(recipe);
                            else
                                foodDetailContract.showApiError();
                        } else {
                            foodDetailContract.showApiError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Recipe> call, Throwable t) {
                        foodDetailContract.showConnectionError();
                    }
                });
    }

}