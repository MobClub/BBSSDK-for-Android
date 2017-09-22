package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.forum.PageReportAccusation;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

public class Theme0PageReportAccusation extends PageReportAccusation {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_reportaccusation"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		Theme0StyleModifier.modifyUniformWhiteStyle(this);
	}
}
