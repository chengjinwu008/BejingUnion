package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/20.
 */
public class MobileNumberListActivity extends BaseActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_phone_number);

        aq = new AQuery(this);

        aq.id(R.id.mobile_number_back).clicked(this,"closeUp");

    }

    public void closeUp(){
        finish();
    }
}
