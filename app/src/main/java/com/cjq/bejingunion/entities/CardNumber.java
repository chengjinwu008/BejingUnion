package com.cjq.bejingunion.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJQ on 2015/9/10.
 */
public class CardNumber implements Parcelable{
    String id;
    String number;

    public CardNumber(String id, String number) {
        this.id = id;
        this.number = number;
    }

    protected CardNumber(Parcel in) {
        id = in.readString();
        number = in.readString();
    }

    public static final Creator<CardNumber> CREATOR = new Creator<CardNumber>() {
        @Override
        public CardNumber createFromParcel(Parcel in) {
            return new CardNumber(in);
        }

        @Override
        public CardNumber[] newArray(int size) {
            return new CardNumber[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(number);
    }
}
