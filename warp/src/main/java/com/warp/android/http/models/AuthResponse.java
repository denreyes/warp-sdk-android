package com.warp.android.http.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("session_token")
    private String token;
    private int id;
    private Pointer<User> user;

    public Pointer<User> getUser() {
        return user;
    }

    public void setUser(Pointer<User> user) {
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
