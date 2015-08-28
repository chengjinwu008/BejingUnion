package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Area4Show;
import com.cjq.bejingunion.view.PinnedSectionListView;

import java.util.List;

/**
 * Created by CJQ on 2015/8/26.
 */
public class AreaAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    List<Area4Show> area4Shows;
    Context context;

    public AreaAdapter(List<Area4Show> area4Shows, Context context) {
        this.area4Shows = area4Shows;
        this.context = context;

    }

    @Override
    public int getCount() {
        return area4Shows.size();
    }

    @Override
    public Object getItem(int position) {
        return area4Shows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.area_list_item,parent,false);
        }
        Area4Show area4Show = area4Shows.get(position);
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.text).text(area4Show.getText());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return true;
    }
}
