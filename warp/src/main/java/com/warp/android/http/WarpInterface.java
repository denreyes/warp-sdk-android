package com.warp.android.http;

import com.warp.android.http.models.Result;

public interface WarpInterface {

    void onCompleted();
    void onError(Throwable e);
    void onSuccess(Result result);

}
