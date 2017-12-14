package com.mob.bbssdk.gui.pages;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.dialog.ModelLoadingDialog;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 所有界面基类，带主题的沉浸式风格
 */
public abstract class BasePage extends FakeActivity {
	public static ArrayList<BasePage> pageList;
	private ModelLoadingDialog modelLoadingDialog;
	private Integer nInitStatusBarColor = null;
	private boolean isResuming = false;
	private BroadcastReceiver loginoutBroadcastReceiver;
	//true login, false logout, null status not changed.
	private Boolean loginout = null;

	public Activity getActivity() {
		return activity;
	}

	public void onCreate() {
		if (pageList == null) {
			pageList = new ArrayList<BasePage>();
		}
		pageList.add(this);

		activity.setTheme(ResHelper.getStyleRes(activity, "BBS_AppTheme"));

		Window window = activity.getWindow();
		if (isFullScreen() && Build.VERSION.SDK_INT >= 11) {
			window.getDecorView().setSystemUiVisibility(0x00000100  //SYSTEM_UI_FLAG_LAYOUT_STABLE
					| 0x00000001                                    //SYSTEM_UI_FLAG_LOW_PROFILE
					| 0x00000200                                    //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| 0x00000400                                    //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| 0x00000004                                    //SYSTEM_UI_FLAG_FULLSCREEN
					| 0x00000002                                    //SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| 0x00001000);                                  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY //在导航栏或状态栏区域上滑或下滑时出现导航栏和状态栏，一段时间后自动隐藏
		} else if (Build.VERSION.SDK_INT >= 21) {
			window.clearFlags(0x04000000                                  // LayoutParams.FLAG_TRANSLUCENT_STATUS
					| 0x08000000);                                        // LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
			window.getDecorView().setSystemUiVisibility(0x00000400        // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| 0x00000200                                          // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| 0x00000100);                                        // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			window.addFlags(0x80000000);                                  // LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
			if (nInitStatusBarColor != null) {
				setStatusBarColor(nInitStatusBarColor);
			} else {
				int statusBarColor = getStatusBarColor();
				if (statusBarColor != 0) {
					setStatusBarColor(statusBarColor);
				}
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
		registerLoginoutBroadCastReciever();
	}

	public void initStatusBarColor(int color) {
		nInitStatusBarColor = color;
	}

	public void setStatusBarColor(int color) {
		Context context;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			// clear FLAG_TRANSLUCENT_STATUS flag:
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// finally change the color
			if (Color.WHITE == color) {
				if(Build.VERSION.SDK_INT < 23) {
					color = Color.parseColor("#ededef");
				}
				window.clearFlags(0x04000000                                  // LayoutParams.FLAG_TRANSLUCENT_STATUS
						| 0x08000000);                                        // LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
				window.getDecorView().setSystemUiVisibility(0x00000400        // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| 0x00000200                                          // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| 0x00000100
						| 0x00002000);                                        // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				window.addFlags(0x80000000);
			}
			window.setStatusBarColor(color);
		}
	}

	protected int getStatusBarColor() {
		return BBSViewBuilder.getInstance().getStatusBarColor(getContext());
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
		isResuming = false;
		DeviceHelper.getInstance(activity).hideSoftInput(activity.getWindow().getDecorView());
	}

	public void onDestroy() {
		super.onDestroy();
		pageList.remove(this);
		unregisterLoginoutBroadcastReceiver();
	}

	public String getStringRes(String name) {
		if (TextUtils.isEmpty(name)) {
			return "";
		}
		return getContext().getString(ResHelper.getStringRes(getContext(), name));
	}

	public Integer getDrawableId(String name) {
		int resid = ResHelper.getBitmapRes(getContext(), name);
		return resid;
	}

	public Integer getColorId(String name) {
		return ResHelper.getColorRes(getContext(), name);
	}

	public Integer getLayoutId(String name) {
		return ResHelper.getLayoutRes(getContext(), name);
	}

	public int getIdRes(String name) {
		return ResHelper.getIdRes(getContext(), name);
	}

	public void toastStringRes(String name) {
		if (TextUtils.isEmpty(name)) {
			return;
		}
		ToastUtils.showToast(getContext(), getStringRes(name));
	}

	protected void showLoadingDialog() {
		if (modelLoadingDialog != null && modelLoadingDialog.isShowing()) {
			return;
		}
		modelLoadingDialog = new ModelLoadingDialog(getContext());
		modelLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (modelLoadingDialog == null) {
			return;
		}
		modelLoadingDialog.dismiss();
	}

	@Override
	public void onResume() {
		isResuming = true;
		super.onResume();
		if (loginout != null) {
			onLoginoutRefresh(loginout);
			//clear the status after processed.
			loginout = null;
		}
	}

	protected void onLoginoutRefresh(Boolean loginout) {

	}

	private void registerLoginoutBroadCastReciever() {
		if (loginoutBroadcastReceiver == null) {
			loginoutBroadcastReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					if (intent.getAction().equals(GUIManager.BROADCAST_LOGIN)) {
						loginout = true;
						if(isResuming) {
							onLoginoutRefresh(loginout);
						}
					} else if (intent.getAction().equals(GUIManager.BROADCAST_LOGOUT)) {
						loginout = false;
						if(isResuming) {
							onLoginoutRefresh(loginout);
						}
					}
				}
			};
		}
		getContext().registerReceiver(loginoutBroadcastReceiver, new IntentFilter(GUIManager.BROADCAST_LOGIN));
		getContext().registerReceiver(loginoutBroadcastReceiver, new IntentFilter(GUIManager.BROADCAST_LOGOUT));
	}

	private void unregisterLoginoutBroadcastReceiver() {
		if (loginoutBroadcastReceiver != null) {
			getContext().unregisterReceiver(loginoutBroadcastReceiver);
		}
	}
}
