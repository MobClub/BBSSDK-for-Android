package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/** 基础页面的基类，包含loadingView 和 contentView，可通过重载initContentView和initLoadingView进行页面初始化 */
public abstract class BaseView extends RelativeLayout {
	private View contentView;
	private LoadingView loadingView;

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

		loadingView = initLoadingView(context);
		if (loadingView != null) {
			addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			loadingView.setOnRetryClickListener(new OnClickListener() {
				public void onClick(View view) {
					setLoadingStatus(LoadingView.LOAD_STATUS_ING);
					loadData();
				}
			});
			setLoadingStatus(LoadingView.LOAD_STATUS_ING);
		}
	}

	protected abstract View initContentView(Context context, AttributeSet attrs, int defStyleAttr);

	protected LoadingView initLoadingView(Context context) {
		return new LoadingView(context);
	}

	public abstract void loadData();

	protected void setLoadingStatus(int loadingStatus) {
		if (loadingView != null) {
			loadingView.setStatus(loadingStatus);
			if (loadingStatus == LoadingView.LOAD_STATUS_ING) {
				if (contentView != null) {
					contentView.setVisibility(View.GONE);
				}
			} else if (loadingView.getVisibility() == View.GONE) {
				if (contentView != null) {
					contentView.setVisibility(View.VISIBLE);
				}
			}
		}
	}
}
