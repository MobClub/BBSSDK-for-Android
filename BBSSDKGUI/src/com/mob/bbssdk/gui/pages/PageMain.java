package com.mob.bbssdk.gui.pages;


import android.content.Context;
import android.view.View;

import com.mob.bbssdk.gui.ForumThreadManager;
import com.mob.bbssdk.gui.views.MainView;

/**
 * 打开app时默认显示的主页面
 */
public class PageMain extends BasePage {
	private MainView mainView;

	protected View onCreateView(Context context) {
		mainView = new MainView(context);
		return mainView;
	}

	protected void onViewCreated(View contentView) {
		mainView.onCreate();
		mainView.loadData();
	}

	public void onPause() {
		super.onPause();
		//保存已读帖子列表
		ForumThreadManager.getInstance(getContext()).saveReaded();
	}

	public void onDestroy() {
		super.onDestroy();
		if (mainView != null) {
			mainView.onDestroy();
		}
	}
}
