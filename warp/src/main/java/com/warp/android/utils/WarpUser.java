package com.warp.android.utils;

import android.content.Context;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpUserInterface;
import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Result;
import com.warp.android.http.models.Status;
import com.warp.android.http.models.User;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.warp.android.utils.WarpConstants.PREF_SESSION_TOKEN;
import static com.warp.android.utils.WarpConstants.PREF_USER_ID;
import static com.warp.android.utils.WarpConstants.PREF_USER_NAME;
import static com.warp.android.utils.WarpConstants.SPK_CREATED_AT;
import static com.warp.android.utils.WarpConstants.SPK_UPDATED_AT;
import static com.warp.android.utils.WarpConstants.SPK_USER_ID;

public class WarpUser{

    private Warp warp;
    private User user;
    private Context context;
    private DataMap bodyRequest, bodyResult;

    private int mId;
    private String mCreatedAt, mUpdatedAt, mSessionToken, mUsername, mEmail;

    WarpUser(WarpUserService builder) {
        this.context = builder.context;
        this.bodyRequest = builder.body;
        this.warp = Warp.getInstance();
    }

    private WarpUser(DataMap hashMap) {

        MapFunctions.setData(hashMap);
        this.mId = MapFunctions.getInteger(SPK_USER_ID, true);
        this.mSessionToken = MapFunctions.getString("session_token", true);
        this.mUsername = MapFunctions.getString("username", true);
        this.mEmail = MapFunctions.getString("email", true);
        this.mCreatedAt = MapFunctions.getString(SPK_CREATED_AT, true);
        this.mUpdatedAt = MapFunctions.getString(SPK_UPDATED_AT, true);
        this.bodyResult = MapFunctions.getData();

    }

    public WarpPointer getPointer(String key) {
        MapFunctions.setData(bodyResult);
        Object value = MapFunctions.getObject(key, false);
        return new WarpPointer(key, value);
    }

    void login(final String username,
                         String password,
                         final WarpUserInterface callback) {
        warp.getWarpService().login(WarpConstants.CLIENT, new AuthRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<AuthResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(final Status<AuthResponse> a) {
                        if (a.getStatus() == 200) {
                            WarpSessions.setSession(context, PREF_SESSION_TOKEN, a.getResult().getToken());
                            WarpSessions.setSession(context, PREF_USER_NAME, username);
                            WarpSessions.setSession(context, PREF_USER_ID, a.getResult().getUser().getId());

                            getUserById(a.getResult().getUser().getId(), new WarpUserInterface() {
                                @Override
                                public void onCompleted() {
                                    callback.onCompleted();
                                }

                                @Override
                                public void onError(WarpCallException e) {
                                    callback.onError(WarpCallErrorHandler.getError(e));
                                }

                                @Override
                                public void onSuccess(WarpUser warpUser) {
                                    callback.onSuccess(warpUser);
                                }
                            });
                        }
                    }
                });
    }

    void logout(final WarpInterface callback) {

        warp.getWarpService().logout(WarpSessions.getSessionToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status<AuthResponse>>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Status<AuthResponse> a) {
                        if (a.getStatus() == 200) {
                            WarpSessions.removeUserSession(context);
                            user = null;
                            callback.onSuccess();
                        }
                    }
                });
    }

    void register(final String username,
                          final String password,
                          String email,
                          final WarpUserInterface callback) {
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
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Status<User> a) {
                        if (a.getStatus() == 200) {
                            login(username, password, new WarpUserInterface() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(WarpCallException e) {
                                    callback.onError(WarpCallErrorHandler.getError(e));
                                }

                                @Override
                                public void onSuccess(WarpUser warpUser) {
                                    callback.onSuccess(warpUser);
                                }
                            });
                        }
                    }
                });
    }

    void save(final WarpUserInterface callback) {
        warp.getWarpService().save(WarpSessions.getSessionToken(context),
                WarpSessions.getUserId(context), bodyRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Result result) {
                        callback.onSuccess(new WarpUser(result.getResult()));
                    }
                });
    }

    private void getUserById(int id, final WarpUserInterface callback) {
        warp.getWarpService().getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Result result) {
                        callback.onSuccess(new WarpUser(result.getResult()));
                    }
                });
    }

    public int getId() {
        return mId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getSessionToken() {
        return mSessionToken;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public DataMap getResult() {
        return bodyResult;
    }

    @Override
    public String toString() {
        return "WarpUser == " +
                "[id=" + mId + ", session_token=" + mSessionToken + ", username=" + mUsername + ", email=" + mEmail + "] " +
                " " + bodyResult + " [created_at='" + mCreatedAt + "', updated_at='" + mUpdatedAt + "\']";
    }
}
