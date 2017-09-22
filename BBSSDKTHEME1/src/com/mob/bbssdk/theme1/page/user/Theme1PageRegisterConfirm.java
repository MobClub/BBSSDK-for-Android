package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.account.PageRegisterConfirm;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;

public class Theme1PageRegisterConfirm extends PageRegisterConfirm {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_registerconfirm"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		Theme1StyleModifier.modifyUniformWhiteStyle(this);
	}
}
