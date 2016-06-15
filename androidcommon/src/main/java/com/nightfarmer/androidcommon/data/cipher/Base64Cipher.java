package com.nightfarmer.androidcommon.data.cipher;

import android.util.Base64;

/**
 * Created by zhangfan on 16-6-13.
 */
public class Base64Cipher extends Cipher {
    private Cipher cipher;

    public Base64Cipher() {
    }

    public Base64Cipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public byte[] decrypt(byte[] res) {
        if (this.cipher != null) {
            res = this.cipher.decrypt(res);
        }

        return Base64.decode(res, 0);
    }

    public byte[] encrypt(byte[] res) {
        if (this.cipher != null) {
            res = this.cipher.encrypt(res);
        }

        return Base64.encode(res, 0);
    }
}
