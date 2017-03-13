package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

/** 请求加载的View */
public class LoadingView extends RelativeLayout {
	public static final int LOAD_STATUS_IDLE = 0;
	public static final int LOAD_STATUS_ING = 1;
	public static final int LOAD_STATUS_FAILED = 2;
	public static final int LOAD_STATUS_EMPTY = 3;
	public static final int LOAD_STATUS_SUCCESS = 4;

	private int loadFailedStrId = 0;
	private int loadingStrId = 0;
	private int loadEmptyStrId = 0;
	private int loadEmptyBitmapId = 0;
	private int loadFailedBitmapId = 0;

	private TextView tvLoad;
	private OnClickListener onRefreshClickListener;

	public LoadingView(Context context) {
		super(context);
		init(context);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		tvLoad = new TextView(context);
		tvLoad.setGravity(Gravity.CENTER);
		tvLoad.setCompoundDrawablePadding(ResHelper.dipToPx(context, 10));
		int tvLoadSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_empty_view_txt_size"));
		tvLoad.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvLoadSize);
		int padding = ResHelper.dipToPx(context, 10);
		tvLoad.setPadding(padding, padding, padding, padding);
		int tvLoadColor = getResources().getColor(ResHelper.getColorRes(context, "bbs_empty_view_txt_color"));
		tvLoad.setTextColor(tvLoadColor);

		LayoutParams rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.addRule(CENTER_IN_PARENT, TRUE);
		addView(tvLoad, rlp);

		tvLoad.setClickable(false);
		tvLoad.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				tvLoad.setClickable(false);
				if (onRefreshClickListener != null) {
					onRefreshClickListener.onClick(view);
				}
			}
		});
	}

	public void setStatus(int status) {
		try {
			setVisibility(View.VISIBLE);
			if (status == LOAD_STATUS_IDLE || status == LOAD_STATUS_SUCCESS) {
				setVisibility(View.GONE);
			} else if (status == LOAD_STATUS_ING) {
				if (loadingStrId == 0) {
					loadingStrId = ResHelper.getStringRes(getContext(), "bbs_loading");
				}
				tvLoad.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvLoad.setText(loadingStrId);
			} else if (status == LOAD_STATUS_FAILED) {
				// 设置加载数据出错了
				tvLoad.setClickable(true);
				if (loadFailedStrId == 0) {
					loadFailedStrId = ResHelper.getStringRes(getContext(), "bbs_loading_failed");
				}
				if (loadFailedBitmapId == 0) {
					loadFailedBitmapId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_net");
				}
				tvLoad.setCompoundDrawablesWithIntrinsicBounds(0, loadFailedBitmapId, 0, 0);
				tvLoad.setText(loadFailedStrId);
			} else if (status == LOAD_STATUS_EMPTY) {
				// 设置加载数据成功,但是服务器返回数据为空
				if (loadEmptyStrId == 0) {
					loadEmptyStrId = ResHelper.getStringRes(getContext(), "bbs_loading_empty");
				}
				if (loadEmptyBitmapId == 0) {
					loadEmptyBitmapId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_data");
				}
				tvLoad.setCompoundDrawablesWithIntrinsicBounds(0, loadEmptyBitmapId, 0, 0);
				tvLoad.setText(loadEmptyStrId);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void setOnRetryClickListener(OnClickListener ocl) {
		onRefreshClickListener = ocl;
	}

	public void setLoadFailedStrId(int loadFailedStrId) {
		this.loadFailedStrId = loadFailedStrId;
	}

	public void setLoadFailedBitmapId(int loadFailedBitmapId) {
		this.loadFailedBitmapId = loadFailedBitmapId;
	}

	public void setLoadingStrId(int loadingStrId) {
		this.loadingStrId = loadingStrId;
	}

	public void setLoadEmptyStrId(int loadEmptyStrId) {
		this.loadEmptyStrId = loadEmptyStrId;
	}

	public void setLoadEmptyBitmapId(int loadEmptyBitmapId) {
		this.loadEmptyBitmapId = loadEmptyBitmapId;
	}
}
