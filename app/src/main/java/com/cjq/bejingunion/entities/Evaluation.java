package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/8/29.
 */
public class Evaluation {
    String image;
    String username;
    long time;
    String content;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Evaluation(String image, String username, long time, String content, String id) {
        this.image = image;
        this.username = username;
        this.time = time;
        this.content = content;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
