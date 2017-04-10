package com.warp.android.utils;

/**
 * Created by TrieNoir on 18/01/2017.
 */

public enum Sort {

    ASC(1),
    DES(-1);

    private int sort;
    Sort(int sort) {
        this.sort = sort;
    }

    public int getSort() {
        return sort;
    }
}
