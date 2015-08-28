package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/8/25.
 */
public class Address4Show {
    String address;
    String trueName;
    String phoneNumber;
    String addressId;

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
}
