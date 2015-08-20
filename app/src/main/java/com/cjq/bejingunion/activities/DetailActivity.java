package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;

import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/20.
 */
public class DetailActivity extends BaseActivity{

    private String goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = getIntent();
        goods_id =  intent.getStringExtra("goods_id");
    }
}
