package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/25.
 */
public class AddressListActivity extends BaseActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diliver_address_list);

        Map<String,String> params = new HashMap<>();
        params.put("key", LoginUtil.getKey(this));

        aq = new AQuery(this);

        aq.id(R.id.address_back).clicked(this,"closeUp");

        aq.ajax(CommonDataObject.ADDRESS_LIST_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                super.callback(url, object, status);
            }
        });
    }

    public void closeUp(){
        finish();
    }
}
