package com.mob.bbssdk.gui.utils;


import android.content.Context;

import com.mob.tools.utils.ResHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

	public static String timeDiff(Context context, long time) {
		if (context == null || time <= 0) {
			return "";
		}
		time = time * 1000;
		long diffdate = new Date().getTime() - time;
		int days = (int) Math.floor(diffdate / (24 * 3600 * 1000));

		if (days == 0) {//一天内显示多少小时前和分钟前。
			String strhourbefore = context.getString(ResHelper.getStringRes(context, "bbs_timediff_hourbefore"));
			String strminutebefore = context.getString(ResHelper.getStringRes(context, "bbs_timediff_minutebefore"));
			String strjust = context.getString(ResHelper.getStringRes(context, "bbs_timediff_just"));
			long leave1 = diffdate % (24 * 3600 * 1000);
			int hours = (int) Math.floor(leave1 / (3600 * 1000));
			long leave2 = leave1 % (3600 * 1000);
			int minutes = (int) Math.floor(leave2 / (60 * 1000));
			return (hours > 0) ? hours + strhourbefore : (minutes > 0) ? minutes + strminutebefore : strjust;
		} else if (days == 1) {//昨天的显示昨天+具体时间。
			SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);
			format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String stryesterday = context.getString(ResHelper.getStringRes(context, "bbs_timediff_yesterday"));
			return stryesterday + " " + format.format(time);
		} else if (days < 365) {//超过昨天的显示日期+时间。
			SimpleDateFormat format = new SimpleDateFormat("MM-dd' 'HH:mm", Locale.CHINA);
			format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			return format.format(time);
		} else {//超过一年的显示年份+日期+时间
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm", Locale.CHINA);
			format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			return format.format(time);
		}
	}
}
