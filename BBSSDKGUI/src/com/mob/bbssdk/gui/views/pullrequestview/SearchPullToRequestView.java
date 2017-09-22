package com.mob.bbssdk.gui.views.pullrequestview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class SearchPullToRequestView extends BBSPullToRequestView<Object> {
	private String strKeywords = "";

	public SearchPullToRequestView(Context context) {
		super(context);
	}

	public SearchPullToRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SearchPullToRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setKeywords(String key) {
		strKeywords = key;
	}

	@Override
	protected void init() {
		super.init();
		setOnRequestListener(new BBSPullToRequestView.OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				if (StringUtils.isEmpty(strKeywords)) {
					ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_searchkey_empty"));
					callback.onFinished(true, false, null);
					return;
				}
				UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
				userAPI.searchPosts(strKeywords, UserAPI.SEARCH_TYPE.THREAD, page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<Object>>() {
					@Override
					public void onSuccess(API api, int action, ArrayList<Object> result) {
						callback.onFinished(true, hasMoreData(result), result);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						callback.onFinished(false, false, null);
					}
				});
			}
		});
	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		View view = ListViewItemBuilder.getInstance().buildListItemView(getItem(position), convertView, parent);
		return view;
	}
}
