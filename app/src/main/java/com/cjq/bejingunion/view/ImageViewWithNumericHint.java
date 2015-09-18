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
public class ImageViewWithNumericHint extends ImageView {

    private int number = 0;
    private Paint paint;

    public ImageViewWithNumericHint(Context context) {
        super(context);
        init();
    }

    public ImageViewWithNumericHint(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewWithNumericHint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (number > 0) {
            String showNumber = String.valueOf(number);
            if(number>100){
                showNumber = String.valueOf(99)+"+";
            }
            int i=12;
            int width = getMeasuredWidth() - i;
            int h = i;

            paint.setColor(Color.RED);
            canvas.drawCircle(width, h, i, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(showNumber,width-showNumber.length()*3,h+4,paint);
        }
    }

    public void setNumber(Integer number) {
        this.number = number;
        invalidate();
    }
}
