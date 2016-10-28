package com.warp.android;

import org.json.JSONException;
import org.json.JSONObject;

public class WarpUserCoder {

    static final String KEY_ID = "id";
    static final String KEY_CLASS_NAME = "className";
    static final String KEY_USER = "user";
    static final String KEY_ORIGIN = "origin";
    static final String KEY_SESSION_TOKEN = "session_token";
    static final String KEY_CREATED_AT = "created_at";
    static final String KEY_UPDATED_AT = "updated_at";
    static final String KEY_FIRST_NAME = "first_name";
    static final String KEY_MIDDLE_NAME = "middle_name";
    static final String KEY_LAST_NAME = "last_name";
    static final String KEY_EMAIL = "email";

    private static final WarpUserCoder INSTANCE = new WarpUserCoder();
    public static WarpUserCoder getUserCoder() { return INSTANCE; }

    private int mId = -1;
    private String mClassName = null;
    private int mUserId = -1;
    private String mOrigin = null;
    private String mSessionToken = null;
    private String mCreatedAt = null;
    private String mUpdatedAt = null;
    private String mFirstName = null;
    private String mMiddleName = null;
    private String mLastName = null;
    private String mEmail = null;
    private JSONObject mObject = null;

    WarpUserCoder() {  }

    public JSONObject warpEncode(JSONObject object) {
        this.mObject = object;
        if (object.has(KEY_ID)) {
            try {
                mId = object.getInt(KEY_ID);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_ID);
        }

        if (object.has(KEY_CLASS_NAME)) {
            try {
                mClassName = object.getString(KEY_CLASS_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_CLASS_NAME);
        }
        if (object.has(KEY_USER)) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(object.getString(KEY_USER));
                mUserId = obj.getInt(KEY_ID);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_USER);
        }
        if (object.has(KEY_ORIGIN)) {
            try {
                mOrigin = object.getString(KEY_ORIGIN);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_ORIGIN);
        }
        if (object.has(KEY_SESSION_TOKEN)) {
            try {
                mSessionToken = object.getString(KEY_SESSION_TOKEN);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_SESSION_TOKEN);
        }
        if (object.has(KEY_CREATED_AT)) {
            try {
                mCreatedAt = object.getString(KEY_CREATED_AT);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_CREATED_AT);
        }
        if (object.has(KEY_UPDATED_AT)) {
            try {
                mUpdatedAt = object.getString(KEY_UPDATED_AT);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_UPDATED_AT);
        }
        if (object.has(KEY_FIRST_NAME)) {
            try {
                mFirstName = object.getString(KEY_FIRST_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_FIRST_NAME);
        }
        if (object.has(KEY_MIDDLE_NAME)) {
            try {
                mMiddleName = object.getString(KEY_MIDDLE_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_MIDDLE_NAME);
        }
        if (object.has(KEY_LAST_NAME)) {
            try {
                mLastName = object.getString(KEY_LAST_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_LAST_NAME);
        }
        if (object.has(KEY_EMAIL)) {
            try {
                mEmail = object.getString(KEY_EMAIL);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_EMAIL);
        }
        return mObject;
    }

    public int getId() { return mId; }
    public void setClassName(String mClassName) { this.mClassName = mClassName; }
    public String getClassName() { return mClassName; }
    public int getUserId() { return mUserId; }
    public String getOrigin() { return mOrigin; }
    public String getSessionToken() { return mSessionToken; }
    public String getCreatedAt() { return mCreatedAt; }
    public String getUpdatedAt() { return mUpdatedAt; }
    public String getEmail() { return mEmail; }
    public String getFirstName() { return mFirstName; }
    public String getMiddleName() { return mMiddleName; }
    public String getLastName() { return mLastName; }
    public JSONObject getObject() { return mObject; }
}
