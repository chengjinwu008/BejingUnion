package com.cjq.bejingunion.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.OrderConfirmActivity;
import com.cjq.bejingunion.adapter.CartListAdapter;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.event.EventCartChange;
import com.cjq.bejingunion.event.EventCartListChange;
import com.cjq.bejingunion.event.EventLoginIn;
import com.cjq.bejingunion.event.EventLogout;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.utils.LoginUtil;
import com.ypy.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CartFragment extends Fragment implements AdapterView.OnItemLongClickListener, CartListAdapter.Listener {

    private View view;
    private AQuery aq;
    private Handler mHandler = new Handler();
    private List<Goods4OrderList> goods4OrderLists;

    public void onEventMainThread(EventCartListChange e) {
        requestData();
    }

    public void onEventMainThread(EventCartChange e) {
        requestData();
    }

    public void onEventMainThread(EventLoginIn e) {
        requestData();
    }

    public void onEventMainThread(EventLogout e) {

        goods4OrderLists = null;
        aq.id(R.id.cart_price).text(String.valueOf(0.00));

        aq.id(R.id.cart_list).adapter(new CartListAdapter(getActivity(), new ArrayList<Goods4OrderList>(), CartFragment.this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart, container, false);
        EventBus.getDefault().register(this);
        aq = new AQuery(view);
        aq.id(R.id.cart_list).getListView().setOnItemLongClickListener(this);
        aq.id(R.id.cart_pay).clicked(this, "pay");

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                requestData();
//            }
//        }, 2000);
        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("key", LoginUtil.getKey(getActivity()));
            aq.ajax(CommonDataObject.CART_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                            System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONArray a = object.getJSONObject("datas").getJSONArray("cart_list");
                            goods4OrderLists = new ArrayList<Goods4OrderList>();

                            double price = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject o = a.getJSONObject(i);
                                Goods4OrderList goods4OrderList = new Goods4OrderList(o.getString("goods_image_url"), o.getString("goods_name"), null, o.getInt("goods_num"), o.getString("goods_price"));
                                goods4OrderList.setCart_id(o.getString("cart_id"));
                                goods4OrderList.setGoods_id(o.getString("goods_id"));
                                goods4OrderLists.add(goods4OrderList);
                                price += Double.parseDouble(goods4OrderList.getPrice4One()) * goods4OrderList.getCount();
                            }

                            aq.id(R.id.cart_price).text(String.valueOf(price));

                            aq.id(R.id.cart_list).adapter(new CartListAdapter(getActivity(), goods4OrderLists, CartFragment.this));
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new WarningAlertDialog(getActivity()).changeText("确定要删除该商品吗").onOKClick(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("key", LoginUtil.getKey(getActivity()));
                    params.put("cart_id", goods4OrderLists.get(position).getCart_id());
                    aq.ajax(CommonDataObject.DELETE_FROM_CART_LIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            try {
                                if (200 == object.getInt("code")) {
                                    Toast.makeText(getActivity(), object.getJSONObject("datas").getString("msg"), Toast.LENGTH_SHORT).show();
//                                    goods4OrderLists.remove(position);
//                                    ((BaseAdapter)aq.id(R.id.cart_list).getListView().getAdapter()).notifyDataSetChanged();
                                    requestData();
                                } else {
                                    Toast.makeText(getActivity(), object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
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
        return true;
    }

    @Override
    public void change() {
        double price = 0;
        for (Goods4OrderList l : goods4OrderLists) {
            price += Double.parseDouble(l.getPrice4One()) * l.getCount();
        }

        aq.id(R.id.cart_price).text(String.valueOf(price));
    }

    @Override
    public void nameAndPortraitClicked(int position) {
        GoodsUtil.showGoodsDetail(getActivity(), goods4OrderLists.get(position).getGoods_id());
    }

    public void pay() {

        double price = 0;
        StringBuilder builder = new StringBuilder();
        for (Goods4OrderList l : goods4OrderLists) {
            price += Double.parseDouble(l.getPrice4One()) * l.getCount();
            builder.append(l.getCart_id()).append("|").append(l.getCount()).append(",");
        }

        String ss = null;

        if (builder.length() > 0)
            ss = builder.toString().substring(0, builder.length() - 1);

        if (price > 0) {
            Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
            intent.putExtra("cart_id", ss);
            intent.putExtra("ifcart", "1");
            startActivity(intent);
        }
    }
}

