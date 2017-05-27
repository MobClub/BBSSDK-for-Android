package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;

import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 所有界面基类，带主题的沉浸式风格
 */
public abstract class BasePage extends FakeActivity {
	public static ArrayList<BasePage> pageList;

	public void onCreate() {
		if (pageList == null) {
			pageList = new ArrayList<BasePage>();
		}
		pageList.add(this);

		activity.setTheme(ResHelper.getStyleRes(activity, "BBS_AppTheme"));

		Window window = activity.getWindow();
		if (isFullScreen() && Build.VERSION.SDK_INT >= 11) {
			window.getDecorView().setSystemUiVisibility(0x00000100  //SYSTEM_UI_FLAG_LAYOUT_STABLE
					| 0x00000001									//SYSTEM_UI_FLAG_LOW_PROFILE
					| 0x00000200									//SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| 0x00000400									//SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| 0x00000004									//SYSTEM_UI_FLAG_FULLSCREEN
					| 0x00000002									//SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| 0x00001000);								  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY //在导航栏或状态栏区域上滑或下滑时出现导航栏和状态栏，一段时间后自动隐藏
		} else if (Build.VERSION.SDK_INT >= 21) {
			window.clearFlags(0x04000000								  // LayoutParams.FLAG_TRANSLUCENT_STATUS
					| 0x08000000);										// LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
			window.getDecorView().setSystemUiVisibility(0x00000400		// View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| 0x00000200										  // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| 0x00000100);										// View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			window.addFlags(0x80000000);								  // LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
			int statusBarColor = getStatusBarColor();
			if (statusBarColor != 0) {
				window.setStatusBarColor(statusBarColor);
			}
		}

		View contentView = onCreateView(activity);
		if (contentView != null) {
			if (isUseDefaultBackground() && Build.VERSION.SDK_INT >= 14) {
				contentView.setFitsSystemWindows(true);
			}
			activity.setContentView(contentView);
			onViewCreated(contentView);
		}
	}

	protected int getStatusBarColor() {
		return 0;
	}

	protected abstract View onCreateView(Context context);

	protected abstract void onViewCreated(View contentView);

	protected boolean isFullScreen() {
		return false;
	}

	protected boolean isUseDefaultBackground() {
		return true;
	}

	public void show(Context context) {
		super.show(context, null);
	}

	public void showForResult(Context context, FakeActivity resultReceiver) {
		super.showForResult(context, null, resultReceiver);
	}

	public void onPause() {
		DeviceHelper.getInstance(activity).hideSoftInput(activity.getWindow().getDecorView());
	}

	public void onDestroy() {
		super.onDestroy();
		pageList.remove(this);
	}
}
