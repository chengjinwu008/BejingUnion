package com.cjq.bejingunion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/19.
 */
public class WarningAlertDialog extends Dialog implements DialogInterface.OnDismissListener, View.OnClickListener {
    private AQuery aq;
    private Runnable okClicked;
    private Runnable cancelClicked;
    private Runnable dismissRunnable;

    public WarningAlertDialog(Context context) {
        super(context,R.style.WarningDialog);
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning);

        View view= getWindow().getDecorView();
        view.findViewById(android.R.id.content).findViewById(R.id.cancel).setOnClickListener(this);
        view.findViewById(android.R.id.content).findViewById(R.id.ok).setOnClickListener(this);
        view.findViewById(android.R.id.content).findViewById(R.id.main).setOnClickListener(this);
        view.findViewById(android.R.id.content).findViewById(R.id.background).setOnClickListener(this);

        aq = new AQuery(view.findViewById(android.R.id.content));
//        aq.id(R.id.ok).clicked(this,"okClick");
//        aq.id(R.id.cancel).clicked(this, "cancelClick");
//        aq.id(R.id.main).clicked(this, "clickNone");
//        aq.id(R.id.background).clicked(this, "dismiss");
        setOnDismissListener(this);
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

    public WarningAlertDialog onDismiss(Runnable runnable){
        dismissRunnable = runnable;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(dismissRunnable!=null)
            dismissRunnable.run();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                if(okClicked!=null)
                    okClicked.run();
                dismiss();
                break;
            case R.id.cancel:
                if(cancelClicked!=null)
                    cancelClicked.run();
                dismiss();
                break;
            case R.id.main:

                break;
            case R.id.background:
                dismiss();
                break;
        }
    }
}
