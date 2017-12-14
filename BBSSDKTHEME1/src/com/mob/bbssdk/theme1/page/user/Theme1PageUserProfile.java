package com.mob.bbssdk.theme1.page.user;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.utils.statusbar.StatusBarCompat;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.theme1.view.Theme1UserProfilePullRequestView;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

public class Theme1PageUserProfile extends BasePageWithTitle {
	private Theme1UserProfilePullRequestView userProfilePullRequestView;
	private ImageView imageViewBack;
	private TextView textViewTitle;
	private ImageView imageViewMessage;
	private ImageView imageViewSettings;
	private View viewBackground;
	private ImageView imageViewFavorite;
	private ImageView imageViewThread;
	private ImageView imageViewHistory;
	private TextView textViewFavoriteCount;
	private TextView textViewThreadCount;
	private TextView textViewHistoryCount;
	protected ViewGroup layoutFavorite;
	protected ViewGroup layoutThread;
	protected ViewGroup layoutHistory;
	private View viewMessageMark;
	View viewFavoriteMark;
	View viewThreadMark;
	View viewHistoryMark;
	View viewTitle;

	@Override
	protected View onCreateContentView(Context context) {
		Integer layoutid = ResHelper.getLayoutRes(getContext(), "bbs_theme1_userprofile");
		View view = LayoutInflater.from(getContext()).inflate(layoutid, null, false);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		contentView.setFitsSystemWindows(false);
		imageViewBack = (ImageView) contentView.findViewById(getIdRes("imageViewBack"));
		textViewTitle = (TextView) contentView.findViewById(getIdRes("textViewTitle"));
		imageViewMessage = (ImageView) contentView.findViewById(getIdRes("imageViewMessage"));
		imageViewSettings = (ImageView) contentView.findViewById(getIdRes("imageViewSettings"));
		viewBackground = contentView.findViewById(getIdRes("viewBackground"));
		viewMessageMark = contentView.findViewById(getIdRes("viewMessageMark"));
		viewTitle = contentView.findViewById(getIdRes("viewTitle"));
		titleBar.setVisibility(View.GONE);
		imageViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		textViewTitle.setText("");
		imageViewMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageMessages().show(getContext());
			}
		});
		imageViewSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Theme1PageSettings().showForResult(getContext(), new FakeActivity() {
					public void onResult(HashMap<String, Object> data) {
						super.onResult(data);
						if (data != null) {
							Boolean logout = ResHelper.forceCast(data.get("logout"));
							if (logout != null && logout) {
								Theme1PageUserProfile.this.setResult(data);
								Theme1PageUserProfile.this.finish();
							}
						}
					}
				});
			}
		});

		final ViewGroup layoutTab = (ViewGroup) contentView.findViewById(ResHelper.getIdRes(getContext(), "layoutTab"));
		layoutTab.setVisibility(View.INVISIBLE);

		imageViewFavorite = (ImageView) contentView.findViewById(getIdRes("imageViewFavorite"));
		imageViewThread = (ImageView) contentView.findViewById(getIdRes("imageViewThread"));
		imageViewHistory = (ImageView) contentView.findViewById(getIdRes("imageViewHistory"));
		textViewFavoriteCount = (TextView) contentView.findViewById(getIdRes("textViewFavoriteCount"));
		textViewThreadCount = (TextView) contentView.findViewById(getIdRes("textViewThreadCount"));
		textViewHistoryCount = (TextView) contentView.findViewById(getIdRes("textViewHistoryCount"));
		viewFavoriteMark = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewFavoriteMark"));
		viewThreadMark = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewThreadMark"));
		viewHistoryMark = contentView.findViewById(ResHelper.getIdRes(getContext(), "viewHistoryMark"));
		layoutFavorite = (ViewGroup) contentView.findViewById(ResHelper.getIdRes(getContext(), "layoutFavorite"));
		layoutThread = (ViewGroup) contentView.findViewById(ResHelper.getIdRes(getContext(), "layoutThread"));
		layoutHistory = (ViewGroup) contentView.findViewById(ResHelper.getIdRes(getContext(), "layoutHistory"));

		layoutFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userProfilePullRequestView.clickTab(Theme1UserProfilePullRequestView.TAB.FAVORITE);
			}
		});
		layoutThread.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userProfilePullRequestView.clickTab(Theme1UserProfilePullRequestView.TAB.THREAD);
			}
		});
		layoutHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userProfilePullRequestView.clickTab(Theme1UserProfilePullRequestView.TAB.HISTORY);
			}
		});
		userProfilePullRequestView = (Theme1UserProfilePullRequestView) contentView.
				findViewById(ResHelper.getIdRes(getContext(), "userProfilePullRequestView"));
		userProfilePullRequestView.setOnScrollListener(new ForumThreadListView.OnScrollListener() {
			@Override
			public void OnScrolledTo(int y) {
				smoothSwitchStatusBar(y);
				BBSPullToRequestView.setAlphaByScrollY(viewBackground, y, ScreenUtils.dpToPx(100));

				int[] location = new int[2];
				userProfilePullRequestView.layoutTab.getLocationOnScreen(location);
				int[] sticklocation = new int[2];
				layoutTab.getLocationOnScreen(sticklocation);

				if (location[1] <= sticklocation[1]) {
					if (layoutTab.getVisibility() != View.VISIBLE) {
						layoutTab.setVisibility(View.VISIBLE);
					}
				} else {
					if (layoutTab.getVisibility() != View.INVISIBLE) {
						layoutTab.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
		userProfilePullRequestView.setOnUserProfileUpdatedListener(new Theme1UserProfilePullRequestView.UserProfileUpdatedListener() {
			@Override
			public void OnUserInfoUpdated(User user) {

			}

			@Override
			public void OnUserOperationUpdated(UserOperations useroper) {
				if (useroper.notices > 0) {
					viewMessageMark.setVisibility(View.VISIBLE);
				} else {
					viewMessageMark.setVisibility(View.INVISIBLE);
				}
				textViewFavoriteCount.setText("" + useroper.favorites);
				textViewThreadCount.setText("" + useroper.threads);
				textViewHistoryCount.setText("" + ForumThreadHistoryManager.getInstance().getReadedThreadCount());
			}

			@Override
			public void OnTabUpdated(Theme1UserProfilePullRequestView.TAB tab) {
				userProfilePullRequestView.updateTabStatus(imageViewFavorite,
						imageViewThread, imageViewHistory, viewFavoriteMark, viewThreadMark, viewHistoryMark);
			}
		});
		userProfilePullRequestView.refreshQuiet();
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

	@Override
	protected void onLoginoutRefresh(Boolean loginout) {
		super.onLoginoutRefresh(loginout);
		if(loginout) {
			userProfilePullRequestView.refreshQuiet();
		} else {
			finish();
		}
	}
}
