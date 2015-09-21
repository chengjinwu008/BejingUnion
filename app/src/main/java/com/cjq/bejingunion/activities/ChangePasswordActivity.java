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
import com.cjq.bejingunion.dialog.MyToast;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/26.
 */
public class ChangePasswordActivity extends BaseActivity {

    private AQuery aq;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        aq = new AQuery(this);

        Intent intent = getIntent();
        name =  intent.getStringExtra("name");

        aq.id(R.id.change_password_user_portrait).image(intent.getStringExtra("portrait"),false,true);
        aq.id(R.id.change_password_user_name).text(name);
        aq.id(R.id.change_password_submit).clicked(this, "submit");
        aq.id(R.id.change_password_back).clicked(this,"closeUp");
    }

    public void closeUp(){
        finish();
    }

    public void submit(){
        String op = aq.id(R.id.change_password_origin_password).getText().toString();
        final String p = aq.id(R.id.change_password_password).getText().toString();
        String rp = aq.id(R.id.change_password_re_password).getText().toString();

        if(LoginUtil.getPassword(this).equals(op)){
            if("".equals(p.trim())){
//                Toast.makeText(this, R.string.empty_password_is_not_allowed,Toast.LENGTH_SHORT).show();
                MyToast.showText(this, R.string.empty_password_is_not_allowed, R.drawable.a2);
            }else{
                if(p.trim().equals(rp.trim())){

                    Map<String,String> params = new HashMap<>();
                    params.put("username",name);
                    params.put("password",p);
                    params.put("password_confirm", rp);

                    aq.ajax(CommonDataObject.CHANGE_PASSWORD_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            try {
                                if(200==object.getInt("code")){
                                    LoginUtil.savePassword(ChangePasswordActivity.this,p);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            super.callback(url, object, status);
                        }
                    });
                }else{
//                    Toast.makeText(this, R.string.p_not_equals_to_rp,Toast.LENGTH_SHORT).show();
                    MyToast.showText(this, R.string.p_not_equals_to_rp, R.drawable.a2);
                }
            }

        }else{
//            Toast.makeText(this, R.string.wrong_origin_password,Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.wrong_origin_password, R.drawable.a2);
        }
    }
}
