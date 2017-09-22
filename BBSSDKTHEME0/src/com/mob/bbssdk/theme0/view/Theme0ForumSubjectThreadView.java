package com.mob.bbssdk.theme0.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mob.bbssdk.gui.views.ForumThreadView;
import com.mob.tools.utils.ResHelper;

public class Theme0ForumSubjectThreadView extends ForumThreadView {
	public Theme0ForumSubjectThreadView(Context context) {
		super(context);
	}

	public Theme0ForumSubjectThreadView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0ForumSubjectThreadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected ForumThreadViewStyle getForumThreadViewStyle() {
		ForumThreadViewStyle viewstyle = new ForumThreadViewStyle();
		viewstyle.idMenuTabDividerHeight = ResHelper.getResId(getContext(), "dimen", "bbs_theme0_forumsubjectthreadview_tab_divider_height");
		return viewstyle;
	}

	@Override
	protected ForumThreadView.SlidingTabStripStyle getSlidingTabStripStyle() {
		SlidingTabStripStyle slidingTabStripStyle = new SlidingTabStripStyle();
		slidingTabStripStyle.idTabSelectedLineColor = ResHelper.getColorRes(getContext(), "bbs_theme0_forumsubjectthreadview_tabselectedline");
		return slidingTabStripStyle;
	}

	@Override
	protected TabTextViewStyle getTabTextViewStyle() {
		TabTextViewStyle tabTextViewStyle = new TabTextViewStyle();
		tabTextViewStyle.idTabTextColor = ResHelper.getColorRes(getContext(), "bbs_theme0_forumsubjectthreadview_tabunselectedtext");
		tabTextViewStyle.idTabTextSize = ResHelper.getResId(getContext(), "dimen", "bbs_theme0_forumsubjectmenu_tab_txt_size");
		return tabTextViewStyle;
	}

	@Override
	protected void scrollToTab(int tabIndex, int positionOffset) {
		//选中未选中会切换颜色
		super.scrollToTab(tabIndex, positionOffset);
		int count = slidingTabStrip.getChildCount();
		for (int i = 0; i < count; i++) {
			TextView textView = (TextView) slidingTabStrip.getChildAt(i);
			if (i != tabIndex) {
				textView.setTextColor(getContext().getResources().getColor(
						ResHelper.getColorRes(getContext(), "bbs_theme0_forumsubjectthreadview_tabunselectedtext")));
			} else {
				textView.setTextColor(getContext().getResources().getColor(
						ResHelper.getColorRes(getContext(), "bbs_theme0_forumsubjectthreadview_tabtext")));
			}
		}

	}
}
