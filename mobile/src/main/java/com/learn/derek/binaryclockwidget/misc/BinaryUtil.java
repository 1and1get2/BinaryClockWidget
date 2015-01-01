package com.learn.derek.binaryclockwidget.misc;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.learn.derek.binaryclockwidget.R;

/**
 * Created by derek on 2/01/15.
 */
public class BinaryUtil {
	private static final String TAG = "BinaryUtil";
	private static final boolean D = Constants.DEBUG;


	private int hours;
	private int minutes;
	private RemoteViews remoteViews;
	private boolean eazyMode;
	private Context context;
	private final int[] CELLS = {R.id.cell_0,R.id.cell_1,R.id.cell_2,R.id.cell_3};


	public BinaryUtil(Context context, RemoteViews remoteViews ,int hours, int minutes){
		this(context, remoteViews, hours, minutes, true);
		//if (D) Log.v(TAG, "Constructor with 3 parameters");
		//new BinaryUtil(remoteViews, hours, minutes, true);
	}
	public BinaryUtil(Context context, RemoteViews remoteViews ,int hours, int minutes, boolean eazyMode){
		if (D) Log.v(TAG, "Constructor with 4 parameters");
		this.context = context;
		this.remoteViews = remoteViews;
		this.hours = hours;
		this.minutes = minutes;
		updateRemoteViews(remoteViews);
	}

	private void updateRemoteViews(RemoteViews remoteViews){
		if (D) Log.i(TAG, "updateRemoteViews");
//		ImageView iv = new ImageView(context);



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


}
