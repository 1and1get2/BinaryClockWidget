package com.learn.derek.binaryclockwidget.misc;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

import com.learn.derek.binaryclockwidget.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by derek on 2/01/15.
 */
public class BinaryUtil {
	private static final String TAG = "BinaryUtil";
	private static final boolean D = Constants.DEBUG;


//	private int hours;
//	private int minutes;
	private RemoteViews remoteViews;
	private boolean eazyMode;
	private Context context;
	private final int[] CELLS = {R.id.cell_0,R.id.cell_1,R.id.cell_2,R.id.cell_3};

	public BinaryUtil(Context context, RemoteViews remoteViews, boolean easyMode){
		if (D) Log.v(TAG, "Constructor with 3 parameters");
		this.context = context;
		this.remoteViews = remoteViews;
		update(remoteViews);
	}
	public void update(RemoteViews remoteViews){
		Log.v(TAG, "updating clock");

		Locale locale = Locale.getDefault();
		Date now = new Date();

		String dateFormat = context.getString(R.string.no_wday_month_day_no_year);
		CharSequence date = DateFormat.format(dateFormat, now);
		String weekDay = new SimpleDateFormat(context.getString(R.string.full_wday), locale).format(now);

		String hours = new SimpleDateFormat(getHourFormat(), locale).format(now);
		String minutes = new SimpleDateFormat(context.getString(R.string.widget_12_hours_format_no_ampm_m),
				locale).format(now);

		remoteViews.setTextViewText(R.id.tv_wday, weekDay);
		remoteViews.setTextViewText(R.id.tv_date, date);
		remoteViews.setTextViewText(R.id.tv_time, hours + " : " + minutes);

		updateRemoteViews(remoteViews);
	}

	@Deprecated
	public void update(){
		Log.w(TAG, "Can't find remoteViews object");
		update(this.remoteViews);
	}
	private void updateRemoteViews(RemoteViews remoteViews){
		if (D) Log.i(TAG, "updateRemoteViews");
		for (int i = 0; i < 4; i++){
			remoteViews.removeAllViews(CELLS[i]);
			for (int j = 0; j < 4; j++){
				RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.cell_layout);
				if(Math.random() > 0.5) rv.setImageViewResource(R.id.imageView, R.drawable.appwidget_bg_pressed);
				remoteViews.addView(CELLS[i], rv);
			}
//			RemoteViews textView = new RemoteViews(context.getPackageName(), R.layout.test_widget_layout);
//			textView.setTextViewText(R.id.textView1, "TextView number " + String.valueOf(i));
//			remoteViews.addView(CELLS[0], textView);
		}

	}
	private int cellTypeToImageId(Constants.CELL_TYPE type){
		int result;
		switch (type){
			case CIRCLE:
				result = R.drawable.appwidget_dark_bg_focused;
				break;
			case DOT:
				result = R.drawable.appwidget_bg_pressed;
				break;
			default:
				result = R.drawable.appwidget_bg;
		}
		return result;
	}
	private String getHourFormat() {
		String format;
		if (DateFormat.is24HourFormat(context)) {
			format = context.getString(R.string.widget_24_hours_format_h_api_16);
		} else {
			format = context.getString(R.string.widget_12_hours_format_h);
		}
		return format;
	}
}
