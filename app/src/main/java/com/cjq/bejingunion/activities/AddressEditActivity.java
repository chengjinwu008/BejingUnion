package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.MyToast;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/26.
 */
public class AddressEditActivity extends BaseActivity {

    private AQuery aq;
    private String provenceId;
    private String cityId;
    private String provenceName;
    private String areaId;
    private String cityName;
    private String areaName;
    private String addressId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address_form);

        Intent intent = getIntent();
        addressId = intent.getStringExtra("addressId");
        position = intent.getIntExtra("position", 0);


        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("address_id", addressId);

            aq = new AQuery(this);
            aq.id(R.id.new_address_form_title).text(getString(R.string.edit_address));
            aq.id(R.id.new_address_form_back).clicked(this, "closeUp");
            aq.id(R.id.new_address_form_submit).clicked(this, "doSubmit");
            aq.id(R.id.new_address_form_select_provence).clicked(this, "chooseProvence");
            aq.id(R.id.new_address_form_select_area).clicked(this, "chooseArea");
            aq.id(R.id.new_address_form_select_city).clicked(this, "chooseCity");
            aq.ajax(CommonDataObject.ADDRESS_EDIT_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONObject o = object.getJSONObject("datas").getJSONObject("address_info");
                            aq.id(R.id.new_address_form_username).text(o.getString("true_name"));
                            aq.id(R.id.new_address_form_mob_phone).text(o.getString("mob_phone"));
                            aq.id(R.id.new_address_form_address).text(o.getString("address"));

                            JSONObject provience = o.getJSONObject("province");
                            provenceId = provience.getString("area_id");
                            provenceName = provience.getString("area_name");
                            aq.id(R.id.new_address_form_select_provence_show).text(provenceName);

                            JSONObject city = o.getJSONObject("city");
                            cityId = city.getString("area_id");
                            cityName = city.getString("area_name");
                            aq.id(R.id.new_address_form_select_city_show).text(cityName);

                            JSONObject area = o.getJSONObject("area");
                            areaId = area.getString("area_id");
                            areaName = area.getString("area_name");
                            aq.id(R.id.new_address_form_select_area_show).text(areaName);
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

    public void chooseProvence() {
        Intent intent = new Intent(this, CommonListActivity.class);
        intent.putExtra("ListNo", CommonListActivity.PROVENCE_LIST);
        startActivityForResult(intent, 0);
    }

    public void chooseCity() {
        if (!"".equals(provenceId)) {
            Intent intent = new Intent(this, CommonListActivity.class);
            intent.putExtra("ListNo", CommonListActivity.CITY_LIST);
            intent.putExtra("upId", provenceId);
            startActivityForResult(intent, 1);
        } else {
//            Toast.makeText(this, R.string.please_choose_provence_first, Toast.LENGTH_SHORT).show();
            MyToast.showText(this,R.string.please_choose_provence_first,R.drawable.a2);
        }
    }

    public void chooseArea() {
        if (!"".equals(cityId)) {
            Intent intent = new Intent(this, CommonListActivity.class);
            intent.putExtra("ListNo", CommonListActivity.AREA_LIST);
            intent.putExtra("upId", cityId);
            startActivityForResult(intent, 2);
        } else {
//            Toast.makeText(this, R.string.please_choose_city_first, Toast.LENGTH_SHORT).show();
            MyToast.showText(this,R.string.please_choose_city_first,R.drawable.gou);
        }
    }

    public void closeUp() {
        finish();
    }

    public void doSubmit() {
        final String true_name = aq.id(R.id.new_address_form_username).getText().toString();
        final String mobile_phone = aq.id(R.id.new_address_form_mob_phone).getText().toString();
        final String address = aq.id(R.id.new_address_form_address).getText().toString();

        if ("".equals(true_name)) {
//            Toast.makeText(this, R.string.please_input_user_name, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_input_user_name, R.drawable.a2);
            return;
        }
        if ("".equals(mobile_phone)) {
//            Toast.makeText(this, R.string.please_input_phone_number, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_input_phone_number, R.drawable.a2);
            return;
        }
        if ("".equals(provenceId)) {
//            Toast.makeText(this, R.string.please_choose_provence_first, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_choose_provence_first, R.drawable.a2);
            return;
        }
        if ("".equals(cityId)) {
//            Toast.makeText(this, R.string.please_choose_city_first, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_choose_city_first, R.drawable.a2);
            return;
        }

        if ("".equals(areaId)) {
//            Toast.makeText(this, R.string.please_select_area, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_select_area, R.drawable.a2);
            return;
        }
        if ("".equals(address)) {
//            Toast.makeText(this, R.string.please_input_address, Toast.LENGTH_SHORT).show();
            MyToast.showText(this, R.string.please_input_address, R.drawable.a2);
            return;
        }
        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(AddressEditActivity.this));
            params.put("address_id", addressId);
            params.put("true_name", true_name);
            params.put("mob_phone", mobile_phone);
            params.put("province_id", provenceId);
            params.put("city_id", cityId);
            params.put("area_id", areaId);
            params.put("address", address);
            params.put("area_info", provenceName + " " + cityName + " " + areaName);

            aq.ajax(CommonDataObject.DO_EDIT_ADDRESS, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            Intent intent = getIntent();
                            intent.putExtra("true_name", true_name);
                            intent.putExtra("phone_number", mobile_phone);
                            intent.putExtra("area_info", provenceName + " " + cityName + " " + areaName + " ");
                            intent.putExtra("address", address);
                            intent.putExtra("address_id", addressId);
                            intent.putExtra("position", position);

                            setResult(RESULT_OK, intent);
                            finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    provenceName = data.getStringExtra("resultName");
                    provenceId = data.getStringExtra("resultId");
                    aq.id(R.id.new_address_form_select_provence_show).text(provenceName);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    cityName = data.getStringExtra("resultName");
                    cityId = data.getStringExtra("resultId");
                    aq.id(R.id.new_address_form_select_city_show).text(cityName);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    areaName = data.getStringExtra("resultName");
                    areaId = data.getStringExtra("resultId");
                    aq.id(R.id.new_address_form_select_area_show).text(areaName);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
