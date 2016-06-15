package com.nightfarmer.androidcommon.device;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by zhangfan on 16-6-14.
 */
public class DeviceUtil {

    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }
}
