package com.cjq.bejingunion;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.BannerAdapter;
import com.cjq.bejingunion.adapter.LeadingAdapter;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.entities.Leading;
import com.cjq.bejingunion.event.EventShutDown;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/8/19.
 */
public class LeadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leading_page);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        List<Leading> leadings = new ArrayList<>();
        leadings.add(new Leading(false,R.drawable.img1,this));
        leadings.add(new Leading(false,R.drawable.img2,this));
        leadings.add(new Leading(true,R.drawable.img3,this));

        pager.setAdapter(new LeadingAdapter(this,leadings));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_BACK){
//            new AlertDialog.Builder(this).setMessage("确定要离开吗？").setPositiveButton("退出", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    EventBus.getDefault().post(new EventShutDown());
//                }
//            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).show();

            new WarningAlertDialog(this).changeText("确定要离开吗").showCancel(true).onOKClick(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventShutDown());
                }
            }).onCancelClick(new Runnable() {
                @Override
                public void run() {
//                    Intent intent  = new Intent(MainActivity.this, SuperRegionSelectActivity.class);
//                    startActivity(intent);
                    // TODO: 2015/9/10 测试代码可以放这里
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }
}
