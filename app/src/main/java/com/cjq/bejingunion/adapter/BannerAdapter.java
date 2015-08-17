package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.entities.Ad;

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
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view  = ads.get(position).getImageView();
        new AQuery(view).image(ads.get(position).getUrl(),false,true);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
