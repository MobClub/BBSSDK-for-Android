package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

/** 请求加载的View */
public class RequestLoadingView extends RelativeLayout {
	public static final int LOAD_STATUS_IDLE = 0;
	public static final int LOAD_STATUS_ING = 1;
	public static final int LOAD_STATUS_FAILED = 2;
	public static final int LOAD_STATUS_EMPTY = 3;
	public static final int LOAD_STATUS_SUCCESS = 4;

	private String emptyResStr = null;
	private String emptyContentStr= null;

	private ImageView imageView;
	private TextView tvTitle;
	private TextView tvDesc;
	private ImageView ivRefresh;
	private LoadingView loadingView;
	private OnClickListener onRefreshClickListener;

	public RequestLoadingView(Context context) {
		super(context);
		init(context);
	}

	public RequestLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RequestLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		emptyResStr = getContext().getString(ResHelper.getStringRes(getContext(), "bbs_loadview_emptytitle"));
		emptyContentStr = getContext().getString(ResHelper.getStringRes(getContext(), "bbs_loadview_emptydes"));

		View view = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_view_requestloading"), null);
		imageView = (ImageView) view.findViewById(ResHelper.getIdRes(context, "bbs_thread_loadingview_imageView"));
		tvTitle = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_thread_loadingview_textViewTitle"));
		loadingView = (LoadingView) view.findViewById(ResHelper.getIdRes(context, "bbs_thread_loadingview_loadingView"));
		tvDesc = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_thread_loadingview_textViewDes"));
		ivRefresh = (ImageView) view.findViewById(ResHelper.getIdRes(context, "bbs_thread_loadingview_imageViewRefresh"));
		loadingView.setViewColor(0xff4e9ee4);
		ivRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (onRefreshClickListener != null) {
					onRefreshClickListener.onClick(view);
				}
			}
		});
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		llp.gravity = Gravity.CENTER;
		addView(view, llp);
	}

	public void setEmptyTitle(String str) {
		emptyResStr = str;
	}

	public void setEmptyContent(String str) {
		emptyContentStr = str;
	}

	public void setStatus(int status) {
		try {
			setVisibility(View.VISIBLE);
			if (status == LOAD_STATUS_IDLE || status == LOAD_STATUS_SUCCESS) {
				setVisibility(View.GONE);
				loadingView.stopAnim();
			} else if (status == LOAD_STATUS_ING) {
				imageView.setVisibility(INVISIBLE);
				tvDesc.setVisibility(INVISIBLE);
				ivRefresh.setVisibility(INVISIBLE);
				tvTitle.setVisibility(INVISIBLE);
				loadingView.setVisibility(VISIBLE);
				loadingView.startAnim();
			} else if (status == LOAD_STATUS_FAILED) {
				imageView.setVisibility(VISIBLE);
				tvDesc.setVisibility(VISIBLE);
				tvTitle.setVisibility(VISIBLE);
				ivRefresh.setVisibility(VISIBLE);
				loadingView.setVisibility(GONE);
				loadingView.stopAnim();

				tvTitle.setText(ResHelper.getStringRes(getContext(), "bbs_loadview_nonetworktitle"));
				tvDesc.setText(ResHelper.getStringRes(getContext(), "bbs_loadview_nonetworkdes"));
				imageView.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_thread_loadingview_nonetwork"));
			} else if (status == LOAD_STATUS_EMPTY) {
				// 设置加载数据成功,但是服务器返回数据为空
				imageView.setVisibility(VISIBLE);
				tvDesc.setVisibility(VISIBLE);
				tvTitle.setVisibility(VISIBLE);
				ivRefresh.setVisibility(GONE);
				loadingView.setVisibility(GONE);
				loadingView.stopAnim();

				tvTitle.setText(emptyResStr);
				tvDesc.setText(emptyContentStr);
				imageView.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_thread_loadingview_empty"));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void setOnRetryClickListener(OnClickListener ocl) {
		onRefreshClickListener = ocl;
	}

}
