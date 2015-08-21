package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by CJQ on 2015/8/21.
 */
public class MyTitleLinearLayout extends LinearLayout{
    private final View topContent;
    private final View topTitle;
    private final View content;

    public MyTitleLinearLayout(Context context) {
        this(context, null);
    }

    public MyTitleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        topContent = getChildAt(0);
        topTitle = getChildAt(1);
        content = getChildAt(2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isTopContentHidden();
        return super.onInterceptTouchEvent(ev);
    }

    public boolean isTopContentHidden(){
        int tx =  topContent.getBottom();
        System.out.println(tx);
        return true;
    }
}
