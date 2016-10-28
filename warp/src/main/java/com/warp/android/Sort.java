package com.warp.android;

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
