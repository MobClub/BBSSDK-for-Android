package com.mob.bbssdk.gui.utils.statusbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * After Lollipop use system method.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarCompatLollipop {

	private static ValueAnimator sAnimator;
	/**
	 * return statusBar's Height in pixels
	 */
	private static int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelOffset(resId);
		}
		return result;
	}

	/**
	 * set StatusBarColor
	 * <p>
	 * 1. set Flags to call setStatusBarColor
	 * 2. call setSystemUiVisibility to clear translucentStatusBar's Flag.
	 * 3. set FitsSystemWindows to false
	 */
	static void setStatusBarColor(Activity activity, int statusColor, boolean isLight) {
		Window window = activity.getWindow();

		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(statusColor);
		int options = View.SYSTEM_UI_FLAG_VISIBLE;
		if (isLight) {
			options = options | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
		}
		window.getDecorView().setSystemUiVisibility(options);
		ViewGroup contentview = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
		View childview = contentview.getChildAt(0);
		if (childview != null) {
			childview.setFitsSystemWindows(false);
			childview.requestApplyInsets();
		}
	}

	/**
	 * translucentStatusBar(full-screen)
	 * <p>
	 * 1. set Flags to full-screen
	 * 2. set FitsSystemWindows to false
	 *
	 * @param hideStatusBarBackground hide statusBar's shadow
	 */
	static void translucentStatusBar(Activity activity, boolean isLight, boolean hideStatusBarBackground) {
		Window window = activity.getWindow();

		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		if (hideStatusBarBackground) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(Color.TRANSPARENT);
			int options = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
			if (isLight) {
				options = options | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
			}
			window.getDecorView().setSystemUiVisibility(options);
		} else {
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			int options = View.SYSTEM_UI_FLAG_VISIBLE;
			if (isLight) {
				options = options | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
			}
			window.getDecorView().setSystemUiVisibility(options);
		}

		ViewGroup contentview = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
		View childview = contentview.getChildAt(0);
		if (childview != null) {
			childview.setFitsSystemWindows(false);
			childview.requestApplyInsets();
		}
	}

	/**
	 * use ValueAnimator to change statusBarColor when using collapsingToolbarLayout
	 */
	static void startColorAnimation(int startColor, int endColor, long duration, final Window window) {
		if (sAnimator != null) {
			sAnimator.cancel();
		}
		sAnimator = ValueAnimator.ofArgb(startColor, endColor)
				.setDuration(duration);
		sAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				if (window != null) {
					window.setStatusBarColor((Integer) valueAnimator.getAnimatedValue());
				}
			}
		});
		sAnimator.start();
	}
}
