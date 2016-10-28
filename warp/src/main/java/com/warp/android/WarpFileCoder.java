package com.warp.android;

import org.json.JSONException;
import org.json.JSONObject;

public class WarpFileCoder {

    static final String KEY_FILE_KEY = "key";
    static final String KEY_FILE_URL = "url";

    private static final WarpFileCoder INSTANCE = new WarpFileCoder();
    public static WarpFileCoder getFileCoder() { return INSTANCE; }

    private String mKey = null;
    private String mUrl = null;
    private JSONObject mObject = null;

    WarpFileCoder() {  }

    public JSONObject warpEncode(JSONObject object) {this.mObject = object;
        if (object.has(KEY_FILE_KEY)) {
            try {
                mKey = object.getString(KEY_FILE_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_FILE_KEY);
        }
        if (object.has(KEY_FILE_URL)) {
            try {
                mUrl = object.getString(KEY_FILE_URL);
            } catch (JSONException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            object.remove(KEY_FILE_URL);
        }
        return mObject;
    }

    public String getKey() { return mKey; }
    public String getUrl() { return mUrl; }
}
