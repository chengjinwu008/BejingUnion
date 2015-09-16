package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4OrderList;

import java.util.List;

/**
 * Created by CJQ on 2015/9/15.
 */
public class OrderItemAdapter extends BaseAdapter  {
    List<Goods4OrderList> goods4OrderListList;
    Context context;

    public OrderItemAdapter(List<Goods4OrderList> goods4OrderListList, Context context) {
        this.goods4OrderListList = goods4OrderListList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goods4OrderListList.size();
    }

    @Override
    public Object getItem(int position) {
        return goods4OrderListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item_item,parent,false);
        }
        AQuery aq = new AQuery(convertView);
        Goods4OrderList  g = goods4OrderListList.get(position);

        aq.id(R.id.order_item_image).image(g.getPortrait(),false,true);
        aq.id(R.id.order_item_name).text(g.getName());
        aq.id(R.id.order_item_price).text(g.getPrice4One());

        return convertView;
    }
}
