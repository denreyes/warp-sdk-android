package com.warp.android;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpPointer extends JSONObject {

    private String mClassName = null;
    private int mId = -1;
    private WarpInterface mWarpInterface;

    private JSONObject mParameterHashMap;

    WarpUtilities mUtils = WarpUtilities.getUtils();

    public interface Result {
        void onCompleted();
        void onSuccess(WarpObject object);
        void onError(String error);
    }

    public WarpPointer(String className, int id) {
        if (id < 0) {
            throw new NumberFormatException("WarpPointer id must not be less than or equal to 0!");
        }
        if (className == null || className.length() == 0) {
            throw new NullPointerException("WarpPointer className must not be null or empty");
        }
        try {
            this.mWarpInterface = Warp.getConnectService();
            this.put("type", "Pointer");
            this.put("className", className);
            this.put("id", id);
            mClassName = className;
            mId = id;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

//    public WarpPointer getPointer(JSONObject parameter) {
//        try {
//            if (mId < 0) {
//                throw new NumberFormatException("WarpPointer id must not be less than or equal to 0!");
//            }
//            if (mClassName == null || mClassName.length() == 0) {
//                throw new NullPointerException("WarpPointer className must not be null or empty");
//            }
//            if (parameter != null && parameter.length() != 0) {
//                return new WarpPointer(parameter.getString("className"), );
//            } else {
//                throw new NullPointerException("WarpUser invalid key");
//            }
//        } catch (JSONException e) {
//            throw new NullPointerException("WarpUser invalid key");
//        }
//    }

//    public static WarpPointer getPointers(JSONObject parameter, String... key) {
//        JSONObject pointer = null;
//        try {
//            if (key != null && key.length != 0) {
//                pointer = parameter.getJSONObject(key);
//
//                return new WarpPointer(pointer.getString("className"), pointer.getInt("id"));
//            } else {
//                throw new NullPointerException("WarpUser invalid key");
//            }
//        } catch (JSONException e) {
//            throw new NullPointerException("WarpUser invalid key");
//        }
//    }


    public String getClassName() { return mClassName; }
    public int getId() { return mId; }

    private WarpPointer getPointer() {
        return this;
    }

    public void getPointerResult(Result result) {
        if (mId < 0) {
            throw new NumberFormatException("WarpPointer id must not be less than or equal to 0!");
        }
        if (mClassName == null || mClassName.length() == 0) {
            throw new NullPointerException("WarpPointer className must not be null or empty");
        }
        Observable<WarpObjectResult> pointerCall = mWarpInterface.fetch(
                mClassName,
                mId);
        receiver(pointerCall, result);
    }

    private void receiver(Observable<WarpObjectResult> call, final Result result) {
        result.onError(null);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpObjectResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(WarpObjectResult response) {
                        mParameterHashMap = mUtils.warpObjectJsonToJSONParser(response.getResult());
                        result.onSuccess(new WarpObject(mClassName, mParameterHashMap));
                    }
                });
    }
}
