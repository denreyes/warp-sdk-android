package com.warp.android.utils;

import android.content.Context;
import com.warp.android.Warp;
import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Status;
import com.warp.android.http.WarpInterface;
import com.warp.android.http.models.User;

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

    public static void register(final Context con, String username, String password, String email, final WarpInterface callback) {
        warp.getWarpService().register(new User(username, password, email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<User>>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Status<User> a) {
                        if(a.getStatus() == 200) {
                            WarpSessions.setSession(con, "user_id", String.valueOf(a.getResult().getId()));
                        }
                    }
                });
    }

    public static void getUserById(final Context con, String id, final WarpInterface callback) {
        warp.getWarpService().getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<User>>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Status<User> a) {
                        if(a.getStatus() == 200) {
                            WarpSessions.setSession(con, "user_id", String.valueOf(a.getResult().getId()));
                        }
                    }
                });
    }
}
