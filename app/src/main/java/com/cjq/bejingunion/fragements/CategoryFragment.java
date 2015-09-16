package com.cjq.bejingunion.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.MarketActivity;
import com.cjq.bejingunion.adapter.Category1Adapter;
import com.cjq.bejingunion.entities.Category4Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private AQuery aq;
    private String id;
    private ListView list1;
    private GridView list2;
    private List<Category4Show> category4ShowList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.allkinds_list,container,false);

        aq = new AQuery(view);
        list1= aq.id(R.id.all_kinds_kinds_list).getListView();
        list2= aq.id(R.id.all_kinds_all_list).getGridView();
        list1.setOnItemClickListener(this);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), MarketActivity.class);
                intent.putExtra("brand_id",category4ShowList.get(position).getId());

                startActivity(intent);
            }
        });

        Map<String,String> params = new HashMap<>();
        params.put("gc_id","4");

        aq.ajax(CommonDataObject.CATEGORY_LIST_FOR_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                try {
                    if (200 == object.getInt("code")) {
                        JSONArray a = object.getJSONObject("datas").getJSONArray("class_list");

                        List<Category4Show> category4ShowList = new ArrayList<Category4Show>();
                        for (int i = 0; i < a.length(); i++) {
                            JSONObject o = a.getJSONObject(i);
                            Category4Show category4Show = new Category4Show(o.getString("gc_name"), o.getString("gc_id"), false);
                            category4ShowList.add(category4Show);
                        }

                        if (category4ShowList.get(0) != null) {
                            category4ShowList.get(0).setChosen(true);
                            id = category4ShowList.get(0).getId();
                            requestData();
                        }

                        list1.setAdapter(new Category1Adapter(category4ShowList,getActivity()));

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.callback(url, object, status);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category1Adapter adapter = (Category1Adapter) parent.getAdapter();
        adapter.changeChosen(position);
        Category4Show category4Show = (Category4Show) adapter.getItem(position);
        this.id = category4Show.getId();
        requestData();
    }

    public void requestData(){
        Map<String,String> params = new HashMap<>();
        params.put("gc_id",id);

        aq.ajax(CommonDataObject.CATEGORY_LIST_FOR_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                try {
                    if (200 == object.getInt("code")) {
                        JSONArray a = object.getJSONObject("datas").getJSONArray("class_list");

                        category4ShowList = new ArrayList<Category4Show>();
                        for (int i = 0; i < a.length(); i++) {
                            JSONObject o = a.getJSONObject(i);
                            Category4Show category4Show = new Category4Show(o.getString("gc_name"), o.getString("gc_id"), false);
                            category4ShowList.add(category4Show);
                        }
                        list2.setAdapter(new Category1Adapter(category4ShowList, getActivity()));
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.callback(url, object, status);
            }
        });
    }
}
