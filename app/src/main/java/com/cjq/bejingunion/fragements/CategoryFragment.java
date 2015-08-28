package com.cjq.bejingunion.fragements;

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

import org.json.JSONObject;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CategoryFragment extends Fragment {

    private View view;
    private AQuery aq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.allkinds_list,container,false);

        aq = new AQuery(view);

        aq.ajax(CommonDataObject.CATEGORIES_GET_URL, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                super.callback(url, object, status);
            }
        });

        return view;
    }
}
