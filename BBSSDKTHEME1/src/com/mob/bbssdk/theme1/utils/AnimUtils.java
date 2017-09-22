package com.mob.bbssdk.theme1.utils;

import android.app.Activity;

import com.mob.tools.utils.ResHelper;

public class AnimUtils {
	public static void playFadeInAnim(Activity activity) {
		if (activity == null) {
			return;
		}
		activity.overridePendingTransition(ResHelper.getAnimRes(activity, "bbs_theme1_anim_fadein"), 0);
	}

	public static void playFadeOutAnim(Activity activity) {
		if (activity == null) {
			return;
		}
		activity.overridePendingTransition(0, ResHelper.getAnimRes(activity, "bbs_theme1_anim_fadeout"));
	}
}
