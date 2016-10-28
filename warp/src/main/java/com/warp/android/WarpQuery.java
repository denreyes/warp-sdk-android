package com.warp.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpQuery implements Iterator<WarpObject> {

    private String mClassName;
    private List<WarpObject> mWarpObjectList;
    private JsonArray mWarpObjectRawList;

    private WarpInterface mWarpInterface;

    private WarpObserver mWarpObserver;
    private WarpObservable mWarpObservable;

    private JSONObject mWhereQueryObject;
    private JSONArray mIncludeQueryObject;
    private JSONArray mOrderQueryObject;
    private int mSkipNumber = 0;                //Default value for SKIP
    private int mLimitNumber = 100;             //Default value for LIMIT

    public interface Result {
        void onCompleted();
        void onSuccess(WarpQuery warpQuery);
        void onError(String error);
    }

    public static WarpQuery create(String className) {
        return new WarpQuery(className);
    }

    public WarpQuery(String className) {
        this.mClassName = className;

        this.mWarpInterface = Warp.getConnectService();
        this.mWarpObjectList = new ArrayList<>();

        this.mWarpObserver = new WarpObserver();
        this.mWarpObservable = new WarpObservable(null);
    }

    public WarpQuery find(Result result) {

        String whereQuery = null;
        String includeQuery = null;
        String orderQuery = null;
        try {
            if (mWhereQueryObject != null) whereQuery = URLEncoder.encode(mWhereQueryObject.toString(), "UTF-8");
            if (mIncludeQueryObject != null) includeQuery = URLEncoder.encode(mIncludeQueryObject.toString(), "UTF-8");
            if (mOrderQueryObject != null) orderQuery = URLEncoder.encode(mOrderQueryObject.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Observable<WarpQueryResult> findCall = mWarpInterface.find(
                mClassName,
                whereQuery,
                includeQuery,
                orderQuery,
                mSkipNumber,
                mLimitNumber
        );
        receiver(findCall, result);
        return this;
    }

    public WarpQuery customFind(String action, Result result) {
        Observable<WarpQueryResult> customCall = mWarpInterface.customQuery(
                mClassName,
                action,
                WarpUtilities.getUtils().warpObjectJSONtoJsonParser(mWhereQueryObject)
        );
        receiver(customCall, result);
        return this;
    }

    public WarpQuery where(String key, WarpConstraints constraints) {
        if (mWhereQueryObject == null) {
            mWhereQueryObject = new JSONObject();
        }
        try {
            mWhereQueryObject.put(key, constraints);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public WarpQuery order(String key, Sort sort) {
        if (mOrderQueryObject == null) {
            mOrderQueryObject = new JSONArray();
        }
        try {
            mOrderQueryObject.put(new JSONObject().put(key, sort.getSort()));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public WarpQuery include(String... keys) {
        if (mIncludeQueryObject == null) {
            mIncludeQueryObject = new JSONArray();
        }
        for (String key : keys) {
            mIncludeQueryObject.put(key);
        }
        return this;
    }

    public WarpQuery skip(int skipRow) {
        mSkipNumber = skipRow;
        return this;
    }

    public WarpQuery limit(int limitRow) {
        mLimitNumber = limitRow;
        return this;
    }

    /*  */
    private void receiver(Observable<WarpQueryResult> call, final Result result) {
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpQueryResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(WarpQueryResult response) {
                        if (response.getStatus() == 200) {
                            List<WarpObject> resultObject = parser(response.getResult());
                            mWarpObjectRawList = response.getResult();
                            mWarpObserver.setResultInterface(new WarpObserver.WarpResultInterface() {
                                @Override
                                public void onResult(Object o) {
                                    mWarpObjectList = (List<WarpObject>) o;
                                    result.onSuccess(getQuery());
                                }
                            });
                            mWarpObservable.addObserver(mWarpObserver);
                            mWarpObservable.setArrayResult(resultObject);
                        } else {
                            try {
                                result.onError(response.toString());
                                System.out.println("onResponse:response:error: " + response.toString());
                            } catch (Exception e) {
                                result.onError(e.getMessage());
                                System.out.println("onResponse:response:error " + e.getMessage());
                            }
                        }
                    }
                });
    }

    public List<WarpObject> getWarpObjectList() {
        return mWarpObjectList;
    }

    public WarpObject getWarpObject(String key, Object value) {
        WarpObject object = null;
        for (WarpObject object1 : mWarpObjectList) {

            if (value instanceof String) {
                if (object1.getStringValue(key).equals(value)) {
                    object = object1;
                }
            } else if (value instanceof Integer) {
                if (object1.getIntValue(key) == (int) value) {
                    object = object1;
                }
            } else if (value instanceof Boolean) {
                if (object1.getBooleanValue(key) == (boolean) value) {
                    object = object1;
                }
            }
        }
        return object;
    }

    public JsonArray getRawList() {
        return mWarpObjectRawList;
    }

    private WarpQuery getQuery() {
        return this;
    }

    private List<WarpObject> parser(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            mWarpObjectList.add(new WarpObject(mClassName, element));
        }
        return mWarpObjectList;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public WarpObject next() {
        return null;
    }

    @Override
    public void remove() {

    }
}
