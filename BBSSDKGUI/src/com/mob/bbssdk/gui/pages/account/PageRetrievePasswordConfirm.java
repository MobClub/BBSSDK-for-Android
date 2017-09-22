package com.mob.bbssdk.gui.pages.account;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;

/**
 * 用户找回密码确认界面
 */
public class PageRetrievePasswordConfirm extends BasePageWithTitle {
	private TextView textViewMail;
	private Button btnBackToLogin;
	private String strMail;
	private String strUser;
	private TextView textViewResend;

	public void initPage(String user, String mail) {
		this.strUser = user;
		this.strMail = mail;
	}

	@Override
	protected void onTitleLeftClick(TitleBar titleBar) {
		super.onTitleLeftClick(titleBar);
		finish();
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_account_retrievepasswordconfirm"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View view) {
		titleBar.setTitle(getStringRes("bbs_pageretrievepasswordconfirm_title"));
		titleBar.setLeftImageResourceDefaultBack();
		textViewMail = (TextView) view.findViewById(getIdRes("bbs_retrievepasswordconfirm_textViewMail"));
		textViewMail.setText(strMail);

		textViewResend = (TextView) view.findViewById(getIdRes("bbs_pageretrievepasswordconfirm_textViewResend"));
		textViewResend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		textViewResend.setTextColor(activity.getResources().getColor(getColorId("bbs_confim_resend")));
		textViewResend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSSDK.getApi(UserAPI.class).forgotPwd(strUser, strMail, false, new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						ToastUtils.showToast(getContext(), getStringRes("bbs_retrievepassword_resend_success"));
					}
					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}
		});

		btnBackToLogin = (Button) view.findViewById(getIdRes("bbs_pageretrievepasswordconfirm_btnBackToLogin"));
		btnBackToLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put(RESULT_FINISH_BOOLEAN, true);
//				setResult(map);
				finish();
			}
		});
	}
}
