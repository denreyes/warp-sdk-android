package com.warp.android;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpUser {

    private int mId = -1;
    private int mUserId = -1;
    private String mOrigin = null;
    private String mSessionToken = null;
    private String mCreatedAt = null;
    private String mUpdatedAt = null;

    private String mFirstName = null;
    private String mMiddleName = null;
    private String mLastName = null;
    private String mEmail = null;

    private String mUsername = null;
    private String mPassword = null;

    private JSONObject mParameterHashMap;
    private WarpInterface mWarpInterface;

    private static WarpUser mWarpUser;

    WarpUtilities mUtils = WarpUtilities.getUtils();
    WarpUserCoder mUserCoder = WarpUserCoder.getUserCoder();

    private enum Session {
        NONE,
        LOGIN,
        LOGIN_USER,
        LOGOUT
    }

    public interface Result {
        void onCompleted();
        void onSuccess(WarpUser user);
        void onError(Throwable e);
    }

    public WarpUser() {
        this.mWarpInterface = Warp.getConnectService();
        this.mParameterHashMap = new JSONObject();
    }

    private WarpUser getUser() {
        return this;
    }

    public WarpUser put(String key, Object value) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpUser not yet initialized!");
        }
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("WarpUser key must be not null!");
        }
        if (value == null) {
            throw new NullPointerException("WarpUser value must be not null!");
        }
        try {
            mParameterHashMap.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public static WarpUser Login(String username, String password, Result result) {
        if (mWarpUser == null) {
            mWarpUser = new WarpUser();
        }
        return mWarpUser.login(username, password, Session.LOGIN, result);
    }

    public static WarpUser LoginWithUser(String username, String password, Result result) {
        if (mWarpUser == null) {
            mWarpUser = new WarpUser();
        }
        return mWarpUser.login(username, password, Session.LOGIN_USER, result);
    }

    private WarpUser login(String username, String password, Session session, Result result) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpUser not yet initialized!");
        }

        try {
            mParameterHashMap.put("username", username);
            mParameterHashMap.put("password", password);
            mUsername = username;
            mPassword = password;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        Observable<WarpUserResult<JsonObject>> loginCall = mWarpInterface.login(
                Warp.deviceId,
                mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
        );
        receiver(loginCall, session, result);
        return this;
    }

    public WarpUser fetchCurrentUser(Result result) {
        Observable<WarpUserResult<JsonObject>> fetchCurrentUserCall = mWarpInterface.fetchCurrentUser();
        receiver(fetchCurrentUserCall, Session.NONE, result);
//        clear();
        return this;
    }

    public WarpUser signUp(Result result) {
        Observable<WarpUserResult<JsonObject>> signUpCall = mWarpInterface.signUp(
                mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
        );
        receiver(signUpCall, Session.NONE, result);
        clear();
        return this;
    }

    public static WarpUser Logout(Result result) {
        if (mWarpUser == null) {
            mWarpUser = new WarpUser();
        }
        return mWarpUser.logout(result);
    }

    public WarpUser logout(Result result) {
        Observable<WarpUserResult<JsonObject>> logoutCall = mWarpInterface.logout();
        receiver(logoutCall, Session.LOGOUT, result);
        return this;
    }

    public WarpUser userInfo(String username, String password, String email) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpUser not yet initialized!");
        }
        if (username == null || username.isEmpty()) {
            throw new NullPointerException("WarpUser username must be not null!");
        }
        if (password == null || password.isEmpty()) {
            throw new NullPointerException("WarpUser password must be not null!");
        }
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("WarpUser email must be not null!");
        }
        try {
            mParameterHashMap.put("username", username);
            mParameterHashMap.put("password", password);
            mParameterHashMap.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public WarpUser addUserInfo(String key, Object value) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpUser not yet initialized!");
        }
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("WarpUser key must be not null!");
        }
        if (value == null) {
            throw new NullPointerException("WarpUser value must be not null!");
        }
        try {
            mParameterHashMap.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    /*  */
    private void receiver(Observable<WarpUserResult<JsonObject>> call, final Session session, final Result result) {
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpUserResult<JsonObject>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (session == Session.LOGOUT) {
                            clear();
                            WarpStorage.Storage.DeleteFile();
                            result.onSuccess(getUser());
                        } else {
                            result.onError(e);
                        }
                    }

                    @Override
                    public void onNext(WarpUserResult<JsonObject> response) {
                        if (response.getStatus() == 200) {
                            System.out.println("onResponse:response:success: " + response);
                            mParameterHashMap = mUtils.warpObjectJsonToJSONParser(response.getResult());

                            switch (session) {
                                case LOGIN:
                                    disassemble(mParameterHashMap);
                                    WarpStorage.Storage.WriteFile(WarpSessionKey.add(
                                            mUserId, mSessionToken, mUsername, mPassword));
                                    result.onSuccess(getUser());
                                    break;
                                case LOGIN_USER:
                                    disassemble(mParameterHashMap);
                                    WarpStorage.Storage.WriteFile(WarpSessionKey.add(
                                            mUserId, mSessionToken, mUsername, mPassword));
                                    getUser().fetchCurrentUser(new Result() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onSuccess(WarpUser user) {
                                            result.onSuccess(getUser());
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            result.onError(e);
                                        }
                                    });
                                    break;
                                case LOGOUT:
                                    clear();
                                    WarpStorage.Storage.DeleteFile();
                                    result.onSuccess(getUser());
                                    break;
                                default:
                                    disassemble(mParameterHashMap);
                                    result.onSuccess(getUser());
                                    break;
                            }
                        } else {
                            try {
                                System.out.println("onResponse:response:error " + response.toString());
                            } catch (Exception e) {
                                result.onError(e);
                                System.out.println("onResponse:response:error " + e.getMessage());
                            }

                            if (session == Session.LOGOUT) {
                                clear();
                                WarpStorage.Storage.DeleteFile();
                            }
                        }
                    }
                });
    }

    /* */
    private void disassemble(JSONObject jsonObject) {
        clear();
        mParameterHashMap = mUserCoder.warpEncode(jsonObject);
        mId = mUserCoder.getId();
        mUserId = mUserCoder.getUserId();
        mOrigin = mUserCoder.getOrigin();
        mSessionToken = mUserCoder.getSessionToken();
        mCreatedAt = mUserCoder.getCreatedAt();
        mUpdatedAt = mUserCoder.getUpdatedAt();

        mFirstName = mUserCoder.getFirstName();
        mMiddleName = mUserCoder.getMiddleName();
        mLastName = mUserCoder.getLastName();
        mEmail = mUserCoder.getEmail();

        System.out.println("WarpUser: " + logs());
    }

    public int getId() {
        return mId;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    /* Clear values */
    private void clear() {
        mId = -1;
        mUserId = -1;
        mOrigin = null;
        mSessionToken = null;
        mCreatedAt = null;
        mUpdatedAt = null;

        mFirstName = null;
        mMiddleName = null;
        mLastName = null;
        mEmail = null;
    }

    public static WarpSessionKey getCurrentUser() {
        return WarpStorage.Storage.GetFile();
    }

    public JSONObject getUserResult() {
        return mParameterHashMap;
    }

    public WarpPointer getPointer(String key) {
        JSONObject pointer;
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("WarpUser parameter object is null");
        }
        if (mParameterHashMap == null || mParameterHashMap.length() == 0) {
            throw new NullPointerException("WarpUser key is null or empty");
        }
        try {
            pointer = mParameterHashMap.getJSONObject(key);
            return new WarpPointer(key, pointer.getInt("id"));
        } catch (JSONException e) {
            throw new NullPointerException("WarpUser invalid key");
        }
    }

    public static boolean hasCurrentUser() {
        return getCurrentUser().getUserId() != 0 &&
                !getCurrentUser().getSessionToken().isEmpty();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[" +
                        "userId=%s,firstName=%s,middleName=%s,lastName=%s,email=%s,origin=%s," +
                        "sessionToken=%s,createdAt=%s,updatedAt=%s,savedData=%s" +
                        "]",
                getClass().getName(),
                String.valueOf(mUserId),
                mFirstName,
                mMiddleName,
                mLastName,
                mEmail,
                mOrigin,
                mSessionToken,
                mCreatedAt,
                mUpdatedAt,
                mParameterHashMap.toString());
    }

    private String logs() {
        return this.toString();
    }
}
