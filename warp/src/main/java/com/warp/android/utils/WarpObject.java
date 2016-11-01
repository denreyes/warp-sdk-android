package com.warp.android.utils;

import com.warp.android.Warp;
import com.warp.android.http.WarpInterface;
import com.warp.android.http.models.Pointer;
import com.warp.android.http.models.Result;

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
            this.body.put(key, new Pointer(key, className, id));
            return this;
        }

        public Builder put(String key, Object value) {
            this.body.put(key, value);
            return this;
        }

        public Builder limit(int value) {
            this.body.put("limit", value);
            return this;
        }

        public Builder skip(int value) {
            this.body.put("skip", value);
            return this;
        }

        public Builder equalTo(String key, Object value) {
            if(value instanceof String) {
                this.body.put("where", String.format("{\"" + key + "\":{\"eq\":\"%s\"}}", value));
            } else {
                this.body.put("where", String.format("{\"" + key + "\":{\"eq\":%d}}", value));
            }
            return this;
        }

        public Builder notEqualTo(String key, Object value) {
            if(value instanceof String) {
                this.body.put("where", String.format("{\"" + key + "\":{\"neq\":\"%s\"}}", value));
            } else {
                this.body.put("where", String.format("{\"" + key + "\":{\"neq\":%d}}", value));
            }
            return this;
        }

        public Builder lessThan(String key, int value) {
            this.body.put("where", String.format("{\"" + key + "\":{\"lt\":%d}}", value));
            return this;
        }

        public Builder lessThanOrEqualTo(String key, int value) {
            this.body.put("where", String.format("{\"" + key + "\":{\"lte\":%d}}", value));
            return this;
        }

        public Builder greaterThan(String key, int value) {
            this.body.put("where", String.format("{\"" + key + "\":{\"gt\":%d}}", value));
            return this;
        }

        public Builder greaterThanOrEqualTo(String key, int value) {
            this.body.put("where", String.format("{\"" + key + "\":{\"gte\":%d}}", value));
            return this;
        }

        public WarpObject save(WarpInterface w) {
            object = create();
            object.save(w);
            return object;
        }

        public WarpObject save(String id, WarpInterface w) {
            object = create();
            object.save(id, w);
            return object;
        }

        public WarpObject find(WarpInterface w) {
            object = create();
            object.find(w);
            return object;
        }

        public WarpObject findById(String id, WarpInterface w) {
            object = create();
            object.findById(id, w);
            return object;
        }

        public WarpObject destroy(WarpInterface w) {
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

    public void save(final WarpInterface w) {
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
                        w.onError(e);
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(result);
                    }
                });
    }

    public void save(String id, final WarpInterface w) {
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
                        w.onError(e);
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(result);
                    }
                });
    }

    public void find(final WarpInterface w) {
        warp.getWarpService().retrieve(sessionToken, className, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        w.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        w.onError(e);
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(result);
                    }
                });
    }

    public void findById(String id, final WarpInterface w) {
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
                        w.onError(e);
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(result);
                    }
                });
    }

    public void destroy(String id, final WarpInterface w) {
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
                        w.onError(e);
                    }

                    @Override
                    public void onNext(Result result) {
                        w.onSuccess(result);
                    }
                });
    }

}
