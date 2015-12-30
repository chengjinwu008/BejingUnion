package com.cjq.bejingunion.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.Constants;
import com.cjq.bejingunion.event.EventWXpayComplete;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ypy.eventbus.EventBus;

/**
 * Created by CJQ on 2015/12/15.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{


    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
//        Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    public void onResp(BaseResp resp){
//        Toast.makeText(this,"3",Toast.LENGTH_SHORT).show();
        if(resp.errCode==0){
            EventBus.getDefault().post(new EventWXpayComplete());
        }
//        Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_SHORT).show();
        finish();

    }
}