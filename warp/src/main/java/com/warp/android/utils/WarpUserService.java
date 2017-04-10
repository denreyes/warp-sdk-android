package com.warp.android.utils;

import android.content.Context;

import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpUserInterface;

/**
 * Created by TrieNoir on 20/01/2017.
 */

public class WarpUserService {

    private WarpUser object;

    Context context;
    DataMap body;

    private WarpUserService(Context context) {
        this.context = context;
        this.body = new DataMap();
    }

    public static WarpUserService create(Context context) {
        return new WarpUserService(context);
    }

    public WarpUserService put(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public WarpUser login(String u, String p, WarpUserInterface w) {
        object = create();
        object.login(u, p, w);
        return object;
    }

    public WarpUser logout(WarpInterface w) {
        object = create();
        object.logout(w);
        return object;
    }

    public WarpUser register(String u, String p, String e, WarpUserInterface w) {
        object = create();
        object.register(u, p, e, w);
        return object;
    }

    public WarpUser save(WarpUserInterface w) {
        object = create();
        object.save(w);
        return object;
    }

    public WarpUser string() {
        object = create();
        System.out.println(body.toString());
        return object;
    }

    public WarpUser create() {
        return new WarpUser(this);
    }

}
