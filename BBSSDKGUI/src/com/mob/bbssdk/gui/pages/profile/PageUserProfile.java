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
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * 用户资源显示界面
 */
public class PageUserProfile extends BasePageWithTitle {
	private GlideImageView aivAvatar;
	private TextView textViewName;
	private TextView textViewGender;
	private TextView textViewMail;
	private TextView textViewGroup;
	private TextView textViewStatus;
	private Button btnQuit;
	private User userInfo;

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		if (userInfo == null) {
			return;
		}
		PageInitProfile editprofile = BBSViewBuilder.getInstance().buildPageEditProfile();
		editprofile.initPage(userInfo);
		editprofile.showForResult(getContext(), new FakeActivity() {
			public void onResult(HashMap<String, Object> data) {
				super.onResult(data);
				if (data != null) {
					User user = ResHelper.forceCast(data.get("user"));
					if (user != null) {
						userInfo = user;
						updateUserInfo();
					}
				}
			}
		});
	}

	@Override
	protected View onCreateContentView(final Context context) {
		titleBar.setTitle(getStringRes("bbs_pageuserprofile_title"));
		titleBar.setLeftImageResourceDefaultBack();
		titleBar.setTvRight(getStringRes("bbs_pageuserprofile_title_edit"));
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_userprofile"), null);
		aivAvatar = (GlideImageView) view.findViewById(getIdRes("bbs_userprofile_aivAvatar"));
		aivAvatar.setExecuteRound();
		textViewName = (TextView) view.findViewById(getIdRes("bbs_userprofile_textViewName"));
		textViewGender = (TextView) view.findViewById(getIdRes("bbs_userprofile_textViewGender"));
		textViewMail = (TextView) view.findViewById(getIdRes("bbs_userprofile_textViewMail"));
		textViewGroup = (TextView) view.findViewById(getIdRes("bbs_userprofile_textViewGroup"));
		textViewStatus = (TextView) view.findViewById(getIdRes("bbs_userprofile_textViewStatus"));
		btnQuit = (Button) view.findViewById(getIdRes("bbs_userprofile_btnQuit"));
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
		showLoadingDialog();
		BBSSDK.getApi(UserAPI.class).getUserInfo(null, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				dismissLoadingDialog();
				if (result != null) {
					userInfo = result;
					updateUserInfo();
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				dismissLoadingDialog();
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
		return view;
	}

	private void updateUserInfo() {
		if (userInfo != null) {
			aivAvatar.setImageBitmap(GUIManager.getInstance().getCurrentUserAvatar());
			textViewName.setText(userInfo.userName);
			textViewGender.setText(DataConverterHelper.getGenderInfo(activity, userInfo.gender));
			textViewMail.setText(userInfo.email);
			textViewGroup.setText(userInfo.groupName);
			if (userInfo.emailStatus == 0) {
				//未验证
				textViewStatus.setText(getStringRes("bbs_useraacount_unverified"));
			} else if (userInfo.emailStatus == 1) {
				//已验证
				textViewStatus.setText(getStringRes("bbs_useraacount_verified"));
			} else {
				textViewStatus.setText("");
			}
		}
	}

	@Override
	protected void onViewCreated(View contentView) {

	}
}
