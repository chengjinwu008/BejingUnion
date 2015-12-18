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
    private String is_virtual;
    private String is_fcode;

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
        aq.id(R.id.broadband_detail_new).clicked(this, "openNewBroadBand");
        aq.id(R.id.broadband_detail_old).clicked(this, "renewBroadBand");

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

                        is_virtual = data.getString("is_virtual");
                        is_fcode = data.getString("is_fcode");

                        aq.id(R.id.broadband_detail_name).text(name);
                        aq.id(R.id.broadband_detail_price).text("￥" + price);
                        aq.id(R.id.broadband_detail_v).text(v);
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

    public void openNewBroadBand() {
//        Intent intent = new Intent(this, BroadbandOrderConfirmActivity.class);
//        intent.putExtra("cart_id", goods_id + "|" + 1);
//        intent.putExtra("ifcart", "0");
//        startActivity(intent);
//        finish();
        GoodsUtil.showIdentify(this, 1);
    }

    public void renewBroadBand() {
//        GoodsUtil.showIdentify(this, 2);
        Intent intent = new Intent(this, BroadbandOrderConfirmActivity2.class);
        intent.putExtra("cart_id", goods_id + "|" + 1);
        intent.putExtra("ifcart", is_fcode);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, BroadbandOrderConfirmActivity.class);
                    intent.putExtra("cart_id", goods_id + "|" + 1);
                    intent.putExtra("phone_additional_id", data.getStringExtra("id"));
                    intent.putExtra("userName",data.getStringExtra("userName"));
                    intent.putExtra("id_number",data.getStringExtra("id_number"));
                    intent.putExtra("ifcart", is_fcode);
                    startActivity(intent);
                    finish();
                }
                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    Intent intent = new Intent(this, BroadbandOrderConfirmActivity2.class);
//                    intent.putExtra("cart_id", goods_id + "|" + 1);
//                    intent.putExtra("phone_additional_id", data.getStringExtra("id"));
//                    intent.putExtra("ifcart", "0");
//                    startActivity(intent);
//                    finish();
//                }
//                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
