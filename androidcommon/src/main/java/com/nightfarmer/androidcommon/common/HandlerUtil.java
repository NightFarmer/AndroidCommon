package com.nightfarmer.androidcommon.common;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhangfan on 16-6-14.
 */
public class HandlerUtil {
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public HandlerUtil() {
    }

    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeRunnable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }
}

