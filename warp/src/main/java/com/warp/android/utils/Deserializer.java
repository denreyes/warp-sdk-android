package com.warp.android.utils;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TrieNoir on 24/01/2017.
 */

public class Deserializer {

    @SuppressWarnings("unchecked")
    public static DataMap serialize(DataMap dataMap) {
        DataMap map = new DataMap();

        Iterator<Map.Entry<String, Object>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object object = entry.getValue();
            if (object instanceof LinkedTreeMap) {

                LinkedTreeMap<String,Object> treeMap = (LinkedTreeMap<String,Object>) object;

                HashMap<String,Object> mapper = new HashMap<>(treeMap);
                mapper.putAll(treeMap);

                map.put(entry.getKey(), mapper);

            } else {
                map.put(entry.getKey(), object);
            }
        }


        return map;
    }

}
