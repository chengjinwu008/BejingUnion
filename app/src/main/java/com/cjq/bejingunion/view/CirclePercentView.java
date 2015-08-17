package com.cjq.bejingunion.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by CJQ on 2015/8/12.
 */
public class CirclePercentView extends View {
    private Paint mPaint;
    private RectF rectF;
    private float origin = 135f ;
    private float progress = 56.23f;

    public void setOrigin(float origin) {
        this.origin = origin;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public CirclePercentView(Context context) {
        this(context, null);
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#00BFBE"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        rectF = new RectF(20,20,30,30);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getMeasuredWidth()/2;
        float height = getMeasuredHeight()/2;

        float radius = Math.min(width, height)-20;

        if(progress >=100){
            progress = 100;
        }

        float xprogress = progress/100*360;

        float start = origin-xprogress/2;
        float left_progress = 360 - xprogress;

        float end_start = start+xprogress;

        rectF.right = radius*2+20;
        rectF.bottom = radius*2+20;

        canvas.drawArc(rectF, start, xprogress, false, mPaint);

        mPaint.setColor(Color.WHITE);

        canvas.drawArc(rectF, end_start, left_progress, false, mPaint);
    }
}
