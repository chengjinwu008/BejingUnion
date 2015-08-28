package com.cjq.bejingunion.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJQ on 2015/8/26.
 */
public class DetailChoice implements Parcelable{

    String value;
    String id;
    String src;

    public DetailChoice(String value, String id, String src) {
        this.value = value;
        this.id = id;
        this.src = src;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    protected DetailChoice(Parcel in) {
        value =  in.readString();
        id =  in.readString();
        src =  in.readString();
    }

    public static final Creator<DetailChoice> CREATOR = new Creator<DetailChoice>() {
        @Override
        public DetailChoice createFromParcel(Parcel in) {
            return new DetailChoice(in);
        }

        @Override
        public DetailChoice[] newArray(int size) {
            return new DetailChoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(id);
        dest.writeString(src);
    }
}
