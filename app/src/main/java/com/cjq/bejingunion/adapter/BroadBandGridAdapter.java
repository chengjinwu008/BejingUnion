package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4IndexList;

import java.util.List;

/**
 * Created by CJQ on 2015/8/19.
 */
public class BroadBandGridAdapter extends BaseAdapter {

    private Context context;
    private List<Goods4IndexList> list;

    public BroadBandGridAdapter(Context context, List<Goods4IndexList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.market_list_item,null,false);
        }
        Goods4IndexList goods = list.get(position);
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.market_item_image).image(goods.getGoods_image_url(),false,true);
        aq.id(R.id.goods_name).text(goods.getGoods_name());
        aq.id(R.id.special_price).text("￥"+goods.getGoods_price());
        aq.id(R.id.market_price).text("已有"+goods.getMarket_price()+"人评价");
        return convertView;
    }
}
