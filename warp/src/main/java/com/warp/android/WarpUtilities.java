package com.warp.android;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class WarpUtilities {

    private static final WarpUtilities INSTANCE = new WarpUtilities();
    public static WarpUtilities getUtils() { return INSTANCE; }

    WarpUtilities() {}

    public JsonObject warpObjectJSONtoJsonParser(JSONObject jsonObject) {
        JsonParser jsonParser = new JsonParser();
        return (JsonObject) jsonParser.parse(jsonObject.toString());
    }

    public JSONObject warpObjectJsonToJSONParser(JsonObject jsonObject) {
        try {
            return new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
