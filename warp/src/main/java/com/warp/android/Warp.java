package com.warp.android;

import android.content.Context;

import com.warp.android.http.WarpRestClient;
import com.warp.android.http.WarpService;

public class Warp {

    private Context context;
    private String url, apiKey, appVersion;
    private boolean isDebug;

    private static Warp warp;
    private WarpService warpService;

    private Warp(Context context, String url, String apiKey, String appVersion, boolean isDebug) {
        this.context = context;
        this.url = url;
        this.apiKey = apiKey;
        this.appVersion = appVersion;
        this.isDebug = isDebug;
        warpService = WarpRestClient.createApiService(WarpService.class, this.context, this.url, this.apiKey, this.appVersion, isDebug);
    }

    public static void initialize(Context context, String url, String apiKey, String appVersion, boolean isDebug) {
        warp = new Warp(context, url, apiKey, appVersion, isDebug);
    }

    public static Warp getInstance() {
        if (warp == null) {
            throw new NullPointerException();
        } else {
            return warp;
        }
    }

    public Context getContext() {
        return context;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public WarpService getWarpService() {
        return this.warpService;
    }

}
