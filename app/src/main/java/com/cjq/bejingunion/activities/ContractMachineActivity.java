package com.cjq.bejingunion.activities;

import android.os.Bundle;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.view.RightSlideMenu;

/**
 * Created by CJQ on 2015/8/20.
 */
public class ContractMachineActivity extends BaseActivity {

    private AQuery aq;
    private RightSlideMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_machine);

        aq = new AQuery(this);

        aq.id(R.id.contract_machine_back).clicked(this,"closeUp");
        aq.id(R.id.contract_machine_draw_category_out).clicked(this, "drawMenuOutSwitch");

        menu = (RightSlideMenu) aq.id(R.id.contract_machine_menu_layout).getView();

    }

    public void closeUp(){
        finish();
    }

    public void drawMenuOutSwitch(){
        menu.animateMenu();
    }
}
