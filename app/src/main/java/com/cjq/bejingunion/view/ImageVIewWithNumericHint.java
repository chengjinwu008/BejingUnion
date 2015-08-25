package com.cjq.bejingunion.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Created by CJQ on 2015/8/25.
 */
public class ImageVIewWithNumericHint extends ImageView {

    private int number = 1;
    private Paint paint;

    public ImageVIewWithNumericHint(Context context) {
        super(context);
        init();
    }

    public ImageVIewWithNumericHint(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageVIewWithNumericHint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (number > 0) {
            String showNumber = String.valueOf(number);
            if(number>10){
                showNumber = String.valueOf(9);
            }
            int width = getMeasuredWidth() - 10;
            int h = 10;
            paint.setColor(Color.RED);
            canvas.drawCircle(width, h, 10, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(String.valueOf(number),width-2,h+4,paint);
        }
    }

    public void setNumber(int number) {
        this.number = number;
        invalidate();
    }
}
