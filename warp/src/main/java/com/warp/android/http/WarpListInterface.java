package com.warp.android.http;

import com.warp.android.http.models.ResultList;

public interface WarpListInterface {

    void onCompleted();
    void onError(Throwable e);
    void onSuccess(ResultList result);

}
