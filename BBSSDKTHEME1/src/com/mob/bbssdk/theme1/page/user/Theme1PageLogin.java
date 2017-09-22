package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.account.PageLogin;

public class Theme1PageLogin extends PageLogin {
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_login"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		//不显示titlebar
		titleBar.setVisibility(View.GONE);
		contentView.findViewById(getIdRes("imageViewClose")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//replace the status bar color set by PageLogin.
		setStatusBarColor(BBSViewBuilder.getInstance().getStatusBarColor(getContext()));
	}
}
