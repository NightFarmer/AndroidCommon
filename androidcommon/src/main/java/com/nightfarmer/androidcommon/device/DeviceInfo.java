package com.nightfarmer.androidcommon.device;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 设备信息
 * Created by zhangfan on 16-6-13.
 */
public class DeviceInfo {
    private static final String TAG = DeviceInfo.class.getSimpleName();


    /**
     * 获取mac地址
     *
     * @param context context
     * @return mac地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000L;
        int h = (int) (ut / 3600L);
        int m = (int) (ut / 60L % 60L);
        return h + ":" + m;
    }

    /**
     * 获取内存信息
     *
     * @param context context
     * @return MemoryInfo
     */
    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi;
    }

    /**
     * 获取并打印内存信息
     *
     * @param context context
     * @return MemoryInfo
     */
    public static ActivityManager.MemoryInfo printMemoryInfo(Context context) {
        ActivityManager.MemoryInfo mi = getMemoryInfo(context);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  Memory :   ");
        if (Build.VERSION.SDK_INT >= 16) {
            sb.append("\ntotalMem        :").append(mi.totalMem);
        }

        sb.append("\navailMem        :").append(mi.availMem);
        sb.append("\nlowMemory       :").append(mi.lowMemory);
        sb.append("\nthreshold       :").append(mi.threshold);
        Log.i(TAG, sb.toString());
        return mi;
    }

    /**
     * 打印屏幕信息
     *
     * @param context context
     * @return DisplayMetrics
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append("_______  显示信息:  ");
        sb.append("\ndensity         :").append(dm.density);
        sb.append("\ndensityDpi      :").append(dm.densityDpi);
        sb.append("\nheightPixels    :").append(dm.heightPixels);
        sb.append("\nwidthPixels     :").append(dm.widthPixels);
        sb.append("\nscaledDensity   :").append(dm.scaledDensity);
        sb.append("\nxdpi            :").append(dm.xdpi);
        sb.append("\nydpi            :").append(dm.ydpi);
        Log.i(TAG, sb.toString());

        return dm;
    }

    /**
     * 打印并返回系统信息
     *
     * @return 系统信息
     */
    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);
        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);
        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= 3) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= 4) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append("unknown");
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= 9) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }

        Log.i(TAG, sb.toString());
        return sb.toString();
    }

    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telephonyManager.getSubscriberId();
        Log.i(TAG, " IMSI：" + IMSI);
        return IMSI;
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        Log.i(TAG, " IMEI：" + IMEI);
        return IMEI;
    }

    public static String printTelephoneInfo(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______ 手机信息  ").append(time).append(" ______________");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        String providerName = null;
        if (IMSI != null) {
            if (!IMSI.startsWith("46000") && !IMSI.startsWith("46002")) {
                if (IMSI.startsWith("46001")) {
                    providerName = "中国联通";
                } else if (IMSI.startsWith("46003")) {
                    providerName = "中国电信";
                }
            } else {
                providerName = "中国移动";
            }
        }

        sb.append(providerName).append("  手机号：").append(tm.getLine1Number()).append(" IMSI是：").append(IMSI);
        sb.append("\nDeviceID(IMEI)       :").append(tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:").append(tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number       :").append(tm.getLine1Number());
        sb.append("\nNetworkCountryIso    :").append(tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator      :").append(tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName  :").append(tm.getNetworkOperatorName());
        sb.append("\nNetworkType          :").append(tm.getNetworkType());
        sb.append("\nPhoneType            :").append(tm.getPhoneType());
        sb.append("\nSimCountryIso        :").append(tm.getSimCountryIso());
        sb.append("\nSimOperator          :").append(tm.getSimOperator());
        sb.append("\nSimOperatorName      :").append(tm.getSimOperatorName());
        sb.append("\nSimSerialNumber      :").append(tm.getSimSerialNumber());
        sb.append("\ngetSimState          :").append(tm.getSimState());
        sb.append("\nSubscriberId         :").append(tm.getSubscriberId());
        sb.append("\nVoiceMailNumber      :").append(tm.getVoiceMailNumber());
        Log.i(TAG, sb.toString());
        return sb.toString();
    }


    /**
     * 获取应用数据路径
     *
     * @return 应用数据路径，如：/data/data/com.nightfarmer.sample/files
     */
    public static String getDataPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * SD卡是可用
     *
     * @return
     */
    public boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获取sd卡路径
     *
     * @return sd卡路径
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取sd卡路径
     *
     * @return sd卡路径
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        String sdcard = null;
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;

        try {
            Process e = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(e.getInputStream())));

            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                Log.i(TAG, "proc/mounts:   " + lineStr);
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        sdcard = strArray[1].replace("/.android_secure", "");
                        Log.i(TAG, "find sd card path:   " + sdcard);
                        return sdcard;
                    }
                }

                if (e.waitFor() != 0 && e.exitValue() == 1) {
                    Log.e(TAG, cmd + " 命令执行失败");
                }
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var17) {
                var17.printStackTrace();
            }

        }

        sdcard = Environment.getExternalStorageDirectory().getPath();
        Log.i(TAG, "not find sd card path return default:   " + sdcard);
        return sdcard;
    }

}
