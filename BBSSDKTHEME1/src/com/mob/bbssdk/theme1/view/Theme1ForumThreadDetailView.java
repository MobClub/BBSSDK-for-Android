package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.ErrorCode;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.utils.SendForumPostManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.FavoriteReturn;
import com.mob.bbssdk.model.ForumPost;
import com.mob.bbssdk.theme0.view.Theme0ForumThreadDetailView;
import com.mob.tools.utils.ResHelper;

public class Theme1ForumThreadDetailView extends Theme0ForumThreadDetailView {
	TextView textViewWriteComment;
	TextView textViewCommentCount;
	TextView textViewLikeCount;
	TextView textViewFavoriteCount;
	ImageView imageViewLike;

	ViewGroup layoutComment;
	ViewGroup layoutLike;
	ViewGroup layoutFavorite;
	ViewGroup layoutBottomPrev;
	private int favcount;
	private int commentcount;
	private int recommentcount;
	private boolean liked = false;

	public Theme1ForumThreadDetailView(Context context) {
		super(context);
	}

	public Theme1ForumThreadDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1ForumThreadDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void loadNativeHtml() {
		webView.loadUrl("file:///android_asset/bbssdk/html1/details/html/index.html");
	}

	@Override
	protected View buildLayoutView(Context context, ViewGroup viewgroup) {
		View view = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_theme1_forumthreadview"), this, false);
		textViewWriteComment = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewWriteComment"));
		imageViewLike = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewLike"));
		layoutComment = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutComment"));
		layoutLike = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutLike"));
		layoutFavorite = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutFavorite"));
		layoutBottomPrev = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutBottomPrev"));
		textViewCommentCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewCommentCount"));
		textViewLikeCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewLikeCount"));
		textViewFavoriteCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFavoriteCount"));

		return view;
	}

	@Override
	protected void OnViewCreated(View contentview) {
		super.OnViewCreated(contentview);
		layoutBottomPrev.setVisibility(GONE);
		textViewWriteComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onReplyClick(null);
			}
		});

		layoutComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageViewGotoComment.performClick();
			}
		});

		layoutLike.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (forumThread == null) {
					return;
				}
				if (liked) {
					ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_theme1_alredylikepost"));
				}
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.recordLikePost(forumThread.fid, forumThread.tid, false, new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_theme1_likepost_success"));
						recommentcount++;
						updateCountView();
						liked = true;
						imageViewLike.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_forumthreadliked"));
						jsInterfaceForumThread.articleLiked(forumThread.fid, forumThread.tid);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						if (errorCode == 405 || errorCode == ErrorCode.SDK_API_ERROR_FORUM_ERROR_TIPS) {
							liked = true;
							imageViewLike.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_forumthreadliked"));
							ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_theme1_alredylikepost"));
						} else {
							ErrorCodeHelper.toastError(getContext(), errorCode, details);
						}
					}
				});
			}
		});

		imageViewUnfavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (forumThread != null) {
					UserAPI api = BBSSDK.getApi(UserAPI.class);
					long favid;
					if (favoriteReturn != null) {
						favid = favoriteReturn.favid;
					} else {
						favid = forumThread.favid;
					}
					if (favid <= 0) {
						ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_unfavoritethread_fail"));
						return;
					}
					api.unfavoritePost(favid, false, new APICallback<Boolean>() {
						@Override
						public void onSuccess(API api, int action, Boolean result) {
							imageViewFavorite.setVisibility(VISIBLE);
							imageViewUnfavorite.setVisibility(GONE);
							ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_unfavoritethread_success"));
							favoriteReturn = null;
							favcount--;
							updateCountView();
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {
							ErrorCodeHelper.toastError(getContext(), errorCode, details);
						}
					});
				}
			}
		});
		imageViewFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (forumThread != null) {
					UserAPI api = BBSSDK.getApi(UserAPI.class);
					api.favoritePost(forumThread.fid, forumThread.tid, false, new APICallback<FavoriteReturn>() {
						@Override
						public void onSuccess(API api, int action, FavoriteReturn result) {
							imageViewFavorite.setVisibility(GONE);
							imageViewUnfavorite.setVisibility(VISIBLE);
							favoriteReturn = result;
							ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_favoritethread_success"));
							favcount++;
							updateCountView();
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {
							ErrorCodeHelper.toastError(getContext(), errorCode, details);
						}
					});
				}
			}
		});
	}

	@Override
	protected void updateSendButton(int status, ForumPost forumPost) {
		super.updateSendButton(status, forumPost);
		if (status == SendForumPostManager.STATUS_SEND_SUCCESS) {
			commentcount++;
			updateCountView();
		}
	}

	@Override
	protected void forumThreadUpdated() {
		super.forumThreadUpdated();
		favcount = forumThread.favtimes;
		commentcount = forumThread.replies;
		recommentcount = forumThread.recommendadd;
		if (favcount < 0) {
			favcount = 0;
		}
		if (commentcount < 0) {
			commentcount = 0;
		}
		if (recommentcount < 0) {
			recommentcount = 0;
		}
		updateCountView();
	}

	protected void updateCountView() {
		textViewFavoriteCount.setText("" + favcount);
		textViewCommentCount.setText("" + commentcount);
		textViewLikeCount.setText("" + recommentcount);
	}
}
