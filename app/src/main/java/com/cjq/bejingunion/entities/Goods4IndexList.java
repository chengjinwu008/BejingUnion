package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/8/19.
 */
public class Goods4IndexList {
    String goods_id;
    String goods_price;
    String goods_image_url;
    String goods_name;
    String market_price;

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Goods4IndexList(String goods_id, String goods_price, String goods_image_url, String goods_name) {
        this.goods_id = goods_id;
        this.goods_price = goods_price;
        this.goods_image_url = goods_image_url;
        this.goods_name = goods_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }
}
