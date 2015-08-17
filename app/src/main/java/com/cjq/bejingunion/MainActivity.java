package com.cjq.bejingunion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cjq.bejingunion.view.FragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricing_package_select_page);
        if(savedInstanceState==null)
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "rU95anFF579fG7z8N2iHcbZ4");



        //初始化banner
//        AQuery aq = new AQuery(this);
//
//        FragmentView fragmentView = (FragmentView) aq.id(R.id.main_fragment).getView();
//        List<Pair<Pair<String,Integer>,Fragment>> data =new ArrayList<>();
//        data.add(new Pair (new Pair<String, Integer>("首页", R.drawable.a45), new Fragment()));
//        data.add(new Pair (new Pair<String, Integer>("首页", R.drawable.a45), new Fragment()));
//        data.add(new Pair (new Pair<String, Integer>("首页", R.drawable.a45), new Fragment()));
//        data.add(new Pair(new Pair<String, Integer>("首页", R.drawable.a45), new Fragment()));
//        try {
//            fragmentView.setData(data, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        BannerView viewPager = (BannerView) aq.id(R.id.detail_banner).getView();
//
//        List<Ad> ads = new ArrayList<>();
//        ads.add(new Ad(this,"https://www.baidu.com/img/bd_logo1.png","1"));
//        ads.add(new Ad(this,"https://www.baidu.com/img/bd_logo1.png","1"));
//        ads.add(new Ad(this,"https://www.baidu.com/img/bd_logo1.png","1"));
//        ads.add(new Ad(this,"https://www.baidu.com/img/bd_logo1.png","1"));
//        ads.add(new Ad(this,"https://www.baidu.com/img/bd_logo1.png","1"));
//
//        viewPager.setAdapter(new BannerAdapter(this,ads));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
