package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/8/25.
 */
public class CollectionToShow {
    String goods_image_url;
    String goods_name;
    String fav_id;
    String goods_price;
    String goods_id;

    public CollectionToShow(String goods_image_url, String goods_name, String fav_id, String goods_price, String goods_id) {
        this.goods_image_url = goods_image_url;
        this.goods_name = goods_name;
        this.fav_id = fav_id;
        this.goods_price = goods_price;
        this.goods_id = goods_id;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
