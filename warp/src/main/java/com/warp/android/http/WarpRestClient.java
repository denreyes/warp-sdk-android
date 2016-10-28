package com.warp.android.http;

import android.content.Context;

import com.warp.android.utils.WarpConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WarpRestClient {

    public static <T> T createApiService(final Class<T> clazz, final Context con, final String endpoint,
                                         final String apiKey, final String appVersion, final boolean isDebug) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (isDebug) {
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
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("X-Warp-API-Key", apiKey)
                                .header("X-Warp-Client", WarpConstants.CLIENT)
                                .header("X-Warp-Version", WarpConstants.sdkVersion(con))
                                .header("X-App-Version", appVersion)
                                .header("Content-Type", WarpConstants.CONTENT_TYPE);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(clazz);
    }
}
