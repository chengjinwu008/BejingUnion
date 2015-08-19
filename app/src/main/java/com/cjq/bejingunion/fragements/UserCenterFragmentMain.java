package com.cjq.bejingunion.fragements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjq.bejingunion.R;
import com.cjq.bejingunion.event.EventLoginIn;
import com.cjq.bejingunion.event.EventLogout;
import com.cjq.bejingunion.utils.LoginUtil;
import com.ypy.eventbus.EventBus;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserCenterFragmentMain extends Fragment {

    private View view;
    private FragmentManager manager;
    private Fragment uc;
    private Fragment login;

    public void onEventMainThread(EventLoginIn in) {
        manager.beginTransaction().replace(R.id.user_center_main, uc).commitAllowingStateLoss();
    }

    public void onEventMainThread(EventLogout out) {
        manager.beginTransaction().replace(R.id.user_center_main, login).commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_center_main, container, false);

        EventBus.getDefault().register(this);

        manager = getFragmentManager();

        uc = new UserCenterFragment();
        login = new LoginFragment();
        if (LoginUtil.isLogin(getActivity()))
            manager.beginTransaction().replace(R.id.user_center_main, uc).commit();
        else
            manager.beginTransaction().replace(R.id.user_center_main, login).commit();
        return view;
    }
}
