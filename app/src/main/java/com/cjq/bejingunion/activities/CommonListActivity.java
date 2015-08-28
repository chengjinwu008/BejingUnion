package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.AreaAdapter;
import com.cjq.bejingunion.adapter.ChoiceListAdapter;
import com.cjq.bejingunion.entities.Area4Show;
import com.cjq.bejingunion.entities.DetailChoice;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/26.
 */
public class CommonListActivity extends BaseActivity {

    public static final int PROVENCE_LIST = 1;
    public static final int CITY_LIST = 2;
    public static final int AREA_LIST = 3;
    public static final int CHOICE_LIST = 4;
    private AQuery aq;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list);
        final Intent intent = getIntent();
        int listCode = intent.getIntExtra("ListNo", 0);

        aq = new AQuery(this);
        aq.id(R.id.common_list_back).clicked(this, "closeUp");
        list = aq.id(R.id.common_list_list).getListView();
        Map<String, String> params = new HashMap<>();

        switch (listCode) {
            case PROVENCE_LIST:
                aq.id(R.id.common_list_title).text(getString(R.string.choose_provence));

                try {
                    params.put("key", LoginUtil.getKey(this));
                    aq.ajax(CommonDataObject.PROVENCE_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            try {
                                if (200 == object.getInt("code")) {
                                    JSONArray area_list = object.getJSONObject("datas").getJSONArray("area_list");

                                    List<Area4Show> area4ShowList = new ArrayList<Area4Show>();
                                    for (int i = 0; i < area_list.length(); i++) {
                                        JSONObject o = area_list.getJSONObject(i);

                                        Area4Show area = new Area4Show(o.getString("area_id"), o.getString("area_name"));
                                        area4ShowList.add(area);
                                    }

                                    aq.id(R.id.common_list_list).adapter(new AreaAdapter(area4ShowList, CommonListActivity.this));

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Area4Show area4Show = (Area4Show) parent.getAdapter().getItem(position);
                                            String aid = area4Show.getId();

                                            intent.putExtra("resultId", aid);
                                            intent.putExtra("resultName", area4Show.getText());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
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

                break;
            case CITY_LIST:
            case AREA_LIST:
                aq.id(R.id.common_list_title).text(getString(R.string.choose_city));

                try {
                    params.put("key", LoginUtil.getKey(this));
                    params.put("area_id", intent.getStringExtra("upId"));
                    aq.ajax(CommonDataObject.CITY_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            try {
                                if (200 == object.getInt("code")) {
                                    JSONArray area_list = object.getJSONObject("datas").getJSONArray("area_list");

                                    List<Area4Show> area4ShowList = new ArrayList<Area4Show>();
                                    for (int i = 0; i < area_list.length(); i++) {
                                        JSONObject o = area_list.getJSONObject(i);

                                        Area4Show area = new Area4Show(o.getString("area_id"), o.getString("area_name"));
                                        area4ShowList.add(area);
                                    }

                                    aq.id(R.id.common_list_list).adapter(new AreaAdapter(area4ShowList, CommonListActivity.this));

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Area4Show area4Show = (Area4Show) parent.getAdapter().getItem(position);
                                            String aid = area4Show.getId();

                                            intent.putExtra("resultId", aid);
                                            intent.putExtra("resultName", area4Show.getText());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
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

                break;
            case CHOICE_LIST:
                ArrayList<DetailChoice> choices = intent.getParcelableArrayListExtra("choices");
                aq.id(R.id.common_list_title).text(getString(R.string.chose_cate));
                aq.id(R.id.common_list_list).adapter(new ChoiceListAdapter(choices, CommonListActivity.this));
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DetailChoice choice = (DetailChoice) parent.getAdapter().getItem(position);
                        String aid = choice.getId();
                        intent.putExtra("resultId", aid);
                        intent.putExtra("resultName", choice.getValue());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
        }
    }

    public void closeUp() {
        finish();
    }
}
