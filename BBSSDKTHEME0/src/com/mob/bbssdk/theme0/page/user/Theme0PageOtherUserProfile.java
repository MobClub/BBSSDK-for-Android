package com.mob.bbssdk.theme0.page.user;


import android.view.View;

import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageOtherUserProfile extends PageOtherUserProfile {

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setLeftImageResource(getDrawableId("bbs_ic_back_white"));
		titleBar.setTitle("");
		Theme0StyleModifier.modifyUniformBlueStyle(this);
	}
}
