package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mob.bbssdk.model.ForumForum;

import java.util.ArrayList;
import java.util.List;

public class Theme1SelectForumFromSticktopView extends Theme1ForumForumView {
	public Theme1SelectForumFromSticktopView(Context context) {
		super(context);
	}

	public Theme1SelectForumFromSticktopView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1SelectForumFromSticktopView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init(Context context) {
		super.init(context);
		imageViewEditStickTop.setVisibility(GONE);
	}

	protected void onItemClick(ForumForum forum) {

	}

	@Override
	protected void OnForumItemViewCreated(final ForumForum forum, View view) {
		super.OnForumItemViewCreated(forum, view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClick(forum);
			}
		});
	}

	//移除置顶中的全部板块
	@Override
	protected void calcAndRefreshStickTopView() {
		ForumForum totalforum = null;
		for(ForumForum forum : listStickTop) {
			if(forum.fid == 0) {
				totalforum = forum;
				break;
			}
		}
		if(totalforum != null) {
			listStickTop.remove(totalforum);
		}
		super.calcAndRefreshStickTopView();
	}

	//移除列表中的全部板块
	@Override
	protected ArrayList<ForumForum> getForumList(List<ForumForum> list) {
		ArrayList<ForumForum> forumlist = super.getForumList(list);
		ForumForum totalforum = null;
		for(ForumForum forum : forumlist) {
			if(forum.fid == 0) {
				totalforum = forum;
				break;
			}
		}
		if(totalforum != null) {
			forumlist.remove(totalforum);
		}
		return forumlist;
	}
}
