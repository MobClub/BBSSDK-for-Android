package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.util.AttributeSet;

import com.mob.bbssdk.gui.views.pullrequestview.MessagesPullRequestView;
import com.mob.tools.utils.ResHelper;

public class Theme1MessagesPullRequestView extends MessagesPullRequestView {
	public Theme1MessagesPullRequestView(Context context) {
		super(context);
	}

	public Theme1MessagesPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1MessagesPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected Integer getEmptyViewDrawableId() {
		return ResHelper.getBitmapRes(getContext(), "bbs_theme1_pullrequestview_nomessage");
	}

	@Override
	protected Integer getEmptyViewStrId() {
		return ResHelper.getStringRes(getContext(), "bbs_theme1_pullrequestview_nomessage");
	}
}
