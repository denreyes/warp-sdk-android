package com.warp.android.http.interfaces;

import com.warp.android.utils.DataMap;
import com.warp.android.utils.WarpCallException;

/**
 * Created by TrieNoir on 20/01/2017.
 */

public interface WarpMappedResultInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess(DataMap dataMap);

}
