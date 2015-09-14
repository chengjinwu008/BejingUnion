package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.AreaAdapter;
import com.cjq.bejingunion.entities.Area4Show;
import com.cjq.bejingunion.entities.AreaInfo;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/9.
 */
public class SuperRegionSelectActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private AQuery aq;

    String provinceName = "";
    String areaName = "";
    String cityName = "";

    private int provinceId = 0;
    private int areaId = 0;
    private int cityId = 0;
    private List<Area4Show> area4ShowList;
    private AreaAdapter adapter;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list);

        Intent intent = getIntent();
        level = intent.getIntExtra("level", 3);

        aq = new AQuery(this);
        aq.id(R.id.common_list_back).clicked(this, "closeUp");
        aq.id(R.id.common_list_title).text("请选择省份");
        area4ShowList = new ArrayList<>();
        adapter = new AreaAdapter(area4ShowList, this);
        aq.id(R.id.common_list_list).adapter(adapter);

        aq.id(R.id.common_list_list).itemClicked(this);

        requestList(0);
    }

    public void closeUp() {
        if (areaId != 0) {
            areaId = 0;
            areaName = "";
            requestList(provinceId);
            aq.id(R.id.common_list_title).text(provinceName + " " + areaName + " " + cityName);
        } else if (provinceId != 0) {
            provinceName = "";
            provinceId = 0;
            requestList(0);
            aq.id(R.id.common_list_title).text("请选择省份");
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            closeUp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (provinceId == 0) {
            //选省
            provinceId = Integer.parseInt(area4ShowList.get(position).getId());
            provinceName = area4ShowList.get(position).getText();
            if (level > 1)
                requestList(provinceId);
            else
                complete();
        } else if (areaId == 0) {
            //选区
            areaId = Integer.parseInt(area4ShowList.get(position).getId());
            areaName = area4ShowList.get(position).getText();
            if (level > 2)
                requestList(areaId);
            else
                complete();
        } else {
            //选市
            cityId = Integer.parseInt(area4ShowList.get(position).getId());
            cityName = area4ShowList.get(position).getText();
            //选完收工
            if (level == 3)
                complete();
        }
        aq.id(R.id.common_list_title).text(provinceName + " " + areaName + " " + cityName);
    }

    private void complete() {
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setAreaId(areaId);
        areaInfo.setAreaName(areaName);
        areaInfo.setProvinceId(provinceId);
        areaInfo.setProvinceName(provinceName);
        areaInfo.setCityId(cityId);
        areaInfo.setCityName(cityName);

        Intent intent = new Intent();
        intent.putExtra("areaInfo", areaInfo);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void requestList(int parentId) {
        Map<String, String> params = new HashMap<>();
        if (parentId != 0)
            params.put("area_id", String.valueOf(parentId));
        try {
            params.put("key", LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.PROVENCE_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            area4ShowList.clear();
                            JSONArray areaList = object.getJSONObject("datas").getJSONArray("area_list");

                            List<Area4Show> area4ShowListTemp = new ArrayList<Area4Show>();

                            for (int i = 0; i < areaList.length(); i++) {
                                JSONObject o = areaList.getJSONObject(i);
                                Area4Show area4Show = new Area4Show(o.getString("area_id"), o.getString("area_name"));
                                area4ShowListTemp.add(area4Show);
                            }

                            area4ShowList.addAll(area4ShowListTemp);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.callback(url, object, status);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
