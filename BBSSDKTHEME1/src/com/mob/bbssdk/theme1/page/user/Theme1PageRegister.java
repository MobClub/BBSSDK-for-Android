package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.account.PageRegister;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;

public class Theme1PageRegister extends PageRegister {
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_register"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setRightImageResource(getDrawableId("bbs_titlebar_close_black"));
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		Theme1StyleModifier.modifyUniformWhiteStyle(this);
	}
}
