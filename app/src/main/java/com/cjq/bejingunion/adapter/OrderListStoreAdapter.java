package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.view.PinnedSectionListView;

import java.util.List;


/**
 * Created by CJQ on 2015/9/8.
 */
public class OrderListStoreAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    Context context;
    List<Goods4OrderList> store4Shows;

    public OrderListStoreAdapter(Context context, List<Goods4OrderList> store4Shows) {
        this.context = context;
        this.store4Shows = store4Shows;
    }

    @Override
    public int getCount() {
        return store4Shows.size();
    }

    @Override
    public Object getItem(int position) {
        return store4Shows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods4OrderList goods4OrderList = store4Shows.get(position);

        if(convertView ==null){
            if(goods4OrderList.isStore()){
                convertView = LayoutInflater.from(context).inflate(R.layout.order_confirm_item,parent,false);
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.order_confirm_item2,parent,false);
            }
        }

        AQuery aq = new AQuery(convertView);
        if(goods4OrderList.isStore()){
            aq.id(R.id.order_list_item_name1).text(goods4OrderList.getName());
        }else{
            aq.id(R.id.order_list_item_name).text(goods4OrderList.getName());
            aq.id(R.id.order_list_item_cover).image(goods4OrderList.getPortrait(), false, false);
            aq.id(R.id.order_list_item_description).text(goods4OrderList.getDescription());
            SpannableString count = new SpannableString("数量："+goods4OrderList.getCount());
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
            count.setSpan(colorSpan1, 3, count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            aq.id(R.id.order_list_item_count).text(count);
            SpannableString price = new SpannableString("单价：￥"+goods4OrderList.getPrice4One());
            price.setSpan(colorSpan, 3, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            aq.id(R.id.order_list_item_price).text(price);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        Goods4OrderList goods4OrderList = store4Shows.get(position);

        return goods4OrderList.isStore()?1:2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType==2;
    }
}
