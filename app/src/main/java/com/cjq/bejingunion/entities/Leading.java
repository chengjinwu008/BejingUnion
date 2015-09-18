package com.cjq.bejingunion.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/9/18.
 */
public class Leading {

    boolean confirm;
    int id;
    View view;

    public Leading(boolean confirm, int id,Context context) {
        this.confirm = confirm;
        this.id = id;
        this.view = LayoutInflater.from(context).inflate(R.layout.leading_item,null,false);
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
