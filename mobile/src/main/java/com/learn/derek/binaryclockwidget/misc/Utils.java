package com.learn.derek.binaryclockwidget.misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

/**
 * Created by derek on 1/01/15.
 */
public class Utils {
    private static final String TAG = "Utils";
    private static boolean D = Constants.DEBUG;



    /**
     *  Networking available check
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            if (D) Log.d(TAG, "No network connection is available for weather update");
            return false;
        }
        return true;
    }
    /**
     *  API level check to see if the new API 17 TextClock is available
     */
    public static boolean isTextClockAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }


}
