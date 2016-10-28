package com.warp.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WarpConstraints extends JSONObject {

    public WarpConstraints(Constraint constraint, Object value) {
        try {
            this.put(constraint.getCode(), value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    public WarpConstraints(Constraint constraint, Object[] value) {
        JSONArray array = new JSONArray();
        for (Object o : value) { array.put(o); }
        try {
            this.put(constraint.getCode(), array);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public WarpConstraints(String key, Object value) {
        try {
            this.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public WarpConstraints set(Constraint constraint, Object value) {
        try {
            this.put(constraint.getCode(), value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }
    public WarpConstraints set(Constraint constraint, Object[] value) {
        JSONArray array = new JSONArray();
        for (Object o : value) { array.put(o); }
        try {
            this.put(constraint.getCode(), array);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public WarpConstraints set(String key, Object value) {
        try {
            this.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }
}
