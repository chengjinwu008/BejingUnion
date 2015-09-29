package com.cjq.bejingunion.reciever;

import android.content.Context;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * Created by CJQ on 2015/8/11.
 */
public class PushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
        System.out.println(1);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        System.out.println(2);
    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        System.out.println(3);
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        System.out.println(4);
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        System.out.println(5);
    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        System.out.println(6);
        //透传
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        System.out.println(7);
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {
        System.out.println(8);
        //通知
    }
}
