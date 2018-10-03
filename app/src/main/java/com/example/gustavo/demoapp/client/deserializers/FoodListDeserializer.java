package com.example.gustavo.demoapp.client.deserializers;

import com.example.gustavo.demoapp.foodList.Food;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodListDeserializer implements JsonDeserializer<List<Food>> {

    @Override
    public List<Food> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Food> foodList = new ArrayList<>();
        JsonArray foodJsonArray = json.getAsJsonObject().get("documents").getAsJsonArray();
        for (JsonElement jsonFood: foodJsonArray){
            //obtain fields jsonObject
            JsonObject fields = jsonFood.getAsJsonObject().get("fields").getAsJsonObject();

            //obtain Food fields
            String id = jsonFood.getAsJsonObject().get("name").getAsString();
            String name = fields.get("name").getAsJsonObject().get("stringValue").getAsString();
            String imageUrl = fields.get("imageUrl").getAsJsonObject().get("stringValue").getAsString();

            //create Food object
            Food food = new Food(id, name, imageUrl);
            foodList.add(food);
        }

        return foodList;
    }
}
