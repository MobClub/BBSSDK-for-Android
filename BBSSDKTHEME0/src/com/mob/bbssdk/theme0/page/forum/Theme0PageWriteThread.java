package com.mob.bbssdk.theme0.page.forum;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.tools.utils.ResHelper;

public class Theme0PageWriteThread extends PageWriteThread {
	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_writethread"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		TextView left = titleBar.getLeftTextView();
		TextView right = titleBar.getRightTextView();
		Context context = getContext();
		Resources resources = context.getResources();

		left.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_white")));
		right.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_white")));
		left.setAlpha(0.8f);
		right.setAlpha(0.8f);
		right.setText(getStringRes("theme0_title_confirm"));
		Theme0StyleModifier.setTextSize(left, "bbs_theme0_writethread_left_txt_size");
		Theme0StyleModifier.setTextSize(right, "bbs_theme0_writethread_right_txt_size");
		Theme0StyleModifier.modifyUniformBlueStyle(this);
	}
}
