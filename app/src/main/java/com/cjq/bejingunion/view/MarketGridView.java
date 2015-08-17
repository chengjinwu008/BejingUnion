package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridView;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/11.
 */
public class MarketGridView extends GridView {

    private final int mTouchSlop;
    private final View mBottomView;
    private onLoadListener listener;
    private float mDownY;
    private float mLastY;
    private boolean isLoading = false;

    public void setListener(onLoadListener listener) {
        this.listener = listener;
    }

    public interface onLoadListener {
        void onLoad();
    }

    public MarketGridView(Context context) {
        this(context, null);
    }

    public MarketGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarketGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mBottomView = LayoutInflater.from(context).inflate(R.layout.list_bottom,null,false);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (this.getAdapter() != null) {
            if (canLoad()) {
                doLoad();
            }
        }
    }

    public boolean isPullUp() {
        return (mLastY - mDownY) > mTouchSlop;
    }

    private boolean isBottom() {
        return getLastVisiblePosition() == getAdapter().getCount() - 1;
    }

    private boolean canLoad() {
        return !isLoading && isPullUp() && isBottom();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                mLastY = mDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = ev.getRawY();
                break;

            case MotionEvent.ACTION_UP:

                if (canLoad()) {

                    doLoad();
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void doLoad() {
        if(listener!=null)
        listener.onLoad();
        setLoading(true);
    }

    public void setLoading(boolean loading) {
        isLoading=loading;
        if(loading)
        addView(mBottomView);
        else{
            removeView(mBottomView);
            mLastY=0;
            mDownY=0;
        }
    }
}
