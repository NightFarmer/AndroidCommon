package com.nightfarmer.androidcommon.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * 文件操作工具类
 * Created by zhangfan on 16-6-14.
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public FileUtil() {
    }

    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;

        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            FileChannel e = fi.getChannel();
            FileChannel out = fo.getChannel();
            e.transferTo(0L, e.size(), out);
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                if(fo != null) {
                    fo.close();
                }

                if(fi != null) {
                    fi.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

    }

    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if(fileLen < 1024L) {
            fileSizeString = df.format((double)fileLen) + "B";
        } else if(fileLen < 1048576L) {
            fileSizeString = df.format((double)fileLen / 1024.0D) + "K";
        } else if(fileLen < 1073741824L) {
            fileSizeString = df.format((double)fileLen / 1048576.0D) + "M";
        } else {
            fileSizeString = df.format((double)fileLen / 1.073741824E9D) + "G";
        }

        return fileSizeString;
    }

    public static boolean deleteFile(File file) throws IOException {
        return file != null && file.delete();
    }

    public static String getExtensionName(String filename) {
        if(filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if(dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }

        return filename;
    }

    public static String getFileOutputString(String path) {
        try {
            BufferedReader e = new BufferedReader(new FileReader(path), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = e.readLine()) != null) {
                sb.append("\n").append(line);
            }

            e.close();
            return sb.toString();
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}