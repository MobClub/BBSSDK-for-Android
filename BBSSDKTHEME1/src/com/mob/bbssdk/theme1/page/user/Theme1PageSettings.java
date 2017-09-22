package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;

import java.util.HashMap;

public class Theme1PageSettings extends BasePageWithTitle {
	private TextView textViewCacheSize;
	private ViewGroup layoutClearCache;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_settings"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofiledetails_title"));
		Theme1StyleModifier.modifyUniformWhiteStyle(this);
		textViewCacheSize = (TextView) contentView.findViewById(getIdRes("textViewCacheSize"));
		layoutClearCache = (ViewGroup) contentView.findViewById(getIdRes("layoutClearCache"));

		updateCacheView();
		layoutClearCache.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GUIManager.clearCache();
				ToastUtils.showToast(getContext(), getStringRes("bbs_clearcache_success"));
				updateCacheView();
			}
		});

		contentView.findViewById(getIdRes("btnQuit")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showLoadingDialog();
				GUIManager.logout(getContext(), new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						dismissLoadingDialog();
						HashMap<String, Object> back = new HashMap<String, Object>();
						back.put("logout", true);
						setResult(back);
						finish();
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						dismissLoadingDialog();
					}
				});
			}
		});
	}

	protected void updateCacheView() {
		textViewCacheSize.setText(GUIManager.getCacheSizeText());
	}
}
