package com.warp.android.utils;

import static com.warp.android.utils.WarpConstants.SPK_USER_ID;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public class WarpPointer {

    private String mClassName;
    private int mId;
    private String mType;


    WarpPointer(String key, Object pointer) {
        this.mClassName = key;
        map(MapFunctions.convertJsonToMap(pointer));
    }

    private void map(DataMap dataMap) {

        MapFunctions.setData(dataMap);
        this.mId = MapFunctions.getInteger(SPK_USER_ID, true);
        MapFunctions.getString("className", true);
        this.mType = MapFunctions.getString("type", true);
//        this.body = MapFunctions.getData();

    }

    public String getClassName() { return mClassName; }
    public int getId() { return mId; }
    public String getType() { return mType; }
}
