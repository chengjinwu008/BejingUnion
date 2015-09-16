package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.event.EventEvaluationChange;
import com.cjq.bejingunion.utils.LoginUtil;
import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/15.
 */
public class WriteEvaluationActivity extends BaseActivity {

    private AQuery aq;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_evaluate);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        aq = new AQuery(this);
        aq.id(R.id.do_write_evaluation).clicked(this,"doWriteEvaluation");
        aq.id(R.id.write_evaluate_back).clicked(this,"finish");
        Map<String,String> params = new HashMap<>();
        try {
            params.put("goods_id",id);
            params.put("key", LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.EVALUATION_INFO_URL, params, JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(200==object.getInt("code")){
                            String goods_id = object.getJSONObject("datas").getString("goods_id");
                            String goods_name = object.getJSONObject("datas").getString("goods_name");
                            String goods_price = object.getJSONObject("datas").getString("goods_price");
                            String src = object.getJSONObject("datas").getString("src");

                            aq.id(R.id.write_evaluate_banner).image(src,false,true);
                            aq.id(R.id.write_evaluate_name).text(goods_name);
                            aq.id(R.id.write_evaluate_price).text("￥"+goods_price);


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

    public void doWriteEvaluation(){
        Map<String,String> params = new HashMap<>();
        try {
            params.put("key",LoginUtil.getKey(this));
            params.put("goods_id",id);
            params.put("score", String.valueOf(aq.id(R.id.write_evaluate_ratingBar).getRatingBar().getRating()));
            params.put("comment",aq.id(R.id.write_evaluate_content).getText().toString());
            params.put("anony",aq.id(R.id.write_evaluate_hidden).isChecked()?"1":"0");
            aq.ajax(CommonDataObject.EVALUATION_ADD_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(object.getInt("code")==200){
                            Toast.makeText(WriteEvaluationActivity.this,object.getJSONObject("datas").getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            EventBus.getDefault().post(new EventEvaluationChange());
                        }else{
                            Toast.makeText(WriteEvaluationActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
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
