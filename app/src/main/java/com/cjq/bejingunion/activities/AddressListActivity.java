package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.AddressAdapter;
import com.cjq.bejingunion.entities.Address4Show;
import com.cjq.bejingunion.utils.LoginUtil;

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
public class AddressListActivity extends BaseActivity implements AddressAdapter.Listener {

    private AQuery aq;
    private ListView list;
    private List<Address4Show> alist;
    private AddressAdapter baseAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diliver_address_list);
        intent = getIntent();
        aq = new AQuery(this);
        aq.id(R.id.address_back).clicked(this, "closeUp");
        aq.id(R.id.address_add_new).clicked(this, "writeForm");
        list = aq.id(R.id.address_list).getListView();

        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.ADDRESS_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONArray al = object.getJSONObject("datas").getJSONArray("address_list");

                            alist = new ArrayList<Address4Show>();
                            for (int i = 0; i < al.length(); i++) {
                                JSONObject o = al.getJSONObject(i);
                                Address4Show address4Show = new Address4Show(o.getString("area_info") + " " + o.getString("address"), o.getString("true_name"), o.getString("mob_phone"), o.getString("address_id"));
                                address4Show.setAreaId(o.getString("area_id"));
                                address4Show.setCityId(o.getString("city_id"));
                                address4Show.setmDefault(o.getInt("is_default")==1);
                                alist.add(address4Show);
                            }
                            baseAdapter = new AddressAdapter(alist, AddressListActivity.this);

                            if(intent.getBooleanExtra("choose",false)){
                                baseAdapter.setListener(AddressListActivity.this);
                            }

                            list.setAdapter(baseAdapter);
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

    public void writeForm() {
        Intent intent = new Intent(this, AddressFormActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //添加成功
                Address4Show address4Show = new Address4Show(data.getStringExtra("area_info") + data.getStringExtra("address"), data.getStringExtra("true_name"), data.getStringExtra("phone_number"), data.getStringExtra("address_id"));
                alist.add(address4Show);
                baseAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //编辑成功
                int position = data.getIntExtra("position", 0);
                Address4Show address4Show = (Address4Show) baseAdapter.getItem(position);
                address4Show.setAddress(data.getStringExtra("area_info") + data.getStringExtra("address"));
                address4Show.setTrueName(data.getStringExtra("true_name"));
                address4Show.setPhoneNumber(data.getStringExtra("phone_number"));

                baseAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onContentClick(int i) {
        intent.putExtra("address",alist.get(i));

        setResult(RESULT_OK,intent);
        finish();
    }
}

