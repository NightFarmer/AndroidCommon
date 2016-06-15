package com.nightfarmer.androidcommon.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhangfan on 16-6-13.
 */
public class ByteUtil {
    public ByteUtil() {
    }

    public static Object byteToObject(byte[] bytes) throws Exception {
        ObjectInputStream ois = null;

        Object var2;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            var2 = ois.readObject();
        } finally {
            if(ois != null) {
                ois.close();
            }

        }

        return var2;
    }

    public static byte[] objectToByte(Object obj) throws Exception {
        ObjectOutputStream oos = null;

        byte[] var3;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            var3 = bos.toByteArray();
        } finally {
            if(oos != null) {
                oos.close();
            }

        }

        return var3;
    }

    public static void byteToBit(byte[] bytes, StringBuilder sb) {
        for(int i = 0; i < 8 * bytes.length; ++i) {
            sb.append((char)((bytes[i / 8] << i % 8 & 128) == 0?'0':'1'));
        }

    }

    public static String byteToBit(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 8 * bytes.length; ++i) {
            sb.append((char)((bytes[i / 8] << i % 8 & 128) == 0?'0':'1'));
        }

        return sb.toString();
    }
}
