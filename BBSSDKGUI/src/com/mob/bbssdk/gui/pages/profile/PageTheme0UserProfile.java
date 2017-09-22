package com.mob.bbssdk.gui.pages.profile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.pages.forum.PageImageViewer;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.utils.StringUtils;

public class PageTheme0UserProfile extends BasePageWithTitle {
	protected User userInfo;
	protected UserOperations userOperations;
	protected GlideImageView aivAvatar;
	protected TextView textViewName;
	protected TextView textViewSignature;
	protected TextView textViewLocation;
	protected TextView textViewFollowing;
	protected TextView textViewFollowers;
	protected TextView textViewFavoriteCount;
	protected TextView textViewPostsCount;
	protected Button btnQuit;
	protected View layoutUserProfile;
	protected View layoutMyFollowing;
	protected View layoutMyFollowers;
	protected View layoutMyFavorites;
	protected View layoutMyPosts;
	protected View layoutMyHistory;
	protected TextView textViewHistoryCount;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_theme0userprofile"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setRightImageResource(getDrawableId("bbs_theme0_setprofile_msg"));
		titleBar.setLeftImageResource(getDrawableId("bbs_ic_back_white"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofile_title"));

		layoutUserProfile = contentView.findViewById(getIdRes("layoutUserProfile"));
		layoutMyFollowing = contentView.findViewById(getIdRes("layoutMyFollowing"));
		layoutMyFollowers = contentView.findViewById(getIdRes("layoutMyFollowers"));
		layoutMyFavorites = contentView.findViewById(getIdRes("layoutMyFavorites"));
		layoutMyPosts = contentView.findViewById(getIdRes("layoutMyPosts"));
		layoutMyHistory = contentView.findViewById(getIdRes("layoutMyHistory"));

		aivAvatar = (GlideImageView) contentView.findViewById(getIdRes("aivAvatar"));
		aivAvatar.setExecuteRound();
		aivAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userInfo == null || StringUtils.isEmpty(userInfo.avatar)) {
					return;
				}
				PageImageViewer page = BBSViewBuilder.getInstance().buildPageImageViewer();
				String[] array = new String[1];
				array[0] = userInfo.avatar;
				page.setImageUrlsAndIndex(array, 0);
				page.show(getContext());
			}
		});
		textViewName = (TextView) contentView.findViewById(getIdRes("textViewName"));
		textViewSignature = (TextView) contentView.findViewById(getIdRes("textViewSignature"));
		textViewLocation = (TextView) contentView.findViewById(getIdRes("textViewLocation"));
		textViewFollowing = (TextView) contentView.findViewById(getIdRes("textViewFollowing"));
		textViewFollowers = (TextView) contentView.findViewById(getIdRes("textViewFollowers"));
		textViewFavoriteCount = (TextView) contentView.findViewById(getIdRes("textViewFavoriteCount"));
		textViewPostsCount = (TextView) contentView.findViewById(getIdRes("textViewPostsCount"));
		textViewHistoryCount = (TextView) contentView.findViewById(getIdRes("textViewHistoryCount"));
		btnQuit = (Button) contentView.findViewById(getIdRes("btnQuit"));
		btnQuit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showLoadingDialog();
				GUIManager.logout(getContext(), new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						dismissLoadingDialog();
						finish();
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						dismissLoadingDialog();
					}
				});
			}
		});
		User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			finish();
		}
		updateUserInfo(user);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateInfoFromServer();
	}

	protected void updateUserInfo(User user) {
		userInfo = user;
		aivAvatar.setImageBitmap(GUIManager.getInstance().getCurrentUserAvatar());
		textViewName.setText(userInfo.userName);
		textViewSignature.setText(userInfo.sightml);
		textViewLocation.setText(DataConverterHelper.getLocationText(userInfo));
		textViewHistoryCount.setText("" + ForumThreadHistoryManager.getInstance().getReadedThreadCount());
	}

	protected void updateInfoFromServer() {
		User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			finish();
			return;
		}
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		api.getUserOperations(null, false, new APICallback<UserOperations>() {
			@Override
			public void onSuccess(API api, int action, UserOperations result) {
				userOperations = result;
				textViewFollowing.setText("" + userOperations.firends);
				textViewFollowers.setText("" + userOperations.followers);
				textViewFavoriteCount.setText("" + userOperations.favorites);
				textViewPostsCount.setText("" + userOperations.threads);
				if(userOperations.notices > 0) {
					titleBar.setRightImageResource(getDrawableId("bbs_theme0_setprofile_msgunread"));
				} else {
					titleBar.setRightImageResource(getDrawableId("bbs_theme0_setprofile_msg"));
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});

		api.getUserInfo(user.userName, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				updateUserInfo(result);
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {

			}
		});
	}

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		BBSViewBuilder.getInstance().buildPageMessages().show(getContext());
	}
}
