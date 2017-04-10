package com.warp.android.http.interfaces;

import com.warp.android.http.models.Result;
import com.warp.android.utils.WarpCallException;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public interface WarpResultInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess(Result result);

}
