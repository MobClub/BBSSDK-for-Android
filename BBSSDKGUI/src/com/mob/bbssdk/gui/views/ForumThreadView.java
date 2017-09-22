package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.datadef.ThreadListOrderType;
import com.mob.bbssdk.gui.datadef.ThreadListSelectType;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 主题帖子列表界面，可以设置内容的adapter, 如果不设置，默认采用{@link ForumThreadListView}来显示内容，使用可参照{@link PageForumThread}
 */
public class ForumThreadView extends BaseView implements ForumThreadListPageAdapter.PageSwitchListener {
	private static final String TAG = "ForumView";
	private HorizontalScrollView hsvContent;
	protected SlidingTabStrip slidingTabStrip;
	private MobViewPager viewPager;
	private ForumThreadListPageAdapter adapter;
	private int tabHeight = 0;
	protected ForumThreadListView.OnItemClickListener itemClickListener;
	protected long forumId = 0;
	protected ForumThreadViewType forumThreadViewType = ForumThreadViewType.FORUM_MAIN;

	public void setType(ForumThreadViewType type) {
		if(type != null) {
			forumThreadViewType = type;
		}
	}

	/**
	 * 设置adapter，不设置采用默认adapter（请在loadData前调用，且采用自定义adapter时，请自行实现item点击事件）
	 */
	public void setAdapter(ForumThreadListPageAdapter adapter) {
		this.adapter = adapter;
	}

	public ForumThreadListPageAdapter getAdapter() {
		return adapter;
	}

	/**
	 * 设置主题帖子列表点击事件，不设置采用默认打开界面（请在loadData前调用，且仅在采用默认adapter时有效）
	 */
	public void setOnItemClickListener(ForumThreadListView.OnItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public ForumThreadView(Context context) {
		this(context, null);
	}

	public ForumThreadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ForumThreadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		ForumThreadViewStyle viewstyle = getForumThreadViewStyle();
		if(viewstyle == null) {
			viewstyle = new ForumThreadViewStyle();
		}
		if(viewstyle.idMenuTabHeight == null) {
			viewstyle.idMenuTabHeight = ResHelper.getResId(context, "dimen", "bbs_menu_tab_height");
		}
		tabHeight = getResources().getDimensionPixelSize(viewstyle.idMenuTabHeight);

		LinearLayout llContent = new LinearLayout(context, attrs, defStyleAttr);
		llContent.setBackgroundColor(getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_white")));
		llContent.setOrientation(LinearLayout.VERTICAL);
		hsvContent = new HorizontalScrollView(context);
		if(viewstyle.idMenuTabBg == null) {
			viewstyle.idMenuTabBg = ResHelper.getColorRes(context, "bbs_menu_tab_bg");
		}
		hsvContent.setBackgroundColor(context.getResources().getColor(viewstyle.idMenuTabBg));
		hsvContent.setHorizontalScrollBarEnabled(false);
		hsvContent.setFillViewport(true);
		slidingTabStrip = new SlidingTabStrip(context);
		initTabStrip();
		hsvContent.addView(slidingTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, tabHeight);
		llContent.addView(hsvContent, llp);

		View divider = new View(context);
		if(viewstyle.idMenuTabDividerColor == null) {
			viewstyle.idMenuTabDividerColor = ResHelper.getColorRes(context, "bbs_divider");
		}
		divider.setBackgroundColor(context.getResources().getColor(viewstyle.idMenuTabDividerColor));

		if(viewstyle.idMenuTabDividerHeight == null) {
			viewstyle.idMenuTabDividerHeight = ResHelper.getResId(context, "dimen", "bbs_menu_tab_divider_height");
		}
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				getResources().getDimensionPixelSize(viewstyle.idMenuTabDividerHeight));
		llContent.addView(divider, llp);
		viewPager = buildViewPager();
		llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		llp.weight = 1;
		llContent.addView(viewPager, llp);
		return llContent;
	}
	public void loadData(Long fid) {
		if(fid != null) {
			this.forumId = fid;
		}
		loadData();
	}

	protected ForumThreadListView getForumThreadListView () {
		return null;
	}

	@Override
	public void onPageChanged(int page) {
		setCurrentPage(page);
	}

	protected MobViewPager buildViewPager() {
		return new MobViewPager(getContext());
	}

