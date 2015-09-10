package com.cjq.bejingunion.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJQ on 2015/8/25.
 */
public class Address4Show implements Parcelable{
    String address;
    String trueName;
    String phoneNumber;
    String addressId;

    String areaId;
    String cityId;

    boolean mDefault=false;

    protected Address4Show(Parcel in) {
        address = in.readString();
        trueName = in.readString();
        phoneNumber = in.readString();
        addressId = in.readString();
        areaId = in.readString();
        cityId = in.readString();
        mDefault = in.readByte() != 0;
    }

    public static final Creator<Address4Show> CREATOR = new Creator<Address4Show>() {
        @Override
        public Address4Show createFromParcel(Parcel in) {
            return new Address4Show(in);
        }

        @Override
        public Address4Show[] newArray(int size) {
            return new Address4Show[size];
        }
    };

    public boolean ismDefault() {
        return mDefault;
    }

    public void setmDefault(boolean mDefault) {
        this.mDefault = mDefault;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Address4Show(String address, String trueName, String phoneNumber, String addressId) {
        this.address = address;
        this.trueName = trueName;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(trueName);
        dest.writeString(phoneNumber);
        dest.writeString(addressId);
        dest.writeString(areaId);
        dest.writeString(cityId);
        dest.writeByte((byte) (mDefault ? 1 : 0));
    }
}
