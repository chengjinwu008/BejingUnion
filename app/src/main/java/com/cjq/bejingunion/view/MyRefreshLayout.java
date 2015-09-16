package com.cjq.bejingunion.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/10.
 */
public class MyRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private float mXDown;
    private float mLastX;
    private boolean ss;
    private boolean f=false;

    public void setOnLoadListener(onLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    public interface onLoadListener {
        void onLoad();
    }

    private AbsListView mListView;
    private float mYDown;
    private float mLastY;
    private int mTouchSlop;
    private View mFooterView;
    private boolean isLoading = false;
    private onLoadListener mOnLoadListener;

    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mFooterView = LayoutInflater.from(context).inflate(R.layout.list_bottom, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mListView == null) {
            getListView();
        }

    }

    private void getListView() {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof AbsListView) {
                mListView = (AbsListView) getChildAt(i);
                mListView.setOnScrollListener(this);
                break;
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown =ev.getRawX();
                mLastX =ev.getRawX();
                mYDown = ev.getRawY();
                mLastY = mYDown;
                f=false;
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = ev.getRawY();
                mLastX = ev.getRawX();
//                if(!f){
//                    ss = Math.abs(mLastX - mXDown) <= Math.abs(mLastY - mYDown);
//                    f=true;
//                }


                break;
            case MotionEvent.ACTION_UP:
//                if (canLoad()) {
//                    loadData();
//                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void loadData() {
        if (mOnLoadListener != null && !isRefreshing()) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    private boolean canLoad() {
        return isBottom() && (!isLoading) && isPullUp() && !isRefreshing();
    }

    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    private boolean isPullUp() {
        return -(mLastY - mYDown) >= mTouchSlop;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return /*ss && */super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (canLoad()) {
            loadData();
        }
    }

    public void setLoading(Boolean loading) {
        this.isLoading = loading;
        if (isLoading) {
//            mListView.addFooterView(mFooterView);
        } else {
//            mListView.removeFooterView(mFooterView);
            mYDown = 0;
            mLastY = 0;
        }
    }
}
