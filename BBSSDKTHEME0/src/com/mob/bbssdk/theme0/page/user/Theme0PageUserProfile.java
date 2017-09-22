package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.pages.profile.PageTheme0UserProfile;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageUserProfile extends PageTheme0UserProfile {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_userprofile"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);

		titleBar.setRightImageResource(getDrawableId("bbs_theme0_setprofile_msg"));
		titleBar.setLeftImageResource(getDrawableId("bbs_ic_back_white"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofile_title"));
		Theme0StyleModifier.modifyUniformBlueStyle(this);

		layoutUserProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageUserProfileDetails().show(getContext());
			}
		});
		layoutMyFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowings page = BBSViewBuilder.getInstance().buildPageFollowings();
				page.initPage(null);
				page.show(getContext());
			}
		});
		layoutMyFollowers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowers page = BBSViewBuilder.getInstance().buildPageFollowers();
				page.initPage(null);
				page.show(getContext());
			}
		});
		layoutMyFavorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageFavorites().show(getContext());
			}
		});
		layoutMyPosts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPagePosts().show(getContext());
			}
		});
		layoutMyHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageHistory().show(getContext());
			}
		});
	}

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		BBSViewBuilder.getInstance().buildPageMessages().show(getContext());
	}
}
