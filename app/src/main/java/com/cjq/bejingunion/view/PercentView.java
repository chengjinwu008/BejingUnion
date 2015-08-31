package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjq.bejingunion.R;

import java.text.NumberFormat;

/**
 * Created by CJQ on 2015/8/12.
 */
public class PercentView extends RelativeLayout {
    private final CirclePercentView mCircle;
    private final TextView textView1;
    private final TextView textView2;
    private final NumberFormat percentFormat;
    private float percent = 53.2f;
    private String description = "满意";

    public PercentView(Context context) {
        this(context, null);
    }

    public PercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCircle = new CirclePercentView(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.percent_text, null, false);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        textView1 = (TextView) view.findViewById(R.id.percent_text1);
        textView2 = (TextView) view.findViewById(R.id.percent_text2);

        percentFormat = java.text.NumberFormat.getPercentInstance();
        percentFormat.setMaximumIntegerDigits(3);//最大整数位数
        percentFormat.setMinimumFractionDigits(0); //最小小数位数
        percentFormat.setMaximumFractionDigits(2); //最大小数位数
        percentFormat.setMinimumIntegerDigits(1);//最小整数位数

        addView(mCircle);
        addView(view);

        mCircle.setProgress(percent);
        textView1.setText(description);
        textView2.setText(percentFormat.format(percent/100));
    }

    public void setPercent(Float percent) {
        this.percent = percent;
        textView2.setText(percentFormat.format(percent/100));
        mCircle.setProgress(percent);
        mCircle.invalidate();
    }

    public void setDescription(String description) {
        this.description = description;
        textView1.setText(description);
    }
}
