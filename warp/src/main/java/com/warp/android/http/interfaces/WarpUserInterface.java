package com.warp.android.http.interfaces;

import com.warp.android.utils.WarpCallException;
import com.warp.android.utils.WarpUser;

/**
 * Created by TrieNoir on 20/01/2017.
 */

public interface WarpUserInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess(WarpUser warpUser);

}
