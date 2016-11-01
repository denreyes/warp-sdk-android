package com.warp.android.http.models;

import com.google.gson.annotations.SerializedName;

public class Status<T> {

    private int status;
    private String message;
    @SerializedName("result")
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
