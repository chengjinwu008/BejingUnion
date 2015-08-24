package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.event.EventLoginIn;
import com.cjq.bejingunion.utils.LoginUtil;
import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CJQ on 2015/8/19.
 */
public class RegisterActivity extends BaseActivity {

    private AQuery aq;
    private ExecutorService timer;
    private final int period = 60;
    private final int rate = 1000;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        aq = new AQuery(this);
        aq.id(R.id.sign_up_back).clicked(this, "closeUp");
        String s = "我已阅读并同意《呵呵协议》";
        SpannableString string = new SpannableString(s);
        URLSpan span = new URLSpan("http://www.baidu.com");
        string.setSpan(span, 7, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        aq.id(R.id.sign_up_agree).text(string).getCheckBox().setMovementMethod(LinkMovementMethod.getInstance());
        aq.id(R.id.find_get_verify).clicked(this, "getVerifyCode");
        aq.id(R.id.sign_up_do_register).clicked(this, "doRegister");

        timer = Executors.newSingleThreadExecutor();
        verify = aq.id(R.id.find_get_verify).getTextView();
    }

    public void getVerifyCode() {
        String phone = aq.id(R.id.sign_up_mobile_phone_number).getText().toString();
        if ("".equals(phone)) {
            Toast.makeText(this, "没有输入手机号，怎么获取短信验证码呢？", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this, "短信验证码发送成功，请注意接收", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
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

    public void doRegister() {
        //检查区域

        final String username = aq.id(R.id.sign_up_mobile_phone_number).getText().toString();
        final String password = aq.id(R.id.sign_up_password).getText().toString();
        String verify = aq.id(R.id.sign_up_verify).getText().toString();

        if ("".equals(username)) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(verify)) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!aq.id(R.id.sign_up_agree).isChecked()) {
            Toast.makeText(RegisterActivity.this, "请阅读并同意注册协议", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("verify", verify);
            params.put("client", "android");

            aq.ajax(CommonDataObject.REGISTER_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if ("200".equals(object.getString("code"))) {
                            JSONObject data = object.getJSONObject("datas");
                            LoginUtil.saveLoginInfo(RegisterActivity.this, username, data.getString("key"), password);
                            EventBus.getDefault().post(new EventLoginIn());
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    super.callback(url, object, status);
                }
            });
        }
    }
}
