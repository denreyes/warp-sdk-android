package com.warp.android.http;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("session_token")
    private String token;
    private int id;
    private WarpPointer<User> user;

    public WarpPointer<User> getUser() {
        return user;
    }

    public void setUser(WarpPointer<User> user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
