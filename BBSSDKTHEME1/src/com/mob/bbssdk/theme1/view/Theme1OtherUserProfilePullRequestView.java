package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.mob.MobSDK;
import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageImageViewer;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;

public class Theme1OtherUserProfilePullRequestView extends BBSPullToRequestView<ForumThread> {
	public Integer nUserID = 0;
	protected GlideImageView aivAvatar;
	protected ViewGroup layoutMyFollowing;
	protected ViewGroup layoutMyFollowers;
	protected TextView textViewName;
	protected TextView textViewLocation;
	protected TextView textViewSignature;
	protected TextView textViewFollowing;
	protected TextView textViewFollowers;
	ImageView imageViewLocationMark;
	ImageView imageViewFollow;
	ImageView imageViewUnfollow;
	ImageView imageViewBlur;
	private UserOperations userOperations;
	private User userInfo;

	public Theme1OtherUserProfilePullRequestView(Context context) {
		super(context);
	}

	public Theme1OtherUserProfilePullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1OtherUserProfilePullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initPage(Integer uid) {
		this.nUserID = uid;
	}

	@Override
	protected void init() {
		super.init();
		setHaveContentHeader(true);
		setOnRequestListener(new BBSPullToRequestView.OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				if (nUserID == null || nUserID <= 0) {
					return;
				}
				UserAPI userapi = BBSSDK.getApi(UserAPI.class);
				userapi.getPersonalPostList(nUserID, page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<ForumThread>>() {
					@Override
					public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
						callback.onFinished(true, hasMoreData(result), result);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {

					}
				});
				updateInfoFromServer();
			}
		});
	}

	@Override
	protected View getContentHeader(ViewGroup viewGroup, View viewprev) {
		if(viewprev != null) {
			return viewprev;
		}
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_header_otheruserprofile"), viewGroup, false);
		aivAvatar = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "aivAvatar"));
		aivAvatar.setExecuteRound();
		aivAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userInfo == null || StringUtils.isEmpty(userInfo.avatar)) {
					return;
				}
				PageImageViewer page = BBSViewBuilder.getInstance().buildPageImageViewer();
				String[] array = new String[1];
				array[0] = userInfo.avatar;
				page.setImageUrlsAndIndex(array, 0);
				page.show(getContext());
			}
		});

		textViewName = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewName"));
		imageViewLocationMark = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewLocationMark"));
		textViewLocation = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewLocation"));
		textViewSignature = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewSignature"));
		textViewFollowing = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowing"));
		textViewFollowers = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowers"));
		imageViewFollow = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewFollow"));
		imageViewUnfollow = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewUnfollow"));
		imageViewFollow.setVisibility(INVISIBLE);
		imageViewUnfollow.setVisibility(INVISIBLE);
		imageViewFollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				User current = GUIManager.getCurrentUser();
				if(current != null && nUserID == current.uid) {
					ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_cant_follower_yourself"));
					return;
				}
				UserAPI user = BBSSDK.getApi(UserAPI.class);
				user.followUser(nUserID, false, new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						imageViewUnfollow.setVisibility(VISIBLE);
						imageViewFollow.setVisibility(GONE);
						ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_follow_success"));
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}
		});
		imageViewUnfollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserAPI user = BBSSDK.getApi(UserAPI.class);
				user.unfollowUser(nUserID, false, new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						imageViewUnfollow.setVisibility(GONE);
						imageViewFollow.setVisibility(VISIBLE);
						ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_unfollow_success"));
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}
		});
		layoutMyFollowing = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowing"));
		layoutMyFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowings page = BBSViewBuilder.getInstance().buildPageFollowings();
				page.initPage(nUserID);
				page.show(getContext());
			}
		});
		layoutMyFollowers = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowers"));
		layoutMyFollowers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowers page = BBSViewBuilder.getInstance().buildPageFollowers();
				page.initPage(nUserID);
				page.show(getContext());
			}
		});
		imageViewBlur = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewBlur"));
		view.setVisibility(INVISIBLE);
		updateViews(view);
		return view;
	}

	protected void updateViews() {
		updateViews(null);
	}

	protected void updateViews(View view) {
		if(view == null) {
			view = viewHeader;
		}

		if (view != null) {
			if (userInfo != null) {
				textViewName.setText(userInfo.userName);
				textViewLocation.setText(DataConverterHelper.getLocationText(userInfo));
				if(StringUtils.isEmpty(textViewLocation.getText().toString().trim())) {
					imageViewLocationMark.setVisibility(INVISIBLE);
				} else {
					imageViewLocationMark.setVisibility(VISIBLE);
				}
				textViewSignature.setText(userInfo.sightml == null ? "" : Html.fromHtml(userInfo.sightml));
				//set blur background
				if(!StringUtils.isEmpty(userInfo.avatar)) {
					Glide.with(MobSDK.getContext())
							.load(userInfo.avatar)
							.asBitmap()
							.signature(new StringSignature("" + System.currentTimeMillis()))
							.into(new SimpleTarget<Bitmap>() {
								@Override
								public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
									Blurry.with(getContext()).from(resource).into(imageViewBlur);
									aivAvatar.setBitmap(resource);
								}
							});
				} else {
					aivAvatar.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_login_defaultportrait"));
				}
				view.setVisibility(VISIBLE);
			}
			if (userOperations != null) {
				textViewFollowing.setText("" + userOperations.firends);
				textViewFollowers.setText("" + userOperations.followers);

				User user = userOperations.userInfo;
				if (user.follow) {
					imageViewUnfollow.setVisibility(VISIBLE);
					imageViewFollow.setVisibility(GONE);
				} else {
					imageViewUnfollow.setVisibility(GONE);
					imageViewFollow.setVisibility(VISIBLE);
				}
			}
		}
	}

	protected void updateInfoFromServer() {
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		api.getUserOperations(nUserID, false, new APICallback<UserOperations>() {
			@Override
			public void onSuccess(API api, int action, UserOperations result) {
				userOperations = result;
				userInfo = userOperations.userInfo;
				updateViews();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

//todo add the blur effect.
//	@Override
//	protected void update() {
//		super.update();
//		if (userOperationsGot != null && viewHeader != null && userOperationsGot.userInfo != null) {
//			User user = userOperationsGot.userInfo;
//			Glide.with(MobSDK.getContext())
//					.load(user.avatar)
//					.asBitmap()
//					.signature(new StringSignature("" + System.currentTimeMillis()))
//					.into(new SimpleTarget<Bitmap>() {
//						@Override
//						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//							Blurry.with(getContext()).from(resource).into(imageViewBlur);
//						}
//					});
//		}
//	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_otheruserprofile");
		View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(getItem(position), convertView, parent, layout);
		TextView textViewPageLike = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewPageLike"));
		textViewPageLike.setText("" + getItem(position).recommendadd);
		view.findViewById(ResHelper.getIdRes(getContext(), "bbs_item_forumpost_textViewSubject")).setVisibility(GONE);
		final ForumThread thread = getItem(position);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (thread != null) {
					PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
					pageForumThreadDetail.setForumThread(thread);
					pageForumThreadDetail.show(getContext());
				}
			}
		});
		return view;
	}
}