	protected ForumThreadListPageAdapter buildForumThreadListPageAdapter() {
		return new ForumThreadListPageAdapter(this) {
			protected View getView(ThreadListSelectType filtertype, int position) {
				ForumThreadListView itemView = getForumThreadListView();
				if(itemView == null) {
					itemView = new ForumThreadListView(getContext());
				}
				if (itemClickListener != null) {
					itemView.setOnItemClickListener(itemClickListener);
				} else {
					itemView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
						public void onItemClick(int position, ForumThread item) {
							if (item != null) {
								PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
								pageForumThreadDetail.setForumThread(item);
								pageForumThreadDetail.show(getContext());
							}
						}
					});
				}
				itemView.setType(forumThreadViewType);
				itemView.setLoadParams(forumId, filtertype, null, 10);
				itemView.startLoadData();
				return itemView;
			}
		};
	}

	public void loadData() {
		setLoadingStatus(RequestLoadingView.LOAD_STATUS_SUCCESS);
		if (adapter == null) {
			adapter = buildForumThreadListPageAdapter();
			ArrayList<ThreadListSelectType> list = new ArrayList<ThreadListSelectType>();
			list.add(ThreadListSelectType.LATEST);
			list.add(ThreadListSelectType.HEATS);
			list.add(ThreadListSelectType.DIGEST);
			list.add(ThreadListSelectType.DISPLAY_ORDER);
			adapter.setThreadListSelectType(list);
			viewPager.setAdapter(adapter);
		}
	}

	public void refreshData(){
		for(int i = 0; i < viewPager.getChildCount(); i++) {
			View child = adapter.getViewAt(i);
			if (child != null && child instanceof ForumThreadListView) {
				ForumThreadListView view = (ForumThreadListView) child;
				view.setLoadParams(null, null, null, null);
				view.refreshData();
			}
		}
	}

	public void refreshCurrentForumListView(ThreadListOrderType ordertype) {
		View child = adapter.getViewAt(viewPager.getCurrentScreen());
		if(child != null && child instanceof ForumThreadListView) {
			ForumThreadListView view = (ForumThreadListView) child;
			view.setLoadParams(null, null, ordertype, null);
			view.refreshData();
		}
	}

	private void initTabStrip() {
		String[] strArrayTabs = getResources().getStringArray(
				ResHelper.getStringArrayRes(getContext(), "bbs_forumview_tabtitle"));

		final OnClickListener tabClickListener = new OnClickListener() {
			public void onClick(View v) {
				for (int i = 0; i < slidingTabStrip.getChildCount(); i++) {
					if (v == slidingTabStrip.getChildAt(i)) {
						viewPager.scrollToScreen(i, true, true);
						return;
					}
				}
			}
		};
		for (int i = 0; i < strArrayTabs.length; i++) {
			TextView tabView = createDefaultTabView(getContext());
			tabView.setText(strArrayTabs[i]);
			tabView.setBackgroundColor(Color.parseColor("#00000000"));
			tabView.setOnClickListener(tabClickListener);
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
			llp.weight = 1;
			llp.gravity = Gravity.CENTER_VERTICAL;
			slidingTabStrip.addView(tabView, llp);
		}
		setCurrentPage(0);
	}

	private TextView createDefaultTabView(Context context) {
		TextView tvTab = new TextView(context);
		tvTab.setGravity(Gravity.CENTER);
		TabTextViewStyle tabTextViewStyle = getTabTextViewStyle();
		if(tabTextViewStyle == null) {
			tabTextViewStyle = new TabTextViewStyle();
		}
		if(tabTextViewStyle.idTabTextSize == null) {
			tabTextViewStyle.idTabTextSize = ResHelper.getResId(context, "dimen", "bbs_menu_tab_txt_size");
		}
		int tvTabSize = getResources().getDimensionPixelSize(tabTextViewStyle.idTabTextSize);
		tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTabSize);
		if(tabTextViewStyle.idTabTextColor == null) {
			tabTextViewStyle.idTabTextColor = ResHelper.getColorRes(context, "bbs_menu_tab_txt_color");
		}
		ColorStateList csl = context.getResources().getColorStateList(tabTextViewStyle.idTabTextColor);
		if (csl != null) {
			tvTab.setTextColor(csl);
		}
		if(tabTextViewStyle.idTabTextLeftRightPadding == null) {
			tabTextViewStyle.idTabTextLeftRightPadding = ResHelper.getResId(context, "dimen", "bbs_menu_tab_txt_padding");
		}
		int tvTabPadding = getResources().getDimensionPixelSize(tabTextViewStyle.idTabTextLeftRightPadding);
		tvTab.setPadding(tvTabPadding, 0, tvTabPadding, 0);
		return tvTab;
	}

	public int getCurrentPagePos() {
		return slidingTabStrip.getSelectedPosition();
	}

	private void setCurrentPage(int currentPage) {
		slidingTabStrip.onViewPagerPageChanged(currentPage, 0f);
		scrollToTab(currentPage, 0);
	}

	protected void scrollToTab(int tabIndex, int positionOffset) {
		final int tabStripChildCount = slidingTabStrip.getChildCount();
		if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
			return;
		}

		View selectedChild = slidingTabStrip.getChildAt(tabIndex);
		if (selectedChild != null) {
			final int parentViewWidth = getWidth();
			int targetScrollX = selectedChild.getLeft() - parentViewWidth;
			setTabSelected(tabIndex);
			int itemWidth = selectedChild.getWidth();
			if (targetScrollX > 0 || targetScrollX > -itemWidth) {
				hsvContent.scrollTo(targetScrollX + itemWidth, 0);
			} else if (targetScrollX <= -itemWidth) {
				hsvContent.scrollTo(0, 0);
			}
		}
	}

	private void setTabSelected(int position) {
		int count = slidingTabStrip.getChildCount();
		for (int i = 0; i < count; i++) {
			if (i == position) {
				slidingTabStrip.getChildAt(i).setSelected(true);
			} else {
				slidingTabStrip.getChildAt(i).setSelected(false);
			}
		}
	}

	public static class ForumThreadViewStyle {
		public Integer idMenuTabHeight;
		public Integer idMenuTabBg;
		public Integer idMenuTabDividerColor;
		public Integer idMenuTabDividerHeight;
	}


	protected ForumThreadViewStyle getForumThreadViewStyle() {
		return null;
	}

	public static class TabTextViewStyle {
		public Integer idTabTextSize;
		public Integer idTabTextColor;
		public Integer idTabTextLeftRightPadding;
	}

	protected TabTextViewStyle getTabTextViewStyle() {
		return null;
	}

	protected SlidingTabStripStyle getSlidingTabStripStyle() {
		return null;// use default;
	}

	public static class SlidingTabStripStyle {
		public Integer idTabUnderLineWidth;
		public Integer idTabSelectedLineHeight;
		public Integer idTabSelectedLineColor;
		public Integer idTabUnderLineHeight;
		public Integer idTabUnderLineColor;
	}

	protected class SlidingTabStrip extends LinearLayout {
		private final Paint selectedTabLinePaint;
		private final Paint underLinePaint;
		private final int selectedTabLineHeight;
		private final int underLineHeight;

		private int selectedPosition;
		private float selectionOffset;

		private int screenWidth = 0;
		private int bottomwidth;
		private SlidingTabStripStyle slidingTabStripStyle;

		public SlidingTabStrip(Context context) {
			this(context, null);
		}

		public SlidingTabStrip(Context context, AttributeSet attrs) {
			super(context, attrs);
			slidingTabStripStyle = getSlidingTabStripStyle();
			if(slidingTabStripStyle == null) {
				slidingTabStripStyle = new SlidingTabStripStyle();
			}
			if(slidingTabStripStyle.idTabUnderLineWidth == null) {
				slidingTabStripStyle.idTabUnderLineWidth = ResHelper.getResId(context, "dimen", "bbs_menu_tab_under_line_width");
			}
			bottomwidth = context.getResources().getDimensionPixelSize(slidingTabStripStyle.idTabUnderLineWidth);
			screenWidth = ResHelper.getScreenWidth(context);
			setWillNotDraw(false);
			if(slidingTabStripStyle.idTabSelectedLineHeight == null) {
				slidingTabStripStyle.idTabSelectedLineHeight = ResHelper.getResId(context, "dimen", "bbs_menu_tab_selected_line_height");
			}
			selectedTabLineHeight = getResources().getDimensionPixelSize(slidingTabStripStyle.idTabSelectedLineHeight);
			selectedTabLinePaint = new Paint();
			if(slidingTabStripStyle.idTabSelectedLineColor == null) {
				slidingTabStripStyle.idTabSelectedLineColor = ResHelper.getColorRes(context, "bbs_menu_tab_selected_line_color");
			}
			selectedTabLinePaint.setColor(getResources().getColor(slidingTabStripStyle.idTabSelectedLineColor));
			if(slidingTabStripStyle.idTabUnderLineHeight == null) {
				slidingTabStripStyle.idTabUnderLineHeight = ResHelper.getResId(context, "dimen", "bbs_menu_tab_under_line_height");
			}
			underLineHeight = getResources().getDimensionPixelSize(slidingTabStripStyle.idTabUnderLineHeight);
			underLinePaint = new Paint();
			if(slidingTabStripStyle.idTabUnderLineColor == null) {
				slidingTabStripStyle.idTabUnderLineColor = ResHelper.getColorRes(context, "bbs_menu_tab_under_line_color");
			}
			underLinePaint.setColor(getResources().getColor(slidingTabStripStyle.idTabUnderLineColor));
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public void onViewPagerPageChanged(int position, float positionOffset) {
			selectedPosition = position;
			selectionOffset = positionOffset;
			invalidate();
		}
		protected void onDraw(Canvas canvas) {
			final int height = getHeight();
			final int childCount = getChildCount();
			final int width = getWidth();
			canvas.drawRect(0, height - underLineHeight, width < screenWidth ? screenWidth : width, height, underLinePaint);
			if (childCount > 0) {
				View selectedTitle = getChildAt(selectedPosition);
				int left = selectedTitle.getLeft();
				int right = selectedTitle.getRight();
				if (selectionOffset > 0f && selectedPosition < (getChildCount() - 1)) {
					View nextTitle = getChildAt(selectedPosition + 1);
					left = (int) (selectionOffset * nextTitle.getLeft() + (1.0f - selectionOffset) * left);
					right = (int) (selectionOffset * nextTitle.getRight() + (1.0f - selectionOffset) * right);
				}
				left = left + bottomwidth / 2;
				right = right - bottomwidth / 2;
				canvas.drawRect(left, height - selectedTabLineHeight, right, height, selectedTabLinePaint);
			}
		}
	}
}
