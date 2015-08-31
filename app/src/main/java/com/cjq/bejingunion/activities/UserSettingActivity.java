package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.view.MotionEvent;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserSettingActivity extends BaseActivity {

    private AQuery aq;
    private String nickName;
    private String avator;
    private int sex;
    private String birthday;
    private String mobile;
    private int is_agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);

        aq = new AQuery(this);

        Map<String,String> params  = new HashMap<>();
        try {
            params.put("key",LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.USER_INFO_SETTING, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONObject member_info = object.getJSONObject("member_info");

                            nickName = member_info.getString("member_nickname");
                            avator = member_info.getString("avator");
                            sex = member_info.getInt("member_sex");
                            birthday = member_info.getString("member_birthday");
                            mobile = member_info.getString("member_mobile");
                            is_agent = member_info.getInt("is_agent");

                            aq.id(R.id.user_setting_showing_nick_name).text(nickName);
                            aq.id(R.id.user_center_setting_portrait).image(avator, false, true);
                            aq.id(R.id.user_setting_edit_nickname).text(nickName);
                            aq.id(R.id.user_setting_username).text(member_info.getString("user_name"));

                            switch (sex) {
                                case 1:
                                    aq.id(R.id.user_setting_sex).text("男");
                                    break;
                                case 2:
                                    aq.id(R.id.user_setting_sex).text("女");
                                    break;
                                default:
                                    aq.id(R.id.user_setting_sex).text("保密");
                                    break;
                            }
                            aq.id(R.id.user_setting_edit_birthday).text(birthday);
                            aq.id(R.id.user_setting_edit_mobile).text(mobile);

                            if (1 != is_agent) {
                                aq.id(R.id.user_setting_vip).background(R.drawable.btn1);
                                aq.id(R.id.user_setting_become_partner).visible();
                            } else {
                                aq.id(R.id.user_setting_vip).background(R.drawable.btn);
                                aq.id(R.id.user_setting_become_partner).gone();
                            }
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

        aq.id(R.id.user_setting_logout).clicked(this,"doLogout");
    }

    public void doLogout(){
        LoginUtil.logout(this);
        finish();
    }
}
