package com.warp.android.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class WarpConstants {

    public static final String CONTENT_TYPE = "application/json";
    public static final String CLIENT = "android";

    public static String sdkVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    static final String SPK_USER_ID = "id";
    static final String SPK_CREATED_AT = "created_at";
    static final String SPK_UPDATED_AT = "updated_at";

    static final String PREF_NAME = "WARP_Default";
    static final String PREF_SESSION_TOKEN = "warp_session_token";
    static final String PREF_USER_NAME = "warp_user_name";
    static final String PREF_USER_ID = "warp_session_id";

    static final String OPT_INCLUDE = "include";
    static final String OPT_LIMIT = "limit";
    static final String OPT_SKIP = "skip";
    static final String OPT_WHERE = "where";
    static final String OPT_SORT = "sort";

    static final String CONS_EQ = "eq";
    static final String CONS_NEQ = "neq";
    static final String CONS_GT = "gt";
    static final String CONS_GTE = "gte";
    static final String CONS_LT = "lt";
    static final String CONS_LTE = "lte";
    static final String CONS_EX = "ex";
    static final String CONS_IN = "in";
    static final String CONS_NIN = "nin";
    static final String CONS_STR = "str";
    static final String CONS_END = "end";
    static final String CONS_HAS = "contains";
}
