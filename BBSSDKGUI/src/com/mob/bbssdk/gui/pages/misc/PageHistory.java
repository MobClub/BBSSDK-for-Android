package com.mob.bbssdk.gui.pages.misc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.pullrequestview.HistoryPullRequestView;

public class PageHistory extends BasePageWithTitle {
	private HistoryPullRequestView pullRequestView;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_history"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("bbs_theme0_myhistory"));
		pullRequestView = (HistoryPullRequestView) contentView.findViewById(getIdRes("pullRequestView"));
		pullRequestView.performPullingDown(true);
	}
}
