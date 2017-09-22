package com.mob.bbssdk.gui.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mob.bbssdk.gui.BuildConfig;
import com.mob.bbssdk.utils.StringUtils;

public class AppUtils {

	private AppUtils() {
		//cannot be instantiated
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isReleaseVersion() {
		return BuildConfig.BUILD_TYPE.equals("release");
	}

	public static boolean isDebugVersion() {
		return BuildConfig.BUILD_TYPE.equals("debug");
	}

	public static boolean isVersion(String str) {
		if(StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("Not a legal parameter!");
		}
		return BuildConfig.BUILD_TYPE.equals(str);
	}

}
