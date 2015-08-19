package com.cjq.bejingunion.fragements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.LoginUtil;

/**
 * Created by CJQ on 2015/8/19.
 */
public class LoginFragment extends Fragment {

    private View view;
    private TextView userName;
    private TextView password;
    private CheckBox auto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login, container, false);

        initView2(view);
        return view;
    }

    private void initView2(View view2) {
        AQuery aq = new AQuery(view2);
        userName = aq.id(R.id.login_user_name).getTextView();
        password = aq.id(R.id.login_password).getTextView();
        auto = aq.id(R.id.login_remember).getCheckBox();

        aq.id(R.id.login_login_btn).clicked(this,"doLogin");
        aq.id(R.id.login_jump_register).clicked(this, "jumpRegister");
        aq.id(R.id.login_jump_forget_password).clicked(this,"jumpForgetPassword");
    }

    public void doLogin(){
        LoginUtil.login(getActivity(), userName.getText().toString(), password.getText().toString(), auto.isChecked());
    }

    public void jumpRegister(){
        // TODO: 2015/8/19 添加跳转到注册

    }

    public void jumpForgetPassword(){
        // TODO: 2015/8/19 添加跳转到找回密码
    }
}
