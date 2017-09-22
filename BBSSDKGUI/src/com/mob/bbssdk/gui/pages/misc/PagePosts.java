package com.mob.bbssdk.gui.pages.misc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.pullrequestview.PostsPullRequestView;

public class PagePosts extends BasePageWithTitle {
	protected PostsPullRequestView pullRequestView;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_posts"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemyposts_title"));
		pullRequestView = (PostsPullRequestView) contentView.findViewById(getIdRes("pullRequestView"));
	}
}
