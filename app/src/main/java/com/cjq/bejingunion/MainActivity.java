package com.cjq.bejingunion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cjq.bejingunion.event.EventShutDown;
import com.cjq.bejingunion.fragements.CartFragment;
import com.cjq.bejingunion.fragements.CategoryFragment;
import com.cjq.bejingunion.fragements.IndexFragment;
import com.cjq.bejingunion.fragements.UserCenterFragmentMain;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.FragmentView;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null)
            PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "rU95anFF579fG7z8N2iHcbZ4");

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
        if (savedInstanceState == null) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
        if(LoginUtil.isAutoLogin(this)){
            LoginUtil.autoLogin(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this).setMessage("确定要离开吗？").setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EventBus.getDefault().post(new EventShutDown());
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
