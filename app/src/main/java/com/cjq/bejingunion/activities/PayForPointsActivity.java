package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/2.
 */
public class PayForPointsActivity extends BaseActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_for_points);

        aq = new AQuery(this);
        aq.id(R.id.pay_for_points_back).clicked(this,"closeUp");
        aq.id(R.id.pay_for_points_submit).clicked(this,"submit");
    }

    public void submit(){
        try {
            Map<String,String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("pdr_amount",String.valueOf(Float.parseFloat(aq.id(R.id.pay_for_points_money).getText().toString())));
            aq.ajax(CommonDataObject.PAY_FOR_POINTS_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if(200==object.getInt("code")){
                            JSONObject data = object.getJSONObject("datas");
                            String name = data.getString("goods_name");
                            String body = data.getString("goods_description");
                            String orderNumber = data.getString("pay_sn");
                            String price = data.getString("pdr_amount");
                            String orderType=data.getString("order_type");

                            PayUtil.payForPoints(PayForPointsActivity.this,name,body,price,orderNumber,orderType);
                            finish();
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

    public void closeUp(){
        finish();
    }
}
