package com.warp.android.utils;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpMappedResultInterface;
import com.warp.android.http.interfaces.WarpResultInterface;
import com.warp.android.http.models.Result;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TrieNoir on 19/01/2017.
 */

public class WarpFunction {

    private Warp warp;
    private String sessionToken;
    private DataMap body;

    WarpFunction(WarpFunctionService builder) {
        this.sessionToken = builder.sessionToken;
        this.body = builder.body;
        this.warp = Warp.getInstance();
    }

    void functions(String action, final WarpMappedResultInterface w) {
        warp.getWarpService().functions(sessionToken, action, body)
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
                        w.onSuccess(result.getResult());
                    }
                });
    }

    void functionsList(String action, final WarpMappedResultInterface w) {
        warp.getWarpService().functionsList(sessionToken, action, body)
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
                        w.onSuccess(result.getResult());
                    }
                });
    }


    void functions(String action, final WarpResultInterface w) {
        warp.getWarpService().functions(sessionToken, action, body)
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
                        w.onSuccess(result);
                    }
                });
    }

    void functionsList(String action, final WarpResultInterface w) {
        warp.getWarpService().functionsList(sessionToken, action, body)
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
                        w.onSuccess(result);
                    }
                });
    }
}
