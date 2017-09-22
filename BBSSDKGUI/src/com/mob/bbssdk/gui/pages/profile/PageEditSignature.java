package com.mob.bbssdk.gui.pages.profile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;

public class PageEditSignature extends BasePageWithTitle {
	protected EditText editTextSignature;
	protected String strSig;

	public void initPage(String sig) {
		if(sig == null) {
			sig = "";
		}
		this.strSig = sig.trim();
	}
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_editsignature"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pageeditsignature_title"));

		editTextSignature = (EditText) contentView.findViewById(getIdRes("editTextSignature"));
		editTextSignature.setText(strSig);
	}

	@Override
	protected void onTitleLeftClick(TitleBar titleBar) {
		String sig = editTextSignature.getText().toString().trim();
		if(!strSig.equals(sig)) {
			update(true);
		}
		finish();
	}

	protected void update(final boolean needfinish) {
		showLoadingDialog();
		String sig = editTextSignature.getText().toString();
		BBSSDK.getApi(UserAPI.class).updateUserInfo(null, null, sig, null,
				null, false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						ToastUtils.showToast(getContext(), getStringRes("bbs_editsignature_submitsuccess"));
						dismissLoadingDialog();
						if(needfinish) {
							finish();
						}
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastErrorCode(getContext(), errorCode);
						dismissLoadingDialog();
						if(needfinish) {
							finish();
						}
					}
				});
	}
}
