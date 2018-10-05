package com.example.gustavo.demoapp.client;

import com.example.gustavo.demoapp.client.deserializers.FoodDetailDeserializer;
import com.example.gustavo.demoapp.client.deserializers.FoodListDeserializer;
import com.example.gustavo.demoapp.foodDetail.FoodDetail;
import com.example.gustavo.demoapp.foodList.Food;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = "https://firestore.googleapis.com/v1beta1/projects/foodapp-6ac03/databases/(default)/documents/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson()));

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    private static Retrofit retrofit = retrofit();


    private static Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Food>>(){}.getType(), new FoodListDeserializer());
        gsonBuilder.registerTypeAdapter(new TypeToken<FoodDetail>(){}.getType(), new FoodDetailDeserializer());

        return gsonBuilder.create();
    }

    private static Retrofit retrofit(){
        httpClient.addInterceptor(logging);
        builder.client(httpClient.build());
        return builder.build();
    }

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}