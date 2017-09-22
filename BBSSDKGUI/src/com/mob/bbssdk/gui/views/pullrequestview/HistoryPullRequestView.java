package com.mob.bbssdk.gui.views.pullrequestview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

import java.util.List;

public class HistoryPullRequestView extends BBSPullToRequestView<ForumThread> {
	public HistoryPullRequestView(Context context) {
		super(context);
	}

	public HistoryPullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HistoryPullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page, BasePagedItemAdapter.RequestCallback callback) {
				//todo to optimize the loading logic.
				List<ForumThread> list = ForumThreadHistoryManager.getInstance().getReadedThread();
				callback.onFinished(true, false, list);
			}
		});
	}

	@Override
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		View view = ListViewItemBuilder.getInstance().buildListItemView(getItem(position), convertView, parent);
		Object tag = view.getTag();
		final ForumThread item = getItem(position);

		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(getContext())
						.setTitle(ResHelper.getStringRes(getContext(), "bbs_delhistory_title"))
						.setMessage(ResHelper.getStringRes(getContext(), "bbs_delhistory_body"))
						.setPositiveButton(ResHelper.getStringRes(getContext(), "bbs_btn_pos"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								ForumThreadHistoryManager.getInstance().removeReadeThread(item);
								HistoryPullRequestView.this.getBasePagedItemAdapter().getDataSet().remove(position);
								HistoryPullRequestView.this.getBasePagedItemAdapter().notifyDataSetChanged();
								ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_delhistory_success"));
							}
						})
						.setNegativeButton(ResHelper.getStringRes(getContext(), "bbs_btn_neg"), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.show();
				return false;
			}
		});
		//show right time in this view.
		if (tag != null && tag instanceof ListViewItemBuilder.ThreadViewHolder) {
			ListViewItemBuilder.ThreadViewHolder holder = (ListViewItemBuilder.ThreadViewHolder) tag;
			holder.tvLeftTime.setVisibility(GONE);
			holder.tvRightTime.setVisibility(VISIBLE);
		}
		return view;
	}
}
