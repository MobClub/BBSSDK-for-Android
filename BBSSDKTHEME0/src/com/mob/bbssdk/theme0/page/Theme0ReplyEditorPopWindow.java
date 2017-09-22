package com.mob.bbssdk.theme0.page;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.dialog.ReplyEditorPopWindow;
import com.mob.tools.utils.ResHelper;

public class Theme0ReplyEditorPopWindow extends ReplyEditorPopWindow {
	public Theme0ReplyEditorPopWindow(Context context, OnConfirmClickListener listener, OnImgAddClickListener addlistener) {
		super(context, listener, addlistener);
	}

	@Override
	protected View getContentView() {
		return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_reply_editor"), null);
	}
}
