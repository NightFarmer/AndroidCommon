package com.nightfarmer.androidcommon.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by zhangfan on 16-6-14.
 */
public class Alert {

    public static void alert(Context context, String title, String content, String[] buttons, DialogInterface.OnClickListener... listeners) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content);
        if (buttons.length > 0) {
            DialogInterface.OnClickListener listener = null;
            if (listeners.length > 0) {
                listener = listeners[0];
            }
            builder.setNeutralButton(buttons[0], listener);
        }
        if (buttons.length > 1) {
            DialogInterface.OnClickListener listener = null;
            if (listeners.length > 1) {
                listener = listeners[1];
            }
            builder.setNegativeButton(buttons[1], listener);
        }
        if (buttons.length > 2) {
            DialogInterface.OnClickListener listener = null;
            if (listeners.length > 2) {
                listener = listeners[2];
            }
            builder.setPositiveButton(buttons[2], listener);
        }
        builder.create().show();
    }

}
