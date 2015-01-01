package com.learn.derek.binaryclockwidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.learn.derek.binaryclockwidget.misc.BinaryUtil;
import com.learn.derek.binaryclockwidget.misc.Constants;
import com.learn.derek.binaryclockwidget.misc.Utils;


/**
 * Created by derek on 1/01/15.
 */
public class ClockWidgetService extends IntentService {
	private static final String TAG = "ClockWidgetService";
	private static final boolean D = Constants.DEBUG;


	// import the intents
	// uh, forget about it, i'll just use Constants. whatever

	private static boolean mShowWDay = true;
	private static boolean mShowDate = true;
	private static boolean mShowTime = true;
	private static BinaryUtil mUtil;

	private int[] mWidgetIds;
	private AppWidgetManager mAppWidgetManager;


	public ClockWidgetService() {
		super(TAG);
	}
	@Override
	public void onCreate() {
		super.onCreate();

		ComponentName thisWidget = new ComponentName(this, BinaryClockWidget.class);
		mAppWidgetManager = AppWidgetManager.getInstance(this);
		mWidgetIds = mAppWidgetManager.getAppWidgetIds(thisWidget);
		//mUtil = new BinaryUtil();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (D) Log.d(TAG, "Got intent " + intent);

		if (mWidgetIds != null && mWidgetIds.length != 0) {
			// Check passed in intents
			if (intent != null) {
				if (Constants.ACTION_HIDE_WEEK_DAY.equals(intent.getAction())) {
					if (D) Log.v(TAG, "Force hiding the ACTION_HIDE_WEEK_DAY");
					// Explicitly hide the panel since we received a broadcast indicating no events
					mShowWDay = false;
				} else if (Constants.ACTION_HIDE_DATE.equals(intent.getAction())) {
					if (D) Log.v(TAG, "Forcing ACTION_HIDE_DATE");
					mShowDate = false;
				}
			}
			// update widget anyway
			refreshWidget();
		}
	}
	/**
	 * Reload the widget
	 */
	private void refreshWidget(){
		// Get things ready
		RemoteViews remoteViews;
		//TODO: need a way to store preferences
		boolean showWDay = mShowWDay;
		boolean showDate = mShowDate;
		boolean showTime = mShowTime;

		// Update the widgets
		for (int id : mWidgetIds) {

			// Determine if its a home or a lock screen widget
			Bundle myOptions = mAppWidgetManager.getAppWidgetOptions (id);
			boolean isKeyguard = false;
			if (Utils.isTextClockAvailable()) {
				// This is only available on API 17+, make sure we are not calling it on API16
				// This generates an API level Lint warning, ignore it
				int category = myOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
				isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
			}
			if (D) Log.d(TAG, "For Widget id " + id + " isKeyguard is set to " + isKeyguard);

			remoteViews = new RemoteViews(getPackageName(), R.layout.hour_min_widget);


			remoteViews.setViewVisibility(R.id.loading_indicator, View.GONE);



			// Hide/Show WDay/DATE/TIME
			// TODO: apply the preference font and format
			remoteViews.setViewVisibility(R.id.tv_date, (showDate?View.VISIBLE:View.GONE));
			remoteViews.setViewVisibility(R.id.tv_wday, (showWDay?View.VISIBLE:View.GONE));
			remoteViews.setViewVisibility(R.id.tv_time, (showTime?View.VISIBLE:View.GONE));

			// Always Refresh the Clock widget
			refreshClock(remoteViews);

			// Do the update
			mAppWidgetManager.updateAppWidget(id, remoteViews);
		}
	}
	private void refreshClock(RemoteViews remoteViews){
		if (mUtil == null) mUtil = new BinaryUtil(this, remoteViews, true);
		mUtil.update(remoteViews);

		//using BinaryUtil();
		//mUtil = new BinaryUtil(this, remoteViews, Integer.parseInt(hours), Integer.parseInt(minutes));

	}
//	private String getHourFormat() {
//		String format;
//		if (DateFormat.is24HourFormat(this)) {
//			format = getString(R.string.widget_24_hours_format_h_api_16);
//		} else {
//			format = getString(R.string.widget_12_hours_format_h);
//		}
//		return format;
//	}
}
