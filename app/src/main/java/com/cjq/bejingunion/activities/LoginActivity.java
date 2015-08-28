package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.event.EventLoginIn;
import com.cjq.bejingunion.fragements.LoginFragment;

/**
 * Created by CJQ on 2015/8/27.
 */
public class LoginActivity extends BaseActivity {

    public void onEventMainThread(EventLoginIn in) {
        finish();
    }

    private android.support.v4.app.FragmentManager manager;
    private Fragment login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_center_main);
        manager = getSupportFragmentManager();
        login = new LoginFragment();
        manager.beginTransaction().replace(R.id.user_center_main, login).commit();
    }
}
