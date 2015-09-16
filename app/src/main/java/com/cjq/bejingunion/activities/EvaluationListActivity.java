package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.EvaluationListAdapter;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.event.EventEvaluationChange;
import com.cjq.bejingunion.utils.LoginUtil;

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
public class EvaluationListActivity extends BaseActivity {

    private AQuery aq;

    public void onEventMainThread(EventEvaluationChange e){
        requestData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list);

        aq = new AQuery(this);

        aq.id(R.id.common_list_title).text("待评论列表");
        aq.id(R.id.common_list_back).clicked(this,"finish");
        requestData();
    }

    private void requestData() {
        Map<String,String> params = new HashMap<>();
        try {
            params.put("key", LoginUtil.getKey(this));

            aq.ajax(CommonDataObject.EVALUATION_LIST,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
//                        System.out.println(object.toString());
                        if(200==object.getInt("code")){
                            JSONArray a = object.getJSONArray("datas");
                            List<Goods4IndexList> goods4IndexLists = new ArrayList<Goods4IndexList>();
                            for(int i=0;i<a.length();i++){
                                JSONObject o = a.getJSONObject(i);

                                Goods4IndexList goods4IndexList = new Goods4IndexList(o.getString("goods_id"),o.getString("goods_price"),o.getString("src"),o.getString("goods_name"));
                                goods4IndexList.setMarket_price(o.getString("evaluation_count"));

                                goods4IndexLists.add(goods4IndexList);
                            }

                            aq.id(R.id.common_list_list).adapter(new EvaluationListAdapter(goods4IndexLists,EvaluationListActivity.this));
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
}
