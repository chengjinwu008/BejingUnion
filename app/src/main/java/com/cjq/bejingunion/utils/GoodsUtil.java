package com.cjq.bejingunion.utils;

import android.content.Context;
import android.content.Intent;

import com.cjq.bejingunion.activities.DetailActivity;

/**
 * Created by CJQ on 2015/8/20.
 */
public class GoodsUtil {
    public static void showGoodsDetail(Context context, String goods_id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("goods_id", goods_id);
        context.startActivity(intent);
    }
}
