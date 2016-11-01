package com.warp.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.warp.android.Warp;
import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Status;
import com.warp.android.http.WarpInterface;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpUser {

    private static Warp warp;

    static {
        warp = Warp.getInstance();
    }

    public static void login(final Context con, String username, String password, final WarpInterface callback) {
        warp.getWarpService().login(new AuthRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<AuthResponse>>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Status<AuthResponse> a) {
                        if(a.getStatus() == 200) {
                            WarpSessions.setSession(con, "session_token", a.getResult().getToken());
                            WarpSessions.setSession(con, "user_id", String.valueOf(a.getResult().getUser().getId()));
                            Log.e("REGISTERED", "REG");
                        }
                    }
                });
    }

    public static void logout(String sessionToken, final WarpInterface callback) {
        warp.getWarpService().logout(sessionToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<AuthResponse>>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Status<AuthResponse> a) {
                    }
                });
    }

    public static WarpUser getCurrentUser() {
        SharedPreferences preferences = warp.getContext().getSharedPreferences("warp", Context.MODE_PRIVATE);
        return new Gson().fromJson(preferences.getString("warp-user", ""), WarpUser.class);
    }

}
