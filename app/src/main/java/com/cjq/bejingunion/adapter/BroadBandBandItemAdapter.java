package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.BandItem;

import java.util.List;

/**
 * Created by CJQ on 2015/8/20.
 */
public class BroadBandBandItemAdapter extends BaseAdapter {

    private List<BandItem> bandItems;
    private Context context;
    private int chose=0;

    public BroadBandBandItemAdapter(List<BandItem> bandItems, Context context) {
        this.bandItems = bandItems;
        this.context = context;
    }

//    public String getPost(int position) {
//        return bandItems.get(position).getPost();
//    }

    @Override
    public int getCount() {
        return bandItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bandItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.text_view_layout, null, false);
            convertView.setLayoutParams(new GridView.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics())
            ));
        }

        AQuery aq = new AQuery(convertView);
        aq.id(R.id.text).text(bandItems.get(position).getShow());

        if (!bandItems.get(position).isChosen()) {
            convertView.setBackgroundResource(R.drawable.border_white_box);
        } else {
            convertView.setBackgroundResource(R.drawable.blue_border_white_background_no_corner);
        }
        return convertView;
    }

    public void changeChosen(int position) {
        bandItems.get(chose).setChosen(false);
        bandItems.get(position).setChosen(true);
        chose = position;
    }
}
