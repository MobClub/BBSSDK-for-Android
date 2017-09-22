package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.account.PageRegister;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageRegister extends PageRegister {
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_register"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setRightImageResource(getDrawableId("bbs_titlebar_close_white"));
		titleBar.setLeftImageResource(getDrawableId("bbs_ic_back_white"));
		Theme0StyleModifier.modifyUniformBlueStyle(this);
	}
}
