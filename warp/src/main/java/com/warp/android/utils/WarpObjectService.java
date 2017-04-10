package com.warp.android.utils;

import android.content.Context;

import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpObjectInterface;

import org.json.JSONObject;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public class WarpObjectService {

    private WarpObject object;

    Context context;
    String className;
    String sessionToken;
    DataMap body;

    private WarpObjectService(Context context) {
        this.context = context;
        body = new DataMap();
    }

    public static WarpObjectService create(Context context) {
        return new WarpObjectService(context);
    }

    public WarpObjectService setClassName(String className) {
        this.className = className;
        return this;
    }

    public WarpObjectService setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public WarpObjectService addPointer(String key, int id) {
        this.body.put(key, pointer(key, id));
        return this;
    }

    private JSONObject pointer(String className, int id) {
        try {
            JSONObject pointer = new JSONObject();
            pointer.put("className", className);
            pointer.put("type", "Pointer");
            pointer.put("id", id);
            return pointer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WarpObjectService put(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public WarpObject save(WarpObjectInterface w) {
        object = create();
        object.save(w);
        return object;
    }

    public WarpObject save(int id, WarpObjectInterface w) {
        object = create();
        object.save(id, w);
        return object;
    }

    public WarpObject destroy(int id, WarpInterface w) {
        object = create();
        object.destroy(id, w);
        return object;
    }

    public WarpObject string() {
        object = create();
        System.out.println(body.toString());
        return object;
    }

    public WarpObject create() {
        return new WarpObject(this);
    }

}
