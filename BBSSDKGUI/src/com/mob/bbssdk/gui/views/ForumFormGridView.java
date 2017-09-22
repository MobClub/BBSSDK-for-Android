package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ForumFormGridView extends GridView {
	public ForumFormGridView(Context context) {
		super(context);
	}

	public ForumFormGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumFormGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
