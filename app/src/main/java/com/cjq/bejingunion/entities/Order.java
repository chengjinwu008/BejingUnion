package com.cjq.bejingunion.entities;

import java.util.List;

/**
 * Created by CJQ on 2015/9/15.
 */
public class Order {

    String orderNum;
    String price;
    String time;
    String phoneNumber;
    boolean paied;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    List<Goods4OrderList> goods4OrderListList;

    public Order(String orderNum, String price, boolean paied, List<Goods4OrderList> goods4OrderListList) {
        this.orderNum = orderNum;
        this.price = price;
        this.paied = paied;
        this.goods4OrderListList = goods4OrderListList;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isPaied() {
        return paied;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public List<Goods4OrderList> getGoods4OrderListList() {
        return goods4OrderListList;
    }

    public void setGoods4OrderListList(List<Goods4OrderList> goods4OrderListList) {
        this.goods4OrderListList = goods4OrderListList;
    }
}
