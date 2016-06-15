package com.nightfarmer.androidcommon.common;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by zhangfan on 16-6-14.
 */
public class ClipboardUtil {

    public static void copyToClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText((CharSequence) null, text));
    }

    public static int getItemCount(Context context) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = clipboard.getPrimaryClip();
        return data.getItemCount();
    }

    public static String getText(Context context, int index) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        return clip != null && clip.getItemCount() > index?String.valueOf(clip.getItemAt(0).coerceToText(context)):null;
    }

    public static String getLatestText(Context context) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        return clip != null && clip.getItemCount() > 0?String.valueOf(clip.getItemAt(0).coerceToText(context)):null;
    }
    
}
