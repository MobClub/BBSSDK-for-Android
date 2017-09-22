package com.mob.bbssdk.gui.views.pullrequestview;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.FavoriteThread;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class FavoritesPullRequestView extends BBSPullToRequestView<FavoriteThread> {
	public FavoritesPullRequestView(Context context) {
		super(context);
	}

	public FavoritesPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FavoritesPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.getFavoritePostList(page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<FavoriteThread>>() {
					@Override
					public void onSuccess(API api, int action, ArrayList<FavoriteThread> result) {
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
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		View view = ListViewItemBuilder.getInstance().buildListItemView(getItem(position), convertView, parent);
		final FavoriteThread item = getItem(position);
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(getContext())
						.setTitle(ResHelper.getStringRes(getContext(), "bbs_unfavorite_title"))
						.setMessage(ResHelper.getStringRes(getContext(), "bbs_unfavorite_body"))
						.setPositiveButton(ResHelper.getStringRes(getContext(), "bbs_unfavorite_btn_pos"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								UserAPI api = BBSSDK.getApi(UserAPI.class);
								api.unfavoritePost(item.favid, false, new APICallback<Boolean>() {
									@Override
									public void onSuccess(API api, int action, Boolean result) {
										FavoritesPullRequestView.this.getBasePagedItemAdapter().getDataSet().remove(position);
										FavoritesPullRequestView.this.getBasePagedItemAdapter().notifyDataSetChanged();
										ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_unfavorite_success"));
									}

									@Override
									public void onError(API api, int action, int errorCode, Throwable details) {
										ErrorCodeHelper.toastError(getContext(), errorCode, details);
									}
								});
							}
						})
						.setNegativeButton(ResHelper.getStringRes(getContext(), "bbs_unfavorite_btn_neg"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.show();
				return false;
			}
		});
		Object tag = view.getTag();
		//show right time in this view.
		if (tag != null && tag instanceof ListViewItemBuilder.ThreadViewHolder) {
			ListViewItemBuilder.ThreadViewHolder holder = (ListViewItemBuilder.ThreadViewHolder) tag;
			holder.tvLeftTime.setVisibility(GONE);
			holder.tvRightTime.setVisibility(VISIBLE);
			holder.layoutCommentView.setVisibility(GONE);
		}
		return view;
	}
}
