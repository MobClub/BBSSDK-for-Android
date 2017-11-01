package com.mob.bbssdk.gui;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends Activity {

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (GUIManager.getInstance().isPermissionGranted()) {
			//保存已经阅读帖子列表
			ForumThreadHistoryManager.getInstance().saveReaded();
		}
	}

	public void setStatusBarColor(int color) {
		Context context;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			// clear FLAG_TRANSLUCENT_STATUS flag:
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// finally change the color
			window.setStatusBarColor(color);
		}
	}
}
