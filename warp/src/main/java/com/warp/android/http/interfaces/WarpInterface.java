package com.warp.android.http.interfaces;

import com.warp.android.utils.WarpCallException;

public interface WarpInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess();

}
