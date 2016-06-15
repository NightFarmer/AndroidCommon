package com.nightfarmer.androidcommon.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by zhangfan on 16-6-14.
 */
public class Network {
    private static final String TAG = Network.class.getSimpleName();

    public Network() {
    }

    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static boolean isConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Network.NetType getConnectedType(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            switch (net.getType()) {
                case 0:
                    return Network.NetType.Mobile;
                case 1:
                    return Network.NetType.Wifi;
                default:
                    return Network.NetType.Other;
            }
        } else {
            return Network.NetType.None;
        }
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == 1 && net.isConnected();
    }

    public static boolean isMobileConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == 0 && net.isConnected();
    }

    public static boolean isAvailable(Context context) {
        return isWifiAvailable(context) || isMobileAvailable(context) && isMobileEnabled(context);
    }

    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == 1) {
                    return net.isAvailable();
                }
            }
        }

        return false;
    }

    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == 0) {
                    return net.isAvailable();
                }
            }
        }

        return false;
    }

    public static boolean isMobileEnabled(Context context) {
        try {
            Method e = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            e.setAccessible(true);
            return ((Boolean) e.invoke(getConnectivityManager(context), new Object[0])).booleanValue();
        } catch (Exception var2) {
            var2.printStackTrace();
            return true;
        }
    }

    public static boolean printNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo in = connectivity.getActiveNetworkInfo();
            Log.i(TAG, "-------------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-------------");
            Log.i(TAG, "getActiveNetworkInfo: " + in);
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; ++i) {
                    Log.i(TAG, "NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
                    Log.i(TAG, "NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
                    Log.i(TAG, "NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
                    Log.i(TAG, "NetworkInfo[" + i + "]: " + info[i]);
                }

                Log.i(TAG, "\n");
            } else {
                Log.i(TAG, "getAllNetworkInfo is null");
            }
        }

        return false;
    }

    public static int getConnectedTypeINT(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            Log.i(TAG, "NetworkInfo: " + net.toString());

            return net.getType();
        } else {
            return -1;
        }
    }

    public static int getTelNetworkTypeINT(Context context) {
        return getTelephonyManager(context).getNetworkType();
    }

    public static Network.NetWorkType getNetworkType(Context context) {
        int type = getConnectedTypeINT(context);
        switch (type) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                int teleType = getTelephonyManager(context).getNetworkType();
                switch (teleType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return Network.NetWorkType.Net2G;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return Network.NetWorkType.Net3G;
                    case 13:
                        return Network.NetWorkType.Net4G;
                    default:
                        return Network.NetWorkType.UnKnown;
                }
            case 1:
                return Network.NetWorkType.Wifi;
            default:
                return Network.NetWorkType.UnKnown;
        }
    }

    public static enum NetWorkType {
        UnKnown(-1),
        Wifi(1),
        Net2G(2),
        Net3G(3),
        Net4G(4);

        public int value;

        private NetWorkType(int value) {
            this.value = value;
        }
    }

    public static enum NetType {
        None(1),
        Mobile(2),
        Wifi(4),
        Other(8);

        public int value;

        private NetType(int value) {
            this.value = value;
        }
    }
}
