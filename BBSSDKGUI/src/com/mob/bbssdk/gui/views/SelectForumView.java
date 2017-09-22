package com.mob.bbssdk.gui.views;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.model.ForumForum;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 选择论坛版块的VIEW
 */
public class SelectForumView extends BaseView {
	private LinearLayout llContainer;
	private OnItemClickListener onItemClickListener;

	public SelectForumView(Context context) {
		super(context);
	}

	public SelectForumView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectForumView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		ScrollView scrollView = new ScrollView(context);
		llContainer = new LinearLayout(context);
		llContainer.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(llContainer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		scrollView.setBackgroundColor(Color.WHITE);
		return scrollView;
	}

	protected RequestLoadingView initLoadingView(Context context) {
		RequestLoadingView requestLoadingView = super.initLoadingView(context);
		requestLoadingView.setEmptyContent(getStringRes("bbs_loading_empty"));
		requestLoadingView.setEmptyTitle("");
		return requestLoadingView;
	}

	public void loadData() {
		setLoadingStatus(RequestLoadingView.LOAD_STATUS_ING);
		BBSSDK.getApi(ForumAPI.class).getForumList(0, false, new APICallback<ArrayList<ForumForum>>() {
			public void onSuccess(API api, int action, ArrayList<ForumForum> result) {
				if (result == null || result.size() == 0) {
					setLoadingStatus(RequestLoadingView.LOAD_STATUS_EMPTY);
					return;
				}
				setLoadingStatus(RequestLoadingView.LOAD_STATUS_SUCCESS);
				for (ForumForum forum : result) {
					if (forum != null && forum.fid > 0) {
						addItem(forum);
					}
				}
			}

			public void onError(API api, int action, int errorCode, Throwable details) {
				setLoadingStatus(RequestLoadingView.LOAD_STATUS_FAILED);
			}
		});
	}

	private void addItem(final ForumForum forum) {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_item_selectsubject"), null);
		GlideImageView imageView = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_list_selectsubject_item_imageView"));
		TextView textView = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_list_selectsubject_item_textView"));
		textView.setText(forum.name);
		if (!TextUtils.isEmpty(forum.forumPic)) {
			imageView.setExecuteRound(ResHelper.dipToPx(getContext(), 5));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		}
		imageView.execute(forum.forumPic, ResHelper.getBitmapRes(getContext(), "bbs_selectsubject_item_default"));
		view.setTag(forum);
		view.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (onItemClickListener != null) {
					Object obj = v.getTag();
					if (obj != null && obj instanceof ForumForum) {
						ForumForum forum = (ForumForum) obj;
						onItemClickListener.onClick(v, forum);
					} else {
						onItemClickListener.onClick(v, null);
					}
				}
			}
		});
		llContainer.addView(view);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		onItemClickListener = listener;
	}

	public interface OnItemClickListener {
		void onClick(View view, ForumForum forum);
	}
}
