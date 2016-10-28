package com.warp.android;

import java.util.Observable;
import java.util.Observer;

public class WarpObserver implements Observer {

    private WarpObservable warpObservable;
    private WarpResultInterface resultInterface;
    public interface WarpResultInterface {
        void onResult(Object o);
    }

    @Override
    public void update(Observable o, Object arg) {
        warpObservable = (WarpObservable) o;
        Object arrayResult = warpObservable.getArrayResult();
        if (resultInterface == null) {
            throw new NullPointerException("WarpObserver is null!");
        }
        resultInterface.onResult(arrayResult);
    }

    public void setResultInterface(WarpResultInterface resultInterface) {
        this.resultInterface = resultInterface;
    }
}
