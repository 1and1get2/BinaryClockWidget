package com.learn.derek.binaryclockwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.learn.derek.binaryclockwidget.misc.Constants;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BinaryClockWidgetConfigureActivity BinaryClockWidgetConfigureActivity}
 */
public class BinaryClockWidget extends AppWidgetProvider {
    private static final String TAG = "BinaryClockWidget";
    private static final boolean D = Constants.DEBUG;
    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";
    //widget views
    RemoteViews views;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //find out the action
        String action = intent.getAction();
        Log.i(TAG, "Received Intent: " + action);
        //is it time to update
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action))
        {

        } else if (intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            // DO SOMETHING

        } else if (Intent.ACTION_PROVIDER_CHANGED.equals(action)
                || Intent.ACTION_TIME_CHANGED.equals(action)
                || Intent.ACTION_TIMEZONE_CHANGED.equals(action)
                || Intent.ACTION_DATE_CHANGED.equals(action)
                || Intent.ACTION_LOCALE_CHANGED.equals(action)
                || "android.intent.action.ALARM_CHANGED".equals(action)) {
            //updateAppWidget(context, null);
            if (D) Log.i(TAG, "Received time/date/etc. update intent, refreshing");
            // There are no events to show in the Calendar panel, hide it explicitly
        }
    }
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle bundle) {
        int minWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        RemoteViews rv = null;
        //Size of a 2x1 widget on a Nexus 4 (other screen densities will differ)
/*        if(minWidth == 152 && maxWidth == 196 && minHeight == 58 && maxHeight == 84){
            rv = new RemoteViews(context.getPackageName(), R.layout.hour_min_widget);
        } else {
            rv = new RemoteViews(context.getPackageName(), R.layout.hour_min_widget);
        }*/

        Log.i(TAG, "onAppWidgetOptionsChanged");
        /* Set some stuff in your layout here */
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context,  AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            BinaryClockWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
        Log.i(TAG, "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // stop alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.stopAlarm();
        Log.i(TAG, "onDisabled");
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = BinaryClockWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.binary_clock_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


