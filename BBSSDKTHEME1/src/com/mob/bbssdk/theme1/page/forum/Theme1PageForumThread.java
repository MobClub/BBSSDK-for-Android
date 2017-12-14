package com.mob.bbssdk.theme1.page.forum;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.datadef.ThreadListSelectType;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.utils.statusbar.StatusBarCompat;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.theme1.view.Theme1ForumThreadPullToRequestView;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

/*
	板块详情列表页面
 */
public class Theme1PageForumThread extends BasePageWithTitle {
	Theme1ForumThreadPullToRequestView forumThreadPullToRequestView;
	View viewBackground;
	ViewGroup layoutStickTab;
	private View viewMarkLatest;
	private View viewMarkHot;
	private View viewMarkEssence;
	private View viewMarkSticktop;
	private View viewTitle;
	private TextView textViewLatest;
	private TextView textViewHot;
	private TextView textViewEssence;
	private TextView textViewSticktop;
	private TextView textViewTitle;
	private ForumForum forumForum;
	private ViewGroup layoutTitleDropDown;

	public void initData(ForumForum forumforum) {
		this.forumForum = forumforum;
	}

	@Override
	protected View onCreateContentView(Context context) {
		Integer layoutid = ResHelper.getLayoutRes(getContext(), "bbs_theme1_forumthread");
		View view = LayoutInflater.from(getContext()).inflate(layoutid, null, false);
		return view;
	}

	protected int getLocationOnScreen(View view) {
		View globalView = mainLayout; // the main view of my activity/application

		DisplayMetrics dm = new DisplayMetrics();
		Activity activity = (Activity) getContext();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int topOffset = dm.heightPixels - globalView.getMeasuredHeight();

		View tempView = view; // the view you'd like to locate
		int[] loc = new int[2];
		tempView.getLocationOnScreen(loc);

		final int y = loc[1] - topOffset;
		return y;
	}

	@Override
	protected void onViewCreated(View contentView) {
		forumThreadPullToRequestView = (Theme1ForumThreadPullToRequestView) contentView.
				findViewById(ResHelper.getIdRes(getContext(), "forumThreadPullToRequestView"));
		forumThreadPullToRequestView.initData(this.forumForum);
		titleBar.setVisibility(View.GONE);
		viewBackground = contentView.findViewById(getIdRes("viewBackground"));
		viewTitle = contentView.findViewById(getIdRes("viewTitle"));
		layoutStickTab = (ViewGroup) contentView.findViewById(getIdRes("layoutStickTab"));
		textViewTitle = (TextView) contentView.findViewById(getIdRes("textViewTitle"));
		if(forumForum != null && !StringUtils.isEmpty(forumForum.name)) {
			textViewTitle.setText(forumForum.name);
		}
		layoutTitleDropDown = (ViewGroup) contentView.findViewById(getIdRes("layoutTitleDropDown"));
		layoutTitleDropDown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(forumThreadPullToRequestView != null) {
					forumThreadPullToRequestView.popFilterMenu(layoutTitleDropDown);
				}
			}
		});
		forumThreadPullToRequestView.setOnScrollListener(new ForumThreadListView.OnScrollListener() {
			@Override
			public void OnScrolledTo(int y) {
				smoothSwitchStatusBar(y);
				BBSPullToRequestView.setAlphaByScrollY(viewBackground, y, ScreenUtils.dpToPx(100));
				BBSPullToRequestView.setAlphaByScrollY(layoutTitleDropDown, y, ScreenUtils.dpToPx(100));

				int[] location = new int[2];
				location[1] = getLocationOnScreen(forumThreadPullToRequestView.layoutTab);
				int[] sticklocation = new int[2];
				sticklocation[1] = getLocationOnScreen(layoutStickTab);
				if (location[1] <= sticklocation[1]) {
					if (layoutStickTab.getVisibility() != View.VISIBLE) {
						layoutStickTab.setVisibility(View.VISIBLE);
					}
				} else {
					if (layoutStickTab.getVisibility() != View.INVISIBLE) {
						layoutStickTab.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
		forumThreadPullToRequestView.setForumThreadUpdateListner(new Theme1ForumThreadPullToRequestView.ForumThreadUpdateListner() {
			@Override
			public void OnTabUpdated(ThreadListSelectType tab) {
				forumThreadPullToRequestView.updateTabView(viewMarkLatest, viewMarkHot, viewMarkEssence, viewMarkSticktop);
			}
		});
		findViewById(getIdRes("imageViewBack")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(getIdRes("imageViewSearch")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageSearch().show(getContext());
			}
		});
		findViewById(getIdRes("imageViewWritePost")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageWriteThread page = BBSViewBuilder.getInstance().buildPageWriteThread();
				page.setForum(forumForum);
				page.show(getContext());
			}
		});

		viewMarkLatest = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewMarkLatest"));
		viewMarkHot = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewMarkHot"));
		viewMarkEssence = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewMarkEssence"));
		viewMarkSticktop = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewMarkSticktop"));
		viewMarkLatest.setVisibility(View.VISIBLE);
		viewMarkHot.setVisibility(View.INVISIBLE);
		viewMarkEssence.setVisibility(View.INVISIBLE);
		viewMarkSticktop.setVisibility(View.INVISIBLE);

		textViewLatest = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewLatest"));
		textViewLatest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				forumThreadPullToRequestView.clickTab(ThreadListSelectType.LATEST);
			}
		});
		textViewHot = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewHot"));
		textViewHot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				forumThreadPullToRequestView.clickTab(ThreadListSelectType.HEATS);
			}
		});
		textViewEssence = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewEssence"));
		textViewEssence.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				forumThreadPullToRequestView.clickTab(ThreadListSelectType.DIGEST);
			}
		});
		textViewSticktop = (TextView) contentView.findViewById(ResHelper.getIdRes(getContext(), "textViewSticktop"));
		textViewSticktop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				forumThreadPullToRequestView.clickTab(ThreadListSelectType.DISPLAY_ORDER);
			}
		});
		forumThreadPullToRequestView.refreshQuiet();
		contentView.setFitsSystemWindows(false);
		smoothSwitchStatusBar(0);
	}

	protected void smoothSwitchStatusBar(int height) {
		if (Build.VERSION.SDK_INT >= 19) {
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewBackground.getLayoutParams();
			layoutParams.height = ScreenUtils.dpToPx(44) + DeviceHelper.getInstance(getContext()).getStatusBarHeight();
			viewBackground.setLayoutParams(layoutParams);
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewTitle.getLayoutParams();
			lp.setMargins(0,DeviceHelper.getInstance(getContext()).getStatusBarHeight(),0,0);
			viewTitle.setLayoutParams(lp);
			if (height > 20) {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),true);
			} else {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),false);
			}
		}
	}
}
