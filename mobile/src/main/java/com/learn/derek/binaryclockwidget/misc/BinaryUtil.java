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
		this.eazyMode = easyMode;
		update(remoteViews);
	}
	public void update(RemoteViews remoteViews){
		Log.v(TAG, "updating clock");

		Locale locale = Locale.getDefault();
		Date now = new Date();

		String dateFormat = context.getString(R.string.no_wday_month_day_no_year);
		CharSequence date = DateFormat.format(dateFormat, now);

		String weekDay = new SimpleDateFormat(context.getString(R.string.full_wday), locale).format(now);

		String hours = getHours(now, locale);
		String minutes = getMinutes(now, locale);

		remoteViews.setTextViewText(R.id.tv_wday, weekDay);
		remoteViews.setTextViewText(R.id.tv_date, date);
		remoteViews.setTextViewText(R.id.tv_time, hours + " : " + minutes);

		updateRemoteViews(remoteViews);
	}
	private String getHours(Date date, Locale locale){
		return new SimpleDateFormat(getHourFormat(), locale).format(date);
	}
	private String getMinutes(Date date, Locale locale){
		return new SimpleDateFormat(context.getString(R.string.widget_12_hours_format_no_ampm_m),
				locale).format(date);
	}
	private int intLength(int num){
//		return num <= 0 ? 0 : (int)Math.log10(num) + 1;
		return String.valueOf(num).length();
	}
	private Constants.CELL_TYPE[][] columnIntToArray(int num, boolean easyMode){
		if (num <= 0 || num > 85) throw new NumberFormatException("input number doesn't fit in"); // check if it fits in a 2D array
		Constants.CELL_TYPE[][] array = new Constants.CELL_TYPE[2][4];
		if (!easyMode){
			String binary = Integer.toBinaryString(num);
			int length = binary.length();
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 4; j++){
					int index = i*4 + j;
					if (index < length){
						char c = binary.charAt(length - index - 1);
						array[i][j] = intToCellType(c);
					}
					else
						array[i][j] = Constants.CELL_TYPE.UNAVAILABLE;
				}
			}
		} else {
			char[] digits = String.valueOf(num).toCharArray();
			array[1] = digitToCellType(digits[digits.length-1]);

			if (digits.length > 1){
				array[0] = digitToCellType(digits[0]);
			} else {
				// fill up
				for (int i = 0; i < 4; i++){
					array[0][i] = Constants.CELL_TYPE.UNAVAILABLE;
				}
			}
		}
		return array;
	}
	private Constants.CELL_TYPE[] digitToCellType(char charNum){
		int num = Character.getNumericValue(charNum);
		if (num < 0 || num > Math.pow(2, 5) - 1) throw new NumberFormatException("input number doesn't fit in"); // check if it fits in a 2D array
		Constants.CELL_TYPE[] array = new Constants.CELL_TYPE[4];
		String binary = Integer.toBinaryString(num);
		int length = binary.length();
		for (int i = 0; i < 4; i++){
			if (i < length){
				char c = binary.charAt(length - i - 1);
				array[i] = intToCellType(c);
			}
			else
				array[i] = Constants.CELL_TYPE.UNAVAILABLE;
		}

		return array;
	}
	private Constants.CELL_TYPE intToCellType(char c){
		switch (Character.getNumericValue(c)){
			case 1:
				return Constants.CELL_TYPE.DOT;
			case 0:
				return Constants.CELL_TYPE.CIRCLE;
			default:
				return Constants.CELL_TYPE.UNAVAILABLE;
		}
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
