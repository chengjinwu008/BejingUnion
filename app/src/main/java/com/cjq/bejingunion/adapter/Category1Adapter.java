package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Category4Show;

import java.util.List;

/**
 * Created by CJQ on 2015/9/6.
 */
public class Category1Adapter extends BaseAdapter {
    List<Category4Show> category4ShowList;
    Context context;
    int chosePosition;

    public Category1Adapter(List<Category4Show> category4ShowList, Context context) {
        this.category4ShowList = category4ShowList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return category4ShowList.size();
    }

    @Override
    public Object getItem(int position) {
        return category4ShowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.category_1_item, parent, false);
        }
        Category4Show category4Show = category4ShowList.get(position);
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.category_item_name).text(category4Show.getName());
        if (category4Show.isChosen()) {
            aq.id(R.id.category_item_chosen).visible();
            aq.id(R.id.category_item_chosen2).gone();
            chosePosition = position;
        } else {
            aq.id(R.id.category_item_chosen).gone();
            aq.id(R.id.category_item_chosen2).visible();
        }
        return convertView;
    }

    public void changeChosen(int i) {
        if (i < getCount()) {
            category4ShowList.get(chosePosition).setChosen(false);
            category4ShowList.get(i).setChosen(true);
            notifyDataSetChanged();
        }
    }
}
