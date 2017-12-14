package com.mob.bbssdk.gui.jimu.query.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/** 日期类型 */
public class Date extends Rangable<Calendar> {

	public Date(Calendar value) {
		super(value);
	}

	public static Date valueOf(Calendar value) {
		return new Date(value);
	}

	/** 通过时间戳设置日期，如果value为负数，表示从当前时间往前退value毫秒 */
	public static Date valueOf(long value) {
		Calendar cal = Calendar.getInstance();
		if (value > 0) {
			cal.setTimeInMillis(value);
		} else {
			cal.setTimeInMillis(System.currentTimeMillis() + value);
		}
		return new Date(cal);
	}

	public static Date[] valueOf(Calendar... values) {
		Date[] ret = new Date[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Date(values[i]);
		}
		return ret;
	}

	public static Date[] valueOf(long... values) {
		Date[] ret = new Date[values.length];
		for (int i = 0; i < ret.length; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(values[i]);
			ret[i] = new Date(cal);
		}
		return ret;
	}

	public HashMap<String, Object> value() {
		// {"__type": "Date", "val": "yyyy-MM-dd HH:mm:ss"}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("__type", "Date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("val", sdf.format(value.getTime()));
		return map;
	}
}
