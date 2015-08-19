package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.LoginUtil;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);

        AQuery aq = new AQuery(this);

        aq.id(R.id.user_setting_logout).clicked(this,"doLogout");
    }

    public void doLogout(){
        LoginUtil.logout(this);
        finish();
    }
}
