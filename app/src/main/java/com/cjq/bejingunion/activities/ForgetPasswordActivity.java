package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CJQ on 2015/8/19.
 */
public class ForgetPasswordActivity extends BaseActivity {

    private AQuery aq;
    private TextView verify;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ExecutorService timer;
    private final int period = 60;
    private final int rate = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_back);

        aq = new AQuery(this);

        aq.id(R.id.find_back).clicked(this,"closeUp");
        aq.id(R.id.find_get_verify).clicked(this,"getVerifyCode");
        aq.id(R.id.find_submit_verify).clicked(this, "doSubmit");

        verify  = aq.id(R.id.find_get_verify).getTextView();
        timer = Executors.newSingleThreadExecutor();
    }

    public void doSubmit(){
        final String phone = aq.id(R.id.find_phone_number).getText().toString();
        String verify = aq.id(R.id.find_verify).getText().toString();

        Map<String,String> params = new HashMap<>();
        params.put("username",phone);
        params.put("verify",verify);

        aq.ajax(CommonDataObject.FIND_PASSWORD_BACK_URL,params,JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    if(200==object.getInt("code")){
                        Intent intent = new Intent(ForgetPasswordActivity.this,FindChangePasswordActivity.class);
                        intent.putExtra("phoneNumber",phone);
                        startActivity(intent);
                        finish();
                    }else{
//                        Toast.makeText(ForgetPasswordActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
                        MyToast.showText(ForgetPasswordActivity.this, object.getJSONObject("datas").getString("error"), R.drawable.a2);
                    }
                } catch (JSONException e) {
                }
                super.callback(url, object, status);
            }
        });
    }

    public void getVerifyCode() {
        String phone = aq.id(R.id.find_phone_number).getText().toString();
        if ("".equals(phone)) {
//            Toast.makeText(this, "没有输入手机号，怎么获取短信验证码呢？", Toast.LENGTH_SHORT).show();
            MyToast.showText(ForgetPasswordActivity.this, "没有输入手机号，怎么获取短信验证码呢？", R.drawable.a2);
        } else {
            verify.setBackgroundColor(getResources().getColor(R.color.fake));
            verify.setEnabled(false);
            verify.setClickable(false);
            Map<String, String> params = new HashMap<>();
            params.put("phone", phone);

            aq.ajax(CommonDataObject.VERIFY_CODE_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                    System.out.println(object.toString());
                    try {
                        if ("200".equals(object.getString("code"))) {
                            //接收发送请求成功，即将发送验证码
//                            Toast.makeText(ForgetPasswordActivity.this, "短信验证码发送成功，请注意接收", Toast.LENGTH_SHORT).show();
                            MyToast.showText(ForgetPasswordActivity.this, "短信验证码发送成功，请注意接收");
                            timer.execute(new Runnable() {
                                @Override
                                public void run() {
                                    int s = period;

                                    while (s != 0) {
                                        try {
                                            Thread.sleep(rate);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        s--;
                                        final String ss = "重新获取(" + s + ")";
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                verify.setText(ss);
                                            }
                                        });
                                    }

                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            verify.setBackgroundColor(getResources().getColor(R.color.title_bar));
                                            verify.setText("获取验证码");
                                            verify.setEnabled(true);
                                            verify.setClickable(true);
                                        }
                                    });
                                }
                            });
                        } else {
//                            Toast.makeText(ForgetPasswordActivity.this, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                            MyToast.showText(ForgetPasswordActivity.this, object.getJSONObject("datas").getString("error"),R.drawable.a2);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    verify.setBackgroundColor(getResources().getColor(R.color.title_bar));
                                    verify.setText("获取验证码");
                                    verify.setEnabled(true);
                                    verify.setClickable(true);
                                }
                            });
                        }
                        super.callback(url, object, status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void closeUp() {
        finish();
    }
}
