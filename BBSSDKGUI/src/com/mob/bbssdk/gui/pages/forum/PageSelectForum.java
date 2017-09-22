package com.mob.bbssdk.gui.pages.forum;


import android.content.Context;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.SelectForumView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * 发帖时提示用户选择自版块的页面
 */
public class PageSelectForum extends BasePageWithTitle {
	private SelectForumView selectForumView;
	protected View onCreateContentView(Context context) {
		titleBar.setTvLeft(context.getResources().getString(ResHelper.getStringRes(context, "bbs_cancel")));
		titleBar.setTitle(context.getResources().getString(ResHelper.getStringRes(context, "bbs_selectsubject_title")));

		selectForumView = new SelectForumView(context);
		selectForumView.setOnItemClickListener(new SelectForumView.OnItemClickListener() {
			public void onClick(View view, ForumForum forum) {
				if(forum != null) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ForumForum", forum);
					setResult(map);
				}
				finish();
			}
		});
		return selectForumView;
	}

	protected void onViewCreated(View contentView) {
		selectForumView.loadData();
	}
}
