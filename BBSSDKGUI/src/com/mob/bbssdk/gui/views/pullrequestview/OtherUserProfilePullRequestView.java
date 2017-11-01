package com.mob.bbssdk.gui.views.pullrequestview;


import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class OtherUserProfilePullRequestView extends BBSPullToRequestView<ForumThread> {

	protected GlideImageView aivAvatar;
	protected TextView textViewName;
	protected TextView textViewSignature;
	protected TextView textViewLocation;
	protected ImageView imageViewLocationMark;
	protected ImageView imageViewFollow;
	protected ImageView imageViewUnfollow;
	protected TextView textViewFollowing;
	protected TextView textViewFollowers;
	protected ViewGroup layoutMyFollowing;
	protected ViewGroup layoutMyFollowers;

	protected Integer nUserID = 0;
	protected UserOperations userOperationsGot;
	protected View viewHeader;

	public OtherUserProfilePullRequestView(Context context) {
		super(context);
	}

	public OtherUserProfilePullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OtherUserProfilePullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void init() {
		super.init();
		getBasePagedItemAdapter().setHasContentHeader(true);
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				final UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.getPersonalPostList(nUserID, page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<ForumThread>>() {
					@Override
					public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
						callback.onFinished(true, hasMoreData(result), result);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {

					}
				});
			}
		});
		setHaveContentHeader(true);
	}


	public void initUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User can't be null!");
		}
		initUser(user.uid);
	}

	public void initUser(final int userid) {
		this.nUserID = userid;
		//Pulling down is only valid when nUserID has been set, because the RequestListener need this variable.
		updateDataFromServer();
		performPullingDown(true);
	}

	public void updateDataFromServer() {
		if (nUserID <= 0) {
			throw new IllegalArgumentException("Illegal user arguments! nUserID: " + nUserID);
		}
		final UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
		userAPI.getUserOperations(nUserID, false, new APICallback<UserOperations>() {
			@Override
			public void onSuccess(API api, int action, UserOperations result) {
				OtherUserProfilePullRequestView.this.userOperationsGot = result;
				update();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void update() {
		if (userOperationsGot != null && viewHeader != null) {
			textViewFollowing.setText("" + userOperationsGot.firends);
			textViewFollowers.setText("" + userOperationsGot.followers);

			User user = userOperationsGot.userInfo;
			if (user != null) {
				aivAvatar.execute(user.avatar, null);
				textViewName.setText(user.userName);
				textViewSignature.setText(user.sightml == null ? "" : Html.fromHtml(user.sightml));
				textViewLocation.setText(DataConverterHelper.getShortLoationText(user));
				if(StringUtils.isEmpty(textViewLocation.getText().toString().trim())) {
					imageViewLocationMark.setVisibility(INVISIBLE);
				} else {
					imageViewLocationMark.setVisibility(VISIBLE);
				}
				if(user.follow) {
					imageViewUnfollow.setVisibility(VISIBLE);
					imageViewFollow.setVisibility(GONE);
				} else {
					imageViewUnfollow.setVisibility(GONE);
					imageViewFollow.setVisibility(VISIBLE);
				}
			}
		}
	}

	public Integer getLayoutId() {
		return BBSViewBuilder.getInstance().getOtherUserProfilePullRequestViewLayout(getContext());
	}

	@Override
	protected View getContentHeader(ViewGroup viewGroup, View viewprev) {
		View view = viewprev;
		if(view == null) {
			Integer layoutid = getLayoutId();
			if (layoutid == null) {
				layoutid = ResHelper.getLayoutRes(getContext(), "bbs_layout_otheruserprofile");
			}
			view = layoutInflater.inflate(layoutid, viewGroup, false);
		}
		aivAvatar = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "aivAvatar"));
		aivAvatar.setExecuteRound();
		textViewName = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewName"));
		imageViewLocationMark = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewLocationMark"));
		textViewSignature = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewSignature"));
		textViewLocation = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewLocation"));
		imageViewFollow = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewFollow"));
		imageViewUnfollow = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewUnfollow"));
		textViewFollowing = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowing"));
		textViewFollowers = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowers"));
		layoutMyFollowing = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowing"));
		layoutMyFollowers = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowers"));

		layoutMyFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowings page = BBSViewBuilder.getInstance().buildPageFollowings();
				page.initPage(nUserID);
				page.show(getContext());
			}
		});
		layoutMyFollowers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageFollowers page = BBSViewBuilder.getInstance().buildPageFollowers();
				page.initPage(nUserID);
				page.show(getContext());
			}
		});


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
		viewHeader = view;
		update();
		return view;
	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		View view = ListViewItemBuilder.getInstance().buildListItemView(getItem(position), convertView, parent);
		ListViewItemBuilder.ThreadViewHolder viewholder = (ListViewItemBuilder.ThreadViewHolder) view.getTag();
		viewholder.layoutHeader.setVisibility(GONE);
		viewholder.tvSubject.setVisibility(GONE);
		return view;
	}
}
