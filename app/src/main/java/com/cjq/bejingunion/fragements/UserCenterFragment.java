package com.cjq.bejingunion.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.AddressListActivity;
import com.cjq.bejingunion.activities.ChangePasswordActivity;
import com.cjq.bejingunion.activities.MyCollectionActivity;
import com.cjq.bejingunion.activities.UserSettingActivity;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserCenterFragment extends Fragment {

    private View view;
    private AQuery aq;
    private String avator;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_center, container, false);
        initView1(view);
        return view;
    }

    private void initView1(View view) {
        aq = new AQuery(view);
        aq.id(R.id.user_center_jump_user_setting).clicked(this, "jumpSetting");

        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(getActivity()));
            aq.id(R.id.user_center_my_collection).clicked(this, "jumpMyCollection");
            aq.id(R.id.user_center_my_address).clicked(this, "jumpMyAddress");
            aq.id(R.id.user_center_change_password).clicked(this, "showChangePassword");

            aq.ajax(CommonDataObject.USER_INFO_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONObject info = object.getJSONObject("datas").getJSONObject("member_info");
                            username = info.getString("user_name");
                            avator = info.getString("avator");
                            int point = info.getInt("point");
                            double predepoit = info.getDouble("predepoit");

                            aq.id(R.id.user_center_points).text(String.valueOf(point));
                            aq.id(R.id.user_center_username).text(username);
                            aq.id(R.id.user_center_user_portrait).image(avator, false, true);

                        }
                        super.callback(url, object, status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void showChangePassword() {
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        intent.putExtra("portrait", avator);
        intent.putExtra("name", username);
        startActivity(intent);
    }

    public void jumpSetting() {
        Intent intent = new Intent(getActivity(), UserSettingActivity.class);
        startActivityForResult(intent, 0);
    }

    public void jumpMyCollection() {
        Intent intent = new Intent(getActivity(), MyCollectionActivity.class);
        startActivity(intent);
    }

    public void jumpMyAddress() {
        Intent intent = new Intent(getActivity(), AddressListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            // TODO: 2015/8/19 对修改信息的应对措施
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
