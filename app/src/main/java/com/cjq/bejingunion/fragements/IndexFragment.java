package com.cjq.bejingunion.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.BroadBandActivity;
import com.cjq.bejingunion.activities.ContractMachineActivity;
import com.cjq.bejingunion.activities.MarketActivity;
import com.cjq.bejingunion.activities.MobileNumberListActivity;
import com.cjq.bejingunion.adapter.BannerAdapter;
import com.cjq.bejingunion.adapter.IndexGridAdapter;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.view.BannerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/8/19.
 */
public class IndexFragment extends Fragment implements TextView.OnEditorActionListener {

    private View view;
    private GridView new_goods;
    private GridView hot_goods;
    private BannerView bannerView;
    private AQuery aq;
    private EditText search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.index,container,false);


        aq = new AQuery(view);
        bannerView = (BannerView) aq.id(R.id.index_banner).getView();
        search = (EditText) aq.id(R.id.index_search_text).getView();
        new_goods = aq.id(R.id.index_new_goods_list).getGridView();
        hot_goods = aq.id(R.id.index_hot_goods_list).getGridView();
        search.clearFocus();
        search.setOnEditorActionListener(this);

        aq.id(R.id.index_market).clicked(this, "jumpMarket");
        aq.id(R.id.index_broad_band).clicked(this,"jumpBroadBand");
        aq.id(R.id.index_broad_band).clicked(this,"jumpBroadBand");
        aq.id(R.id.index_buy_phone_number).clicked(this, "jumpMobileNumberList");
        aq.id(R.id.index_contract_machine).clicked(this, "jumpContractMachine");

        requestDatas();

        return view;
    }

    private void requestDatas(){

        aq.ajax(CommonDataObject.BANNER_URL, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONObject data = object.getJSONObject("datas");
                        JSONArray banner = data.getJSONArray("banner");
                        List<Ad> adList = new ArrayList<>();
                        for (int i = 0; i < banner.length(); i++) {
                            JSONObject o = banner.getJSONObject(i);
                            Ad ad = new Ad(getActivity(), o.getString("title"), String.valueOf(i));
                            adList.add(ad);
                        }
                        bannerView.setAdapter(new BannerAdapter(getActivity(), adList));

                        aq.id(R.id.left_ad).image(!"null".equals(data.getString("left")) ? data.getJSONObject("left").getString("title") : "http://i2.tietuku.com/e86aeadd2b3fa6a2s.jpg", false, true, 200, 0);
                        aq.id(R.id.center_ad).image(!"null".equals(data.getString("center")) ? data.getJSONObject("center").getString("title") : "http://i2.tietuku.com/e86aeadd2b3fa6a2s.jpg", false, true, 200, 0);
                        aq.id(R.id.right_ad).image(!"null".equals(data.getString("right")) ? data.getJSONObject("right").getString("title") : "http://i2.tietuku.com/e86aeadd2b3fa6a2s.jpg", false, true, 200, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });

        aq.ajax(CommonDataObject.INDEX_NEW_GOODS_LIST, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONArray goodsList = object.getJSONObject("datas").getJSONArray("goods_list");
                        int x = 0;
                        if (goodsList.length() <= 4) {
                            x = goodsList.length();
                        } else {
                            x = 4;
                        }

                        List<Goods4IndexList> indexLists = new ArrayList<Goods4IndexList>();

                        for (int i = 0; i < x; i++) {
                            JSONObject o = goodsList.getJSONObject(i);

                            Goods4IndexList goods = new Goods4IndexList(o.getString("goods_id"), o.getString("goods_price"), o.getString("goods_image_url"), o.getString("goods_name"));
                            goods.setMarket_price(o.getString("goods_marketprice"));
                            indexLists.add(goods);
                        }
                        new_goods.setAdapter(new IndexGridAdapter(getActivity(), indexLists));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });

        aq.ajax(CommonDataObject.INDEX_HOT_GOODS_LIST, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//
                try {
                    if ("200".equals(object.getString("code"))) {
                        JSONArray goodsList = object.getJSONObject("datas").getJSONArray("goods_list");
                        int x = 0;
                        if (goodsList.length() <= 4) {
                            x = goodsList.length();
                        } else {
                            x = 4;
                        }

                        List<Goods4IndexList> indexLists = new ArrayList<Goods4IndexList>();

                        for (int i = 0; i < x; i++) {
                            JSONObject o = goodsList.getJSONObject(i);

                            Goods4IndexList goods = new Goods4IndexList(o.getString("goods_id"), o.getString("goods_price"), o.getString("goods_image_url"), o.getString("goods_name"));
                            goods.setMarket_price(o.getString("goods_marketprice"));
                            indexLists.add(goods);
                        }
                        hot_goods.setAdapter(new IndexGridAdapter(getActivity(), indexLists));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });

    }

    public void jumpMarket(){
        Intent intent = new Intent(getActivity(), MarketActivity.class);
        startActivity(intent);
    }

    public void jumpBroadBand(){
        Intent intent = new Intent(getActivity(), BroadBandActivity.class);
        startActivity(intent);
    }

    public void jumpMobileNumberList(){
        Intent intent = new Intent(getActivity(), MobileNumberListActivity.class);
        startActivity(intent);
    }

    public void jumpContractMachine(){
        Intent intent = new Intent(getActivity(), ContractMachineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        search.clearFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER )&& event.getAction()== MotionEvent.ACTION_DOWN){
            // TODO: 2015/8/19 搜索
            String text = v.getText().toString();
            System.out.println(text);
            return true;
        }
        return false;
    }
}
