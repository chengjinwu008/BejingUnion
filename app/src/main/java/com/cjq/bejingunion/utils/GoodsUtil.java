package com.cjq.bejingunion.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cjq.bejingunion.activities.BroadBandActivity;
import com.cjq.bejingunion.activities.BroadBandDetailActivity;
import com.cjq.bejingunion.activities.DetailActivity;
import com.cjq.bejingunion.activities.EvaluateActivity;

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

                break;
            case CONTRACT_MACHINE:

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
}
