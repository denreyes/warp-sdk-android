package com.warp.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.warp.android.utils.WarpConstants.PREF_NAME;
import static com.warp.android.utils.WarpConstants.PREF_SESSION_TOKEN;
import static com.warp.android.utils.WarpConstants.PREF_USER_ID;

/**
 * Created by Mike on 10/22/15.
 */
public class WarpSessions {

    public static void setSession(Context context, String key, String value) {
        SharedPreferences.Editor editor = Preference(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSession(Context context, String key, int value) {
        SharedPreferences.Editor editor = Preference(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getSessionToken(Context context) {
        return Preference(context).getString(PREF_SESSION_TOKEN, "");
    }

    public static int getUserId(Context context) {
        return Preference(context).getInt(PREF_USER_ID, 0);
    }

    public static void removeUserSession(Context context) {
        SharedPreferences.Editor editor = Preference(context).edit();
        editor.remove(PREF_USER_ID);
        editor.remove(PREF_SESSION_TOKEN);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = Preference(context);
        return preferences.contains(PREF_USER_ID) && preferences.contains(PREF_USER_ID);
    }

    private static SharedPreferences Preference(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}