package com.warp.android;

import com.google.gson.JsonArray;

public class WarpQueryResult {
    private int status;
    private String message;
    private JsonArray result;

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

    public JsonArray getResult() {
        return result;
    }

    public void setResult(JsonArray result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WarpQueryResult{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
