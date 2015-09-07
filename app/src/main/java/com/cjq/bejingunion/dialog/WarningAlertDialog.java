package com.cjq.bejingunion.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/19.
 */
public class WarningAlertDialog extends AlertDialog{
    private AQuery aq;

    public WarningAlertDialog(Context context) {
        super(context);
    }

    public WarningAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public WarningAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning);
        aq = new AQuery(findViewById(android.R.id.content));
    }

    public WarningAlertDialog changeText(String string){
        aq.id(R.id.alert_text).text(string);
        return this;
    }

    public WarningAlertDialog showCancel(Boolean show){
        if(show)
            aq.id(R.id.cancel).visible();
        else
            aq.id(R.id.cancel).gone();
        return this;
    }
}
