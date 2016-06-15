package com.nightfarmer.androidcommon.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by zhangfan on 16-6-14.
 */
public class TimeReceiver extends BroadcastReceiver {
    private static final String TAG = "TimeReceiver";
    private TimeReceiver.TimeListener timeListener;

    public TimeReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Log.i("TimeReceiver", "action: " + intent.getAction());
        Log.d("TimeReceiver", "intent : ");
        Bundle bundle = intent.getExtras();

        for (String key : bundle.keySet()) {
            Log.d("TimeReceiver", key + " : " + bundle.get(key));
        }

        if ("android.intent.action.TIME_TICK".equals(intent.getAction())) {
            if (this.timeListener != null) {
                this.timeListener.onTimeTick();
            }
        } else if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
            if (this.timeListener != null) {
                this.timeListener.onTimeChanged();
            }
        } else if ("android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction()) && this.timeListener != null) {
            this.timeListener.onTimeZoneChanged();
        }

    }

    public void registerReceiver(Context context, TimeReceiver.TimeListener timeListener) {
        try {
            IntentFilter e = new IntentFilter();
            e.addAction("android.intent.action.TIME_SET");
            e.addAction("android.intent.action.TIME_TICK");
            e.addAction("android.intent.action.TIMEZONE_CHANGED");
            e.setPriority(2147483647);
            context.registerReceiver(this, e);
            this.timeListener = timeListener;
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void unRegisterReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public interface TimeListener {
        void onTimeZoneChanged();

        void onTimeChanged();

        void onTimeTick();
    }
}
