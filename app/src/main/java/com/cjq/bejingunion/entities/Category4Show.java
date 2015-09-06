package com.cjq.bejingunion.entities;

/**
 * Created by CJQ on 2015/9/6.
 */
public class Category4Show {

    String name;
    String id;
    boolean chosen;

    public Category4Show(String name, String id, boolean chosen) {
        this.name = name;
        this.id = id;
        this.chosen = chosen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
