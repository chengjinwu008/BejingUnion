package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by CJQ on 2015/8/21.
 */
public class MyTitleLinearLayout extends LinearLayout {
    private View topContent;
    private View topTitle;
    private View content;
    private int mDownX;
    private int mDownY;
    private int bottom;
    private int top;
    private int top2;
    private int top3;

    public MyTitleLinearLayout(Context context) {
        this(context, null);
    }

    public MyTitleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            topContent = getChildAt(0);
            topTitle = getChildAt(1);
            content = getChildAt(2);
            bottom = getBottom();

            top = (int) topContent.getY();
            top2 = (int) topTitle.getY();
            top3 = (int)content.getY();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (topTitle.getY() + y - mDownY <= 0) {
                    scrollTo(0, -top2);
                } else if (topContent.getY() + y - mDownY > top) {
                    scrollTo(0, 0);
                } else {
                    scrollBy(0, y - mDownY);
                }
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_UP:
                mDownX = 0;
                mDownY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollChild(topContent, x, y);
        scrollChild(topTitle, x, y);
        stretchChild(content, x, y);
    }

    private void scrollChild(View child, int x, int y) {
        int tx = (int) child.getX();
        int ty = (int) child.getY();
        child.setX(tx + x);
        child.setY(ty + y);
    }

    private void stretchChild(View child, int x, int y) {
        int tx = (int) child.getX();
        int ty = (int) child.getY();

        child.setX(tx + x);
        child.setY(ty + y);

        int height = bottom-(ty + y);

        LayoutParams params = (LayoutParams) child.getLayoutParams();
        params.height = height;
        child.setLayoutParams(params);
    }

    @Override
    public void scrollTo(int x, int y) {
        topContent.setX(x);
        topContent.setY(y);

        topTitle.setX(x);
        topTitle.setY(y + top2);

//        content.layout(x, y + top + topTitle.getHeight(), content.getWidth(), bottom);

        content.setX(x);
        content.setY(y+top3);

        int height = bottom-(y+top3);

        LayoutParams params = (LayoutParams) content.getLayoutParams();
        params.height = height;
        content.setLayoutParams(params);
    }
}
