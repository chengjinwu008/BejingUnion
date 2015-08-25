package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.MarketGridAdapter;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.view.MyRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/20.
 */
public class MarketActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyRefreshLayout.onLoadListener, AdapterView.OnItemClickListener {

    private AQuery aq;
    private int activeSort = 1;
    private boolean[] up = {false, false, false, false};
    private int current_page = 1;
    private int gc_id = 4;
    private List<Goods4IndexList> goodsList = new ArrayList<Goods4IndexList>();
    private BaseAdapter adapter;
    private MyRefreshLayout refreshLayout;
    private ImageView[] sortViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        aq = new AQuery(this);

        sortViews = new ImageView[4];
        sortViews[0] = aq.id(R.id.market_sort1).getImageView();
        sortViews[1] = aq.id(R.id.market_sort2).getImageView();
        sortViews[2] = aq.id(R.id.market_sort3).getImageView();
        sortViews[3] = aq.id(R.id.market_sort4).getImageView();

        aq.id(R.id.market_search_text).getView().clearFocus();
        aq.id(R.id.market_back).clicked(this, "closeUp");

        aq.id(R.id.market_sort_click1).clicked(this, "sortByTime");
        aq.id(R.id.market_sort_click2).clicked(this, "sortByComments");
        aq.id(R.id.market_sort_click3).clicked(this, "sortBySales");
        aq.id(R.id.market_sort_click4).clicked(this, "sortByPrice");
        adapter = new MarketGridAdapter(this, goodsList);
        aq.id(R.id.market_list).getGridView().setAdapter(adapter);
        aq.id(R.id.market_list).itemClicked(this);
        refreshLayout = (MyRefreshLayout) aq.id(R.id.market_refresh).getView();

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);

        requestData();
    }

    public void sortByTime() {
        changeArrow(1);
        current_page = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortByComments() {
        changeArrow(2);
        current_page = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortBySales() {
        changeArrow(3);
        current_page = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortByPrice() {
        changeArrow(4);
        current_page = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    private void changeArrow(int i) {
        if (activeSort == i) {
            up[i-1] = !up[i-1];
            if (up[i-1])
                sortViews[activeSort-1].setImageResource(R.drawable.a35);
            else
                sortViews[activeSort-1].setImageResource(R.drawable.a32);
        } else {
            if (up[activeSort-1])
                sortViews[activeSort-1].setImageResource(R.drawable.a34);
            else
                sortViews[activeSort-1].setImageResource(R.drawable.a33);
            activeSort = i;
            if (up[activeSort-1])
                sortViews[activeSort-1].setImageResource(R.drawable.a35);
            else
                sortViews[activeSort-1].setImageResource(R.drawable.a32);
        }
    }

    public void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("key", String.valueOf(activeSort));
        params.put("page", String.valueOf(CommonDataObject.PAGE_SIZE));
        params.put("curpage", String.valueOf(current_page));
        params.put("gc_id", String.valueOf(gc_id));
        params.put("order", up[activeSort-1] ? "1" : "2");

        aq.ajax(CommonDataObject.GOODS_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONArray goods_list = object.getJSONObject("datas").getJSONArray("goods_list");

                        int size = goods_list.length();
                        if (size == 0) {
                            Toast.makeText(MarketActivity.this, "没有下一页了", Toast.LENGTH_SHORT).show();
                            current_page--;
                            refreshLayout.setLoading(false);
                            refreshLayout.setRefreshing(false);
                            return;
                        }

                        List<Goods4IndexList> goods4IndexLists = new ArrayList<Goods4IndexList>();
                        for (int i = 0; i < size; i++) {
                            JSONObject o = goods_list.getJSONObject(i);
                            Goods4IndexList goods4IndexList = new Goods4IndexList(o.getString("goods_id"), o.getString("goods_price"), o.getString("goods_image_url"), o.getString("goods_name"));
                            goods4IndexList.setMarket_price(o.getString("goods_marketprice"));
                            goods4IndexLists.add(goods4IndexList);
                        }
                        goodsList.addAll(goods4IndexLists);
                        adapter.notifyDataSetChanged();
                        refreshLayout.setLoading(false);
                        refreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }

    public void closeUp() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        aq.id(R.id.market_search_text).getView().clearFocus();
    }

    @Override
    public void onRefresh() {
        current_page = 1;
        goodsList.clear();
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    @Override
    public void onLoad() {
        refreshLayout.setLoading(true);
        current_page++;
        requestData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Goods4IndexList goods = goodsList.get(position);

        GoodsUtil.showGoodsDetail(MarketActivity.this,goods.getGoods_id());
    }
}
