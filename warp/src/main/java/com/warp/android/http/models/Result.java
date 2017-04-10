package com.warp.android.http.models;

import com.warp.android.utils.DataMap;

public class Result {

    private int status;
    private String message;
    private DataMap result;

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

    public DataMap getResult() {
        return result;
    }

    public void setResult(DataMap result) {
        this.result = result;
    }
}
