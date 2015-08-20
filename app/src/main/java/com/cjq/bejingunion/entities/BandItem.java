package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/8/20.
 */
public class BandItem {
    String show;
    String post;
    boolean chosen = false;

    public boolean isChosen() {
        return chosen;
    }

    public BandItem setChosen(boolean chosen) {
        this.chosen = chosen;
        return this;
    }

    public BandItem(String show, String post) {
        this.show = show;
        this.post = post;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
