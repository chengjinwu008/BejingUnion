package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.DetailChoice;
import com.cjq.bejingunion.view.PinnedSectionListView;

import java.util.List;

/**
 * Created by CJQ on 2015/8/27.
 */
public class ChoiceListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    List<DetailChoice> choices;
    Context context;

    public ChoiceListAdapter(List<DetailChoice> choices, Context context) {
        this.choices = choices;
        this.context = context;
    }

    @Override
    public int getCount() {
        return choices.size();
    }

    @Override
    public Object getItem(int position) {
        return choices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.area_list_item, parent, false);
        }
        DetailChoice choice = choices.get(position);
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.text).text(choice.getValue());
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
