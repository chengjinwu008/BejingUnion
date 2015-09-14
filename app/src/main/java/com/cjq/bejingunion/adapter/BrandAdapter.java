package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.BandItem;

import java.util.List;

/**
 * Created by CJQ on 2015/9/14.
 */
public class BrandAdapter extends BaseAdapter {
    List<BandItem> brandItems;
    Context context;

    public BrandAdapter(List<BandItem> brandItems, Context context) {
        this.brandItems = brandItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return brandItems.size();
    }

    @Override
    public Object getItem(int position) {
        return brandItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.text_view_layout,parent,false);
        }

        AQuery aq = new AQuery(convertView);

        BandItem item = brandItems.get(position);

        aq.id(R.id.text).text(item.getShow());
//        if(item.isChosen()){
//            aq.background(R.drawable.blue_with_shadow_border);
//            aq.id(R.id.text).textColor(Color.WHITE);
//        }else{
//            aq.background(R.drawable.white_with_shadow_border);
//            aq.id(R.id.text).textColor(Color.BLACK);
//        }

        return convertView;
    }
}
