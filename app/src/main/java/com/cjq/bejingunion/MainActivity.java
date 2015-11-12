package com.cjq.bejingunion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.ThemeUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.event.EventShutDown;
import com.cjq.bejingunion.fragements.CartFragment;
import com.cjq.bejingunion.fragements.CategoryFragment;
import com.cjq.bejingunion.fragements.IndexFragment;
import com.cjq.bejingunion.fragements.UserCenterFragmentMain;
import com.cjq.bejingunion.service.BackgroundService;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.FragmentView;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (LoginUtil.isAutoLogin(this)) {
            LoginUtil.autoLogin(this);
        }
        intent = new Intent(this, BackgroundService.class);

        if (savedInstanceState == null){
            PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "rU95anFF579fG7z8N2iHcbZ4");
            startService(intent);
            Bmob.initialize(this, "e44326ae71a6f2bf955ee40f80a341e1");
        }

        FragmentView fragmentView = (FragmentView) findViewById(R.id.main_fragment);

        List<Pair<Pair<String, Integer>, Fragment>> datas = new ArrayList<>();

        datas.add(new Pair<Pair<String, Integer>, Fragment>(new Pair<String, Integer>("首页", R.drawable.a45), new IndexFragment()));
        datas.add(new Pair<Pair<String, Integer>, Fragment>(new Pair<String, Integer>("分类", R.drawable.a46), new CategoryFragment()));
        datas.add(new Pair<Pair<String, Integer>, Fragment>(new Pair<String, Integer>("购物车", R.drawable.a47), new CartFragment()));
        datas.add(new Pair<Pair<String, Integer>, Fragment>(new Pair<String, Integer>("会员中心", R.drawable.a48), new UserCenterFragmentMain()));

        try {
            fragmentView.setData(datas, getSupportFragmentManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //loading
//        if (savedInstanceState == null) {
//            Intent intent = new Intent(this, WelcomeActivity.class);
//            startActivity(intent);
//        }

        //leading

        if (LoginUtil.firstOpen(this)) {
            Intent intent = new Intent(this, LeadingActivity.class);
            startActivity(intent);
            LoginUtil.firstOpenSet(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
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

    public void clickNothing(View view) {

    }
}
