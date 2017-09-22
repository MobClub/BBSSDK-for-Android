package com.mob.bbssdk.gui.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.text.DecimalFormat;

public class CommonUtils {

	public final static String formatFileSize(long fileLength) {
		DecimalFormat df = new DecimalFormat("#.00");
		String formatResultStr;
		if (fileLength < 1024) {
			formatResultStr = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			formatResultStr = df.format((double) fileLength / 1024) + "KB";
		} else if (fileLength < 1073741824) {
			formatResultStr = df.format((double) fileLength / 1048576) + "MB";
		} else {
			formatResultStr = df.format((double) fileLength / 1073741824) + "G";
		}
		return formatResultStr;
	}

	public static String getApplicationName(Context context) {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
	}
}
