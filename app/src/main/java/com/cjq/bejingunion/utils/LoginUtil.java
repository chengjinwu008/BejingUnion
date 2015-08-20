package com.cjq.bejingunion.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.event.EventLoginIn;
import com.cjq.bejingunion.event.EventLogout;
import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class LoginUtil {

    public static void login(final Context context, String userName, final String password, final boolean remember) {
        AQuery aq = new AQuery(context);
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("client", "android");
        aq.ajax(CommonDataObject.LOGIN_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    System.out.println("loginCallBack:----------" + object.toString());
                    if ("200".equals(object.getString("code"))) {
                        if (remember) {
                            saveLoginInfo(context,object.getJSONObject("datas").getString("username"),object.getJSONObject("datas").getString("key"),password);
                        }
                        EventBus.getDefault().post(new EventLoginIn());
                    } else {
                        Toast.makeText(context, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                    }
                    super.callback(url, object, status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void logout(Context context) {
        if (isLogin(context)) {
            AQuery aq = new AQuery(context);
            // TODO: 2015/8/19 登出接口待实现
            SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user_name");
            editor.remove("key");
            editor.remove("password");
            editor.putBoolean("auto", false);
            editor.apply();
            EventBus.getDefault().post(new EventLogout());
        }
    }

    public static boolean isLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("key", "");
        return !"".equals(user_name);
    }

    public static boolean isAutoLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean("auto", false);
    }

    public static void setAuto(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto", true);
        editor.apply();
    }

    public static void cancelAuto(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto", false);
        editor.apply();
    }

    public static void autoLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("password", "");
        login(context, user_name, password, true);
    }

    public static String getKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        return sharedPreferences.getString("key", "");
    }

    public static void saveLoginInfo(Context context, String username, String key, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", username);
        editor.putString("key", key);
        editor.putString("password", password);
        editor.putBoolean("auto", true);
        editor.apply();
    }

    public static void logoutWithDeleteKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("key");
        editor.apply();
    }
}
