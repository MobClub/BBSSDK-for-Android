package com.mob.bbssdk.theme0.page.forum;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.forum.PageSelectForum;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.tools.utils.ResHelper;

public class Theme0PageSelectForum extends PageSelectForum {
	@Override
	protected void onViewCreated(View contentView) {
		TextView left = titleBar.getLeftTextView();
		Context context = getContext();
		Resources resources = context.getResources();

		left.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_white")));
		left.setAlpha(0.8f);
		Theme0StyleModifier.setTextSize(left, "bbs_theme0_writethread_left_txt_size");
		Theme0StyleModifier.modifyUniformBlueStyle(this);
		super.onViewCreated(contentView);
	}
}
