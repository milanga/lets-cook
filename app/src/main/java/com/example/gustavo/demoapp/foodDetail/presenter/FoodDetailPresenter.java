package com.example.gustavo.demoapp.foodDetail.presenter;

import com.example.gustavo.demoapp.client.ServiceGenerator;
import com.example.gustavo.demoapp.client.services.FoodService;
import com.example.gustavo.demoapp.foodDetail.FoodDetail;

import org.parceler.Parcel;
import org.parceler.Transient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Parcel
public class FoodDetailPresenter implements FoodDetailContract.Presenter{
    @Transient
    private FoodDetailContract.View foodDetailContract;
    FoodDetail foodDetail;
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
        if (foodDetail!=null){
            foodDetailContract.showFoodDetail(foodDetail);
        }else {
            obtainFoodDetail();
        }
    }

    private void obtainFoodDetail() {
        foodDetailContract.startLoading();
        ServiceGenerator.createService(FoodService.class).obtainFoodDetail(foodId)
                .enqueue(new Callback<FoodDetail>() {
                    @Override
                    public void onResponse(Call<FoodDetail> call, Response<FoodDetail> response) {
                        foodDetailContract.stopLoading();
                        if (response.isSuccessful()) {
                            foodDetail = response.body();
                            if (foodDetail != null)
                                foodDetailContract.showFoodDetail(foodDetail);
                            else
                                foodDetailContract.showApiError();
                        } else {
                            foodDetailContract.showApiError();
                        }
                    }

                    @Override
                    public void onFailure(Call<FoodDetail> call, Throwable t) {
                        foodDetailContract.stopLoading();
                        foodDetailContract.showConnectionError();
                    }
                });
    }
}