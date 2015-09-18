package com.cjq.bejingunion.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.entities.Leading;
import com.cjq.bejingunion.utils.GoodsUtil;

import java.util.List;

/**
 * Created by CJQ on 2015/8/10.
 */
public class LeadingAdapter extends PagerAdapter {

    Context context;
    List<Leading> leadings;

    public LeadingAdapter(Context context, List<Leading> leadings) {
        this.context = context;
        this.leadings = leadings;
    }

    @Override
    public int getCount() {
        return leadings.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view  = leadings.get(position).getView();
        Leading leading = leadings.get(position);

        AQuery aq = new AQuery(view);
        if(leading.isConfirm()){
            aq.id(R.id.btn).visible();
            aq.id(R.id.btn).clicked(this,"closeUp");
        }else{
            aq.id(R.id.btn).gone();
        }

        aq.id(R.id.background).background(leading.getId());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void closeUp(){
        ((Activity)context).finish();
    }
}
