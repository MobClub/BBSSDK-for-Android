package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

public class Theme1PageWriteThread extends PageWriteThread {

	RelativeLayout layoutChooseCat;
	GlideImageView aivForumHeader;
	TextView textViewCate;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_writethread"), null);
		layoutChooseCat = (RelativeLayout) view.findViewById(getIdRes("layoutChooseCat"));
		aivForumHeader = (GlideImageView) view.findViewById(getIdRes("aivForumHeader"));
		aivForumHeader.setExecuteRound();
//		aivForumHeader.execute(null, ResHelper.getBitmapRes(getContext(), "bbs_theme1_news"));

		textViewCate = (TextView) view.findViewById(getIdRes("textViewCate"));
		layoutChooseCat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ChooseForum();
			}
		});
		return view;
	}

	@Override
	protected void ChooseForum() {
		Theme1PageSelectForum selectforum = new Theme1PageSelectForum();
		selectforum.showForResult(getContext(), new FakeActivity() {
			public void onResult(HashMap<String, Object> data) {
				if (data != null) {
					ForumForum forumForum = ResHelper.forceCast(data.get("ForumForum"));
					if (forumForum != null) {
						if (forumForum.fid < 1) {
							textViewChooseCat.setText(getStringRes("bbs_pagewritethread_choose_category"));
						} else {
							selectedForum = forumForum;
							textViewChooseCat.setText(selectedForum.name);
						}
					}
					OnForumChoosed(forumForum);
				}
			}
		});
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setTvRight(getStringRes("bbs_theme1_writethread_title"));
		int yellow = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_theme1_defaultyellow"));
		titleBar.getRightTextView().setTextColor(yellow);
		int cancelgrey = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_theme1_cancelgrey"));
		titleBar.getLeftTextView().setTextColor(cancelgrey);
		titleBar.setTitle("");
		Theme0StyleModifier.modifyUniformWhiteStyle(this);

	}

	@Override
	protected void restoreCache(Context context) {
		HashMap<String, Object> map = SendForumThreadManager.getThreadCache(context);
		if (selectedForum != null && selectedForum.fid > 0) {
			textViewCate.setText(selectedForum.name);
			aivForumHeader.executeForce(selectedForum.forumPic, ResHelper.getBitmapRes(getContext(), "bbs_theme1_forumdefault"));
		}

		super.restoreCache(context);
	}

	protected void resetUI() {
		super.resetUI();
		textViewChooseCat.setText(getStringRes("bbs_pagewritethread_choose_category"));
		aivForumHeader.execute(null, ResHelper.getBitmapRes(getContext(), "bbs_theme1_forumdefault"));
	}

	@Override
	protected void OnForumChoosed(ForumForum forum) {
		super.OnForumChoosed(forum);
		aivForumHeader.executeForce(forum.forumPic, ResHelper.getBitmapRes(getContext(), "bbs_theme1_forumdefault"));
		textViewCate.setText(forum.name);
	}
}
