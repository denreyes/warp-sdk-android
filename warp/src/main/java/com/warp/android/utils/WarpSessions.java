package com.warp.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mike on 10/22/15.
 */
public class WarpSessions {

    public static void setSession(Context context, String key, String value) {
        SharedPreferences spDefault = context.getSharedPreferences("Default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spDefault.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSession(Context context, String key) {
        SharedPreferences spDefault = context.getSharedPreferences("Default", Context.MODE_PRIVATE);
        String value = spDefault.getString(key, "");
        return value;
    }

    public static String getSessionToken(Context context) {
        SharedPreferences spDefault = context.getSharedPreferences("Default", Context.MODE_PRIVATE);
        String value = spDefault.getString("session_token", "");
        return value;
    }

    public static String getUserId(Context context) {
        SharedPreferences spDefault = context.getSharedPreferences("Default", Context.MODE_PRIVATE);
        String value = spDefault.getString("user_id", "");
        return value;
    }
}