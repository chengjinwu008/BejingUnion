package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by CJQ on 2015/8/18.
 */
public class SwipeListView extends ListView {

    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    private boolean isJudged = false;
    private boolean isHorizontal = false;
    private SwipeListItemView mCurrentViewItem = null;
    private int mPosition;
    private int mCurrentX;
    private boolean drewOut = false;
    private SwipeListItemView lastItem;

    public SwipeListView(Context context) {
        super(context);
        init(context);
    }

    public SwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                isHorizontal = false;
                isJudged = false;

                lastItem = mCurrentViewItem;

                mPosition = pointToPosition((int) mDownX, (int) mDownY);
                mCurrentViewItem = (SwipeListItemView) getChildAt(mPosition - getFirstVisiblePosition());

                if(lastItem!=null && drewOut && lastItem!=mCurrentViewItem){
                    lastItem.mSmoothScrollTo(0);
                    drewOut=false;
                }
                if(mCurrentViewItem!=null)
                mCurrentX = mCurrentViewItem.getCurrentX();

                break;
            case MotionEvent.ACTION_MOVE:
                if (!isJudged) {
                    int deltaX = (int) Math.abs(mDownX - x);
                    int deltaY = (int) Math.abs(mDownY - y);

                    if (deltaX >= mTouchSlop || deltaY >= mTouchSlop) {
                        if (deltaX > deltaY && mCurrentViewItem != null) {
                            isHorizontal = true;
                        } else {
                            isHorizontal = false;
                            if(lastItem!=null && drewOut ){
                                lastItem.mSmoothScrollTo(0);
                                drewOut=false;
                            }
                        }
                        isJudged = true;
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isHorizontal) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (!mCurrentViewItem.isScrolling()) {
                        int x = (int) ev.getX();
                        int dx = (int) (x - mDownX + mCurrentX);
                        if (dx < 0 && dx > mCurrentViewItem.getRightEdge())
                            mCurrentViewItem.mScrollTo((int) (x - mDownX + mCurrentX));
                        else if (dx <= mCurrentViewItem.getRightEdge()) {
                            mCurrentViewItem.mScrollTo(mCurrentViewItem.getRightEdge());
                        } else {
                            mCurrentViewItem.mScrollTo(0);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!mCurrentViewItem.isScrolling()) {
                        if (mCurrentViewItem.getCurrentX() > mCurrentViewItem.getRightEdge() / 2) {
                            mCurrentViewItem.mSmoothScrollTo(0);
                            drewOut=false;
                        } else {
                            mCurrentViewItem.mSmoothScrollTo(mCurrentViewItem.getRightEdge());
                            drewOut=true;
                        }
                    }
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }
}
