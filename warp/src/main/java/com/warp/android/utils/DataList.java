package com.warp.android.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by TrieNoir on 25/01/2017.
 */

public class DataList extends ArrayList<WarpObject> {

    public DataList(WarpObject c) {
        super((Collection<? extends WarpObject>) c);
    }

    public DataList() {
        super();
    }

    public ArrayList<WarpObject> getArray() {
        return this;
    }

}
