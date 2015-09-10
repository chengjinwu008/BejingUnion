package com.cjq.bejingunion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/19.
 */
public class WarningAlertDialog extends Dialog{
    private AQuery aq;
    private Runnable okClicked;
    private Runnable cancelClicked;

    public WarningAlertDialog(Context context) {
        super(context,R.style.WarningDialog);
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning);
        aq = new AQuery(findViewById(android.R.id.content));
        aq.id(R.id.ok).clicked(this,"okClick");
        aq.id(R.id.cancel).clicked(this, "cancelClick");
        aq.id(R.id.main).clicked(this, "clickNone");
        aq.id(R.id.background).clicked(this, "dismiss");
    }

    public void clickNone(){

    }

    public void okClick(){
        if(okClicked!=null)
        okClicked.run();
        dismiss();
    }

    public void cancelClick(){
        if(cancelClicked!=null)
            cancelClicked.run();
        dismiss();
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

    public WarningAlertDialog onOKClick(Runnable runnable){
        okClicked=runnable;
        return this;
    }

    public WarningAlertDialog onCancelClick(Runnable runnable){
        cancelClicked=runnable;
        return this;
    }
}
