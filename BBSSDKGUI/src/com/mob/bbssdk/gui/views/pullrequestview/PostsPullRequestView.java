package com.mob.bbssdk.gui.views.pullrequestview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class PostsPullRequestView extends BBSPullToRequestView<ForumThread> {
	public PostsPullRequestView(Context context) {
		super(context);
	}

	public PostsPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PostsPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				User user = BBSViewBuilder.getInstance().ensureLogin(true);
				if (user == null) {
					callback.onFinished(false, false, null);
					return;
				}
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.getPersonalPostList(user.uid,
						page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<ForumThread>>() {
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
	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		final View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(
				getItem(position), convertView, parent
				, ResHelper.getLayoutRes(parent.getContext(), "bbs_item_defaultmythread"));
		Object tag = view.getTag();
		//Doesn't show header layout.
		if (tag instanceof ListViewItemBuilder.ThreadViewHolder) {
			ListViewItemBuilder.ThreadViewHolder holder = (ListViewItemBuilder.ThreadViewHolder) tag;
			holder.layoutHeader.setVisibility(GONE);
			holder.tvLeftTime.setVisibility(GONE);
			holder.tvRightTime.setVisibility(VISIBLE);
		}
		final ForumThread item = getItem(position);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (item != null) {
					PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
					pageForumThreadDetail.setForumThread(item);
					pageForumThreadDetail.show(view.getContext());
				}
			}
		});
		return view;
	}
}
