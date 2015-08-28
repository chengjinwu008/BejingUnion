package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by CJQ on 2015/8/26.
 */
public class AllGridView extends GridView {
    public AllGridView(Context context) {
        super(context);
    }

    public AllGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
