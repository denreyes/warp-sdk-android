package com.warp.android.http.models;

import org.json.JSONObject;

import java.util.ArrayList;

public class Result {

    private int status;
    private String message;
    private ArrayList<JSONObject> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<JSONObject> getResult() {
        return result;
    }

    public void setResult(ArrayList<JSONObject> result) {
        this.result = result;
    }
}
