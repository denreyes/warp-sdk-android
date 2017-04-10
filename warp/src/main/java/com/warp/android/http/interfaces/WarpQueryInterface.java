package com.warp.android.http.interfaces;

import com.warp.android.utils.WarpCallException;
import com.warp.android.utils.WarpObject;

import java.util.ArrayList;

public interface WarpQueryInterface {

    void onCompleted();
    void onError(WarpCallException e);
    void onSuccess(ArrayList<WarpObject> warpObjects);

}
