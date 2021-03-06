package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cjq.bejingunion.R;

import java.util.List;

/**
 * Created by CJQ on 2015/8/19.
 */
public abstract class SwipeListAdapter extends BaseAdapter {
    protected Context context;

    public SwipeListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.collection_list_item,parent,false);
        }
        return convertView;
    }
}
