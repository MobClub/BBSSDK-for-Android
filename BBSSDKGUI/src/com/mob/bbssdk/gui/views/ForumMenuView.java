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

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.pages.PageForumThreadDetail;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.gui.ViewPagerAdapter;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 论坛版块的View，可以设置内容的adapter, 如果不设置，默认采用{@link ForumThreadListView}来显示内容，使用可参照{@link com.mob.bbssdk.gui.pages.PageForum}
 */
public class ForumMenuView extends BaseView {
	private HorizontalScrollView hsvContent;
	private SlidingTabStrip slidingTabStrip;
	private MobViewPager viewPager;
	private PageAdapter adapter;
	private int tabHeight = 0;
	private ForumThreadListView.OnItemClickListener itemClickListener;

	/**
	 * 设置adapter，不设置采用默认adapter（请在loadData前调用，且采用自定义adapter时，请自行实现item点击事件）
	 */
	public void setAdapter(PageAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * 设置主题帖子列表点击事件，不设置采用默认打开界面（请在loadData前调用，且仅在采用默认adapter时有效）
	 */
	public void setThreadListItemClickListener(ForumThreadListView.OnItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public ForumMenuView(Context context) {
		this(context, null);
	}

	public ForumMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ForumMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		tabHeight = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_menu_tab_height"));

		LinearLayout llContent = new LinearLayout(context, attrs, defStyleAttr);
		llContent.setOrientation(LinearLayout.VERTICAL);
		hsvContent = new HorizontalScrollView(context);
		hsvContent.setBackgroundColor(context.getResources().getColor(ResHelper.getColorRes(context, "bbs_menu_tab_bg")));
		hsvContent.setHorizontalScrollBarEnabled(false);
		hsvContent.setFillViewport(true);
		slidingTabStrip = new SlidingTabStrip(context);
		hsvContent.addView(slidingTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, tabHeight);
		llContent.addView(hsvContent, llp);

		viewPager = new MobViewPager(context);
		llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		llp.weight = 1;
		llContent.addView(viewPager, llp);
		return llContent;
	}

	public void loadData() {
		if (adapter == null) {
			adapter = new ForumMenuView.PageAdapter(this) {
				protected View getView(ForumForum forumMenu, int position) {
					ForumThreadListView itemView = new ForumThreadListView(getContext());
					if (itemClickListener != null) {
						itemView.setOnItemClickListener(itemClickListener);
					} else {
						itemView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
							public void onItemClick(int position, ForumThread item) {
								if (item != null) {
									PageForumThreadDetail pageForumThreadDetail = new PageForumThreadDetail();
									pageForumThreadDetail.setForumThread(item);
									pageForumThreadDetail.show(getContext());
								}
							}
						});
					}
					itemView.loadData(forumMenu.fid, 10);
					return itemView;
				}
			};
		}
		ForumAPI forumAPI = BBSSDK.getApi(ForumAPI.class);
		forumAPI.getForumList(false, new APICallback<ArrayList<ForumForum>>() {
			public void onSuccess(API api, int action, ArrayList<ForumForum> result) {
				if (result == null || result.size() == 0) {
					setLoadingStatus(LoadingView.LOAD_STATUS_EMPTY);
				} else {
					setLoadingStatus(LoadingView.LOAD_STATUS_SUCCESS);
					initMenuTab(result);
				}
			}

			public void onError(API api, int action, int errorCode, Throwable details) {
				setLoadingStatus(LoadingView.LOAD_STATUS_FAILED);
			}
		});
	}

	private void initMenuTab(ArrayList<ForumForum> menuList) {
		if (adapter == null) {
			adapter = new PageAdapter(this) {
				protected View getView(ForumForum forumMenu, int position) {
					ForumThreadListView forumThreadListView = new ForumThreadListView(getContext());
					forumThreadListView.loadData(forumMenu.fid, 10);
					return forumThreadListView;
				}
			};
		}
		if (adapter != null) {
			adapter.setForumMenuList(menuList);
		}
		viewPager.setAdapter(adapter);
		freshTabs();
	}

	public void freshTabs() {
		slidingTabStrip.removeAllViews();
		populateTabStrip();
		setTabSelected(0);
	}

	private void populateTabStrip() {
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
		for (int i = 0; i < adapter.getCount(); i++) {
			TextView tabView = createDefaultTabView(getContext());
			tabView.setText(adapter.getPageTitle(i));
			tabView.setBackgroundColor(Color.parseColor("#00000000"));
			tabView.setOnClickListener(tabClickListener);
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.gravity = Gravity.CENTER_VERTICAL;
			slidingTabStrip.addView(tabView, llp);
		}
	}

	private TextView createDefaultTabView(Context context) {
		TextView tvTab = new TextView(context);
		tvTab.setGravity(Gravity.CENTER);
		int tvTabSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_menu_tab_txt_size"));
		tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTabSize);
		ColorStateList csl = context.getResources().getColorStateList(ResHelper.getColorRes(context, "bbs_menu_tab_txt_color"));
		if (csl != null) {
			tvTab.setTextColor(csl);
		}
		int tvTabPadding = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_menu_tab_txt_padding"));
		tvTab.setPadding(tvTabPadding, 0, tvTabPadding, 0);
		return tvTab;
	}

	private void setCurrentPage(int currentPage) {
		slidingTabStrip.onViewPagerPageChanged(currentPage, 0f);
		scrollToTab(currentPage, 0);
	}

	private void scrollToTab(int tabIndex, int positionOffset) {
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

	static class SlidingTabStrip extends LinearLayout {
		private final Paint selectedTabLinePaint;
		private final Paint underLinePaint;
		private final int selectedTabLineHeight;
		private final int underLineHeight;

		private int selectedPosition;
		private float selectionOffset;

		private int screenWidth = 0;

		public SlidingTabStrip(Context context) {
			this(context, null);
		}

		public SlidingTabStrip(Context context, AttributeSet attrs) {
			super(context, attrs);
			screenWidth = ResHelper.getScreenWidth(context);
			setWillNotDraw(false);
			selectedTabLineHeight = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_menu_tab_selected_line_height"));
			selectedTabLinePaint = new Paint();
			selectedTabLinePaint.setColor(getResources().getColor(ResHelper.getColorRes(context, "bbs_menu_tab_selected_line_color")));
			underLineHeight = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_menu_tab_under_line_height"));
			underLinePaint = new Paint();
			underLinePaint.setColor(getResources().getColor(ResHelper.getColorRes(context, "bbs_menu_tab_under_line_color")));

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
				canvas.drawRect(left, height - selectedTabLineHeight, right, height, selectedTabLinePaint);
			}
		}
	}

	public static abstract class PageAdapter extends ViewPagerAdapter {
		private ForumMenuView forumMenuView;
		private ArrayList<ForumForum> forumMenuList;

		public PageAdapter(ForumMenuView forumMenuView) {
			this.forumMenuView = forumMenuView;
			this.forumMenuList = new ArrayList<ForumForum>();
		}

		public void setForumMenuList(ArrayList<ForumForum> forumMenuList) {
			this.forumMenuList.clear();
			this.forumMenuList.addAll(forumMenuList);
		}

		public void onScreenChange(int currentScreen, int lastScreen) {
			super.onScreenChange(currentScreen, lastScreen);
			forumMenuView.setCurrentPage(currentScreen);
		}

		public int getCount() {
			return forumMenuList.size();
		}

		public ForumForum getItem(int position) {
			return (position >= 0 && position < forumMenuList.size()) ? forumMenuList.get(position) : null;
		}

		public String getPageTitle(int position) {
			ForumForum forumMenu = getItem(position);
			if (forumMenu == null) {
				return null;
			}
			return getItem(position).name;
		}

		public View getView(int position, View view, ViewGroup viewGroup) {
			ForumForum forumMenu = getItem(position);
			View itemView = getView(forumMenu, position);
			return itemView;
		}

		protected abstract View getView(ForumForum forumMenu, int position);
	}
}
