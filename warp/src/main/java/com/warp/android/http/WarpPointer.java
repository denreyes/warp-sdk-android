package com.warp.android.http;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WarpPointer<T> implements Parcelable {

    private int id;
    private String type;
    private String className;

    @SerializedName("attributes")
    private T attribute;

    public WarpPointer(String type, String className, int id) {
        this.id = id;
        this.type = type;
        this.className = className;
    }

    public WarpPointer() {
    }

    protected WarpPointer(Parcel in) {
        id = in.readInt();
        type = in.readString();
        className = in.readString();
    }

    public static final Creator<WarpPointer> CREATOR = new Creator<WarpPointer>() {
        @Override
        public WarpPointer createFromParcel(Parcel in) {
            return new WarpPointer(in);
        }

        @Override
        public WarpPointer[] newArray(int size) {
            return new WarpPointer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public T getAttribute() {
        return attribute;
    }

    public void setAttribute(T attribute) {
        this.attribute = attribute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(className);
    }
}
