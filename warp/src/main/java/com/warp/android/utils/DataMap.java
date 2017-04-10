package com.warp.android.utils;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public class DataMap extends HashMap<String, Object>{

    public DataMap(LinkedTreeMap m) { super(m); }

    public DataMap() { super(); }

    public String getString(String key) {
        return String.valueOf(this.get(key));
    }

    public Integer getInt(String key) {
        return (Integer) this.get(key);
    }

    public Float getFloat(String key) {
        return (Float) this.get(key);
    }

    public HashMap<String,Object> getHash() {
        return this;
    }

    public DataMap getNestedHash(String key) {
        return (DataMap) this.get(key);
    }

    public DataList getNestedArray(String key) {
        ArrayList arrayList = (ArrayList) this.get(key);
        DataList newList = new DataList();
        for (int i = 0; i < arrayList.size(); i++) {
            DataMap dataMap = new DataMap((LinkedTreeMap) arrayList.get(i));
            WarpObject warpObject = new WarpObject(dataMap);
            newList.add(warpObject);
        }
        return newList;
    }
}
