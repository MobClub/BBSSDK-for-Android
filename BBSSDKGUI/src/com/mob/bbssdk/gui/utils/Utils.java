package com.mob.bbssdk.gui.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public final class Utils {

	@SuppressLint("StaticFieldLeak")
	private static Application sApplication;

	static WeakReference<Activity> sTopActivityWeakRef;
	static List<Activity> sActivityList = new LinkedList<Activity>();

	private static Application.ActivityLifecycleCallbacks callbacks;

	private Utils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化工具类
	 *
	 * @param app 应用
	 */
	public static void init(final Application app) {
		Utils.sApplication = app;
		callbacks  = new Application.ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle bundle) {
				sActivityList.add(activity);
				setTopActivityWeakRef(activity);
			}

			@Override
			public void onActivityStarted(Activity activity) {
				setTopActivityWeakRef(activity);
			}

			@Override
			public void onActivityResumed(Activity activity) {
				setTopActivityWeakRef(activity);
			}

			@Override
			public void onActivityPaused(Activity activity) {

			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				sActivityList.remove(activity);
			}
		};
		app.registerActivityLifecycleCallbacks(callbacks);
	}

	/**
	 * 获取Application
	 *
	 * @return Application
	 */
	public static Application getApp() {
		if (sApplication != null) {
			return sApplication;
		}
		throw new NullPointerException("u should init first");
	}

	private static void setTopActivityWeakRef(Activity activity) {
		if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
			sTopActivityWeakRef = new WeakReference<Activity>(activity);
		}
	}
}
