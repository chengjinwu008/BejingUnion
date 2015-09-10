package com.cjq.bejingunion.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJQ on 2015/9/10.
 */
public class AreaInfo implements Parcelable{
    int provinceId;
    int areaId;
    int cityId;

    String provinceName;
    String areaName;
    String cityName;

    public AreaInfo() {
    }

    protected AreaInfo(Parcel in) {
        provinceId = in.readInt();
        areaId = in.readInt();
        cityId = in.readInt();
        provinceName = in.readString();
        areaName = in.readString();
        cityName = in.readString();
    }

    public static final Creator<AreaInfo> CREATOR = new Creator<AreaInfo>() {
        @Override
        public AreaInfo createFromParcel(Parcel in) {
            return new AreaInfo(in);
        }

        @Override
        public AreaInfo[] newArray(int size) {
            return new AreaInfo[size];
        }
    };

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(provinceId);
        dest.writeInt(areaId);
        dest.writeInt(cityId);
        dest.writeString(provinceName);
        dest.writeString(areaName);
        dest.writeString(cityName);
    }
}
