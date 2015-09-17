package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.CardNumberAdapter;
import com.cjq.bejingunion.entities.AreaInfo;
import com.cjq.bejingunion.entities.CardNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/10.
 */
public class PhoneNumberActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private AQuery aq;
    private List<CardNumber> cardNumberList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_number_list);

        aq = new AQuery(this);
        aq.id(R.id.phone_number_list_select_city).clicked(this, "selectCity");
        aq.id(R.id.phone_number_back).clicked(this, "finish");
        aq.id(R.id.phone_number_list_list).itemClicked(this);
        requestList(0, 0);
    }

    public void selectCity(){
        Intent intent = new Intent(this,SuperRegionSelectActivity.class);
        intent.putExtra("level",2);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode==RESULT_OK){
                    AreaInfo info = data.getParcelableExtra("areaInfo");
                    aq.id(R.id.phone_number_list_select_city).text(info.getAreaName());
                    requestList(info.getProvinceId(),info.getAreaId());
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void requestList(long province_id,long city_id){
        Map<String,String> params = new HashMap<>();
        if(province_id!=0 && city_id!=0){
            params.put("province_id", String.valueOf(province_id));
            params.put("city_id", String.valueOf(city_id));
        }

        aq.ajax(CommonDataObject.CARD_LIST_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                try {
                    if(200==object.getInt("code")){
                        JSONArray a= object.getJSONArray("datas");

                        cardNumberList =new ArrayList<CardNumber>();
                        for(int i=0;i<a.length();i++){
                            JSONObject o = a.getJSONObject(i);
                            CardNumber cardNumber =new CardNumber(o.getString("id"),o.getString("phone_number"));
                            cardNumberList.add(cardNumber);
                        }
                        adapter = new  CardNumberAdapter(cardNumberList,PhoneNumberActivity.this);
                        aq.id(R.id.phone_number_list_list).adapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardNumber selected = cardNumberList.get(position);
        Intent intent = new Intent();
        intent.putExtra("cardNumber",selected);
        setResult(RESULT_OK,intent);
        finish();
    }
}
