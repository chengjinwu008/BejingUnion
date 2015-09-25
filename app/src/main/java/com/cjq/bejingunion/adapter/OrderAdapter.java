package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.OrderInfoDetailActivity;
import com.cjq.bejingunion.dialog.MyToast;
import com.cjq.bejingunion.entities.Order;
import com.cjq.bejingunion.event.EventPayComplete;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayUtil;
import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        }

        final Order order = orderList.get(position);

        final AQuery aq = new AQuery(convertView);
        aq.id(R.id.order_number).text("订单编号：" + order.getOrderNum()).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, OrderInfoDetailActivity.class);
                intent.putExtra("order_id",order.getOrder_id());
                context.startActivity(intent);
            }
        });
        if (order.getPhoneNumber() != null) {
            aq.id(R.id.order_phone_number).visible().text("选择卡号：" + order.getPhoneNumber());
        } else {
            aq.id(R.id.order_phone_number).gone();
        }

        aq.id(R.id.order_price).text("￥" + order.getPrice());

        aq.id(R.id.order_goods_list).adapter(new OrderItemAdapter(order.getGoods4OrderListList(), context));

        if (order.isPaied() != 10)
            aq.id(R.id.order_item_pay).gone();
        else {
            aq.id(R.id.order_item_pay).visible();
            aq.id(R.id.order_list_cancel_order).visible();
            aq.id(R.id.order_item_pay).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayUtil.pay(context, "订单编号：" + order.getOrderNum(), order.getPhoneNumber() == null ? "选择卡号：" + order.getPhoneNumber() : "", order.getPrice(), order.getOrderNum(), "");
                }
            });
            aq.id(R.id.order_list_cancel_order).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        PayUtil.cancelOrder(context, order.getOrder_id(), new Runnable() {
                            @Override
                            public void run() {
                                orderList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (order.isPaied() != 30)
            aq.id(R.id.order_item_confirm).gone();
        else {
            aq.id(R.id.order_item_confirm).visible();
            aq.id(R.id.order_item_confirm).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Map<String, String> params = new HashMap<String, String>();
                    try {
                        params.put("key", LoginUtil.getKey(context));
                        params.put("order_id", order.getOrder_id());
                        aq.ajax(CommonDataObject.ORDER_RECEIVE_CONFIRM, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                            @Override
                            public void callback(String url, JSONObject object, AjaxStatus status) {
                                try {
                                    if (200 == object.getInt("code")) {
                                        MyToast.showText(context, object.getJSONObject("datas").getString("msg"));
                                        EventBus.getDefault().post(new EventPayComplete());
                                    } else {
                                        MyToast.showText(context, object.getJSONObject("datas").getString("error"), R.drawable.a2);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                super.callback(url, object, status);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return convertView;
    }
}
