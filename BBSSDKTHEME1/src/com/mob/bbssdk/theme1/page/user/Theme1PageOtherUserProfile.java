package com.mob.bbssdk.theme1.page.user;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.utils.statusbar.StatusBarCompat;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.theme1.view.Theme1OtherUserProfilePullRequestView;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

public class Theme1PageOtherUserProfile extends PageOtherUserProfile {
	Theme1OtherUserProfilePullRequestView pullRequestView;
	View viewBackground;
	View viewTitle;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_pageotheruserprofile"), null);
		viewBackground = view.findViewById(getIdRes("viewBackground"));
		pullRequestView = (Theme1OtherUserProfilePullRequestView) view.findViewById(getIdRes("pullRequestView"));
		viewTitle = view.findViewById(getIdRes("viewTitle"));
		pullRequestView.setOnScrollListener(new ForumThreadListView.OnScrollListener() {
			@Override
			public void OnScrolledTo(int y) {
				smoothSwitchStatusBar(y);
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
		titleBar.setVisibility(View.GONE);
		contentView.findViewById(ResHelper.getIdRes(getContext(), "imageViewBack")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pullRequestView.refreshQuiet();
		contentView.setFitsSystemWindows(false);
		smoothSwitchStatusBar(0);
	}

	protected void smoothSwitchStatusBar(int height) {
		if (Build.VERSION.SDK_INT >= 19) {
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewBackground.getLayoutParams();
			layoutParams.height = ScreenUtils.dpToPx(44) + DeviceHelper.getInstance(getContext()).getStatusBarHeight();
			viewBackground.setLayoutParams(layoutParams);
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewTitle.getLayoutParams();
			lp.setMargins(0,DeviceHelper.getInstance(getContext()).getStatusBarHeight(),0,0);
			viewTitle.setLayoutParams(lp);
			if (height > 20) {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),true);
			} else {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),false);
			}
		}
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
