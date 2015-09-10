package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.GoodsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/9.
 */
public class BroadBandDetailActivity extends BaseActivity {

    private AQuery aq;
    private String goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broad_band_detail);
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");

        aq = new AQuery(this);
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goods_id);
        aq.id(R.id.broadband_detail_evaluations).clicked(this, "showEvaluations");
        aq.id(R.id.broadband_detail_back).clicked(this, "finish");

        aq.ajax(CommonDataObject.GOODS_DETAIL_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                try {
                    if (200 == object.getInt("code")) {
                        JSONObject data = object.getJSONObject("datas").getJSONObject("goods_info");
                        String name = data.getString("goods_name");
                        String v = data.getString("gc_name");
                        String body = data.getString("mobile_body");
                        String evaluationCount = data.getString("evaluation_count");
                        String price = data.getString("goods_price");

                        aq.id(R.id.broadband_detail_name).text(name);
                        aq.id(R.id.broadband_detail_price).text("￥" + price);
                        aq.id(R.id.broadband_detail_v).text(v + "M");
                        aq.id(R.id.broadband_detail_body).text(body);
                        aq.id(R.id.broadband_detail_evaluation_count).text("已有" + evaluationCount + "人评论");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }

    public void showEvaluations() {
        GoodsUtil.showEvaluations(this, goods_id);
    }
}
