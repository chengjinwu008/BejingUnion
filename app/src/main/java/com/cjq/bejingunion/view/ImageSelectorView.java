package com.cjq.bejingunion.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/17.
 */
public class ImageSelectorView extends ImageView implements View.OnClickListener {
    private final Context context;

    public ImageSelectorView(Context context) {
        this(context, null);
    }

    public ImageSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setImageResource(R.drawable.a28);
        setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AlertDialog dialog =  new AlertDialog.Builder(context).setAdapter(new ArrayAdapter<String>(context, R.layout.text_view_layout, R.id.text, new String[]{"拍照","从相册选取"}), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
    }
}
