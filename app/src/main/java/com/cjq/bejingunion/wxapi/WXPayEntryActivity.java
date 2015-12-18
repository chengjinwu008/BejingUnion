package com.cjq.bejingunion.wxapi;

import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.event.EventWXpayComplete;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.ypy.eventbus.EventBus;

/**
 * Created by CJQ on 2015/12/15.
 */
public class WXPayEntryActivity extends BaseActivity {

    public void onResp(BaseResp resp){
        if(resp.errCode==0){
            EventBus.getDefault().post(new EventWXpayComplete());
        }
        finish();
    }
}
