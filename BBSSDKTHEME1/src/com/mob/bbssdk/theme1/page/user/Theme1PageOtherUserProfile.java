package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;
import com.mob.bbssdk.theme1.view.Theme1OtherUserProfilePullRequestView;
import com.mob.tools.utils.ResHelper;

public class Theme1PageOtherUserProfile extends PageOtherUserProfile {
	Theme1OtherUserProfilePullRequestView pullRequestView;
	View viewBackground;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_pageotheruserprofile"), null);
		viewBackground = view.findViewById(getIdRes("viewBackground"));
		pullRequestView = (Theme1OtherUserProfilePullRequestView) view.findViewById(getIdRes("pullRequestView"));
		pullRequestView.setOnScrollListener(new ForumThreadListView.OnScrollListener() {
			@Override
			public void OnScrolledTo(int y) {
				BBSPullToRequestView.setAlphaByScrollY(viewBackground, y, ScreenUtils.dpToPx(100));
			}
		});
		if (userOther != null && userOther.uid >= 0) {
			pullRequestView.initPage(userOther.uid);
		} else {
			pullRequestView.initPage(nUid);
		}
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		setStatusBarColor(BBSViewBuilder.getInstance().getStatusBarColor(getContext()));
		titleBar.setVisibility(View.GONE);
		contentView.findViewById(ResHelper.getIdRes(getContext(), "imageViewBack")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Theme1StyleModifier.modifyUniformWhiteStyle(this);
		pullRequestView.refreshQuiet();
	}

	@Override
	protected void onLoginoutRefresh(Boolean loginout) {
		super.onLoginoutRefresh(loginout);
		if(loginout) {
			pullRequestView.refreshQuiet();
		} else {
			finish();
		}
	}
}
