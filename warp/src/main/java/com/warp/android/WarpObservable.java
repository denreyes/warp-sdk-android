package com.warp.android;

import java.util.Observable;

public class WarpObservable extends Observable {

    private Object arrayResult;

    public WarpObservable(Object arrayResult) {
        this.arrayResult = arrayResult;
    }

    public Object getArrayResult() {
        return arrayResult;
    }

    public void setArrayResult(Object arrayResult) {
        this.arrayResult = arrayResult;
        setChanged();
        notifyObservers();
    }
}
