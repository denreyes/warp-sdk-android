package com.warp.android.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrieNoir on 23/01/2017.
 */

public abstract class Transformer<E, F> {
    abstract F transform(E e);

    public List<F> transform(List<E> list) {
        List<F> newList = new ArrayList<F>();
        for (E e : list) {
            newList.add(transform(e));
        }
        return newList;
    }
}
