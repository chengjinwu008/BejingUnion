package com.cjq.bejingunion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/13.
 */
public class NumericView extends FrameLayout {
    private int number = 0;
    private TextView num;

    public interface OnNumberChangeListener {
        public void change(int number);
    }

    private OnNumberChangeListener listener;

    public OnNumberChangeListener getListener() {
        return listener;
    }

    public void setListener(OnNumberChangeListener listener) {
        this.listener = listener;
    }

    public int getNumber() {
        return number;
    }

    public NumericView(Context context) {
        this(context, null);
    }

    public NumericView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumericView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.num_util, null, false);

        AQuery aq = new AQuery(view);

        aq.id(R.id.num_util_minus).clicked(this, "minus");
        aq.id(R.id.num_util_plus).clicked(this, "plus");

        num = (TextView) view.findViewById(R.id.num_util_num);

        addView(view);
    }

    public void minus() {
        if (number > 0) {
            number--;
            num.setText(String.valueOf(number));
            if (listener != null)
                listener.change(number);
        }
    }

    public void plus() {
        number++;
        num.setText(String.valueOf(number));
        if (listener != null)
            listener.change(number);
    }

    public void setNumber(int number) {
        if (number > 0) {
            this.number = number;
            num.setText(String.valueOf(number));
        }
    }
}
