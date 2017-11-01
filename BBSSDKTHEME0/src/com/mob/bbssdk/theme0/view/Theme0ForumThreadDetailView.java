package com.mob.bbssdk.theme0.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.utils.CommonUtils;
import com.mob.bbssdk.gui.utils.ShareUtils;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.ForumThreadDetailView;
import com.mob.bbssdk.model.FavoriteReturn;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

public class Theme0ForumThreadDetailView extends ForumThreadDetailView {

	protected ImageView imageViewGotoComment;
	protected ImageView imageViewFavorite;
	protected ImageView imageViewUnfavorite;
	protected ImageView imageViewShare;
	protected FavoriteReturn favoriteReturn;

	public Theme0ForumThreadDetailView(Context context) {
		super(context);
	}

	public Theme0ForumThreadDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0ForumThreadDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		View view = super.initContentView(context, attrs, defStyleAttr);
		return view;
	}

	@Override
	protected void loadNativeHtml() {
		webView.loadUrl("file:///android_asset/bbssdk/html0/details/html/index.html");
	}

	@Override
	protected void forumThreadUpdated() {
		super.forumThreadUpdated();
		updateFavoriteViews();
	}

	protected void updateFavoriteViews() {
		//已经收藏
		if (forumThread.favid > 0) {
			imageViewUnfavorite.setVisibility(VISIBLE);
			imageViewFavorite.setVisibility(GONE);
		}
	}

	protected View buildLayoutView(Context context, ViewGroup viewgroup) {
		return null;
	}

	@Override
	protected View buildContentView(final Context context) {
		View view = buildLayoutView(context, this);
		if(view == null) {
			view = BBSViewBuilder.getInstance().buildForumThreadDetailView(context, this);
		}
		if(view == null) {
			view = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_theme0_forumthreadview"), this, false);
		}
		imageViewGotoComment = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewGotoComment"));
		imageViewFavorite = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewFavorite"));
		imageViewUnfavorite = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewUnfavorite"));
		imageViewShare = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewShare"));
		if(GUIManager.isShareEnable) {
			imageViewShare.setVisibility(View.VISIBLE);
		} else {
			imageViewShare.setVisibility(View.GONE);
		}
		imageViewShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(forumThread == null) {
					return;
				}
				String title = (StringUtils.isEmpty(forumThread.subject) ? "" : forumThread.subject);
				String text = (StringUtils.isEmpty(forumThread.summary) ? "" : forumThread.summary);
				String url = (StringUtils.isEmpty(forumThread.threadurl) ? "" : forumThread.threadurl);
				String titleurl = url;
				String image = "";
				if(forumThread.images != null && forumThread.images.size() > 0) {
					image = forumThread.images.get(0);
				}
				String sitename = CommonUtils.getApplicationName(context);
				String siteurl = "";
				ShareUtils.startShare(getContext(), title, titleurl, text, image, url, "", sitename, siteurl);
			}
		});
		imageViewGotoComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jsInterfaceForumThread.gotoPosts();
			}
		});

		imageViewUnfavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(forumThread != null) {
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
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {
							ErrorCodeHelper.toastError(getContext(), errorCode, details);
						}
					});
				}
			}
		});
		return view;
	}
}
