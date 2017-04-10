package com.warp.android.utils;

import android.content.Context;

import com.warp.android.http.interfaces.WarpObjectInterface;
import com.warp.android.http.interfaces.WarpQueryInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import static com.warp.android.utils.WarpConstants.CONS_END;
import static com.warp.android.utils.WarpConstants.CONS_EQ;
import static com.warp.android.utils.WarpConstants.CONS_EX;
import static com.warp.android.utils.WarpConstants.CONS_GT;
import static com.warp.android.utils.WarpConstants.CONS_GTE;
import static com.warp.android.utils.WarpConstants.CONS_HAS;
import static com.warp.android.utils.WarpConstants.CONS_IN;
import static com.warp.android.utils.WarpConstants.CONS_LT;
import static com.warp.android.utils.WarpConstants.CONS_LTE;
import static com.warp.android.utils.WarpConstants.CONS_NEQ;
import static com.warp.android.utils.WarpConstants.CONS_NIN;
import static com.warp.android.utils.WarpConstants.CONS_STR;
import static com.warp.android.utils.WarpConstants.OPT_INCLUDE;
import static com.warp.android.utils.WarpConstants.OPT_LIMIT;
import static com.warp.android.utils.WarpConstants.OPT_SKIP;
import static com.warp.android.utils.WarpConstants.OPT_SORT;
import static com.warp.android.utils.WarpConstants.OPT_WHERE;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public class WarpQueryService {

    private WarpQuery object;
    private JSONObject equals, sort;

    Context context;
    String className;
    String sessionToken;
    DataMap body;

    public WarpQueryService(Context context) {
        this.context = context;
        this.body = new DataMap();
        this.equals = new JSONObject();
        this.sort = new JSONObject();
    }

    public static WarpQueryService create(Context context) {
        return new WarpQueryService(context);
    }

    public WarpQueryService setClassName(String className) {
        this.className = className;
        return this;
    }

    public WarpQueryService setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public WarpQueryService include(String... values) {
        JSONArray includeArray = new JSONArray();
        for (String value : values) { includeArray.put(value); }
        this.body.put(OPT_INCLUDE, includeArray.toString());
        return this;
    }

    public WarpQueryService limit(int value) {
        this.body.put(OPT_LIMIT, value);
        return this;
    }

    public WarpQueryService sort(String key, Sort sort) {
        try {
            this.sort.put(key, sort.getSort());
            this.body.put(OPT_SORT, this.sort.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService skip(int value) {
        this.body.put(OPT_SKIP, value);
        return this;
    }

    public WarpQueryService equalTo(String key, Object value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_EQ, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService notEqualTo(String key, Object value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_NEQ, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService lessThan(String key, int value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_LT, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService lessThanOrEqualTo(String key, int value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_LTE, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService greaterThan(String key, int value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_GT, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService greaterThanOrEqualTo(String key, int value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_GTE, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService exists(String key, boolean value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_EX, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService containedIn(String key, Object... value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_IN, new JSONArray(Arrays.asList(value))));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService notContainedIn(String key, Object... value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_NIN, new JSONArray(Arrays.asList(value))));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService beginsWith(String key, String value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_STR, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService endsWith(String key, String value) {
        try {
            this.equals.put(key, new JSONObject().put(CONS_END, value));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQueryService contains(String key, String... values) {
        try {
            StringBuilder hasBuilder = new StringBuilder();
            for (String value : values) {
                hasBuilder.append(value);
                int idx = hasBuilder.length() - value.length();
                while (idx > 0) {
                    hasBuilder.insert(idx, "|");
                    idx = 0;
                }
            }
            this.equals.put(key, new JSONObject().put(CONS_HAS, hasBuilder));
            this.body.put(OPT_WHERE, equals.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    public WarpQuery find(WarpQueryInterface w) {
        object = create();
        object.find(w);
        return object;
    }

    public WarpQuery findById(int id, WarpObjectInterface w) {
        object = create();
        object.findById(id, w);
        return object;
    }

    public WarpQuery findUserById(int id, WarpObjectInterface w) {
        object = create();
        object.findUserById(id, w);
        return object;
    }

    public WarpQuery findUsers(WarpQueryInterface w) {
        object = create();
        object.findUsers(w);
        return object;
    }

    public WarpQuery string() {
        object = create();
        System.out.println(body.toString());
        return object;
    }

    public WarpQuery create() {
        return new WarpQuery(this);
    }

}
