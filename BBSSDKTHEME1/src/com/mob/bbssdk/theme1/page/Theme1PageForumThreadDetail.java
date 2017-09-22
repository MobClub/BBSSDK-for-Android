package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.utils.CommonUtils;
import com.mob.bbssdk.gui.utils.ShareUtils;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.theme1.view.Theme1ForumThreadDetailView;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

public class Theme1PageForumThreadDetail extends PageForumThreadDetail {
	TextView textViewTitle;
	ImageView imageViewRepost;

	@Override
	protected View getTitleCenterView() {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_forumthreaddetail_center"), null);
		textViewTitle = (TextView) view.findViewById(getIdRes("textViewTitle"));
		imageViewRepost = (ImageView) view.findViewById(getIdRes("imageViewRepost"));
		return view;
	}

	@Override
	protected View onCreateContentView(Context context) {
		forumThreadDetailView = new Theme1ForumThreadDetailView(context);
		return forumThreadDetailView;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		setStatusBarColor(BBSViewBuilder.getInstance().getStatusBarColor(getContext()));
//		textViewTitle.setText(getStringRes("bbs_thread_details_title"));
		if(GUIManager.isShareEnable) {
			imageViewRepost.setVisibility(View.VISIBLE);
		} else {
			imageViewRepost.setVisibility(View.GONE);
		}
		imageViewRepost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( forumThreadDetailView == null || forumThreadDetailView.getForumThread() == null) {
					return;
				}
				ForumThread thread = forumThreadDetailView.getForumThread();

				String title = (StringUtils.isEmpty(thread.subject) ? "" : thread.subject);
				String text = (StringUtils.isEmpty(thread.summary) ? "" : thread.summary);
				String url = (StringUtils.isEmpty(thread.threadurl) ? "" : thread.threadurl);
				String titleurl = url;
				String image = "";
				if(thread.images != null && thread.images.size() > 0) {
					image = thread.images.get(0);
				}
				String sitename = CommonUtils.getApplicationName(getContext());
				String siteurl = "";
				ShareUtils.startShare(getContext(), title, titleurl, text, image, url, "", sitename, siteurl);
			}
		});
	}
}
