package com.cjq.bejingunion.utils;

import android.content.Context;
import android.content.Intent;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.PayActivity;
import com.cjq.bejingunion.dialog.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/6.
 */
public class PayUtil {
    public static void pay(Context context,String name,String body,String price,String orderNumber,String type){
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("body",body);
        intent.putExtra("price",price);
        intent.putExtra("orderNumber",orderNumber);
        intent.putExtra("type",type);

        context.startActivity(intent);
    }

    public static void payForPoints(Context context,String name,String body,String price,String orderNumber,String type){
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("body",body);
        intent.putExtra("price",price);
        intent.putExtra("orderNumber",orderNumber);
        intent.putExtra("type",type);
        intent.putExtra("points",true);

        context.startActivity(intent);
    }

    public static void cancelOrder(final Context context,String orderId, final Runnable afterCancel) throws Exception {
        AQuery aq = new AQuery(context);
        Map<String,String> params = new HashMap<>();
        params.put("order_id",orderId);
        params.put("key",LoginUtil.getKey(context));

        aq.ajax(CommonDataObject.ORDER_CANCEL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    if(200==object.getInt("code")){
                        afterCancel.run();
                        MyToast.showText(context,object.getJSONObject("datas").getString("msg"), R.drawable.gou);
                    }else {
                        MyToast.showText(context,object.getJSONObject("datas").getString("error"), R.drawable.a2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }
}
