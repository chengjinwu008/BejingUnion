package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.BroadBandBandItemAdapter;
import com.cjq.bejingunion.adapter.BroadBandGridAdapter;
import com.cjq.bejingunion.adapter.MarketGridAdapter;
import com.cjq.bejingunion.entities.BandItem;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.view.BannerView;
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
public class BroadBandActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, MyRefreshLayout.onLoadListener {
    private int width = 100;
    private AQuery aq;
    private GridView gridView;
    private int gc_id = 1;
    private int activeSort = 1;
    private int currentPage = 1;
    private boolean[] up = {false, false, false, false};
    private ImageView[] sortViews;
    private MyRefreshLayout refreshLayout;
    private List<Goods4IndexList> goodsList = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadband);

        aq = new AQuery(this);

        sortViews = new ImageView[4];
        sortViews[0] = aq.id(R.id.broadband_sort1).getImageView();
        sortViews[1] = aq.id(R.id.broadband_sort2).getImageView();
        sortViews[2] = aq.id(R.id.broadband_sort3).getImageView();
        sortViews[3] = aq.id(R.id.broadband_sort4).getImageView();

        aq.id(R.id.broadband_sort_click1).clicked(this, "sortByTime");
        aq.id(R.id.broadband_sort_click2).clicked(this, "sortByComments");
        aq.id(R.id.broadband_sort_click3).clicked(this, "sortBySales");
        aq.id(R.id.broadband_sort_click4).clicked(this, "sortByPrice");

        aq.id(R.id.broadband_back).clicked(this, "closeUp");
        adapter = new BroadBandGridAdapter(this, goodsList);
        refreshLayout = (MyRefreshLayout) aq.id(R.id.broadband_refresh).getView();
        aq.id(R.id.broadband_list).getGridView().setAdapter(adapter);
        initBandList();

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);

        requestData();
    }

    public void sortByTime() {
        changeArrow(1);
        currentPage = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortByComments() {
        changeArrow(2);
        currentPage = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortBySales() {
        changeArrow(3);
        currentPage = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void sortByPrice() {
        changeArrow(4);
        currentPage = 1;
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

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("key", String.valueOf(activeSort));
        params.put("page", String.valueOf(CommonDataObject.PAGE_SIZE));
        params.put("curpage", String.valueOf(currentPage));
        params.put("gc_id", String.valueOf(gc_id));
        params.put("order", up[activeSort - 1] ? "1" : "2");

        aq.ajax(CommonDataObject.GOODS_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(url +"++++++++++++++++++++++++" +object.toString());
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONArray goods_list = object.getJSONObject("datas").getJSONArray("goods_list");

                        int size = goods_list.length();
                        if (size == 0) {
                            Toast.makeText(BroadBandActivity.this, "没有下一页了", Toast.LENGTH_SHORT).show();
                            currentPage--;
                            refreshLayout.setLoading(false);
                            refreshLayout.setRefreshing(false);
                            return;
                        }

                        List<Goods4IndexList> goods4IndexLists = new ArrayList<Goods4IndexList>();
                        for (int i = 0; i < size; i++) {
                            JSONObject o = goods_list.getJSONObject(i);
                            Goods4IndexList goods4IndexList = new Goods4IndexList(o.getString("goods_id"), o.getString("goods_price"), o.getString("goods_image_url"), o.getString("goods_name"));
                            goods4IndexList.setMarket_price(o.getString("evaluation_count"));
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

    private void initBandList() {
        // TODO: 2015/8/20 这个列表应该从网络得到

        List<BandItem> bandItems = new ArrayList<>();
        bandItems.add(new BandItem("10M", "").setChosen(true));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));
        bandItems.add(new BandItem("10M", ""));

        gridView = aq.id(R.id.broadband_band_item).getGridView();
        int ww = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics()) * bandItems.size());
        gridView.setAdapter(new BroadBandBandItemAdapter(bandItems, this));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ww, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = 10;
        params.bottomMargin = 10;
        params.leftMargin = 10;
        params.rightMargin = 10;
        gridView.setColumnWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics()));
        gridView.setNumColumns(bandItems.size());
        gridView.setHorizontalSpacing(10);
        gridView.setHorizontalSpacing(10);
        gridView.setLayoutParams(params);

        gridView.setOnItemClickListener(this);
    }

    public void closeUp() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BroadBandBandItemAdapter adapter = (BroadBandBandItemAdapter) gridView.getAdapter();
        String post = adapter.getPost(position);

        // TODO: 2015/8/20 改变了套餐类型 相应的列表也应该跟随改变

        adapter.changeChosen(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        goodsList.clear();
        refreshLayout.setRefreshing(true);
        requestData();
    }

    @Override
    public void onLoad() {
        refreshLayout.setLoading(true);
        currentPage++;
        requestData();
    }
}
