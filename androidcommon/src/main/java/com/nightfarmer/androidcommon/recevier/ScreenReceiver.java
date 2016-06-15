package com.nightfarmer.androidcommon.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by zhangfan on 16-6-14.
 */
public class ScreenReceiver extends BroadcastReceiver {
    private String TAG = "ScreenActionReceiver";
    private ScreenReceiver.ScreenListener screenListener;

    public ScreenReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("android.intent.action.SCREEN_ON")) {
            Log.d(this.TAG, "屏幕解锁广播...");
            if(this.screenListener != null) {
                this.screenListener.screenOn();
            }
        } else if(action.equals("android.intent.action.SCREEN_OFF")) {
            Log.d(this.TAG, "屏幕加锁广播...");
            if(this.screenListener != null) {
                this.screenListener.screenOff();
            }
        }

    }

    public void registerScreenReceiver(Context context, ScreenReceiver.ScreenListener screenListener) {
        try {
            this.screenListener = screenListener;
            IntentFilter e = new IntentFilter();
            e.addAction("android.intent.action.SCREEN_OFF");
            e.addAction("android.intent.action.SCREEN_ON");
            Log.d(this.TAG, "注册屏幕解锁、加锁广播接收者...");
            context.registerReceiver(this, e);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void unRegisterScreenReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
            Log.d(this.TAG, "注销屏幕解锁、加锁广播接收者...");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public interface ScreenListener {
        void screenOn();

        void screenOff();
    }
}
