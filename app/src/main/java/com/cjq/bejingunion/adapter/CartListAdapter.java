package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.view.NumericView;

import java.util.List;

/**
 * Created by CJQ on 2015/9/14.
 */
public class CartListAdapter extends SwipeListAdapter {

    List<Goods4OrderList> goods4OrderListList;
    Context context;
    Listener listener;

    public interface Listener{
        void change();
        void nameAndPortraitClicked(int position);
    }

    public CartListAdapter(Context context, List<Goods4OrderList> goods4OrderListList,Listener listener) {
        super(context);
        this.goods4OrderListList = goods4OrderListList;
        this.context =context;
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.cart_list_item,parent,false);
        }

        final Goods4OrderList goods4OrderList =goods4OrderListList.get(position);
        final AQuery aQuery = new AQuery(convertView);
        aQuery.id(R.id.cart_item_image).image(goods4OrderList.getPortrait(),false,true).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nameAndPortraitClicked(position);
            }
        });
        aQuery.id(R.id.cart_item_name).text(goods4OrderList.getName()).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nameAndPortraitClicked(position);
            }
        });
        NumericView numericView = (NumericView) aQuery.id(R.id.cart_item_count).getView();
        numericView.setNumber(goods4OrderList.getCount());
        aQuery.id(R.id.cart_item_price).text("￥" + goods4OrderList.getPrice4One());
        numericView.setListener(new NumericView.OnNumberChangeListener() {
            @Override
            public void change(int number) {
                goods4OrderList.setCount(number);
                listener.change();
            }
        });
        return convertView;
    }
}
