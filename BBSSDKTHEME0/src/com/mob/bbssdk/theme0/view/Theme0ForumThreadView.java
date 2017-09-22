package com.mob.bbssdk.theme0.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.views.ForumThreadListPageAdapter;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.ForumThreadListViewWithBanner;
import com.mob.bbssdk.gui.views.ForumThreadView;
import com.mob.bbssdk.model.Banner;
import com.mob.bbssdk.theme0.HeaderMobViewPager;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.utils.ResHelper;

import java.util.List;
import java.util.Map;

/**
 *  UI0首页的帖子页面
 */
public class Theme0ForumThreadView extends ForumThreadView {
	public Theme0ForumThreadView(Context context) {
		super(context);
	}

	public Theme0ForumThreadView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0ForumThreadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected ForumThreadView.SlidingTabStripStyle getSlidingTabStripStyle() {
		SlidingTabStripStyle slidingTabStripStyle = new SlidingTabStripStyle();
		slidingTabStripStyle.idTabSelectedLineColor = ResHelper.getColorRes(getContext(), "bbs_theme0_forumthreadview_tabselectedline");
		return slidingTabStripStyle;
	}

	@Override
	protected TabTextViewStyle getTabTextViewStyle() {
		TabTextViewStyle tabTextViewStyle = new TabTextViewStyle();
		tabTextViewStyle.idTabTextColor = ResHelper.getColorRes(getContext(), "bbs_theme0_forumthreadview_tabtext");
		tabTextViewStyle.idTabTextSize = ResHelper.getResId(getContext(), "dimen", "bbs_theme0_menu_tab_txt_size");
		return tabTextViewStyle;
	}

	@Override
	protected ForumThreadListView getForumThreadListView() {
		final ForumThreadListViewWithBanner listviewwithheader = new ForumThreadListViewWithBanner(getContext()) {
			@Override
			protected void OnBannerGot(List<Banner> list) {
				ForumThreadListPageAdapter adapter = getAdapter();
				Map<Integer, View> map = adapter.getViewMap();
				for(Integer item : map.keySet()) {
					View view = map.get(item);
					if(view != null && view instanceof ForumThreadListViewWithBanner) {
						ForumThreadListViewWithBanner listview = (ForumThreadListViewWithBanner) view;
						listview.updateBanner(list);
					}
				}
			}

			@Override
			protected View getContentView(int position, View convertView, ViewGroup parent) {
				View view = super.getContentView(position, convertView, parent);
				if(view.getTag() instanceof ListViewItemBuilder.ThreadViewHolder) {
					ListViewItemBuilder.ThreadViewHolder viewref = (ListViewItemBuilder.ThreadViewHolder) view.getTag();
					viewref.tvLeftTime.setVisibility(GONE);
					viewref.tvRightTime.setVisibility(GONE);
				}
				return view;
			}
		};
		return listviewwithheader;
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
						ResHelper.getColorRes(getContext(), "bbs_theme0_forumthreadview_tabunselectedtext")));
			} else {
				textView.setTextColor(getContext().getResources().getColor(
						ResHelper.getColorRes(getContext(), "bbs_theme0_forumthreadview_tabtext")));
			}
		}
	}

	@Override
	protected MobViewPager buildViewPager() {
		HeaderMobViewPager headermobviewpager = new HeaderMobViewPager(getContext());
		//setHeaderHeight according to the height of bannerlayout in ForumThreadListViewWithHeader.
		return headermobviewpager;
	}
}
