package com.warp.android.utils;

import com.google.gson.internal.LinkedTreeMap;
import com.warp.android.http.models.ResultList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TrieNoir on 20/01/2017.
 */

public class MapFunctions {

    private static DataMap sDataMap;

    public static DataMap getData() {
        return sDataMap;
    }

    public static void setData(DataMap dataMap) {
        if (dataMap != null && dataMap.isEmpty()) { MapFunctions.sDataMap.clear(); }
        MapFunctions.sDataMap = dataMap;
    }

    public static Object get(String key, boolean remove) {
        Object object = null;
        Iterator<Map.Entry<String, Object>> it = sDataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getKey().equals(key)) {
                object = entry.getValue();
                if (remove) it.remove();
                break;
            }
        }
        return object;
    }

    public static String getString(String key, boolean remove) { return String.valueOf(get(key, remove)); }
    public static Integer getInteger(String key, boolean remove) { return ((Double) get(key, remove)).intValue(); }
    public static Float getFloat(String key, boolean remove) { return (Float) get(key, remove); }
    public static Object getObject(String key, boolean remove) { return get(key, remove); }

    public static ArrayList<WarpObject> convertToWarpObjectArray(ResultList result) {
        ArrayList<DataMap> hashMaps = result.getResult();
        ArrayList<WarpObject> warpObjects = new ArrayList<>();
        for (int i = 0; i < hashMaps.size(); i++) {
            WarpObject warpObject = new WarpObject(hashMaps.get(i));
            warpObjects.add(warpObject);
        }
        return warpObjects;
    }

    public static DataMap convertJsonToMap(Object json){
        try {
            DataMap map = new DataMap();

            LinkedTreeMap mapped = (LinkedTreeMap) json;

            Iterator entries = mapped.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                map.put(key, value);
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
