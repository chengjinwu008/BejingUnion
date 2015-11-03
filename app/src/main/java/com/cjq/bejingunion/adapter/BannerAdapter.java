package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.utils.GoodsUtil;

import java.util.List;

/**
 * Created by CJQ on 2015/8/10.
 */
public class BannerAdapter extends PagerAdapter {

    Context context;
    List<Ad> ads;

    public BannerAdapter(Context context, List<Ad> ads) {
        this.context = context;
        this.ads = ads;
    }

    @Override
    public int getCount() {
        return ads.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView view  = ads.get(position).getImageView();
        new AQuery(view).image(ads.get(position).getUrl(),false,true);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ads.get(position).getId();
                if(!(id.equals("") || id.equals("0"))){
                    GoodsUtil.showGoodsDetail(context, id);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
