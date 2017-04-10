package com.warp.android.http.interfaces;

import com.warp.android.utils.WarpCallException;
import com.warp.android.utils.WarpObject;

/**
 * Created by TrieNoir on 19/01/2017.
 */

public interface WarpObjectInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess(WarpObject warpObject);

}
