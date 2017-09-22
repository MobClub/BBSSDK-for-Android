package com.mob.bbssdk.gui.views.pullrequestview;


import android.content.Context;
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
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.Follower;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.ResHelper;

import java.util.List;

public class FollowersPullRequestView extends BBSPullToRequestView<Follower> {
	private Integer nUserID = null;

	public FollowersPullRequestView(Context context) {
		super(context);
	}

	public FollowersPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FollowersPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setUserId(Integer userid) {
		this.nUserID = userid;
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.getFollowersList(nUserID, page, nDefaultLoadOnceCount, false, new APICallback<List<Follower>>() {
					@Override
					public void onSuccess(API api, int action, List<Follower> result) {
						callback.onFinished(true, hasMoreData(result), result);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {

					}
				});
			}
		});
	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = ListViewItemBuilder.getInstance().buildFollowerItemView(convertView, parent);
			if(convertView == null) {
				convertView = layoutInflater.inflate(ResHelper.getLayoutRes(getContext(), "bbs_item_follower"), parent, false);
			}
		}
		GlideImageView aivAvatar = (GlideImageView) convertView.findViewById(ResHelper.getIdRes(getContext(), "aivAvatar"));
		TextView textViewName = (TextView) convertView.findViewById(ResHelper.getIdRes(getContext(), "textViewName"));
		final ImageView imageViewFollow = (ImageView) convertView.findViewById(ResHelper.getIdRes(getContext(), "imageViewFollow"));
		final ImageView imageViewUnfollow = (ImageView) convertView.findViewById(ResHelper.getIdRes(getContext(), "imageViewUnfollow"));
		final Follower item = getItem(position);
		aivAvatar.setExecuteRound();
		aivAvatar.execute(item.avatar, null);
		textViewName.setText(item.userName);

		if(item.isFollow) {
			imageViewUnfollow.setVisibility(VISIBLE);
			imageViewFollow.setVisibility(GONE);
		} else {
			imageViewUnfollow.setVisibility(GONE);
			imageViewFollow.setVisibility(VISIBLE);
		}
		imageViewUnfollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserAPI userapi = BBSSDK.getApi(UserAPI.class);
				userapi.unfollowUser(item.uid, false, new APICallback<Boolean>() {
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
		imageViewFollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				User current = GUIManager.getCurrentUser();
				if(current != null && item.uid == current.uid) {
					ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_cant_follower_yourself"));
					return;
				}
				UserAPI userapi = BBSSDK.getApi(UserAPI.class);
				userapi.followUser(item.uid, false, new APICallback<Boolean>() {
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
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PageOtherUserProfile page = BBSViewBuilder.getInstance().buildPageOtherUserProfile();
				page.initPage(item.uid);
				page.show(getContext());
			}
		});
		return convertView;
	}
}
