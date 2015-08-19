package com.cjq.bejingunion.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.FragemtPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/8/13.
 */
public class FragmentView extends FrameLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout bottomBar;
    private ViewPager content;
    private List<Pair<Pair<String,Integer>,Fragment>> data;
    private LayoutInflater inflater;
    private FragmentManager manager;
    private int no = 0;

    public FragmentView(Context context) {
        this(context, null);
    }

    public FragmentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if(context instanceof FragmentActivity)
            manager = ((FragmentActivity)context).getSupportFragmentManager();
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_view, null, false);
        bottomBar = (LinearLayout) view.findViewById(R.id.main_bottom_bar);
        content = (ViewPager) view.findViewById(R.id.main_content);
        content.setOffscreenPageLimit(3);
        addView(view);
    }

    public void setData(List<Pair<Pair<String, Integer>, Fragment>> data, FragmentManager fragmentManager) throws Exception {
        this.data = data;
        content.removeAllViews();
        bottomBar.removeAllViews();
        List<Fragment> fragments = new ArrayList<>();
        if(manager==null && fragmentManager!=null)
            manager = fragmentManager;
        else if(manager == null)
            throw new Exception(getContext().getString(R.string.system_hint_1));

        int size = data.size();
        float width = getResources().getDisplayMetrics().widthPixels/size;
        Log.e("width", String.valueOf(size));

        for(int i = 0;i<size;i++){
            Pair<Pair<String,Integer>,Fragment> e = data.get(i);
            Pair<String,Integer> icon = e.first;
            View bottomIcon = inflater.inflate(R.layout.fragment_bottom_bar_item, null, false);

            if(i==no)
                bottomIcon.setBackgroundColor(getResources().getColor(R.color.bottom_bar_chosen));
            else
                bottomIcon.setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));
            bottomIcon.setTag(i);
            bottomIcon.setOnClickListener(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
            bottomIcon.setLayoutParams(layoutParams);

            AQuery aq = new AQuery(bottomIcon);
            aq.id(R.id.fragment_bottom_icon).image(icon.second);
            aq.id(R.id.fragment_bottom_text).text(icon.first);

            bottomBar.addView(bottomIcon);

            fragments.add(e.second);
        }
        content.setAdapter(new FragemtPagerAdapter(manager,fragments));
        content.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = (int) v.getTag();

        content.setCurrentItem(i);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomBar.getChildAt(no).setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));

        no=position;

        bottomBar.getChildAt(no).setBackgroundColor(getResources().getColor(R.color.bottom_bar_chosen));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
