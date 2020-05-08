package com.laolian.base.util;

import android.content.Context;
import android.util.Log;

/**
 * 日志工具类
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/11 23:08
 */
public class LogUtils {
    private static boolean isDebug = false;
    private static String DEFAULT_TAG = "test";

    public static void setIsDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static void d(String tag, String msg){
        if (isDebug) {
            Log.d(tag,msg);
        }
    }

    public static void i(String tag, String msg){
        if (isDebug) {
            Log.i(tag,msg);
        }
    }

    public static void e(String tag, String msg){
        if (isDebug) {
            Log.e(tag,msg);
        }
    }
    public static void d( String msg){
        if (isDebug) {
            Log.d(DEFAULT_TAG,msg);
        }
    }

    public static void i( String msg){
        if (isDebug) {
            Log.i(DEFAULT_TAG,msg);
        }
    }

    public static void e( String msg){
        if (isDebug) {
            Log.e(DEFAULT_TAG,msg);
        }
    }


}
