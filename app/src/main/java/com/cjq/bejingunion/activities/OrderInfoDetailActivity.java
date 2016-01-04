package com.cjq.bejingunion.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.OrderItemAdapter;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.utils.JSONArray;
import com.cjq.bejingunion.utils.JSONObject;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayUtil;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by CJQ on 2015/9/24.
 */
public class OrderInfoDetailActivity extends BaseActivity{

    private String orderId;
    private AQuery aq;
    private JSONObject data;
    private List<Goods4OrderList> goods4OrderListList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_detail_layout);

        orderId = getIntent().getStringExtra("order_id");
        aq = new AQuery(this);

        aq.id(R.id.order_info_detail_back).clicked(this, "finish");
        goods4OrderListList = new ArrayList<>();
        adapter = new OrderItemAdapter(goods4OrderListList,this);
        aq.id(R.id.order_info_detail_goods_info).adapter(adapter);

        try {
            requestData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestData() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("key", LoginUtil.getKey(this));
        params.put("order_id",orderId);

        aq.ajax(CommonDataObject.ORDER_INFO_DETAIL, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                try {
                    if(object!=null){
                        JSONObject object1 = new JSONObject(object);
                        if (200 == object1.getInt("code")) {
                            data = object1.getJSONObject("datas");
                            putDataIntoView();
                        }
                    }
                    super.callback(url, object, status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void putDataIntoView() throws JSONException {
        aq.id(R.id.order_info_detail_number).text("订单编号："+data.getString("pay_sn"));
        JSONObject address = data.getJSONObject("extend_order_common");
        aq.id(R.id.order_info_detail_address_info).text("收货人：" + address.getString("reciver_name") + "\n" + "收货人电话：" + address.getJSONObject("reciver_info").getString("phone") + "\n" + "收货地址：" + address.getJSONObject("reciver_info").getString("address"));

        goods4OrderListList.clear();
        JSONArray a = data.getJSONArray("extend_order_goods");
        for(int i=0;i<a.length();i++){
            JSONObject o= a.getJSONObject(i);
            Goods4OrderList goods4OrderList = new Goods4OrderList(o.getString("goods_image_url"),o.getString("goods_name"),null,o.getInt("goods_num"),o.getString("goods_price")).setGc_id("gc_id").setGoods_id(o.getString("goods_id"));
            goods4OrderListList.add(goods4OrderList);
        }
        adapter.notifyDataSetChanged();
        if(!data.has("phone_about")){
            aq.id(R.id.order_info_detail_remark_parent).gone();
        }else{
            JSONObject remark = data.getJSONObject("phone_about");
            if(remark.getString("phone")!=null && !"null".equals(remark.getString("phone"))){
                aq.id(R.id.order_info_detail_remark_parent).visible();
                aq.id(R.id.order_info_detail_remark).text("卡号：" + remark.getString("phone") + "\n" + "购卡人姓名：" + remark.getJSONObject("user").getString("user_name") + "\n" + "购卡人身份证号：" + remark.getJSONObject("user").getString("ID_card"));
            }
            else
            {
                aq.id(R.id.order_info_detail_remark_parent).gone();
            }
        }

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        double all =  data.getDouble("order_amount");
        double shipping_fee =  data.getDouble("shipping_fee");
        double fee = all-shipping_fee;

        aq.id(R.id.order_info_detail_fee).text(numberFormat.format(fee));
        aq.id(R.id.order_info_detail_shipping_fee).text(numberFormat.format(shipping_fee));

        SpannableString spannableString = new SpannableString("实付款："+numberFormat.format(all));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);

        spannableString.setSpan(colorSpan,4,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        aq.id(R.id.order_info_detail_all_fee).text(spannableString);

        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        aq.id(R.id.order_info_detail_time).text("下单时间："+dateFormat.format(new Date(data.getLong("add_time")*1000)));

        if(data.getInt("order_state")==10){
            aq.id(R.id.order_info_detail_pay).visible().clicked(this,"goPay");
        }else {
            aq.id(R.id.order_info_detail_pay).gone().clicked(null);
        }
    }

    public void goPay() throws JSONException {
        PayUtil.pay(this,"订单号："+data.getString("pay_sn"),"订单号："+data.getString("pay_sn"),data.getString("order_amount"),data.getString("pay_sn"),"");
        finish();
    }
}
