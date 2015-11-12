package com.cjq.bejingunion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cjq.bejingunion.event.EventShutDown;
import com.cjq.bejingunion.utils.MD5;
import com.ypy.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

public class BackgroundService extends Service {

    boolean flag = false;

    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if(!flag){
                        flag=true;
                        ace.callEndpoint(BackgroundService.this, "test", null,
                                new CloudCodeListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        flag=false;
                                        String code = (String) object;
                                        DateFormat dateFormat = DateFormat.getDateInstance();
                                        Calendar calendar = Calendar.getInstance(Locale.CHINA);
                                        String s = "chenjinqiang" + calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                                        String ncode = MD5.getMD5(s.getBytes());
//                                        System.out.println(ncode+"----------------"+code);
                                        if (!ncode.equals(code)) {
                                            EventBus.getDefault().post(new EventShutDown());
                                        }
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        EventBus.getDefault().post(new EventShutDown());
                                    }
                                });
                    }

                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }
}
