package com.warp.android.http.models;

import com.warp.android.utils.DataMap;

import java.util.ArrayList;

public class ResultList {

    private int status;
    private String message;
    private ArrayList<DataMap> result;

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

    public ArrayList<DataMap> getResult() {
        return result;
    }

    public void setResult(ArrayList<DataMap> result) {
        this.result = result;
    }
}
