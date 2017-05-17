package com.mob.bbssdk.gui;


import android.content.Context;

import com.mob.tools.utils.ResHelper;

public class DataConverter {

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
}
