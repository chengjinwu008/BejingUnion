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
 * Created by CJQ on 2015/8/25.
 */
public class AddressFormActivity extends BaseActivity {

    private AQuery aq;
    private String provenceName;
    private String provenceId = "";
    private String cityName;
    private String cityId = "";
    private String areaName;
    private String areaId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address_form);

        aq = new AQuery(this);
        aq.id(R.id.new_address_form_back).clicked(this, "closeUp");
        aq.id(R.id.new_address_form_submit).clicked(this, "doSubmit");
        aq.id(R.id.new_address_form_select_provence).clicked(this, "chooseProvence");
        aq.id(R.id.new_address_form_select_area).clicked(this, "chooseArea");
        aq.id(R.id.new_address_form_select_city).clicked(this, "chooseCity");
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
            MyToast.showText(this, R.string.please_choose_provence_first, R.drawable.a2);
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
            MyToast.showText(this, R.string.please_choose_city_first, R.drawable.a2);
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
            params.put("key", LoginUtil.getKey(AddressFormActivity.this));
            params.put("true_name", true_name);
            params.put("mob_phone", mobile_phone);
            params.put("province_id", provenceId);
            params.put("city_id", cityId);
            params.put("area_id", areaId);
            params.put("address", address);
            params.put("area_info", provenceName + " " + cityName + " " + areaName);

            aq.ajax(CommonDataObject.DO_ADD_ADDRESS, params, JSONObject.class, new AjaxCallback<JSONObject>() {
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
                            intent.putExtra("address_id", object.getJSONObject("datas").getString("address_id"));

                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
//                            Toast.makeText(AddressFormActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
                            MyToast.showText(AddressFormActivity.this, object.getJSONObject("datas").getString("error"), R.drawable.a2);
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
