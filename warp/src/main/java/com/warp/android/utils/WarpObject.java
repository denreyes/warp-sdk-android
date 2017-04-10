package com.warp.android.utils;

import android.util.Log;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpObjectInterface;
import com.warp.android.http.models.Result;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.warp.android.utils.WarpConstants.SPK_CREATED_AT;
import static com.warp.android.utils.WarpConstants.SPK_UPDATED_AT;
import static com.warp.android.utils.WarpConstants.SPK_USER_ID;


public class WarpObject implements WarpModel {

    private Warp warp;
    private String className;
    private String sessionToken;
    private DataMap body;

    private int mId;
    private String mCreatedAt, mUpdatedAt;
    private DataMap callResult, copy;

    public WarpObject(WarpObjectService builder) {
        this.className = builder.className;
        this.sessionToken = builder.sessionToken;
        this.body = builder.body;
        this.warp = Warp.getInstance();
    }

    public WarpObject(DataMap hashMap) {
        map(hashMap);
    }

    private void map(DataMap hashMap) {

        this.copy = hashMap;

        MapFunctions.setData(hashMap);
        this.mId = MapFunctions.getInteger(SPK_USER_ID, true);
        this.mCreatedAt = MapFunctions.getString(SPK_CREATED_AT, true);
        this.mUpdatedAt = MapFunctions.getString(SPK_UPDATED_AT, true);
        this.callResult = MapFunctions.getData();
    }

    public int getId() { return mId; }
    public String getCreatedAt() { return mCreatedAt; }
    public String getUpdatedAt() { return mUpdatedAt; }
    public DataMap getResult() { return callResult; }


    public WarpPointer getPointer(String key) {
        Log.e("MainActivity.class", "result: " + callResult);
        MapFunctions.setData(callResult);
        Object value = MapFunctions.getObject(key, false);
        return new WarpPointer(key, value);
    }

    void save(final WarpObjectInterface w) {
        warp.getWarpService().create(sessionToken, className, body)
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
                        map(result.getResult());
                        w.onSuccess(new WarpObject(result.getResult()));
                    }
                });
    }

    void save(int id, final WarpObjectInterface w) {
        warp.getWarpService().update(sessionToken, className, id, body)
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
                        map(result.getResult());
                        w.onSuccess(new WarpObject(result.getResult()));
                    }
                });
    }

    void destroy(int id, final WarpInterface w) {
        warp.getWarpService().delete(sessionToken, className, id)
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
                        w.onSuccess();
                    }
                });
    }

    @Override
    public String toString() {
        return "WarpObject == " +
                "[id=" + mId + "] " + callResult +
                " [created_at='" + mCreatedAt + "', updated_at='" + mUpdatedAt + "\']";
    }
}
