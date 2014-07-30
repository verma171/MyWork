package com.utils;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Util {
	public static String usingDateFormatter(long input)
	{
	    Date date = new Date(input);
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd hh:mm:ss z");
		sdf.setCalendar(cal);
		cal.setTime(date);
		return sdf.format(date);
    }
	
	public static CharSequence createDate(long timestamp) {
	    Calendar c = Calendar.getInstance();
	    timestamp = timestamp * 1000;
	    c.setTimeInMillis(timestamp);
	    Date d = (Date) c.getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    return sdf.format(d);
	  }
	public static int getDpValue(Context context,int value)
	{
		Resources r = context.getResources();
		int dpvalue = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
	
		return dpvalue;
	}
}
