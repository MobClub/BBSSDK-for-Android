package com.mob.bbssdk.theme0.page.forum;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mob.bbssdk.gui.datadef.ThreadListOrderType;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.tools.utils.ResHelper;

public class Theme0PageForumThread extends PageForumThread {
	private TextView textViewLabelArrangeByReply;
	private TextView textViewLabelArrangeByPost;

	@Override
	protected View buildMainView() {
		return LayoutInflater.from(getContext()).inflate(getLayoutId("bbs_theme0_forumsubjectthread"), null);
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		Theme0StyleModifier.modifyUniformWhiteStyle(this);
		titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme0_title_writepostblack"));

		textViewLabelArrangeByReply = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewLabelArrangeByReply"));
		textViewLabelArrangeByPost = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewLabelArrangeByPost"));
	}

	@Override
	protected void showListMenu() {
		super.showListMenu();
		int n = forumThreadView.getCurrentPagePos();
		if(n < 0 || n >= threadListOrderTypeCurrent.length) {
			return;
		}
		ThreadListOrderType type = threadListOrderTypeCurrent[forumThreadView.getCurrentPagePos()];
		if(type == null) {
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByReply, "bbs_theme0_forumthread_titleunselected");
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByPost, "bbs_theme0_forumthread_titleunselected");
		} else if(type == ThreadListOrderType.LAST_POST){
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByReply, "bbs_theme0_forumthread_titleselected");
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByPost, "bbs_theme0_forumthread_titleunselected");
		} else if(type == ThreadListOrderType.CREATE_ON) {
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByReply, "bbs_theme0_forumthread_titleunselected");
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByPost, "bbs_theme0_forumthread_titleselected");
		} else {
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByReply, "bbs_theme0_forumthread_titleunselected");
			Theme0StyleModifier.setTextColor(textViewLabelArrangeByPost, "bbs_theme0_forumthread_titleunselected");
		}
	}
}
