package com.udacity.sandwichclub.utils;

import android.app.Application;
import android.support.v4.content.ContextCompat;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";

    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwitchJson = new JSONObject(json);

        JSONObject sandwitchNameJson = sandwitchJson.getJSONObject(NAME);
        String name = sandwitchNameJson.getString(MAIN_NAME);
        List<String> alsoKnownAs = getStringListFromJson(sandwitchNameJson.getJSONArray(ALSO_KNOWN_AS));

        String placeOfOrigin = sandwitchJson.getString(PLACE_OF_ORIGIN);
        String description = sandwitchJson.getString(DESCRIPTION);
        String image = sandwitchJson.getString(IMAGE);
        List<String> ingredients = getStringListFromJson(sandwitchJson.getJSONArray(INGREDIENTS));

        return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> getStringListFromJson(JSONArray jsonArray) throws JSONException {
        List<String> knownAs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            knownAs.add(jsonArray.getString(i));
        }
        return knownAs;
    }
}
