package com.mob.bbssdk.gui.utils;


import android.content.Context;

import com.mob.tools.utils.ResHelper;

import java.util.Date;

public class TimeUtils {

	public static String timeDiff(Context context, long time) {
		if (context == null || time <= 0) {
			return "";
		}
		long diffdate = new Date().getTime() - time * 1000;
		int days = (int) Math.floor(diffdate / (24 * 3600 * 1000));
		long leave1 = diffdate % (24 * 3600 * 1000);
		int hours = (int) Math.floor(leave1 / (3600 * 1000));

		long leave2 = leave1 % (3600 * 1000);
		int minutes = (int) Math.floor(leave2 / (60 * 1000));
		long leave3 = leave2 % (60 * 1000);
		int seconds = (int) Math.round(leave3 / 1000);

		String strdaybefore = context.getString(ResHelper.getStringRes(context, "bbs_timediff_daybefore"));
		String strhourbefore = context.getString(ResHelper.getStringRes(context, "bbs_timediff_hourbefore"));
		String strminutebefore = context.getString(ResHelper.getStringRes(context, "bbs_timediff_minutebefore"));
		String strjust = context.getString(ResHelper.getStringRes(context, "bbs_timediff_just"));

		return (days > 0) ? days + strdaybefore : (hours > 0) ? hours + strhourbefore : (minutes > 0) ? minutes + strminutebefore : strjust;
	}
}
