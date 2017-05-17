package com.mob.bbssdk.gui;


import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

public class BBSViewBuilder {
	private static final String TAG = "bbsViewBuilder";
	private static BBSViewBuilder bbsViewBuilder;

	public static synchronized void init(BBSViewBuilder viewbuilder) {
		if (bbsViewBuilder != null) {
			throw new IllegalAccessError("You can only init bbsViewBuilder once!");
		}
		if(viewbuilder == null) {
			bbsViewBuilder = new BBSViewBuilder();
		} else {
			bbsViewBuilder = viewbuilder;
		}
	}

	public static BBSViewBuilder getInstance() {
		if (bbsViewBuilder == null) {
			init(null);
		}
		return bbsViewBuilder;
	}

	public BBSViewBuilder() {
	}

	public TextView buildLoadingView_TextView(Context context) {
		TextView tvLoad = new TextView(context);
		tvLoad.setGravity(Gravity.CENTER);
		tvLoad.setCompoundDrawablePadding(ResHelper.dipToPx(context, 10));
		int tvLoadSize = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_empty_view_txt_size"));
		tvLoad.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvLoadSize);
		int padding = ResHelper.dipToPx(context, 10);
		tvLoad.setPadding(padding, padding, padding, padding);
		int tvLoadColor = context.getResources().getColor(ResHelper.getColorRes(context, "bbs_empty_view_txt_color"));
		tvLoad.setTextColor(tvLoadColor);
		return tvLoad;
	}

	public ProgressBar buildForumThreadDetailView_ProgressBar(Context context) {
		ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setIndeterminate(false);
		progressBar.setMax(100);
		progressBar.setProgressDrawable(context.getResources().getDrawable(ResHelper.getBitmapRes(context, "bbs_webview_progressbar_bg")));
		return progressBar;
	}
}
