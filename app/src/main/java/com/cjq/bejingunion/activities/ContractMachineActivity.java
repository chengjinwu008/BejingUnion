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
import com.cjq.bejingunion.adapter.BrandAdapter;
import com.cjq.bejingunion.adapter.BroadBandBandItemAdapter;
import com.cjq.bejingunion.adapter.MarketGridAdapter;
import com.cjq.bejingunion.entities.BandItem;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.view.MyRefreshLayout;
import com.cjq.bejingunion.view.RightSlideMenu;

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
public class ContractMachineActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyRefreshLayout.onLoadListener, AdapterView.OnItemClickListener {

    private AQuery aq;
    private RightSlideMenu menu;
    private boolean[] up = {false, false, false, false};
    private int activeSort = 1;
    private int current_page = 1;
    private int gc_id = 3;
    private List<Goods4IndexList> goodsList = new ArrayList<Goods4IndexList>();
    private BaseAdapter adapter;
    private MyRefreshLayout refreshLayout;
    private ImageView[] sortViews;
    private GridView contractMachineList;
    private String band_id;
    private List<BandItem> brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_machine);

        aq = new AQuery(this);

        aq.id(R.id.contract_machine_back).clicked(this, "closeUp");
        aq.id(R.id.contract_machine_draw_category_out).clicked(this, "drawMenuOutSwitch");

        menu = (RightSlideMenu) aq.id(R.id.contract_machine_menu_layout).getView();
        contractMachineList=aq.id(R.id.contract_machine_grid_list).getGridView();
        adapter=new MarketGridAdapter(this,goodsList);

        refreshLayout = (MyRefreshLayout) aq.id(R.id.contract_machine_refresh).getView();
        contractMachineList.setAdapter(adapter);
        contractMachineList.setOnItemClickListener(this);

        sortViews = new ImageView[4];
        sortViews[0]=aq.id(R.id.contract_machine_sort1).getImageView();
        sortViews[1]=aq.id(R.id.contract_machine_sort2).getImageView();
        sortViews[2]=aq.id(R.id.contract_machine_sort3).getImageView();
        sortViews[3]=aq.id(R.id.contract_machine_sort4).getImageView();

        aq.id(R.id.contract_machine_sort_click1).clicked(this, "sortByTime");
        aq.id(R.id.contract_machine_sort_click2).clicked(this, "sortByComments");
        aq.id(R.id.contract_machine_sort_click3).clicked(this, "sortBySales");
        aq.id(R.id.contract_machine_sort_click4).clicked(this, "sortByPrice");
//        System.out.println(contractMachineList);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        initCategory();
        requestData();
    }

    private void initCategory() {
        Map<String,String> params = new HashMap<>();
//        params.put("gc_id", String.valueOf(gc_id));

        //生成品牌菜单

        aq.ajax(CommonDataObject.BRAND_LIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                try {
                    if(object.getInt("code")==200){
                        JSONArray a = object.getJSONObject("datas").getJSONArray("brand_list");
                        brandList = new ArrayList<BandItem>();
                        for (int i=0;i<a.length();i++){

                            JSONObject o = a.getJSONObject(i);
                            BandItem bandItem =new BandItem(o.getString("brand_name"),o.getString("brand_id"));

                            brandList.add(bandItem);
                        }
                        aq.id(R.id.contract_machine_menu_list).adapter(new BrandAdapter(brandList,ContractMachineActivity.this));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });

        aq.id(R.id.contract_machine_menu_list).itemClicked(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BandItem bandItem =  brandList.get(position);
                band_id = bandItem.getPost();
                menu.animateMenu();
                onRefresh();
            }
        });
    }

    private void requestData() {
        System.out.println(current_page);
        Map<String, String> params = new HashMap<>();
        params.put("key", String.valueOf(activeSort));
        params.put("page", String.valueOf(CommonDataObject.PAGE_SIZE));
        params.put("curpage", String.valueOf(current_page));
        params.put("gc_id", String.valueOf(gc_id));
        params.put("order", up[activeSort - 1] ? "1" : "2");
        params.put("keyword", aq.id(R.id.contract_machine_search_text).getText().toString());
        params.put("brand_id", band_id);

        aq.ajax(CommonDataObject.GOODS_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONArray goods_list = object.getJSONObject("datas").getJSONArray("goods_list");

                        int size = goods_list.length();
                        if (size == 0) {
                            Toast.makeText(ContractMachineActivity.this, "没有下一页了", Toast.LENGTH_SHORT).show();
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

    public void closeUp(){
        finish();
    }

    public void drawMenuOutSwitch(){
        menu.animateMenu();
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
        try{
            Goods4IndexList goods4IndexList = goodsList.get(position);

            GoodsUtil.showGoodsDetail(this,goods4IndexList.getGoods_id(), GoodsUtil.TYPE.CONTRACT_MACHINE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
