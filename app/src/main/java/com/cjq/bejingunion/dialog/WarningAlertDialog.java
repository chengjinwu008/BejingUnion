package com.cjq.bejingunion.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/19.
 */
public class WarningAlertDialog extends AlertDialog{
    protected WarningAlertDialog(Context context) {
        super(context);
    }

    protected WarningAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    protected WarningAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning);
    }
}
