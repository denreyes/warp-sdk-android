package com.warp.android;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WarpFile {

    private File mFile;
    private String mKey;
    private String mUrl;

    private JSONObject mParameterHashMap;
    private WarpInterface mWarpInterface;

    WarpUtilities mUtils = WarpUtilities.getUtils();
    WarpFileCoder mFileCoder = WarpFileCoder.getFileCoder();

    public interface Result {
        void onCompleted();

        void onSuccess(WarpFile warpFile);

        void onError(Throwable e);
    }

    public static WarpFile create(String fileLocation) {
        return new WarpFile(fileLocation);
    }

    public WarpFile(String fileLocation) {
        if (fileLocation == null || fileLocation.isEmpty()) {
            throw new NullPointerException("");
        }
        this.mWarpInterface = Warp.getConnectService();
        this.mFile = new File(fileLocation);
    }

    public WarpFile upload(Result result) {

        if (!mFile.exists()) {
            try {
                throw new FileNotFoundException("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), mFile);

        Map<String, RequestBody> mapParameters = new HashMap<>();
        mapParameters.put("file\"; filename=\"image.jpg\"", fileBody);
        mapParameters.put("name", RequestBody.create(MediaType.parse("text/plain"), mFile.getName()));

        Observable<WarpFileResult> uploadCall = mWarpInterface.upload(mapParameters);
        receiver(uploadCall, result);
        return this;
    }

    private void receiver(Observable<WarpFileResult> call, final Result result) {
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WarpFileResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.onError(e);
                    }

                    @Override
                    public void onNext(WarpFileResult response) {
                        System.out.println("onResponse:call: " + response.toString());
                        if (response.getStatus() == 200) {
                            System.out.println("onResponse:response:success: " + response);
                            mParameterHashMap = mUtils.warpObjectJsonToJSONParser(response.getResult());
                            disassemble(mParameterHashMap);

                            result.onSuccess(getFile());
                        } else {
                            try {
                                System.out.println("onResponse:response:error " + response.toString());
                            } catch (Exception e) {
                                result.onError(e);
                                System.out.println("onResponse:response:error " + e.getMessage());
                            }
                        }
                    }
                });
    }

    public String getKey() {
        return mKey;
    }

    public String getUrl() {
        return mUrl;
    }

    private WarpFile getFile() {
        return this;
    }

    private void disassemble(JSONObject jsonObject) {
        mParameterHashMap = mFileCoder.warpEncode(jsonObject);
        mKey = mFileCoder.getKey();
        mUrl = mFileCoder.getUrl();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[" +
                        "key=%s,url=%s,savedData=%s" +
                        "]",
                getClass().getName(),
                mKey,
                mUrl,
                mParameterHashMap.toString());
    }
}
