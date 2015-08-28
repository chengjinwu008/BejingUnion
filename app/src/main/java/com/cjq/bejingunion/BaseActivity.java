package com.cjq.bejingunion;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import com.cjq.bejingunion.event.EventShutDown;
import com.ypy.eventbus.EventBus;

/**
 * Created by CJQ on 2015/8/13.
 */
public class BaseActivity extends FragmentActivity {

    public void onEventMainThread(EventShutDown e) {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
