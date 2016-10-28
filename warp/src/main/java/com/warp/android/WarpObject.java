package com.warp.android;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpObject extends Warp {

    private int mId = 0;
    private String mClassName = null;
    private String mCreatedAt = null;
    private String mUpdatedAt = null;

    private JSONObject mParameterHashMap;
    private WarpInterface mWarpInterface;

    private boolean isCallable = true;

    WarpUtilities mUtils = WarpUtilities.getUtils();
    WarpObjectCoder mObjectCoder = WarpObjectCoder.getObjectCoder();

    public interface Result {
        void onCompleted();
        void onSuccess(WarpObject warpObject);
        void onError(Throwable e);
    }

    public static WarpObject create(String className) {
        return new WarpObject(className);
    }

    public WarpObject(String className) {
        this.mClassName = className;

        this.mWarpInterface = Warp.getConnectService();
        this.mParameterHashMap = new JSONObject();
    }

    public WarpObject(String className, JSONObject jsonObject) {
        isCallable = false;
        this.mClassName = className;

        this.mParameterHashMap = jsonObject;
        disassemble(mParameterHashMap);
    }

    public WarpObject(String className, JsonElement element) {
        this.mClassName = className;
        this.mParameterHashMap = mUtils.warpObjectJsonToJSONParser(element.getAsJsonObject());
        disassemble(mParameterHashMap);
    }

    public WarpObject put(String key, Object value) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpObject not yet initialized!");
        }
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("WarpObject key must be not null!");
        }
        if (value == null) {
            throw new NullPointerException("WarpObject value must be not null!");
        }
        if (value instanceof String) {
//            if (((String) value).length() > 8) {
//                throw new IllegalStateException("WarpObject String value must not be less than 8 characters long.");
//            }
//            if (((String) value).isEmpty()) {
//                throw new IllegalStateException("WarpObject String value must not be empty");
//            }
            try { mParameterHashMap.put(key, value); }
            catch (JSONException e) { throw new IllegalStateException(e); }
        }
        else {
            try { mParameterHashMap.put(key, value); }
            catch (JSONException e) { throw new IllegalStateException(e); }
        }
        return this;
    }

    public WarpObject save(Result result) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpObject not yet initialized!");
        }
        if (isCallable) {
            Observable<WarpObjectResult> saveCall = mWarpInterface.save(
                    mClassName,
                    mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
            );
            receiver(saveCall, result);
        }
        return this;
    }

    public WarpObject customSave(String action, Result result) {
        Observable<WarpObjectResult> customSave = mWarpInterface.customObject(
                mClassName,
                action,
                mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
        );
        receiver(customSave, result);
        return this;
    }

    public WarpObject save(int id, Result result) {
        if (this.mParameterHashMap == null) {
            throw new NullPointerException("WarpObject not yet initialized!");
        }
        if (id < 0) {
            throw new NumberFormatException("WarpObject id must not be not less than 0");
        }
        if (isCallable) {
            Observable<WarpObjectResult> saveCall = mWarpInterface.save(
                    mClassName,
                    id,
                    mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
            );
            receiver(saveCall, result);
        }
        return this;
    }

    public WarpObject remove(int id, Result result) {
        if (id < 0) {
            throw new NumberFormatException("WarpObject id must not be not less than 0");
        }
        if (isCallable) {
            Observable<WarpObjectResult> removeCall = mWarpInterface.remove(
                    mClassName,
                    id
            );
            receiver(removeCall, result);
            clear();
        }
        return null;
    }

    public WarpObject fetch(int id, Result result) {
        if (id < 0) {
            throw new NumberFormatException("WarpObject id must not be not less than 0");
        }
        if (isCallable) {
            Observable<WarpObjectResult> fetchCall = mWarpInterface.fetch(
                    mClassName,
                    id
            );
            receiver(fetchCall, result);
        }
        return this;
    }

    public WarpObject customFetch(String action, Result result) {
        Observable<WarpObjectResult> customSave = mWarpInterface.customObject(
                mClassName,
                action,
                mUtils.warpObjectJSONtoJsonParser(mParameterHashMap)
        );
        receiver(customSave, result);
        return this;
    }

    /* Other stuff */
    public int getIntValue(String fieldName) {
        try {
            return !mParameterHashMap.isNull(fieldName) ?
                    (int) mParameterHashMap.get(fieldName) : 0;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public long getLongValue(String fieldName) {
        try {
            return !mParameterHashMap.isNull(fieldName) ?
                    (long) mParameterHashMap.get(fieldName) : 0;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public String getStringValue(String fieldName) {
        try {
            return !mParameterHashMap.isNull(fieldName) ?
                    (String) mParameterHashMap.get(fieldName) : "";
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public boolean getBooleanValue(String fieldName) {
        try {
            return !mParameterHashMap.isNull(fieldName) &&
                    (boolean) mParameterHashMap.get(fieldName);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public String getCreatedAtValue() { return mCreatedAt; }
    public String getUpdatedAtValue() { return mUpdatedAt; }
    public int getIdValue() { return mId; }
    public JSONObject getValues() { return mParameterHashMap; }

    private void receiver(Observable<WarpObjectResult> call, final Result result) {
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
                        System.out.println("onResponse:call: " + response.getMessage());
                        if (response.getStatus() == 200) {
                            System.out.println("onResponse:response:success: " + response);
                            mParameterHashMap = mUtils.warpObjectJsonToJSONParser(response.getResult());
                            disassemble(mParameterHashMap);
                            result.onSuccess(getObject());
                        } else {
                            try {
                                System.out.println("onResponse:response:error: " + response.getResult().toString());
                            } catch (Exception e) {
                                result.onError(e);
                                System.out.println("onResponse:response:error " + e.getMessage());
                            }
                        }
                    }
                });
    }

    private WarpObject getObject() {
        return this;
    }

    /* */
    private void disassemble(JSONObject jsonObject) {
        clear();
        try {
            mParameterHashMap = mObjectCoder.warpEncode(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        mId = mObjectCoder.getId();
        mCreatedAt = mObjectCoder.getCreatedAt();
        mUpdatedAt = mObjectCoder.getUpdatedAt();
//        System.out.println("WarpObject: " + logs());
    }

    /* Clear values */
    private void clear() {
        mId = -1;
        mCreatedAt = null;
        mUpdatedAt = null;
    }

    public WarpPointer getPointer(String key) {
        JSONObject pointer;
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("WarpObject parameter object is null");
        }
        if (mParameterHashMap == null || mParameterHashMap.length() == 0) {
            throw new NullPointerException("WarpObject key is null or empty");
        }
        try {
            pointer = mParameterHashMap.getJSONObject(key);
            return new WarpPointer(key, pointer.getInt("id"));
        } catch (JSONException e) {
            throw new NullPointerException("WarpObject invalid key");
        }
    }

    /* */
    @Override
    public String toString() {
        return String.format(Locale.US, "%s[" +
                        "className=%s,id=%s,createdAt=%s,updatedAt=%s,savedData=%s" +
                        "]",
                getClass().getName(),
                mClassName,
                String.valueOf(mId),
                mCreatedAt,
                mUpdatedAt,
                mParameterHashMap.toString());
    }

    private String logs() {
        return toString();
    }
}
