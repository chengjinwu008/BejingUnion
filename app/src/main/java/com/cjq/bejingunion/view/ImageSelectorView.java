package com.cjq.bejingunion.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.MyToast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/8/17.
 */
public class ImageSelectorView extends LinearLayout{
    private final LayoutParams params;
    private int ww;
    private int hh;
    private final int margin=2;
    private Context context;
    private ImageView addImageButton;
    private List<String> images = new ArrayList<>();
    private int max=1;

    public int getMax() {
        return max;
    }

    /**
     * 设置图片选择器能选择的图片的最大数量
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    private OnImageChangeListener imageChangeListener;

    public OnImageChangeListener getImageChangeListener() {
        return imageChangeListener;
    }

    public void setImageChangeListener(OnImageChangeListener imageChangeListener) {
        this.imageChangeListener = imageChangeListener;
    }

    public interface OnImageChangeListener {
        void add(String path);

        void delete(String path);
    }

    private int w = 80, h = 80;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
        this.ww = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getResources().getDisplayMetrics());
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
        this.hh = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, getResources().getDisplayMetrics());
    }

    private OnClickListener listener;

    public ImageSelectorView(Context context) {
        this(context, null);
    }

    public ImageSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        addImageButton = new ImageView(context);
        ww = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getResources().getDisplayMetrics());
        hh = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, getResources().getDisplayMetrics());
        params = new LayoutParams(ww, hh);
        addImageButton.setLayoutParams(params);
        addImageButton.setImageResource(R.drawable.a28);
        addImageButton.setBackgroundColor(Color.WHITE);
        addImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(addImageButton);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int cellWidthSpec = MeasureSpec.makeMeasureSpec(ww, MeasureSpec.AT_MOST);
        int cellHeightSpec = MeasureSpec.makeMeasureSpec(hh, MeasureSpec.AT_MOST);

        int cx = margin;
        int row = margin;

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(cellWidthSpec, cellHeightSpec);
            int cw = getChildAt(i).getMeasuredWidth();
            int ch = getChildAt(i).getMeasuredHeight();

            if(row==margin){
                row+=hh;
            }

            cx+=ww+margin;
            if(cx>mWidth){
                row+=hh+margin;
                cx=ww+margin;
            }
        }

        setMeasuredDimension(mWidth, row + margin);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int row = 0;// which row lay you view relative to parent
        int lengthX = 0;    // right position of child relative to parent
        int lengthY = 0;    // bottom position of child relative to parent
        for (int i = 0; i < getChildCount(); i++) {
            final View child = this.getChildAt(i);
            int width = /*child.getMeasuredWidth()*/ww;
            int height = /*child.getMeasuredHeight()*/hh;
            lengthX += width + 2;
            lengthY = row * (height + 2) + 2 + height + top;
            //if it can't drawing on a same line , skip to next line
            if (lengthX > right-left) {
                lengthX = width + 2;
                row++;
                lengthY = row * (height + 2) + 2 + height + top;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
    }

    public void setOnAddButtonClickListener(OnClickListener listener){
        this.listener = listener;
        addImageButton.setOnClickListener(listener);
    }

    public void addImage(final String path){
        if(images.contains(path)){
//            Toast.makeText(getContext(),"不要添加重复的图片，谢谢！",Toast.LENGTH_SHORT).show();
            MyToast.showText(context, "不要添加重复的图片，谢谢！", R.drawable.a2);
        }else{
            images.add(path);

            if(images.size()<max){

                if(imageChangeListener!=null){
                    imageChangeListener.add(path);
                }

                new AQuery(addImageButton).image(new File(path), ww);

                addImageButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(imageChangeListener!=null){
                            imageChangeListener.delete(path);
                        }
                        images.remove(path);
                        removeView(v);
                    }
                });
                addImageButton = new ImageView(context);
                addImageButton.setLayoutParams(params);
                addImageButton.setImageResource(R.drawable.a28);
                addImageButton.setBackgroundColor(Color.WHITE);
                addImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                addImageButton.setOnClickListener(this.listener);
                addView(addImageButton);
            }else{
                if(imageChangeListener!=null){
                    imageChangeListener.add(path);
                }

                new AQuery(addImageButton).image(new File(path), ww);

                addImageButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(imageChangeListener!=null){
                            imageChangeListener.delete(path);
                        }
                        images.remove(path);
                        removeView(v);

                        addImageButton = new ImageView(context);
                        addImageButton.setLayoutParams(params);
                        addImageButton.setImageResource(R.drawable.a28);
                        addImageButton.setBackgroundColor(Color.WHITE);
                        addImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                        addImageButton.setOnClickListener(ImageSelectorView.this.listener);
                        addView(addImageButton);
                    }
                });
            }
        }
    }
}
