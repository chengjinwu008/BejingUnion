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
import com.cjq.bejingunion.adapter.OrderListStoreAdapter;
import com.cjq.bejingunion.entities.Address4Show;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/11.
 */
public class ContractMachineConfirmActivity extends BaseActivity {

    private String cart_id;
    private String ifcart;
    private AQuery aq;
    private String freight_hash;
    private String vat_hash;
    private int count;
    private double price;
    private String addressId;
    private String offpay_hash;
    private String offpay_hash_batch;
    private String invoice_id="0";
    private String phone_id;
    private String phone_additional_id;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_machine_order_confirm);

        Intent intent = getIntent();
        cart_id = intent.getStringExtra("cart_id");
        ifcart = intent.getStringExtra("ifcart");
        phone_id = intent.getStringExtra("phone_id");
        phone_additional_id = intent.getStringExtra("phone_additional_id");
        phoneNumber = intent.getStringExtra("phoneNumber");

        aq =new AQuery(this);
        aq.id(R.id.contract_machine_order_confirm_back).clicked(this, "finish");
        aq.id(R.id.contract_machine_confirm_choose_address).clicked(this,"chooseAddress");
        aq.id(R.id.contract_machine_order_confirm_submit).clicked(this,"submit");
        aq.id(R.id.contract_machine_confirm_identify_phone_number).text(phoneNumber);

        requestIdentifyInfo(phone_additional_id);

        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("cart_id", cart_id);

            aq.ajax(CommonDataObject.CONFIRM_ORDER_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
//                        System.out.println(object.toString());
                        if (200 == object.getInt("code")) {
                            JSONObject data = object.getJSONObject("datas");
                            freight_hash = data.getString("freight_hash");
                            if (data.has("address_info")) {
                                JSONObject address_info = data.getJSONObject("address_info");
                                Address4Show address4Show = new Address4Show(null, null, null, address_info.getString("address_id"));
                                address4Show.setCityId(address_info.getString("city_id"));
                                address4Show.setAreaId(address_info.getString("area_id"));
                                requestHashCode(address4Show);
                                String aa = address_info.getString("area_info") + " " + address_info.getString("address") + "\n" + address_info.getString("true_name") + " tel:" + address_info.getString("mob_phone");
                                aq.id(R.id.contract_machine_confirm_choose_address).text(aa);
                            }
                            vat_hash = data.getString("vat_hash");
                            JSONArray ga = data.getJSONArray("store_cart_list");
                            List<Goods4OrderList> store4ShowList = new ArrayList<Goods4OrderList>();
                            for (int i = 0; i < ga.length(); i++) {
                                JSONObject store = ga.getJSONObject(i);
                                Goods4OrderList store4Show = new Goods4OrderList(store.getString("store_id"), store.getString("store_name"), null, 0, "");
                                store4Show.setIsStore(true);
                                store4ShowList.add(store4Show);
                                JSONArray gaa = store.getJSONArray("goods_list");
                                for (int j = 0; j < gaa.length(); j++) {
                                    JSONObject o = gaa.getJSONObject(j);
                                    Goods4OrderList goods4OrderList = new Goods4OrderList(o.getString("goods_image_url"), o.getString("goods_name"), null, o.getInt("goods_num"), o.getString("goods_price"));
                                    store4ShowList.add(goods4OrderList);
                                    count+=goods4OrderList.getCount();
                                    price+=goods4OrderList.getCount()*Double.valueOf(goods4OrderList.getPrice4One());
                                }
                            }

                            NumberFormat format = NumberFormat.getInstance();
                            format.setMinimumFractionDigits(2);
                            format.setMaximumFractionDigits(2);

                            aq.id(R.id.contract_machine_order_confirm_count).text(String.valueOf(count));
                            aq.id(R.id.contract_machine_order_confirm_price).text(format.format(price));

                            Goods4OrderList goods =  store4ShowList.get(1);
                            aq.id(R.id.contract_machine_order_confirm_image).image(goods.getPortrait(),false,true);
                            aq.id(R.id.contract_machine_order_confirm_name).text(goods.getName());
                            aq.id(R.id.contract_machine_order_confirm_one_price).text("￥"+goods.getPrice4One());
                        }else{
                            Toast.makeText(getApplicationContext(),object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
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
            finish();
        }
    }

    public void submit(){
        try{
            Map<String,String> params = new HashMap<>();
            params.put("key",LoginUtil.getKey(this));
            params.put("cart_id",cart_id);
            params.put("ifcart",ifcart);
            params.put("address_id",addressId);
            params.put("pay_name","online");
            params.put("pd_pay",aq.id(R.id.contract_machine_order_confirm_use_points).isChecked()?"1":"0");
            params.put("vat_hash",vat_hash);
            params.put("offpay_hash",offpay_hash);
            params.put("offpay_hash_batch",offpay_hash_batch);
            params.put("invoice_id",invoice_id);
            params.put("phone_additional_id",phone_additional_id);
            params.put("phone_id",phone_id);

            aq.ajax(CommonDataObject.SUBMIT_ORDER_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(object.getInt("code")==200){
                            JSONObject data = object.getJSONObject("datas");
                            PayUtil.pay(ContractMachineConfirmActivity.this, data.getString("goods_name"), data.getString("goods_description"), data.getString("api_pay_amount"), data.getString("pay_sn"), data.getString("order_type"));
                            finish();
                        }
                        else{
                            Toast.makeText(ContractMachineConfirmActivity.this, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.callback(url, object, status);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void chooseAddress() {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra("choose", true);
        startActivityForResult(intent, 0);
    }

    private void requestHashCode(Address4Show address4Show) {
        try {
            addressId = address4Show.getAddressId();
            Map<String,String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("area_id",address4Show.getAreaId());
            params.put("city_id",address4Show.getCityId());
            params.put("address_id",address4Show.getAddressId());
            params.put("freight_hash",freight_hash);

            aq.ajax(CommonDataObject.ADDRESS_HASH_CODE_URL,params,JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(object.getInt("code")==200){
                            JSONObject data =  object.getJSONObject("datas");
                            offpay_hash= data.getString("offpay_hash");
                            offpay_hash_batch = data.getString("offpay_hash_batch");
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
                    Address4Show address4Show = data.getParcelableExtra("address");
                    String aa = address4Show.getAddress() + "\n" + address4Show.getTrueName() + " tel:" + address4Show.getPhoneNumber();
                    aq.id(R.id.contract_machine_confirm_choose_address).text(aa);
                    requestHashCode(address4Show);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestIdentifyInfo(String id){
        Map<String,String> params = new HashMap<>();
        params.put("phone_additional_id",id);

        aq.ajax(CommonDataObject.IDENTIFY_INFO,params,JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    if(200==object.getInt("code")){
                        aq.id(R.id.contract_machine_confirm_identify_name).text(object.getJSONObject("datas").getString("user_name"));
                        aq.id(R.id.contract_machine_confirm_identify_number).text(object.getJSONObject("datas").getString("ID_card"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.callback(url, object, status);
            }
        });
    }
}
