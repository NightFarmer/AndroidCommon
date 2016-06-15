package com.nightfarmer.androidcommon.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nightfarmer.androidcommon.data.cipher.Cipher;

import java.io.Serializable;


/**
 * 本地数据
 * Created by zhangfan on 16-6-13.
 */
public class LocalData {

    private String fileName;

    public <T> T get(Class<T> cls) {
        return get(DEFAULT, cls, null);
    }

    public <T> T get(String key, Class<T> cls) {
        return get(key, cls, null);
    }

    public <T> T get(Class<T> cls, Cipher cipher) {
        return get(DEFAULT, cls, cipher);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> cls, Cipher cipher) {
        T obj = null;
        try {
            String e = this.get(key + "-" + cls.getName(), (String) null);
            if (e == null) {
                return null;
            } else {
                byte[] bytes = HexUtil.decodeHex(e.toCharArray());
                if (cipher != null) {
                    bytes = cipher.decrypt(bytes);
                }
                obj = (T) ByteUtil.byteToObject(bytes);
                Log.i(TAG, fileName + " => " + key + " get: " + obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void put(Serializable ser) {
        this.put(DEFAULT, ser);
    }

    private SharedPreferences sp;
    public static final String DEFAULT = "default";
    public static final String KEY_PK_HOME = "msg_pk_home";
    public static final String KEY_PK_NEW = "msg_pk_new";
    private static final String TAG = LocalData.class.getSimpleName();

    public LocalData(Context context) {
        this(context, DEFAULT);
    }

    public LocalData(Context context, String fileName) {
        this.fileName = fileName;
        this.sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public String get(String key, String defValue) {
        return this.sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return this.sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return this.sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return this.sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return this.sp.getLong(key, defValue);
    }

    public void put(String key, Serializable ser) {
        this.put(key, ser, null);
    }

    public void put(String key, Serializable ser, Cipher cipher) {
        try {
            Log.i(TAG, fileName + " => " + key + " put: " + ser);
            if (ser == null) {
                this.sp.edit().remove(key).commit();
            } else {
                byte[] e = ByteUtil.objectToByte(ser);
                if (cipher != null) {
                    e = cipher.encrypt(e);
                }
                this.put(key + "-" + ser.getClass().getName(), HexUtil.encodeHexStr(e));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void put(String key, String value) {
        if (value == null) {
            this.sp.edit().remove(key).commit();
        } else {
            this.sp.edit().putString(key, value).commit();
        }

    }

    public void put(String key, boolean value) {
        this.sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        this.sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        this.sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        this.sp.edit().putInt(key, value).commit();
    }
}
