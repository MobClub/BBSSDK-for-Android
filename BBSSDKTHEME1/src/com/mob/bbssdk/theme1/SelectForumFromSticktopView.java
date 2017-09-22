package com.mob.bbssdk.theme1;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.theme1.view.Theme1ForumForumView;

public class SelectForumFromSticktopView extends Theme1ForumForumView {
	public SelectForumFromSticktopView(Context context) {
		super(context);
	}

	public SelectForumFromSticktopView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectForumFromSticktopView(Context context, AttributeSet attrs, int defStyle) {
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

	@Override
	protected View buildForumItemView(ForumForum forum, ViewGroup viewGroup) {
		View view = super.buildForumItemView(forum, viewGroup);
		//doesn't display all.
		if(forum != null && forum.fid == 0) {
			view.setVisibility(GONE);
		}
		return view;
	}
}
