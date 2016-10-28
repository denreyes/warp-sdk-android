package com.warp.android;

import org.json.JSONException;
import org.json.JSONObject;

public class WarpObjectCoder {

    static final String KEY_ID = "id";
    static final String KEY_CLASS_NAME = "className";
    static final String KEY_CREATED_AT = "created_at";
    static final String KEY_UPDATED_AT = "updated_at";

    private static final WarpObjectCoder INSTANCE = new WarpObjectCoder();
    public static WarpObjectCoder getObjectCoder() { return INSTANCE; }

    private int mId = 0;
    private String mClassName = null;
    private String mCreatedAt = null;
    private String mUpdatedAt = null;
    private JSONObject mObject = null;

    WarpObjectCoder() {}

    public JSONObject warpEncode(JSONObject object) throws JSONException {
        this.mObject = object;
        if (object.has(KEY_ID)) {
            mId = object.getInt(KEY_ID);
            object.remove(KEY_ID);
        }
        if (object.has(KEY_CLASS_NAME)) {
            mClassName = object.getString(KEY_CLASS_NAME);
            object.remove(KEY_CLASS_NAME);
        }
        if (object.has(KEY_CREATED_AT)) {
            mCreatedAt = object.getString(KEY_CREATED_AT);
            object.remove(KEY_CREATED_AT);
        }
        if (object.has(KEY_UPDATED_AT)) {
            mUpdatedAt = object.getString(KEY_UPDATED_AT);
            object.remove(KEY_UPDATED_AT);
        }
        return mObject;
    }

    public void warpDecode(JSONObject object) {

    }

    public int getId() { return mId; }
    public void setClassName(String mClassName) { this.mClassName = mClassName; }
    public String getClassName() { return mClassName; }
    public String getCreatedAt() { return mCreatedAt; }
    public String getUpdatedAt() { return mUpdatedAt; }


}
