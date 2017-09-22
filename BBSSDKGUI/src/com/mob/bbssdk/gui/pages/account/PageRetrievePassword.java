package com.mob.bbssdk.gui.pages.account;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.datadef.BBSRegex;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.datadef.PageResult;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.TitleBar;

import java.util.HashMap;

/**
 * 用户找回密码界面
 */
public class PageRetrievePassword extends BasePageWithTitle {
	private EditText editTextMail;
	private EditText editTextUserName;
	private Button btnSubmit;

	@Override
	protected void onTitleLeftClick(TitleBar titleBar) {
		super.onTitleLeftClick(titleBar);
		finish();
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_account_retrievepassword"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View view) {
		titleBar.setTitle(getStringRes("bbs_pageretrievepassword_title"));
		titleBar.setLeftImageResourceDefaultBack();
		editTextMail = (EditText) view.findViewById(getIdRes("bbs_retrievepassword_editTextMail"));
		editTextUserName = (EditText) view.findViewById(getIdRes("bbs_retrievepassword_editTextUserName"));
		btnSubmit = (Button) view.findViewById(getIdRes("bbs_retrievepassword_btnSubmit"));
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				retrievePwd();
			}
		});
	}

	private void retrievePwd() {
		String name = editTextUserName.getText().toString();
		String mail = editTextMail.getText().toString();
		if (TextUtils.isEmpty(name)) {
			toastStringRes("bbs_invalid_username");
			return;
		}
		if (!mail.matches(BBSRegex.EMAIL_REGEX)) {
			toastStringRes("bbs_email_wrongformat");
			return;
		}
		final String strmail = mail;
		final String strusername = name;
		BBSSDK.getApi(UserAPI.class).forgotPwd(strusername, mail, false, new APICallback<Boolean>() {
			@Override
			public void onSuccess(API api, int action, Boolean result) {
				PageRetrievePasswordConfirm page = BBSViewBuilder.getInstance().buildPageRetrievePasswordConfirm();
				page.initPage(strusername, strmail);
				page.show(activity);
				finish();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	@Override
	public void onResult(HashMap<String, Object> data) {
		super.onResult(data);
		if(data != null) {
			Boolean finish = (Boolean) data.get(PageResult.RESULT_FINISH_BOOLEAN);
			if (finish != null && finish) {
				finish();
				return;
			}
		}
	}
}
