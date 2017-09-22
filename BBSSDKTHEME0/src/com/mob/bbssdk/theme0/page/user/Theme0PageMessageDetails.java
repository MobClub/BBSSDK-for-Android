package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.misc.PageMessageDetails;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageMessageDetails extends PageMessageDetails {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_messagedetails"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemymsgdetails_title"));
		Theme0StyleModifier.modifyUniformWhiteStyle(this);
	}
}
