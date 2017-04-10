package com.warp.android.utils;

import android.util.Log;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpObjectInterface;
import com.warp.android.http.interfaces.WarpQueryInterface;
import com.warp.android.http.models.Result;
import com.warp.android.http.models.ResultList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TrieNoir on 19/01/2017.
 */

public class WarpQuery {

    private Warp warp;
    private String className;
    private String sessionToken;
    private DataMap body;

    WarpQuery(WarpQueryService builder) {
        this.className = builder.className;
        this.sessionToken = builder.sessionToken;
        this.body = builder.body;
        this.warp = Warp.getInstance();
    }

    void find(final WarpQueryInterface w) {
        warp.getWarpService().retrieve(sessionToken, className, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultList>() {
                    @Override
                    public void onCompleted() {
                        w.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity.class", "e: " + e);
                        w.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(ResultList result) {
                        w.onSuccess(MapFunctions.convertToWarpObjectArray(result));
                    }
                });
    }

    void findById(int id, final WarpObjectInterface w) {
        warp.getWarpService().first(sessionToken, className, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        w.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        w.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(new WarpObject(result.getResult()));
                    }
                });
    }

    void findUserById(int id,
                             final WarpObjectInterface callback) {
        warp.getWarpService().getUserById(id)
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
                        callback.onSuccess(new WarpObject(result.getResult()));
                    }
                });
    }

    void findUsers(final WarpQueryInterface callback) {
        warp.getWarpService().getUsers(sessionToken, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultList>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(WarpCallErrorHandler.getError(e));
                    }

                    @Override
                    public void onNext(ResultList resultList) {
                        callback.onSuccess(MapFunctions.convertToWarpObjectArray(resultList));
                    }
                });
    }
}
