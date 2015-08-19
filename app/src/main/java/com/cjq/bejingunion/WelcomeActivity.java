package com.cjq.bejingunion;

import android.os.Bundle;
import android.os.Handler;

/**
 * Created by CJQ on 2015/8/19.
 */
public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }
}
