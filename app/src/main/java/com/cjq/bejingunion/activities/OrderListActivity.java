package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.OrderAdapter;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.entities.Order;
import com.cjq.bejingunion.event.EventPayComplete;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.MyRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/15.
 */
public class OrderListActivity extends BaseActivity implements MyRefreshLayout.onLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private AQuery aq;
    private int curpage = 1;
    private String order_state = "";
    private List<Order> orderList;
    private BaseAdapter adapter;
    private MyRefreshLayout refreshLayout;

    public void onEventMainThread(EventPayComplete e){
        onRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_list);

        Intent intent = getIntent();
        order_state = intent.getStringExtra("order_state");

        aq = new AQuery(this);
        aq.id(R.id.msg_list_back).clicked(this,"finish");
        aq.id(R.id.msg_title).text("订单列表");
        orderList = new ArrayList<Order>();
        adapter = new OrderAdapter(OrderListActivity.this, orderList);
        aq.id(R.id.msg_list_list).adapter(adapter);
        requestData();

        refreshLayout = (MyRefreshLayout) aq.id(R.id.msg_list_refresh).getView();
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    public void requestData() {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("key", LoginUtil.getKey(this));
            params.put("page", String.valueOf(CommonDataObject.PAGE_SIZE));
            params.put("curpage", String.valueOf(curpage));
            params.put("order_state", order_state);
            aq.ajax(CommonDataObject.ORDER_LIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        System.out.println(object.toString());
                        JSONArray a = object.getJSONObject("datas").getJSONArray("order_group_list");
                        if (object.getInt("code") == 200 && a.length() > 0) {

                            for (int i = 0; i < a.length(); i++) {

                                JSONObject o = a.getJSONObject(i);
                                JSONArray aa = o.getJSONObject("order_list").getJSONArray("extend_order_goods");

                                Order order = new Order(o.getString("pay_sn"), o.getString("pay_amount"),Integer.parseInt(order_state), null);

                                List<Goods4OrderList> goods4OrderLists = new ArrayList<Goods4OrderList>();
                                for (int j = 0; j < aa.length(); j++) {
                                    JSONObject oo = aa.getJSONObject(j);
                                    goods4OrderLists.add(new Goods4OrderList(oo.getString("goods_image_url"), oo.getString("goods_name"), "", oo.getInt("goods_num"), oo.getString("goods_price")));
                                }

                                order.setGoods4OrderListList(goods4OrderLists);

                                if(!"".equals(o.getString("phone_number"))){
                                    order.setPhoneNumber(o.getString("phone_number"));
                                }

                                order.setOrder_id(o.getJSONObject("order_list").getString("order_id"));

                                orderList.add(order);
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            curpage--;
                        }
                        refreshLayout.setLoading(false);
                        refreshLayout.setRefreshing(false);
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

    @Override
    public void onLoad() {
        curpage++;
        requestData();
        refreshLayout.setLoading(true);
        requestData();
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        orderList.clear();
        adapter.notifyDataSetChanged();

        refreshLayout.setRefreshing(true);
        requestData();
    }
}
