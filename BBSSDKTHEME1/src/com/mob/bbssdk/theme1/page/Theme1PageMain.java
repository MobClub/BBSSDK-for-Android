package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.view.View;

import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.pages.BasePage;
import com.mob.bbssdk.theme1.view.Theme1MainView;

public class Theme1PageMain extends BasePage {
	private Theme1MainView mainView;

	protected View onCreateView(Context context) {
		mainView = new Theme1MainView(context);
		return mainView;
	}

	protected void onViewCreated(View contentView) {
		mainView.onCreate();
		mainView.loadData();
	}

	public void onPause() {
		super.onPause();
		//保存已读帖子列表
		ForumThreadHistoryManager.getInstance().saveReaded();
	}

	public void onDestroy() {
		super.onDestroy();
		if (mainView != null) {
			mainView.onDestroy();
		}
	}
}
