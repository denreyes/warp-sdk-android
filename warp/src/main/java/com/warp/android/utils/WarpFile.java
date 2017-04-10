package com.warp.android.utils;

import android.content.Context;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpResultInterface;
import com.warp.android.http.models.Result;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TrieNoir on 16/01/2017.
 */

public class WarpFile {

    private static Warp warp;


    static {
        warp = Warp.getInstance();
    }

    public static void uploadFile(Context context,
                                  File file,
                                  String fileName,
                                  final WarpResultInterface callback) {

        String sessionToken = WarpSessions.getSessionToken(context);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);

        DataMap mapParameters = new DataMap();
        mapParameters.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
        mapParameters.put("name", RequestBody.create(MediaType.parse("text/plain"),
                fileName == null || fileName.equals("") ? file.getName() : fileName));

        warp.getWarpService().uploadFile(sessionToken, mapParameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Result result) {
                        callback.onSuccess(result);
                    }
                });
    }

    public static void deleteFile(Context context,
                                  String fileKey,
                                  final WarpResultInterface callback) {


        String sessionToken = WarpSessions.getSessionToken(context);

        warp.getWarpService().deleteFile(sessionToken, fileKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Result result) {
                        callback.onSuccess(result);
                    }
                });
    }

}
