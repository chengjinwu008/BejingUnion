package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by CJQ on 2015/8/24.
 */
public class MyScrollView extends ScrollView{

    private MyScrollListener listener;

    public void setListener(MyScrollListener listener) {
        this.listener = listener;
    }

    public interface MyScrollListener{
        void onVerticalScroll(int t,int oldt);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(listener!=null){
            listener.onVerticalScroll(t,oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
