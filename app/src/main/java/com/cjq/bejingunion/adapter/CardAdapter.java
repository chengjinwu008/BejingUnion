package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.view.PinnedSectionListView;

import java.util.List;

/**
 * Created by CJQ on 2015/9/10.
 */
public class CardAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{

    List<Goods4IndexList> goods4IndexLists;
    Context context;

    public CardAdapter(List<Goods4IndexList> goods4IndexLists, Context context) {
        this.goods4IndexLists = goods4IndexLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goods4IndexLists.size();
    }

    @Override
    public Object getItem(int position) {
        return goods4IndexLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.buy_phone_number_item,parent,false);
        }
        Goods4IndexList goods4IndexList = goods4IndexLists.get(position);
        AQuery aq = new AQuery(convertView);

        aq.id(R.id.buy_phone_number_item_image).image(goods4IndexList.getGoods_image_url(),false,true);
        aq.id(R.id.buy_phone_number_item_name).text(goods4IndexList.getGoods_name());
        aq.id(R.id.buy_phone_number_item_price).text("￥"+goods4IndexList.getGoods_price());

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
        return false;
    }
}
