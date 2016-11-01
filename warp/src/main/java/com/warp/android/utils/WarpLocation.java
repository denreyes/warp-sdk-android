package com.warp.android.utils;

import android.content.Context;

import com.warp.android.Warp;
import com.warp.android.http.WarpInterface;
import com.warp.android.http.models.Location;
import com.warp.android.http.models.Result;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpLocation {

    private static Warp warp;

    static {
        warp = Warp.getInstance();
    }

    public static void saveLocation(final Context con, String address, String city, String province, final WarpInterface callback) {
        warp.getWarpService().createLocation(new Location(address, city, province))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Result a) {
                        callback.onSuccess(a);
                    }
                });
    }
}
