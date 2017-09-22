package com.mob.bbssdk.theme0.view;


import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.views.ForumForumView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *  UI0首页的板块页面
 */
public class Theme0ForumForumView extends ForumForumView {
	protected LinearLayout layoutSticktop;

	public Theme0ForumForumView(Context context) {
		super(context);
	}

	public Theme0ForumForumView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0ForumForumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View buildMainLayoutView() {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_forumforum"), null);
		layoutSticktop = (LinearLayout) view.findViewById(ResHelper.getIdRes(getContext(), "layoutSticktop"));
		return view;
	}

	@Override
	protected View buildForumItemView(ForumForum forum, ViewGroup viewGroup) {
		return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_subject_list_item"), viewGroup, false);
	}

	@Override
	protected void refreshStickTopView() {
		calcAndRefreshStickTopView();
		calcAndRefreshForumView();
	}

	private void calcAndRefreshStickTopView() {
		Collections.sort(listStickTop, new Comparator<ForumForum>() {
			@Override
			public int compare(ForumForum lhs, ForumForum rhs) {
				return new Long(lhs.fid - rhs.fid).intValue();
			}
		});
		layoutSticktop.removeAllViews();
		for (int i = 0; i < listStickTop.size(); i++) {
			View view = createSticktopItem(listStickTop.get(i), null);
			layoutSticktop.addView(view);
		}
		if (layoutSticktop.getChildCount() == 0) {
			layoutSticktop.setVisibility(View.GONE);
		} else {
			layoutSticktop.setVisibility(VISIBLE);
		}
	}

	private void calcAndRefreshForumView() {
		ArrayList<ForumForum> listforum = getForumList(listStickTop);
		adapterForum.getDataSet().clear();
		adapterForum.getDataSet().addAll(listforum);
		adapterForum.notifyDataSetChanged();
	}

	protected View createSticktopItem(final ForumForum forum, ViewGroup viewGroup) {
		if (forum == null) {
			return null;
		}
		View view = buildForumItemView(forum, viewGroup);
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(),
					"bbs_theme0_forumforum_sticktopitem"), viewGroup, false);
		}
		GlideImageView aivIcon = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_asyImageView"));
		TextView tvTitle = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewTitle"));
		TextView tvLable = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewLabel"));
		TextView tvDes = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewDes"));
		View viewStick = view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewStick"));
		View viewUnstick = view.findViewById(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewUnstick"));
		viewStick.setVisibility(GONE);
		if (isEditMode) {
			viewUnstick.setVisibility(VISIBLE);
		} else {
			viewUnstick.setVisibility(GONE);
		}
		viewUnstick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removeStickTopItem(forum);
			}
		});

		if (!TextUtils.isEmpty(forum.forumPic)) {
			aivIcon.setExecuteRound(ResHelper.dipToPx(getContext(), 5));
			aivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		}
		aivIcon.execute(forum.forumPic, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
		tvTitle.setText(forum.name);
		if(!StringUtils.isEmpty(forum.description)) {
			tvDes.setText(Html.fromHtml(forum.description));
		}
		view.setTag(forum);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isEditMode) {
					return;
				}
				ForumForum forum = (ForumForum) v.getTag();
				if (forum == null) {
					return;
				}
				PageForumThread page = BBSViewBuilder.getInstance().buildPageForumThread();
				page.initPage(forum);
				page.show(getContext());
			}
		});
		OnForumItemViewCreated(forum, view);
		return view;
	}

	@Override
	protected boolean removeStickTopItem(ForumForum forum) {
		boolean result = super.removeStickTopItem(forum);
		if (result) {
			calcAndRefreshForumView();
		}
		return result;
	}

	@Override
	protected boolean addStickTopItemToView(ForumForum forum) {
		boolean result = super.addStickTopItemToView(forum);
		if (result) {
			calcAndRefreshForumView();
		}
		return result;
	}

	private ArrayList<ForumForum> getForumList(List<ForumForum> list) {
		ArrayList<ForumForum> listforum = new ArrayList<ForumForum>(listForumAll);
		for (ForumForum forum : list) {
			//remove the item from forum list.
			ForumForum founded = null;
			for (int i = 0; i < listforum.size(); i++) {
				if (forum.fid == listforum.get(i).fid) {
					founded = listforum.get(i);
					break;
				}
			}
			if (founded != null) {
				listforum.remove(founded);
			}
		}
		return listforum;
	}

	private void removeFromForumListView(List<ForumForum> list) {
		for (ForumForum forum : list) {
			//remove the item from forum list.
			ForumForum founded = null;
			for (int i = 0; i < listForum.size(); i++) {
				if (forum.fid == listForum.get(i).fid) {
					founded = listForum.get(i);
					break;
				}
			}
			if (founded != null) {
				listForum.remove(founded);
			}
		}
		adapterForum.notifyDataSetChanged();
	}

	private void removeFromForumListView(ForumForum forum) {
		//remove the item from forum list.
		ForumForum founded = null;
		for (int i = 0; i < listForum.size(); i++) {
			if (forum.fid == listForum.get(i).fid) {
				founded = listForum.get(i);
				break;
			}
		}
		if (founded != null) {
			listForum.remove(founded);
		}
		adapterForum.notifyDataSetChanged();
	}
}
