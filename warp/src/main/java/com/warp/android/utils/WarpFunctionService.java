package com.warp.android.utils;

import android.content.Context;

import com.warp.android.http.interfaces.WarpMappedResultInterface;
import com.warp.android.http.interfaces.WarpResultInterface;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public class WarpFunctionService {

    Context context;
    String sessionToken;
    WarpFunction object;
    DataMap body;

    public WarpFunctionService(Context context) {
        this.context = context;
        this.body = new DataMap();
    }

    public static WarpFunctionService create(Context context) {
        return new WarpFunctionService(context);
    }

    public WarpFunctionService setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public WarpFunctionService put(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public WarpFunction functions(String a, WarpMappedResultInterface w) {
        object = create();
        object.functions(a, w);
        return object;
    }

    public WarpFunction functionsList(String a, WarpMappedResultInterface w) {
        object = create();
        object.functionsList(a, w);
        return object;
    }


    public WarpFunction functions(String a, WarpResultInterface w) {
        object = create();
        object.functions(a, w);
        return object;
    }

    public WarpFunction functionsList(String a, WarpResultInterface w) {
        object = create();
        object.functionsList(a, w);
        return object;
    }


    public WarpFunction string() {
        object = create();
        System.out.println(body.toString());
        return object;
    }

    public WarpFunction create() {
        return new WarpFunction(this);
    }

}
