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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/21.
 */
public class FindChangePasswordActivity extends BaseActivity{

    private String phoneNumber;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up_new_password);

        aq = new AQuery(this);
        Intent intent =getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        
        aq.id(R.id.find_password_back).clicked(this,"closeUp");
        aq.id(R.id.find_password_submit).clicked(this,"submit");
        aq.id(R.id.find_password_phone_number).text(phoneNumber);
    }

    public void submit(){
        String password = aq.id(R.id.find_password_new_password).getText().toString();
        String re_password = aq.id(R.id.find_password_re_password).getText().toString();

        if(password.equals(re_password)){
            Map<String,String> params = new HashMap<>();
            params.put("username",phoneNumber);
            params.put("password",password);
            params.put("password_confirm",re_password);

            aq.ajax(CommonDataObject.CHANGE_PASSWORD_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if(200==object.getInt("code")){
                            Toast.makeText(FindChangePasswordActivity.this,object.getJSONObject("datas").getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(FindChangePasswordActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
                        }
                        super.callback(url, object, status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            Toast.makeText(FindChangePasswordActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
        }
    }

    public void closeUp() {
        finish();
    }
}
