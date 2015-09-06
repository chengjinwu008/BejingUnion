package com.cjq.bejingunion.utils;

import android.content.Context;
import android.content.Intent;

import com.cjq.bejingunion.activities.PayActivity;

/**
 * Created by CJQ on 2015/9/6.
 */
public class PayUtil {
    public static void pay(Context context,String name,String body,String price,String orderNumber){
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("body",body);
        intent.putExtra("price",price);
        intent.putExtra("orderNumber",orderNumber);

        context.startActivity(intent);
    }
}
