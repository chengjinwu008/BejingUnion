package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.CollectionAdapter;
import com.cjq.bejingunion.adapter.SwipeListAdapter;
import com.cjq.bejingunion.entities.CollectionToShow;
import com.cjq.bejingunion.event.EventCollectionChange;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.SwipeListView;
import com.ypy.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/25.
 */
public class MyCollectionActivity extends BaseActivity {

    private AQuery aq;
    private SwipeListView listView;

    public void onEventMainThread(EventCollectionChange e) {
        aq.id(R.id.collection_count).text(String.valueOf(listView.getAdapter().getCount()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_list);

        //请求列表

        aq = new AQuery(this);

        aq.id(R.id.collection_back).clicked(this, "closeUp");

        listView = (SwipeListView) aq.id(R.id.collection_list).getView();


        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.COLLECTION_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONArray fl = object.getJSONObject("datas").getJSONArray("favorites_list");

                            int count = object.getJSONObject("datas").getInt("count");

                            aq.id(R.id.collection_count).text(String.valueOf(count));

                            List<CollectionToShow> list = new ArrayList<CollectionToShow>();
                            for (int i = 0; i < fl.length(); i++) {
                                JSONObject o = fl.getJSONObject(i);
                                CollectionToShow collectionToShow = new CollectionToShow(o.getString("goods_image_url"), o.getString("goods_name"), o.getString("fav_id"), o.getString("goods_price"), o.getString("goods_id"));
                                list.add(collectionToShow);
                            }

                            listView.setAdapter(new CollectionAdapter(MyCollectionActivity.this, list));
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

    public void closeUp() {
        finish();
    }
}
