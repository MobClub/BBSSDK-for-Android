package com.mob.bbssdk.theme0.view;


import android.content.Context;
import android.util.AttributeSet;

import com.mob.bbssdk.gui.views.ForumImageViewer;

public class Theme0ForumImageViewer extends ForumImageViewer{
	public Theme0ForumImageViewer(Context context) {
		super(context);
	}

	public Theme0ForumImageViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0ForumImageViewer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected void loadNativeHtml() {
		webView.loadUrl("file:///android_asset/bbssdk/html0/details/html/imgshow.html");
	}
}
