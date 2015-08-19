package com.cjq.bejingunion.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.UserSettingActivity;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserCenterFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_center, container, false);
        initView1(view);

        return view;
    }

    private void initView1(View view) {
        AQuery aq = new AQuery(view);
        aq.id(R.id.user_center_jump_user_setting).clicked(this,"jumpSetting");
    }

    public void jumpSetting(){
        Intent intent  = new Intent(getActivity(),UserSettingActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            // TODO: 2015/8/19 对修改信息的应对措施
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
