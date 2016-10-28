package com.warp.android;

import android.content.Context;
import android.provider.Settings;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Warp {

    public static String urlEndpointString;
    public static String apiKeyString;
    public static String sessionToken;
    public static String appVersion;
    public static String deviceId;

    private static Context context;
    private static Warp warpClient;
    private static Retrofit retrofit;
    private static WarpInterface warpInterface;
    private static boolean isDebugEnabled = false;

    public static Warp init(Context context, String endpoint, String apiKey, String appVersion, String sessionToken, boolean isEnabled) {
        warpClient = new Warp()
                .setContext(context)
                .setEndPoint(endpoint)
                .setApiKey(apiKey)
                .setAppVersion(appVersion)
                .setSessionToken(sessionToken)
                .setDebugEnabled(isEnabled);

        warpClient.init();
        deviceId = getDeviceId();
        return warpClient;
    }

    public static Warp get() {
        return warpClient;
    }

    private void init() {
        WarpStorage.Storage.Init(context);
        System.out.println("sessionKey: " + WarpStorage.Storage.getSessionKey());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (Warp.isDebugEnabled) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("X-Warp-API-Key", apiKeyString)
                                .header("X-Warp-Session-Token", sessionToken)
                                .header("X-Warp-Client", WarpConstants.CLIENT)
                                .header("X-Warp-Version", WarpConstants.sdkVersion(context))
                                .header("X-App-Version", appVersion)
                                .header("Content-Type", WarpConstants.CONTENT_TYPE);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(urlEndpointString)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        warpInterface = retrofit.create(WarpInterface.class);
    }

    public void unset() {
        warpClient = null;
        retrofit = null;
        warpInterface = null;
    }

    public static WarpInterface getConnectService() {
        return warpInterface;
    }

    public static String getUserSessionKey() {
        return WarpStorage.Storage.GetFile().getSessionToken();
    }

    public Warp setEndPoint(String urlEndpointString) {
        Warp.urlEndpointString = urlEndpointString;
        return this;
    }

    public Warp setApiKey(String apiKeyString) {
        Warp.apiKeyString = apiKeyString;
        return this;
    }

    public Warp setAppVersion(String appVersion) {
        Warp.appVersion = appVersion;
        return this;
    }

    public Warp setSessionToken(String sessionToken) {
        Warp.sessionToken = sessionToken;
        return this;
    }

    public Warp setContext(Context context) {
        Warp.context = context;
        return this;
    }

    public Warp setDebugEnabled(boolean isEnabled) {
        Warp.isDebugEnabled = isEnabled;
        return this;
    }

    private static String getDeviceId() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
