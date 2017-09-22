package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.mob.tools.utils.ResHelper;

/** 基础页面的基类，包含loadingView 和 contentView，可通过重载initContentView和initLoadingView进行页面初始化 */
public abstract class BaseView extends RelativeLayout {
	private View contentView;
	protected RequestLoadingView requestLoadingView;

	public BaseView(Context context) {
		this(context, null);
	}

	public BaseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		contentView = initContentView(context, attrs, defStyleAttr);
		if (contentView != null) {
			addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

		requestLoadingView = initLoadingView(context);
		if (requestLoadingView != null) {
			addView(requestLoadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			requestLoadingView.setOnRetryClickListener(new OnClickListener() {
				public void onClick(View view) {
					setLoadingStatus(RequestLoadingView.LOAD_STATUS_ING);
					loadData();
				}
			});
			setLoadingStatus(RequestLoadingView.LOAD_STATUS_ING);
		}
		OnViewCreated(contentView);
	}

	protected abstract View initContentView(Context context, AttributeSet attrs, int defStyleAttr);

	protected void OnViewCreated(View contentview) {

	}

	protected RequestLoadingView initLoadingView(Context context) {
		return new RequestLoadingView(context);
	}

	public abstract void loadData();

	protected void setLoadingStatus(int loadingStatus) {
		if (requestLoadingView != null) {
			requestLoadingView.setStatus(loadingStatus);
			if (loadingStatus == RequestLoadingView.LOAD_STATUS_ING) {
				if (contentView != null) {
					contentView.setVisibility(View.GONE);
				}
			} else if (requestLoadingView.getVisibility() == View.GONE) {
				if (contentView != null) {
					contentView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	public String getStringRes(String name) {
		if(TextUtils.isEmpty(name)) {
			return "";
		}
		return getContext().getString(ResHelper.getStringRes(getContext(), name));
	}

	public Integer getDrawableId(String name){
		int resid = ResHelper.getBitmapRes(getContext(), name);
		return resid;
	}

	public Integer getLayoutId(String name) {
		return ResHelper.getLayoutRes(getContext(), name);
	}

	public int getIdRes(String name) {
		return ResHelper.getIdRes(getContext(), name);
	}
}
