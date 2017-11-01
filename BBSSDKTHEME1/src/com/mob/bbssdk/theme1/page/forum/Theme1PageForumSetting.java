package com.mob.bbssdk.theme1.page.forum;


import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.ForumForumView;
import com.mob.bbssdk.theme1.view.Theme1ForumForumView;

public class Theme1PageForumSetting extends BasePageWithTitle {
	ForumForumView forumForumView;
	@Override
	protected View onCreateContentView(Context context) {
		forumForumView = new Theme1ForumForumView(context);
		return forumForumView;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setBackgroundColor(Color.TRANSPARENT);
		titleBar.setLeftImageResourceDefaultBack();
		titleBar.setTitle(getStringRes("bbs_theme1_forumsetting_title"));
		forumForumView.performPullingDown(true);
	}
}
