package com.warp.android;

public class WarpUserResult<T> {
    private int status;
    private String message;
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

    @Override
    public String toString() {
        return "WarpResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
