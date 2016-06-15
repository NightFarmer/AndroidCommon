package com.nightfarmer.androidcommon.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.nightfarmer.androidcommon.other.Check;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangfan on 16-6-14.
 */
public class AppUtil {
    public static boolean install(Context context, File file) {
        if(file != null && file.exists() && file.isFile()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.addFlags(268435456);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.DELETE");
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    public static void goToInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", packageName, (String)null));
        } else {
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(sdkVersion == 8?"pkg":"com.android.settings.ApplicationPkgName", packageName);
        }

        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static boolean isSystemApplication(Context context) {
        return context == null?false:isSystemApplication(context, context.getPackageName());
    }

    public static boolean isSystemApplication(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if(packageManager != null && packageName != null && packageName.length() != 0) {
            try {
                ApplicationInfo e = packageManager.getApplicationInfo(packageName, 0);
                return e != null && (e.flags & 1) > 0;
            } catch (Exception var4) {
                var4.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static List<PackageInfo> getInstalledPackages(Context context) {
        return context.getPackageManager().getInstalledPackages(0);
    }

    public static boolean isInstalled(Context context, String pkg) {
        if(!Check.isEmpty(pkg)) {
            List list = getInstalledPackages(context);
            if(!Check.isEmpty(list)) {
                for (Object aList : list) {
                    PackageInfo pi = (PackageInfo) aList;
                    if (pkg.equalsIgnoreCase(pi.packageName)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean startAppByPackageName(Context context, String packageName) {
        return startAppByPackageName(context, packageName, null);
    }

    public static boolean startAppByPackageName(Context context, String packageName, Map<String, String> param) {
        PackageInfo pi = null;

        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent e = new Intent("android.intent.action.MAIN", null);
            e.addCategory("android.intent.category.LAUNCHER");
            if(Build.VERSION.SDK_INT >= 4) {
                e.setPackage(pi.packageName);
            }

            List apps = context.getPackageManager().queryIntentActivities(e, 0);
            ResolveInfo ri = (ResolveInfo)apps.iterator().next();
            if(ri != null) {
                String packageName1 = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setFlags(268435456);
                intent.addCategory("android.intent.category.LAUNCHER");
                ComponentName cn = new ComponentName(packageName1, className);
                intent.setComponent(cn);
                if(param != null) {
                    for (Object o : param.entrySet()) {
                        Map.Entry en = (Map.Entry) o;
                        intent.putExtra((String) en.getKey(), (String) en.getValue());
                    }
                }

                context.startActivity(intent);
                return true;
            }
        } catch (Exception var13) {
            var13.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "启动失败", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
