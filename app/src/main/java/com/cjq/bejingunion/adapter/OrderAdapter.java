package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Order;

import java.util.List;

/**
 * Created by CJQ on 2015/9/15.
 */
public class OrderAdapter extends BaseAdapter {
    private Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        }

        Order order = orderList.get(position);

        AQuery aq = new AQuery(convertView);
        aq.id(R.id.order_number).text("订单编号：" + order.getOrderNum());
        aq.id(R.id.order_price).text("￥" + order.getPrice());

        aq.id(R.id.order_goods_list).adapter(new OrderItemAdapter(order.getGoods4OrderListList(), context));

        return convertView;
    }
}
