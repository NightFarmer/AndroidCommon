package com.nightfarmer.androidcommon.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by zhangfan on 16-6-14.
 */
public class AppInfo {

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var5) {
            var5.printStackTrace();
        }
        return packageInfo;
    }

    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List taskList = am.getRunningTasks(1);
        if(taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = ((ActivityManager.RunningTaskInfo)taskList.get(0)).topActivity;
            if(componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void shareToOtherApp(Context context, String title, String content, String dialogTitle) {
        Intent intentItem = new Intent("android.intent.action.SEND");
        intentItem.setType("text/plain");
        intentItem.putExtra("android.intent.extra.SUBJECT", title);
        intentItem.putExtra("android.intent.extra.TEXT", content);
        intentItem.setFlags(268435456);
        context.startActivity(Intent.createChooser(intentItem, dialogTitle));
    }

}
