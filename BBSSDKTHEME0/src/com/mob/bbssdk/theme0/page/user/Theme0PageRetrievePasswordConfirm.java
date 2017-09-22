package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.account.PageRetrievePasswordConfirm;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageRetrievePasswordConfirm extends PageRetrievePasswordConfirm {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_retrievepasswordconfirm"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setLeftImageResource(getDrawableId("bbs_ic_back_white"));
		Theme0StyleModifier.modifyUniformWhiteStyle(this);
	}

}
