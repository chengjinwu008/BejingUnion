package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/9/2.
 */
public class PayForPointsActivity extends BaseActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_for_points);

        aq = new AQuery(this);
        aq.id(R.id.pay_for_points_back).clicked(this,"closeUp");
    }

    public void closeUp(){
        finish();
    }
}
