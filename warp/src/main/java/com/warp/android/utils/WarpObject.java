package com.warp.android.utils;

import com.warp.android.Warp;
import com.warp.android.http.WarpCallback;
import com.warp.android.http.WarpPointer;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WarpObject {

    private Warp warp;
    private String className;
    private String sessionToken;
    private HashMap<String, Object> body;

    public static class Builder {
        private String className;
        private String sessionToken;
        private WarpObject object;
        private HashMap<String, Object> body;

        public Builder() {
            body = new HashMap<>();
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        public Builder addPointer(String key, String className, int id) {
            this.body.put(key, new WarpPointer(key, className, id));
            return this;
        }

        public Builder put(String key, Object value) {
            this.body.put(key, value);
            return this;
        }

        public WarpObject save(WarpCallback callback) {
            object = create();
            object.save(callback);
            return object;
        }

        public WarpObject save(String id, WarpCallback callback) {
            object = create();
            object.save(id, callback);
            return object;
        }

        public WarpObject find(WarpCallback callback) {
            object = create();
            object.find(callback);
            return object;
        }

        public WarpObject findById(String id, WarpCallback callback) {
            object = create();
            object.findById(id, callback);
            return object;
        }

        public WarpObject destroy(WarpCallback callback) {
            object = create();
            return object;
        }

        public WarpObject create() {
            return new WarpObject(this);
        }
    }

    private WarpObject(Builder builder) {
        this.className = builder.className;
        this.sessionToken = builder.sessionToken;
        this.body = builder.body;
        this.warp = Warp.getInstance();
    }

    public void save(final WarpCallback callback) {
        warp.getWarpService().insert(sessionToken, className, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpResult>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(WarpResult result) {
                        callback.onSuccess(result);
                    }
                });
    }

    public void save(String id, final WarpCallback callback) {
        warp.getWarpService().update(sessionToken, className, id, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpResult>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(WarpResult result) {
                        callback.onSuccess(result);
                    }
                });
    }

    public void find(final WarpCallback callback) {
        warp.getWarpService().findAll(sessionToken, className, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpResult>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(WarpResult result) {
                        callback.onSuccess(result);
                    }
                });
    }

    public void findById(String id, final WarpCallback callback) {
        warp.getWarpService().first(sessionToken, className, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpResult>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(WarpResult result) {
                        callback.onSuccess(result);
                    }
                });
    }

    public void destroy(String id, final WarpCallback callback) {
        warp.getWarpService().delete(sessionToken, className, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpResult>() {
                    @Override
                    public void onCompleted() {
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(WarpResult result) {
                        callback.onSuccess(result);
                    }
                });
    }

}
