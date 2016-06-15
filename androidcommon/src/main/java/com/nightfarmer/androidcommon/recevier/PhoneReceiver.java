package com.nightfarmer.androidcommon.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.nightfarmer.androidcommon.other.Check;

import java.util.Iterator;

/**
 * Created by zhangfan on 16-6-14.
 */
public class PhoneReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneReceiver";
    private static final String RINGING = "RINGING";
    private static final String OFFHOOK = "OFFHOOK";
    private static final String IDLE = "IDLE";
    private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";
    private static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String INTENT_STATE = "state";
    private static final String INTENT_INCOMING_NUMBER = "incoming_number";
    private PhoneReceiver.PhoneListener phoneListener;
    private boolean isDialOut;
    private String number;

    public PhoneReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Log.i("PhoneReceiver", "action: " + intent.getAction());
        Log.d("PhoneReceiver", "intent : ");
        Bundle state = intent.getExtras();

        for (String key : state.keySet()) {
            Log.d("PhoneReceiver", key + " : " + state.get(key));
        }

        String state1;
        if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
            this.isDialOut = true;
            state1 = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
            if (!Check.isEmpty(state1)) {
                this.number = state1;
            }

            if (this.phoneListener != null) {
                this.phoneListener.onPhoneStateChanged(PhoneReceiver.CallState.Outgoing, this.number);
            }
        } else if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
            state1 = intent.getStringExtra("state");
            String inNumber1 = intent.getStringExtra("incoming_number");
            if (!Check.isEmpty(inNumber1)) {
                this.number = inNumber1;
            }

            if ("RINGING".equals(state1)) {
                this.isDialOut = false;
                if (this.phoneListener != null) {
                    this.phoneListener.onPhoneStateChanged(PhoneReceiver.CallState.IncomingRing, this.number);
                }
            } else if ("OFFHOOK".equals(state1)) {
                if (!this.isDialOut && this.phoneListener != null) {
                    this.phoneListener.onPhoneStateChanged(PhoneReceiver.CallState.Incoming, this.number);
                }
            } else if ("IDLE".equals(state1)) {
                if (this.isDialOut) {
                    if (this.phoneListener != null) {
                        this.phoneListener.onPhoneStateChanged(PhoneReceiver.CallState.OutgoingEnd, this.number);
                    }
                } else if (this.phoneListener != null) {
                    this.phoneListener.onPhoneStateChanged(PhoneReceiver.CallState.IncomingEnd, this.number);
                }
            }
        }

    }

    public void registerReceiver(Context context, PhoneReceiver.PhoneListener phoneListener) {
        try {
            IntentFilter e = new IntentFilter();
            e.addAction("android.intent.action.PHONE_STATE");
            e.addAction("android.intent.action.NEW_OUTGOING_CALL");
            e.setPriority(2147483647);
            context.registerReceiver(this, e);
            this.phoneListener = phoneListener;
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

    public static enum CallState {
        Outgoing,
        OutgoingEnd,
        IncomingRing,
        Incoming,
        IncomingEnd;

        private CallState() {
        }
    }

    public interface PhoneListener {
        void onPhoneStateChanged(PhoneReceiver.CallState var1, String var2);
    }
}
