package com.mob.bbssdk.gui.pages.misc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.pullrequestview.FollowingsPullRequestView;

public class PageFollowings extends BasePageWithTitle {
	protected FollowingsPullRequestView followingsPullRequestView;
	private Integer nUserID = null;

	public void initPage(Integer userid) {
		this.nUserID = userid;
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_followings"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemyfollowing_title"));
		followingsPullRequestView = (FollowingsPullRequestView) contentView.findViewById(getIdRes("followingPullRequestView"));
		followingsPullRequestView.setUserId(nUserID);
	}
}
