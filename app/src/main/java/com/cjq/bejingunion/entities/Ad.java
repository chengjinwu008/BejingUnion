package com.cjq.bejingunion.entities;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by CJQ on 2015/8/12.
 */
public class Ad {
    String url;
    String id;
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public Ad(Context context) {
        imageView =new ImageView(context);
    }

    public Ad(Context context,String url, String id) {
        this.url = url;
        this.id = id;
        imageView = new ImageView(context);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
