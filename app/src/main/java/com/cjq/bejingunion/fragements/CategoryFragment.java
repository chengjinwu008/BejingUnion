package com.cjq.bejingunion.fragements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CategoryFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.allkinds_list,container,false);
        return view;
    }
}
