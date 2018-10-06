package com.example.gustavo.demoapp.client.deserializers;

import com.example.gustavo.demoapp.recipe.model.Recipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodDetailDeserializer implements JsonDeserializer<Recipe> {
    @Override
    public Recipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject foodDetailJson = json.getAsJsonObject().get("documents").getAsJsonArray().get(0).getAsJsonObject();
        JsonObject foodDetailFields = foodDetailJson.get("fields").getAsJsonObject();

        String id = foodDetailJson.getAsJsonObject().get("name").getAsString();
        id = id.substring(id.lastIndexOf('/')+1);
        String name = foodDetailFields.get("name").getAsJsonObject().get("stringValue").getAsString();
        String imageUrl = foodDetailFields.get("imageUrl").getAsJsonObject().get("stringValue").getAsString();

        ArrayList<String> ingredients = obtainStringArray(foodDetailFields, "ingredients");
        ArrayList<String> directions = obtainStringArray(foodDetailFields, "directions");

        HashMap<String,String> nutrition = obtainNutrition(foodDetailFields);

        return new Recipe(name, imageUrl, ingredients, directions, nutrition);
    }

    private HashMap<String, String> obtainNutrition(JsonObject foodDetailFields) {
        HashMap<String,String> nutrition = new HashMap<>();
        JsonObject nutritionFields = foodDetailFields
                .get("nutrition").getAsJsonObject()
                .getAsJsonObject().get("arrayValue").getAsJsonObject()
                .get("values").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("mapValue").getAsJsonObject()
                .get("fields").getAsJsonObject();

        for(Map.Entry<String,JsonElement> fieldEntry: nutritionFields.entrySet()){
            String key = fieldEntry.getKey();
            String value = fieldEntry.getValue().getAsJsonObject().get("stringValue").getAsString();
            nutrition.put(key,value);
        }

        return nutrition;
    }

    private ArrayList<String> obtainStringArray(JsonObject foodDetailFields, String key) {
        ArrayList<String> result = new ArrayList<>();
        JsonArray ingredientsJson = foodDetailFields.get(key).getAsJsonObject().get("arrayValue").getAsJsonObject().get("values").getAsJsonArray();
        for (JsonElement ingredientJson: ingredientsJson){
            result.add(ingredientJson.getAsJsonObject().get("stringValue").getAsString());
        }

        return result;
    }
}
