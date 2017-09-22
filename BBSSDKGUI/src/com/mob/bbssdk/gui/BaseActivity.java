package com.mob.bbssdk.gui;


import android.app.Activity;

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

}
