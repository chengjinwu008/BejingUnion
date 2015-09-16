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
import com.cjq.bejingunion.activities.EvaluationListActivity;
import com.cjq.bejingunion.activities.MessageActivity;
import com.cjq.bejingunion.activities.MyCollectionActivity;
import com.cjq.bejingunion.activities.OrderListActivity;
import com.cjq.bejingunion.activities.PayForPointsActivity;
import com.cjq.bejingunion.activities.UserSettingActivity;
import com.cjq.bejingunion.event.EventPortraitChange;
import com.cjq.bejingunion.utils.LoginUtil;
import com.ypy.eventbus.EventBus;

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

    public void onEventMainThread(EventPortraitChange e) {
        aq.id(R.id.user_center_user_portrait).image(e.getImage(), true, false);
    }

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
        EventBus.getDefault().register(this);

        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(getActivity()));
            aq.id(R.id.user_center_my_collection).clicked(this, "jumpMyCollection");
            aq.id(R.id.user_center_my_address).clicked(this, "jumpMyAddress");
            aq.id(R.id.user_center_change_password).clicked(this, "showChangePassword");
            aq.id(R.id.user_center_jump_pay_points).clicked(this, "jumpPayPoints");
            aq.id(R.id.user_center_jump_msg_list).clicked(this, "jumpMsgList");
            aq.id(R.id.user_center_jump_evaluation).clicked(this, "jumpEvaluationList");
            aq.id(R.id.user_center_broadband_order_list).clicked(this, "jumpOrderListActivity");
            aq.id(R.id.user_center_card_number_order_list).clicked(this, "jumpOrderListActivity2");
            aq.id(R.id.user_center_contract_mobile_order_list).clicked(this, "jumpOrderListActivity3");
            aq.id(R.id.user_center_market_order_list).clicked(this, "jumpOrderListActivity4");

            aq.ajax(CommonDataObject.USER_INFO_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONObject info = object.getJSONObject("datas").getJSONObject("member_info");
                            username = info.getString("member_nickname");
                            avator = info.getString("avator");
                            double predepoit = info.getDouble("predepoit");
                            Object is_agent = info.get("is_agent");

                            aq.id(R.id.user_center_points).text(String.valueOf(predepoit));
                            aq.id(R.id.user_center_username).text(username);
                            aq.id(R.id.user_center_user_portrait).image(avator, true, false);

                            if (is_agent == JSONObject.NULL || 1 != info.getInt("is_agent")) {
                                aq.id(R.id.user_center_vip_charge).gone();
                                aq.id(R.id.user_center_vip).background(R.drawable.btn1);
                            } else {
                                aq.id(R.id.user_center_vip_charge).visible();
                                aq.id(R.id.user_center_vip).background(R.drawable.btn);
                            }
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

        requestOrderCount();
    }

    public void jumpOrderListActivity() {
        Intent intent = new Intent(getActivity(), OrderListActivity.class);

        intent.putExtra("order_state", "10");

        startActivity(intent);
    }

    public void jumpOrderListActivity2() {
        Intent intent = new Intent(getActivity(), OrderListActivity.class);

        intent.putExtra("order_state", "30");

        startActivity(intent);
    }

    public void jumpOrderListActivity3() {
        Intent intent = new Intent(getActivity(), OrderListActivity.class);

        intent.putExtra("order_state", "40");

        startActivity(intent);
    }

    public void jumpOrderListActivity4() {
        Intent intent = new Intent(getActivity(), OrderListActivity.class);

        intent.putExtra("order_state", "0");

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void requestOrderCount() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(getActivity()));

            aq.ajax(CommonDataObject.ORDER_COUNT_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {

                    try {
                        if (200 == object.getInt("code")) {
                            int n0 = object.getJSONObject("datas").getInt("0");
                            int n10 = object.getJSONObject("datas").getInt("10");
                            int n30 = object.getJSONObject("datas").getInt("30");
                            int n40 = object.getJSONObject("datas").getInt("40");

                            aq.id(R.id.user_center_n0).invoke("setNumber", new Class[]{Integer.class}, n10);
                            aq.id(R.id.user_center_n10).invoke("setNumber", new Class[]{Integer.class}, n30);
                            aq.id(R.id.user_center_n30).invoke("setNumber", new Class[]{Integer.class}, n40);
                            aq.id(R.id.user_center_n40).invoke("setNumber", new Class[]{Integer.class}, n0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    super.callback(url, object, status);
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

    public void jumpPayPoints() {
        Intent intent = new Intent(getActivity(), PayForPointsActivity.class);
        startActivity(intent);
    }

    public void jumpEvaluationList() {
        Intent intent = new Intent(getActivity(), EvaluationListActivity.class);
        startActivity(intent);
    }

    public void jumpMsgList() {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
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
