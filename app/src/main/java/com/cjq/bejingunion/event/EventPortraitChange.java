package com.cjq.bejingunion.event;

/**
 * Created by CJQ on 2015/9/1.
 */
public class EventPortraitChange {
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EventPortraitChange(String image) {
        this.image = image;
    }
}
