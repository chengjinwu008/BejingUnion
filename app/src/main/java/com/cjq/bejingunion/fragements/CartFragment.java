package com.cjq.bejingunion.fragements;

import android.os.Bundle;
import android.os.Handler;
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
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.utils.LoginUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CartFragment extends Fragment {

    private View view;
    private AQuery aq;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart, container, false);

        aq = new AQuery(view);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("key", LoginUtil.getKey(getActivity()));
                    aq.ajax(CommonDataObject.CART_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
//                            System.out.println(object.toString());
                            try {
                                if(200==object.getInt("code")){
                                    JSONArray a = object.getJSONObject("datas").getJSONArray("cart_list");
                                    List<Goods4OrderList> goods4OrderLists = new ArrayList<Goods4OrderList>();
                                    for(int i=0;i<a.length();i++){
                                        JSONObject o = a.getJSONObject(i);
                                        Goods4OrderList goods4OrderList = new Goods4OrderList(o.getString("goods_image_url"),o.getString("goods_name"),null,o.getInt("goods_num"),o.getString("goods_price"));
                                        goods4OrderLists.add(goods4OrderList);
                                    }


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
        }, 2000);


        return view;
    }
}
