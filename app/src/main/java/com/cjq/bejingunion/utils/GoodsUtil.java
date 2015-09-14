package com.cjq.bejingunion.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cjq.bejingunion.activities.BroadBandActivity;
import com.cjq.bejingunion.activities.BroadBandDetailActivity;
import com.cjq.bejingunion.activities.CardDetailActivity;
import com.cjq.bejingunion.activities.ContractMachineDetailActivity;
import com.cjq.bejingunion.activities.DetailActivity;
import com.cjq.bejingunion.activities.EvaluateActivity;
import com.cjq.bejingunion.activities.IdentifyActivity;

/**
 * Created by CJQ on 2015/8/20.
 */
public class GoodsUtil {
    public enum TYPE{
        BROAD_BAND,CARD,CONTRACT_MACHINE,DEFAULT
    }

    public static void showGoodsDetail(Context context, String goods_id) {
        showGoodsDetail(context,goods_id,TYPE.DEFAULT);
    }

    public static void showGoodsDetail(Context context, String goods_id, TYPE type) {
        switch (type){
            case BROAD_BAND:
                Intent intent = new Intent(context, BroadBandDetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                context.startActivity(intent);
                break;
            case CARD:
                Intent intent1 = new Intent(context, CardDetailActivity.class);
                intent1.putExtra("goods_id", goods_id);
                context.startActivity(intent1);
                break;
            case CONTRACT_MACHINE:
                Intent intentd2 = new Intent(context, ContractMachineDetailActivity.class);
                intentd2.putExtra("goods_id", goods_id);
                context.startActivity(intentd2);
                break;
            default:
                Intent intentd = new Intent(context, DetailActivity.class);
                intentd.putExtra("goods_id", goods_id);
                context.startActivity(intentd);
                break;
        }
    }

    public static void showEvaluations(Activity context,String goods_id){
        Intent intent = new Intent(context,EvaluateActivity.class);
        intent.putExtra("goods_id",goods_id);
        context.startActivity(intent);
    }

    public static void showIdentify(Activity context,String setMeal,String price,String phoneNumber,int requestCode){
        Intent intent = new Intent(context,IdentifyActivity.class);

        intent.putExtra("setMeal",setMeal);
        intent.putExtra("price",price);
        intent.putExtra("phoneNumber",phoneNumber);

        context.startActivityForResult(intent, requestCode);
    }
}
