package com.example.gustavo.demoapp.foodList.presenter;

import com.example.gustavo.demoapp.client.ServiceGenerator;
import com.example.gustavo.demoapp.client.services.FoodService;
import com.example.gustavo.demoapp.foodList.Food;

import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Parcel
public class FoodListPresenter implements FoodListContract.Presenter{
    @Transient
    private FoodListContract.View foodListContract;
    private List<Food> foodList;

    public FoodListPresenter(){}

    public void attach(FoodListContract.View foodListContract){
        this.foodListContract = foodListContract;
    }

    public void start(){
        if (foodList!=null){
            foodListContract.showFoodList(foodList);
        }else {
            obtainList();
        }
    }

    private void obtainList() {
        foodListContract.startLoading();
        ServiceGenerator.createService(FoodService.class).obtainFoodList()
                .enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        foodListContract.stopLoading();
                        if (response.isSuccessful()) {
                            foodList = response.body();
                            if (foodList != null && !foodList.isEmpty())
                                foodListContract.showFoodList(foodList);
                            else
                                foodListContract.showApiError();
                        } else {
                            foodListContract.showApiError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        foodListContract.stopLoading();
                        foodListContract.showConnectionError();
                    }
                });
    }
}
