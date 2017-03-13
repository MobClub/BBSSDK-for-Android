package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.adapter.ForumThreadItemAdapter;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/** 帖子列表的View */
public class ForumThreadListView extends PullToRequestView {
	private static final int MAX_PAGE_SIZE = 20;
	private int pageSize = 10;
	private long fid;

	private ForumAPI contentAPI;
	private OnItemClickListener itemClickListener;

	public ForumThreadListView(Context context) {
		super(context);
	}

	public ForumThreadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumThreadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		itemClickListener = listener;
	}

	public void loadData(long fid, int pageSize) {
		if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		if (contentAPI == null) {
			contentAPI = BBSSDK.getApi(ForumAPI.class);
		}
		this.fid = fid;
		this.pageSize = pageSize;
		final ForumThreadItemAdapter adapter = new ForumThreadItemAdapter(this) {
			protected void onRequest(int page, final RequestCallback callback) {
				contentAPI.getThreadListByForumId(ForumThreadListView.this.fid, page, ForumThreadListView.this.pageSize, false,
						new APICallback<ArrayList<ForumThread>>() {
					public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
						callback.onFinished(true, (result != null && result.size() == ForumThreadListView.this.pageSize), result);
					}

					public void onError(API api, int action, int errorCode, Throwable details) {
						callback.onFinished(false, false, null);
					}
				});
			}
		};
		ColorDrawable dividerColor = new ColorDrawable(0x00ffffff);
		adapter.getListView().setDivider(dividerColor);
		int dividerHeight = getResources().getDimensionPixelOffset(ResHelper.getResId(getContext(), "dimen", "bbs_forum_thread_list_divider_height"));
		adapter.getListView().setDividerHeight(dividerHeight);
		if(Build.VERSION.SDK_INT > 17) {
			adapter.getListView().addHeaderView(new ViewStub(getContext()));
		}
		adapter.getListView().setHeaderDividersEnabled(true);
		setAdapter(adapter);
		adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				if(Build.VERSION.SDK_INT > 17) {
					position -= 1;
				}
				ForumThread item = adapter.getItem(position);
				if (itemClickListener != null) {
					itemClickListener.onItemClick(position, item);
				}
			}
		});
		performPullingDown(true);
	}

	public interface OnItemClickListener {
		void onItemClick(int position, ForumThread item);
	}
}
