package com.cjq.bejingunion.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cjq.bejingunion.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CJQ on 2015/8/11.
 */
public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private final ViewPager mPager;
    private final LinearLayout points;
    private final LayoutParams layoutParams;
    private final ExecutorService scheduledExecutorService;
    private PagerAdapter mAdapter;
    private int mNowPage = 0;
    private Handler mHandler = null;
    private int normalId = R.drawable.a6;
    private int selectedId = R.drawable.a7;
    public final int SECONDS_LEFT = 30;
    private int secondsLeft = SECONDS_LEFT;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPager = new ViewPager(context, attrs);
        points = new LinearLayout(context, attrs);
        mHandler = new Handler();

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
//        layoutParams.bottomMargin = 30;
//
//        points.setLayoutParams(layoutParams);
//        points.setBackgroundColor(Color.BLACK);.

        scheduledExecutorService = Executors.newSingleThreadExecutor();

        points.setGravity(Gravity.CENTER);
        addView(mPager);
        addView(points);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        points.setLayoutParams(layoutParams);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        ((ImageView)points.getChildAt(5)).setImageResource(R.drawable.a7);
        super.onLayout(changed, l, t, r, b);
    }

    public void setAdapter(PagerAdapter adapter) {
        mPager.setAdapter(adapter);
        this.mAdapter = adapter;
        mPager.addOnPageChangeListener(this);
        notifyPageChanged();
    }

    private void startService() {
        secondsLeft = SECONDS_LEFT;
        scheduledExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                while (secondsLeft != 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    secondsLeft--;
                }
                int count = mAdapter.getCount();
                int nn = 0;
                if (mNowPage < count - 1) {
                    nn = mNowPage + 1;
                } else {
                    nn = 0;
                }

                final int finalNn = nn;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mPager.setCurrentItem(finalNn);
                    }
                });
                startService();
            }
        });
    }

    public void notifyDataSetChanged() {
        scheduledExecutorService.shutdown();
        this.mAdapter.notifyDataSetChanged();
        notifyPageChanged();
    }

    private void notifyPageChanged() {
        points.removeAllViews();

        mNowPage = 0;

        for (int i = 0; i != mAdapter.getCount(); i++) {
            ImageView tempView = new ImageView(getContext());

            tempView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mNowPage != i)
                tempView.setImageResource(normalId);
            else
                tempView.setImageResource(selectedId);
            points.addView(tempView);
        }

        mPager.setCurrentItem(0, true);
        startService();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int nn = mNowPage;
        if (mNowPage != position) {
            mNowPage = position;
        }

        ((ImageView) points.getChildAt(nn)).setImageResource(normalId);
        ((ImageView) points.getChildAt(mNowPage)).setImageResource(selectedId);
        secondsLeft = SECONDS_LEFT;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public void start() {
        startService();
    }
}
