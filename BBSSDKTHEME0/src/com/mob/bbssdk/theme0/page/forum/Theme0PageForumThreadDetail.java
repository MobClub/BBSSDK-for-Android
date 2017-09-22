package com.mob.bbssdk.theme0.page.forum;


import android.content.Context;
import android.view.View;

import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.theme0.view.Theme0ForumThreadDetailView;

public class Theme0PageForumThreadDetail extends PageForumThreadDetail {

	@Override
	protected View onCreateContentView(Context context) {
		forumThreadDetailView = new Theme0ForumThreadDetailView(context);
		return forumThreadDetailView;
	}
}
