package com.mob.bbssdk.gui.pages.misc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.pullrequestview.FavoritesPullRequestView;

public class PageFavorites extends BasePageWithTitle {
	protected FavoritesPullRequestView pullRequestView;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_favorites"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemyfavorites_title"));
		pullRequestView = (FavoritesPullRequestView) contentView.findViewById(getIdRes("pullRequestView"));
	}
}
