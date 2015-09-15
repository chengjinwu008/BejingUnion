package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/9/8.
 */
public class Goods4OrderList {
    String portrait;
    String name;
    String description;
    int count;
    String price4One;
    boolean isStore;
    String cart_id;
    String goods_id;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setIsStore(boolean isStore) {
        this.isStore = isStore;
    }

    public Goods4OrderList(String portrait, String name, String description, int count, String price4One) {
        this.portrait = portrait;
        this.name = name;
        this.description = description;
        this.count = count;
        this.price4One = price4One;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice4One() {
        return price4One;
    }

    public void setPrice4One(String price4One) {
        this.price4One = price4One;
    }
}
