package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.CardAdapter;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.utils.GoodsUtil;

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
public class MobileNumberListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private AQuery aq;
    private int currentPage=1;
    private int gc_id = 2;
    private CardAdapter adapter;
    private ArrayList<Goods4IndexList> goods4IndexLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list);
        aq = new AQuery(this);
        aq.id(R.id.common_list_title).text("卡号商品");
        aq.id(R.id.common_list_back).clicked(this, "finish");
        aq.id(R.id.common_list_list).itemClicked(this);

        try{
            Map<String,String> params = new HashMap<>();
            params.put("key","4");
            params.put("page", String.valueOf(CommonDataObject.PAGE_SIZE));
            params.put("curpage", String.valueOf(currentPage));
            params.put("gc_id", String.valueOf(gc_id));
            params.put("order", String.valueOf(2));

            aq.ajax(CommonDataObject.GOODS_LIST_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                    System.out.println(object.toString());
                    try {
                        if(200==object.getInt("code")){
                            JSONArray goods_list=object.getJSONObject("datas").getJSONArray("goods_list");

                            goods4IndexLists = new ArrayList<Goods4IndexList>();
                            for (int i = 0;i<goods_list.length();i++){
                                JSONObject o = goods_list.getJSONObject(i);
                                Goods4IndexList goods4IndexList = new Goods4IndexList(o.getString("goods_id"),o.getString("goods_price"),o.getString("goods_image_url"),o.getString("goods_name"));
                                goods4IndexLists.add(goods4IndexList);
                            }

                            adapter = new CardAdapter(goods4IndexLists,MobileNumberListActivity.this);

                            aq.id(R.id.common_list_list).adapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.callback(url, object, status);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String goods_id =  goods4IndexLists.get(position).getGoods_id();
        GoodsUtil.showGoodsDetail(this,goods_id, GoodsUtil.TYPE.CARD);
    }
}
