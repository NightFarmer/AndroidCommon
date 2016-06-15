package com.nightfarmer.androidcommon.recevier;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zhangfan on 16-6-14.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private SmsReceiver.SmsListener smsListener;

    public SmsReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        try {
            String serviceCenterAddress;
            Log.i(TAG, "收到广播：" + intent.getAction());
            Bundle e = intent.getExtras();

            for (String o : e.keySet()) {
                serviceCenterAddress = o;
                Log.i(TAG, serviceCenterAddress + " : " + e.get(serviceCenterAddress));
            }

            Object[] var13 = (Object[]) intent.getExtras().get("pdus");
            String var14 = null;
            serviceCenterAddress = null;
            if (var13 != null) {
                String msgBody = "";
                if (Build.VERSION.SDK_INT >= 4) {
                    for (Object obj : var13) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                        msgBody = msgBody + sms.getMessageBody();
                        var14 = sms.getOriginatingAddress();
                        serviceCenterAddress = sms.getServiceCenterAddress();
                        if (this.smsListener != null) {
                            this.smsListener.onMessage(sms);
                        }
                    }
                }

                if (this.smsListener != null) {
                    this.smsListener.onMessage(msgBody, var14, serviceCenterAddress);
                }
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }

    }

    public void registerSmsReceiver(Context context, SmsReceiver.SmsListener smsListener) {
        try {
            this.smsListener = smsListener;
            IntentFilter e = new IntentFilter();
            e.addAction("android.provider.Telephony.SMS_RECEIVED");
            e.setPriority(2147483647);
            context.registerReceiver(this, e);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void unRegisterSmsReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void sendMsgToPhone(String phone, String msg) {
        Log.i(TAG, "发送手机：" + phone + " ,内容： " + msg);
        if (Build.VERSION.SDK_INT >= 4) {
            SmsManager manager = SmsManager.getDefault();
            ArrayList texts = manager.divideMessage(msg);
            for (Object text : texts) {
                String txt = (String) text;
                manager.sendTextMessage(phone, null, txt, null, null);
            }
        } else {
            Log.e(TAG, "发送失败，系统版本低于DONUT，" + phone + " ,内容： " + msg);
        }

    }

    public static void saveMsgToSystem(Context context, String phone, String msg) {
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        values.put("read", 0);
        values.put("type", 2);
        values.put("address", phone);
        values.put("body", msg);
        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

    public abstract static class SmsListener {
        public SmsListener() {
        }

        public abstract void onMessage(String var1, String var2, String var3);

        public void onMessage(SmsMessage msg) {
        }
    }
}