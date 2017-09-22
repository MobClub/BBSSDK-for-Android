package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mob.bbssdk.gui.utils.ImageDownloader;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.theme0.view.Theme0ForumForumView;
import com.mob.bbssdk.theme1.page.Theme1PageForumThread;
import com.mob.tools.utils.ResHelper;

public class Theme1ForumForumView extends Theme0ForumForumView {
	protected ImageView imageViewEditStickTop;
	public Theme1ForumForumView(Context context) {
		super(context);
	}

	public Theme1ForumForumView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1ForumForumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View buildMainLayoutView() {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_forumforum"), null);
		imageViewEditStickTop = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewEditStickTop"));
		imageViewEditStickTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(textViewEditStickTop != null) {
					textViewEditStickTop.performClick();
				}
				if (isEditMode) {
					imageViewEditStickTop.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_editsticktopdone"));
				} else {
					imageViewEditStickTop.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_editsticktop"));

				}
			}
		});
		layoutSticktop = (LinearLayout) view.findViewById(ResHelper.getIdRes(getContext(), "layoutSticktop"));

		return view;
	}

	@Override
	protected View buildForumItemView(ForumForum forum, ViewGroup viewGroup) {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_subject_list_item"), viewGroup, false);
		return view;
	}

	@Override
	protected void OnForumItemViewCreated(final ForumForum forum, View view) {
		super.OnForumItemViewCreated(forum, view);
		GlideImageView glideimageview = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_asyImageView"));
		if(glideimageview != null) {
			glideimageview.setVisibility(GONE);
		}
		ImageView imageViewAvatar = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewAvatar"));
		ImageDownloader.loadCircleImage(forum.forumPic, imageViewAvatar);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Theme1PageForumThread page = new Theme1PageForumThread();
				page.initData(forum);
				page.show(getContext());
			}
		});
	}
}
