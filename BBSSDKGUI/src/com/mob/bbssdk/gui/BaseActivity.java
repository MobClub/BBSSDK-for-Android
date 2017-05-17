package com.mob.bbssdk.gui;


import android.app.Activity;

import com.mob.MobSDK;

public class BaseActivity extends Activity {
	@Override
	protected void onPause() {
		super.onPause();
		//保存已经阅读帖子列表
		ForumThreadManager.getInstance(MobSDK.getContext()).saveReaded();
	}
}
