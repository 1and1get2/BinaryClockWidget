package com.learn.derek.binaryclockwidget.misc;

/**
 * Created by derek on 31/12/14.
 */
public class Constants {
	public static final boolean DEBUG = true;
	public static final String PACKAGE_NAME = "com.learn.derek.binaryclockwidget";
	public static final String REFRESH = PACKAGE_NAME + ".REFRESH";
	public static final String ACTION_AUTO_UPDATE = PACKAGE_NAME + ".AUTO_UPDATE";
	public static final String ACTION_HIDE_WEEK_DAY = PACKAGE_NAME + ".ACTION_HIDE_WEEK_DAY";
	public static final String ACTION_HIDE_DATE = PACKAGE_NAME + ".ACTION_HIDE_DATE";

	public static enum CELL_TYPE {
		DOT(1), CIRCLE(0), UNAVAILABLE(-1);
		private final int id;
		CELL_TYPE(int id) { this.id = id; }
		public int getValue() { return id; }
	}

}
