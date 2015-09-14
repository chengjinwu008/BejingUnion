package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.CardNumber;

import java.util.List;

/**
 * Created by CJQ on 2015/9/10.
 */
public class CardNumberAdapter extends BaseAdapter {
    List<CardNumber> cardNumberList;
    Context context;

    public CardNumberAdapter(List<CardNumber> cardNumberList, Context context) {
        this.cardNumberList = cardNumberList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardNumberList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardNumberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.text_view_layout,parent,false);
        }
        AQuery aQuery = new AQuery(convertView);
        aQuery.id(R.id.text).text(cardNumberList.get(position).getNumber());
        return convertView;
    }
}
