package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;

public class Theme1PageMyFollowers extends BasePageWithTitle {
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_myfollowers"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemyfollowers_title"));
		Theme1StyleModifier.modifyUniformWhiteStyle(this);
	}

	private void update() {
		//use bbs_theme0_followitem to fill the view.
	}
}
