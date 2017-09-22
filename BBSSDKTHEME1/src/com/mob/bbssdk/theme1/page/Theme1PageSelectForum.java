package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.theme1.SelectForumFromSticktopView;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

public class Theme1PageSelectForum extends BasePageWithTitle {
	private SelectForumFromSticktopView selectForumView;

	protected View onCreateContentView(Context context) {
		titleBar.setTvLeft(context.getResources().getString(ResHelper.getStringRes(context, "bbs_cancel")));
		titleBar.setTitle(context.getResources().getString(ResHelper.getStringRes(context, "bbs_selectsubject_title")));

		selectForumView = new SelectForumFromSticktopView(context) {
			@Override
			protected void onItemClick(ForumForum forum) {
				super.onItemClick(forum);
				if (forum != null) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ForumForum", forum);
					setResult(map);
				}
				finish();
			}
		};
		return selectForumView;
	}

	protected void onViewCreated(View contentView) {
		selectForumView.loadData();
	}
}
