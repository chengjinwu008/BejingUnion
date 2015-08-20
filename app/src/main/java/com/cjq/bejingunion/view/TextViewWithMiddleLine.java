package com.cjq.bejingunion.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by CJQ on 2015/8/11.
 */
public class TextViewWithMiddleLine extends TextView {
    public TextViewWithMiddleLine(Context context) {
        this(context, null);
    }

    public TextViewWithMiddleLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWithMiddleLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
