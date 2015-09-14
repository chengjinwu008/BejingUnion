package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CJQ on 2015/9/14.
 */
public class CartListAdapter extends SwipeListAdapter {

    public CartListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
