package com.learn.derek.binaryclockwidget.misc;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.learn.derek.binaryclockwidget.R;

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
	/**
	 *  Calculate the scale factor of the fonts in the widget
	 */
	public static float getScaleRatio(Context context, int id) {
		Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(id);
		if (options != null) {
			int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
			if (minWidth == 0) {
				// No data , do no scaling
				return 1f;
			}
			Resources res = context.getResources();
			float ratio = minWidth / res.getDimension(R.dimen.def_digital_widget_width);
			return (ratio > 1) ? 1f : ratio;
		}
		return 1f;
	}

}
