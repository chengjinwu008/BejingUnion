package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.EvaluationAdapter;
import com.cjq.bejingunion.entities.Evaluation;
import com.cjq.bejingunion.view.PercentView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/28.
 */
public class EvaluateActivity extends BaseActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");

        aq = new AQuery(this);

        Map<String,String> params = new HashMap<>();
        params.put("goods_id", goods_id);
        aq.id(R.id.evaluation_back).clicked(this, "closeUp");
        aq.id(R.id.evaluation_write_evaluation).clicked(this,"jumpToWriteEvaluation");

        aq.ajax(CommonDataObject.EVALUATION_LIST_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    System.out.println(object.toString());
                    if(200==object.getInt("code")){
                        JSONObject datas = object.getJSONObject("datas");
                        aq.id(R.id.evaluate_avg_points).text(String.valueOf(datas.getDouble("avg")));
                        aq.id(R.id.evaluate_avg_rating_bar).rating((float) datas.getDouble("avg"));
                        aq.id(R.id.evaluate_good_percent).invoke("setPercent", new Class[]{Float.class}, Double.valueOf(datas.getDouble("good")).floatValue());
                        aq.id(R.id.evaluate_good_percent).invoke("setDescription", new Class[]{String.class}, getString(R.string.good));
                        aq.id(R.id.evaluate_general_percent).invoke("setPercent", new Class[]{Float.class}, Double.valueOf(datas.getDouble("general")).floatValue());
                        aq.id(R.id.evaluate_general_percent).invoke("setDescription", new Class[]{String.class}, getString(R.string.general));
                        aq.id(R.id.evaluate_poor_percent).invoke("setPercent", new Class[]{Float.class}, Double.valueOf(datas.getDouble("poor")).floatValue());
                        aq.id(R.id.evaluate_poor_percent).invoke("setDescription", new Class[]{String.class}, getString(R.string.poor));

                        JSONArray goodsevallist = datas.getJSONArray("goodsevallist");
                        List<Evaluation> evaluationList = new ArrayList<Evaluation>();
                        if(goodsevallist.length()>0){
                            for (int i = 0;i<goodsevallist.length();i++){
                                JSONObject e = goodsevallist.getJSONObject(i);
                                Evaluation ev = new Evaluation(e.getString("avator"),e.getString("member_nickname"),e.getLong("geval_addtime"),e.getString("geval_content"),e.getString("geval_id"));
                                evaluationList.add(ev);
                            }
                        }else{
                            aq.id(R.id.evaluation_no_evaluation_hint).visible();
                        }

                        aq.id(R.id.evaluate_comments_list).adapter(new EvaluationAdapter(evaluationList,EvaluateActivity.this)).visible();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }

    public void closeUp(){
        finish();
    }

    public void jumpToWriteEvaluation(){

    }
}
