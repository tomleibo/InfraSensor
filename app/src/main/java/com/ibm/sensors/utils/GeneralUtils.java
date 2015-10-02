package com.ibm.sensors.utils;

import android.util.Log;

/**
 * Created by thinkPAD on 10/2/2015.
 */
public class GeneralUtils {
    public static void logException(String TAG, Throwable e) {
        logException(TAG,e,0);
    }

    public static void logException(String TAG, Throwable e,int depth) {
        Log.wtf(TAG, getIndentation(depth)+"cause: "+e.toString());
        Log.wtf(TAG, getIndentation(depth)+"stack trace: ");
        for (StackTraceElement ele : e.getStackTrace()) {
            Log.wtf(TAG, getIndentation(depth)+ele.toString());
            Throwable t =e.getCause();
            if (t!=null) {
                logException(TAG, t,depth+1);
            }
        }
    }

    private static String getIndentation(int c) {
        String s = "   ";
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<c;i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
