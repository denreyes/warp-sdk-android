package com.warp.android.http;

import com.warp.android.utils.WarpResult;

public interface WarpCallback {

    void onCompleted();
    void onError(Throwable e);
    void onSuccess(WarpResult result);

}
