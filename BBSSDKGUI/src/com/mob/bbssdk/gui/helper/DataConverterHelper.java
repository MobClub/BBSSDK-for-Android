package com.mob.bbssdk.gui.helper;


import android.content.Context;

import com.mob.bbssdk.model.User;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.Locale;

public class DataConverterHelper {

	public static String getGenderInfo(Context context, User user) {
		if (context == null) {
			throw new IllegalArgumentException("context can't be null!");
		}
		return getGenderInfo(context, user.gender);
	}

	public static String getGenderInfo(Context context, int gender) {
		if (context == null) {
			throw new IllegalArgumentException("context can't be null!");
		}
		if (gender == 0) {
			return context.getString(ResHelper.getStringRes(context, "bbs_userprofile_gender_secret"));
		} else if (gender == 1) {
			return context.getString(ResHelper.getStringRes(context, "bbs_userprofile_gender_male"));
		} else if (gender == 2) {
			return context.getString(ResHelper.getStringRes(context, "bbs_userprofile_gender_female"));
		} else {
			return "";
		}
	}

	public static String getLocationText(User user) {
		String strlocation = user.resideprovince + " " + user.residecity
				+ " " + user.residedist + " " + user.residecommunity + " " + user.residesuite;
		return strlocation;
	}

	public static String getShortLoationText(User user) {
		return buildShortLocatinText(user.resideprovince, user.residecity, user.residedist);
	}

	public static String buildShortLocatinText(String province, String city, String dist) {
		if (StringUtils.isEmpty(province)) {
			return "";
		} else if (StringUtils.isEmpty(city)) {
			return province;
		} else if (StringUtils.isEmpty(dist)) {
			return province + " " + city;
		} else {
			return province + " " + city + " " + dist;
		}
	}

	public static String getEmailStatusText(Context context, User user) {
		if (user.emailStatus == 1) {
			return context.getString(ResHelper.getStringRes(context, "bbs_userprofile_verified"));
		} else {
			return context.getString(ResHelper.getStringRes(context, "bbs_userprofile_unverified"));
		}
	}

	public static String getBirthday(User user) {
		if (user.birthyear <= 0 || user.birthmonth <= 0 || user.birthday <= 0) {
			return null;
		}
		String month = String.format(Locale.CHINA, "%02d", user.birthmonth);
		String day = String.format(Locale.CHINA, "%02d", user.birthday);
		return "" + user.birthyear + "-" + month + "-" + day;
	}
}
