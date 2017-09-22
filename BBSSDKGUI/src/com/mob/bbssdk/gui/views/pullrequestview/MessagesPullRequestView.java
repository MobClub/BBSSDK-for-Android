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
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.misc.PageMessageDetails;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.Notification;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class MessagesPullRequestView extends BBSPullToRequestView<Notification> {
	public MessagesPullRequestView(Context context) {
		super(context);
	}

	public MessagesPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MessagesPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.getNotificationList(page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<Notification>>() {
					@Override
					public void onSuccess(API api, int action, ArrayList<Notification> result) {
						callback.onFinished(true, hasMoreData(result), result);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {

					}
				});
			}
		});
	}

	protected void showDetails(final Notification item) {
		PageMessageDetails page = BBSViewBuilder.getInstance().buildPageMessageDetails();
		page.setNotification(item);
		page.show(getContext());
	}

	protected void showThreadDetails(final Notification item) {
		if(item.fid <= 0 || item.tid <= 0) {
			ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_message_threadinvalid"));
			return;
		}
		PageForumThreadDetail details = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
		details.setForumThread(item.fid, item.tid, item.author);
		details.show(getContext());
	}

	protected void showOtherUserProfile(final Notification item) {
		PageOtherUserProfile profile = BBSViewBuilder.getInstance().buildPageOtherUserProfile();
		profile.initPage(item.authorid);
		profile.show(getContext());
	}

	@Override
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		final Notification item = getItem(position);
		final View view = ListViewItemBuilder.getInstance().buildMessageView(item, convertView, parent);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (item.getType()) {
					case COMMENT: {
						showThreadDetails(item);
					} break;
					case SYSTEM: {
						showDetails(item);
					} break;
					case MOB_LIKE: {
						showThreadDetails(item);
					} break;
					case MOB_FOLLOW: {
						showOtherUserProfile(item);
					} break;
					case MOB_NOTICE: {
						showDetails(item);
					} break;
				}
				//set notification read.
				if(item.isnew > 0) {
					UserAPI api = BBSSDK.getApi(UserAPI.class);
					api.setNotificationReaded(item.noid, false, new APICallback<Boolean>() {
						@Override
						public void onSuccess(API api, int action, Boolean result) {
							ListViewItemBuilder.MessageViewHolder viewholder = (ListViewItemBuilder.MessageViewHolder) view.getTag();
							viewholder.viewUnreadedMark.setVisibility(GONE);
							item.isnew = 0;
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {

						}
					});
				}
			}
		});
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(getContext())
						.setTitle(ResHelper.getStringRes(getContext(), "bbs_delmessage_title"))
						.setMessage(ResHelper.getStringRes(getContext(), "bbs_delmessage_body"))
						.setPositiveButton(ResHelper.getStringRes(getContext(), "bbs_delmessage_btn_pos"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								UserAPI api = BBSSDK.getApi(UserAPI.class);
								api.delNotification(item.noid, false, new APICallback<Boolean>() {
									@Override
									public void onSuccess(API api, int action, Boolean result) {
										MessagesPullRequestView.this.getBasePagedItemAdapter().getDataSet().remove(position);
										MessagesPullRequestView.this.getBasePagedItemAdapter().notifyDataSetChanged();
										ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_delmessage_success"));
									}

									@Override
									public void onError(API api, int action, int errorCode, Throwable details) {
										ErrorCodeHelper.toastError(getContext(), errorCode, details);
									}
								});
							}
						})
						.setNegativeButton(ResHelper.getStringRes(getContext(), "bbs_delmessage_btn_neg"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.show();
				return false;
			}
		});
		return view;
	}
}
