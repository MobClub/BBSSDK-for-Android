package com.mob.bbssdk.theme1.view;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.other.BannerLayout;
import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageSearch;
import com.mob.bbssdk.gui.utils.ImageDownloader;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.utils.statusbar.StatusBarCompat;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.MainView;
import com.mob.bbssdk.gui.views.MainViewInterface;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.Banner;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.theme1.BBSTheme1ViewBuilder;
import com.mob.bbssdk.theme1.page.forum.Theme1PageForumSetting;
import com.mob.bbssdk.theme1.page.forum.Theme1PageForumThread;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Theme1MainView extends FrameLayout implements MainViewInterface {
	protected ForumThreadListView forumThreadListViewWithBanner;
	protected GlideImageView imageViewAvatar;
	protected boolean isDefaultAvatar = true;
	protected ImageView imageViewSearch;
	protected ImageView imageViewWritePost;
	protected HorizontalScrollView horizontalScrollView;
	protected View viewTitle;
	protected View viewBackground;
	protected TextView textViewTitle;
	protected View viewMessageMark;
	private GUIManager.LoginListener loginListener;
	private ArrayList<ForumForum> forumForum;
	private BroadcastReceiver sendThreadReceiver;
	private View viewHeaderContent;
	protected LayoutInflater layoutInflater;
	protected LinearLayout layoutScrollView;

	protected ViewGroup viewHeaderFuncMore;
	protected View placeHolderView;
	protected int defaultTotalForumPic;

	public Theme1MainView(Context context) {
		super(context);
		init(context);
	}

	public Theme1MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Theme1MainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	protected void updateTab() {
		BBSSDK.getApi(ForumAPI.class).getForumList(0, false, new APICallback<ArrayList<ForumForum>>() {
			@Override
			public void onSuccess(API api, int action, final ArrayList<ForumForum> result) {
				forumForum = result;
				updateTabFunc();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void updateNewMessageMark() {
		//only update the mark when user have logged in.
		if (!GUIManager.isLogin()) {
			return;
		}
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		api.getUserOperations(null, false, new APICallback<UserOperations>() {
			@Override
			public void onSuccess(API api, int action, UserOperations result) {
				if (result != null) {
					if (result.notices > 0) {
						viewMessageMark.setVisibility(VISIBLE);
					} else {
						viewMessageMark.setVisibility(INVISIBLE);
					}
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void updateTabFunc() {
		//view created and data got.
		if (viewHeaderContent != null && forumForum != null) {
			layoutScrollView.removeAllViews();
			for(final ForumForum forum : forumForum) {
				Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_mainviewheader_funcitem");
				View view = layoutInflater.inflate(layout, layoutScrollView, false);
				ImageView imageViewHeaderFunc = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewHeaderFunc"));
				TextView textViewHeaderFunc = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewHeaderFunc"));
				textViewHeaderFunc.setText(forum.name);
				if(forum.fid == 0 && StringUtils.isEmpty(forum.forumPic)) {
					imageViewHeaderFunc.setImageResource(defaultTotalForumPic);
				} else {
					ImageDownloader.loadCircleImage(forum.forumPic, imageViewHeaderFunc);
				}
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (forum != null) {
							Theme1PageForumThread page = new Theme1PageForumThread();
							page.initData(forum);
							page.show(getContext());
						}
					}
				});
				layoutScrollView.addView(view);
			}
		}
	}

	protected void init(Context context) {
		defaultTotalForumPic = ResHelper.getBitmapRes(getContext(), "bbs_theme1_totaldefault");
		setBackgroundColor(Color.WHITE);
		loginListener = new GUIManager.LoginListener() {
			@Override
			public void OnLoggedIn() {
				if (forumThreadListViewWithBanner != null) {
					forumThreadListViewWithBanner.refreshData();
				}
			}

			@Override
			public void OnCancel() {

			}
		};

		forumThreadListViewWithBanner = new ForumThreadListView(context) {
			BannerLayout bannerLayout;
			ImageView imageViewFakeBanner;
			List<Banner> listBanner = new ArrayList<Banner>();

			public boolean dispatchTouchEvent(MotionEvent ev) {
				if (checkIsTouchHeaderView(ev.getY())) {
					return viewHeader.dispatchTouchEvent(ev);
				}
				return super.dispatchTouchEvent(ev);
			}

			public boolean onInterceptTouchEvent(MotionEvent ev) {
				if (checkIsTouchHeaderView(ev.getY())) {
					return true;
				}
				return super.onInterceptTouchEvent(ev);
			}

			private boolean checkIsTouchHeaderView(float eventY) {
				if (!basePagedItemAdapter.haveContentHeader()) {
					return false;
				}
				if (viewHeader != null && viewHeader.isShown()) {
					float headerViewY = viewHeader.getY();
					return (eventY < viewHeader.getHeight() + headerViewY)
							&& (Math.abs(headerViewY) < viewHeader.getHeight())
							&& (headerViewY <= 0 && headerViewY > -10);
				}
				return false;
			}

			@Override
			protected void init() {
				super.init();
				setHaveContentHeader(true);
				basePagedItemAdapter.setDividerHeight(0);
			}

			@Override
			public View getContentHeader(ViewGroup viewGroup, View viewprev) {
				if (viewprev != null) {
					return viewprev;
				}
				//Don't reuse the previous view to prevent the click no response bug during scroll back and forth.
				View viewheader = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(),
						"bbs_theme1_layout_mainviewheader"), viewGroup, false);
				imageViewFakeBanner = (ImageView) viewheader.findViewById(ResHelper.getIdRes(getContext(), "imageViewFakeBanner"));
				bannerLayout = (BannerLayout) viewheader.findViewById(ResHelper.getIdRes(getContext(), "bannerLayout"));
				layoutScrollView = (LinearLayout) viewheader.findViewById(ResHelper.getIdRes(getContext(), "layoutScrollView"));
				horizontalScrollView = (HorizontalScrollView) viewheader.findViewById(ResHelper.getIdRes(getContext(),
						"horizontalScrollView"));
				horizontalScrollView.setHorizontalScrollBarEnabled(false);
				placeHolderView = viewheader.findViewById(ResHelper.getIdRes(getContext(), "placeHolderView"));

				viewHeaderFuncMore = (ViewGroup) viewheader.findViewById(ResHelper.getIdRes(getContext(), "viewHeaderFuncMore"));
				viewHeaderFuncMore.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Theme1PageForumSetting().show(getContext());
					}
				});
				if (viewprev == null) {
					getBanner();
				} else {
					updateBanner(null);
				}
				viewHeaderContent = viewheader;
				updateTabFunc();
				return viewheader;
			}

			@Override
			protected View getContentView(final int position, View convertView, ViewGroup parent) {
				Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_mainviewthread");
				final View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(getItem(position), convertView, parent, layout);
				final ForumThread forumthread = getItem(position);
				setThreadReaded(view, ForumThreadHistoryManager.getInstance().isThreadReaded(forumthread));
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (forumthread != null) {
							if (itemClickListener != null) {
								itemClickListener.onItemClick(position, forumthread);
							}
							ForumThreadHistoryManager.getInstance().addReadedThread(forumthread);
							setThreadReaded(view, true);
						}
					}
				});
				TextView textViewTime = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewMainViewThreadTime"));
				if (textViewTime != null) {
					textViewTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(getContext(), forumthread.createdOn));
				}
				return view;
			}

			@Override
			protected void OnRefresh() {
				super.OnRefresh();
				getBanner();
				updateTab();
				updateNewMessageMark();
			}

			protected void getBanner() {
				UserAPI userapi = BBSSDK.getApi(UserAPI.class);
				userapi.getBannerList(false, new APICallback<List<Banner>>() {
					@Override
					public void onSuccess(API api, int action, List<Banner> result) {
						listBanner = result;
						OnBannerGot(listBanner);
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}

			protected void OnBannerGot(List<Banner> list) {
				updateBanner(list);
			}

			public void updateBanner(List<Banner> l) {
				if (imageViewFakeBanner == null || bannerLayout == null) {
					return;
				}
				final List<Banner> banners;
				if (l == null) {
					banners = listBanner;
				} else {
					banners = l;
				}
				if (banners == null || banners.size() == 0) {
					imageViewFakeBanner.setVisibility(VISIBLE);
					bannerLayout.setVisibility(GONE);
					return;
				} else {
					imageViewFakeBanner.setVisibility(GONE);
					bannerLayout.setVisibility(VISIBLE);
				}
				if (bannerLayout != null && banners != null) {
					List<BannerLayout.Item> list = new ArrayList<BannerLayout.Item>();
					for (Banner banner : banners) {
						BannerLayout.Item item = new BannerLayout.Item();
						item.strUrl = banner.picture;
						item.strTitle = banner.title;
						list.add(item);
					}
					bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
						@Override
						public void onItemClick(int position) {
							if (banners != null) {
								Banner banner = banners.get(position);
								if (banner != null && banner.btype != null) {
									if (banner.btype.equals("thread")) {
										PageForumThreadDetail page = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
										ForumThread thread = new ForumThread();
										thread.tid = Long.valueOf(banner.tid);
										thread.fid = Long.valueOf(banner.fid);
										page.setForumThread(thread);
										page.show(getContext());
									} else if (banner.btype.equals("link")) {
										String url = banner.link;
										if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
											PageWeb pageWeb = BBSViewBuilder.getInstance().buildPageWeb();
											pageWeb.setLink(url);
											pageWeb.show(getContext());
										}
									}
								}
							}
						}
					});
					bannerLayout.setViewItems(list);
				}
			}
		};
		addView(forumThreadListViewWithBanner, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		forumThreadListViewWithBanner.startLoadData();
		viewTitle = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_theme1_mainview_title"), this, false);
		viewBackground = viewTitle.findViewById(ResHelper.getIdRes(getContext(), "viewBackground"));
		imageViewAvatar = (GlideImageView) viewTitle.findViewById(ResHelper.getIdRes(context, "imageViewAvatar"));
		imageViewSearch = (ImageView) viewTitle.findViewById(ResHelper.getIdRes(context, "imageViewSearch"));
		imageViewWritePost = (ImageView) viewTitle.findViewById(ResHelper.getIdRes(context, "imageViewWritePost"));
		textViewTitle = (TextView) viewTitle.findViewById(ResHelper.getIdRes(context, "textViewTitle"));
		viewMessageMark = viewTitle.findViewById(ResHelper.getIdRes(context, "viewMessageMark"));
		addView(viewTitle);
		imageViewAvatar.setExecuteRound();

		imageViewSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PageSearch search = BBSTheme1ViewBuilder.getInstance().buildPageSearch();
				search.show(getContext());
			}
		});
		imageViewWritePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GUIManager.getInstance().writePost(getContext(), null);
			}
		});
		imageViewAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GUIManager.isLogin()) {
					BBSViewBuilder.getInstance().buildPageUserProfile().showForResult(getContext(), new FakeActivity() {
						@Override
						public void onResult(HashMap<String, Object> data) {
							super.onResult(data);
							if (data != null) {
								Boolean logout = ResHelper.forceCast(data.get("logout"));
								if (logout != null && logout) {
									forumThreadListViewWithBanner.performPullingDown(true);
									viewMessageMark.setVisibility(GONE);
								}
							}
						}
					});
				} else {
					GUIManager.showLogin(getContext(), loginListener);
				}
			}
		});
		forumThreadListViewWithBanner.setOnScrollListener(new ForumThreadListView.OnScrollListener() {
			@Override
			public void OnScrolledTo(int y) {
				smoothSwitchStatusBar(y);
				BBSPullToRequestView.setAlphaByScrollY(viewBackground, y, ScreenUtils.dpToPx(100));
				BBSPullToRequestView.setAlphaByScrollY(textViewTitle, y, ScreenUtils.dpToPx(100));

				smoothSwitchView(imageViewWritePost, y, 500,
						ResHelper.getBitmapRes(getContext(), "bbs_theme1_writepost_white"),
						ResHelper.getBitmapRes(getContext(), "bbs_theme1_writepost_black"));

				smoothSwitchView(imageViewSearch, y, 500,
						ResHelper.getBitmapRes(getContext(), "bbs_theme1_search_white"),
						ResHelper.getBitmapRes(getContext(), "bbs_theme1_search_black"));

				smoothSwitchAvatarView(y, 500);
			}
		});
		layoutInflater = LayoutInflater.from(getContext());
	}

	protected void smoothSwitchStatusBar(int height) {
		if (Build.VERSION.SDK_INT >= 19) {
			LayoutParams layoutParams = (LayoutParams) viewBackground.getLayoutParams();
			layoutParams.height = ScreenUtils.dpToPx(40) + DeviceHelper.getInstance(getContext()).getStatusBarHeight();
			viewBackground.setLayoutParams(layoutParams);
			LayoutParams lp = (LayoutParams) viewTitle.getLayoutParams();
			lp.height = ScreenUtils.dpToPx(40) + DeviceHelper.getInstance(getContext()).getStatusBarHeight();
			viewTitle.setLayoutParams(lp);
			if (height > 20) {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),true);
			} else {
				StatusBarCompat.translucentStatusBar((Activity) getContext(),false);
			}
		}
	}

	protected void smoothSwitchAvatarView(Integer height, Integer opacityheight) {
		if(isDefaultAvatar) {
			if(height == null) {
				height = forumThreadListViewWithBanner.getScrollHeight();
			}
			smoothSwitchView(imageViewAvatar, height, opacityheight,
					ResHelper.getBitmapRes(getContext(), "bbs_theme1_account_white"),
					ResHelper.getBitmapRes(getContext(), "bbs_theme1_account_black"));
		} else {
			imageViewAvatar.setAlpha(1.0f);
		}
	}

	protected void smoothSwitchView(ImageView imageview, Integer height, Integer opacityheight, int residfirst, int residsecond) {
		if(height == null) {
			height = forumThreadListViewWithBanner.getScrollHeight();
		}
		int halfopacityheight = opacityheight / 2;
		//// TODO: 2017/10/9 optimize should be made.
		if(height < halfopacityheight) {
			float alpha1 = (float) height / halfopacityheight;
			alpha1 = 1 - alpha1;
			imageview.setImageResource(residfirst);
			imageview.setAlpha(alpha1);
		} else if(height < opacityheight) {
			float alpha1 = (float) (height - halfopacityheight) / halfopacityheight;
			imageview.setImageResource(residsecond);
			imageview.setAlpha(alpha1);
		} else {
			imageview.setImageResource(residsecond);
			imageview.setAlpha(1.0f);
		}
	}

	@Override
	public void loadData() {
		forumThreadListViewWithBanner.performPullingDown(true);
	}

	@Override
	public void onCreate() {
		getContext().registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumThreadManager.BROADCAST_SEND_THREAD));
		updateTitleBarRight(SendForumThreadManager.getStatus(getContext()));
		smoothSwitchStatusBar(0);
	}

	@Override
	public void onDestroy() {
		if (sendThreadReceiver != null) {
			getContext().unregisterReceiver(sendThreadReceiver);
		}
	}

	@Override
	public void setThreadItemClickListener(ForumThreadListView.OnItemClickListener listener) {
		forumThreadListViewWithBanner.setOnItemClickListener(listener);
	}

	@Override
	public void setForumItemClickListener(MainView.ForumItemClickListener listener) {

	}

	@Override
	public void updateTitleUserAvatar() {
		if (imageViewAvatar == null) {
			return;
		}
		if (!GUIManager.isLogin()) {
			imageViewAvatar.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_account_white"));
			isDefaultAvatar = true;
		} else {
			Bitmap currenavatar = GUIManager.getInstance().getCurrentUserAvatar();
			if (currenavatar == null) {
				GUIManager.getInstance().forceUpdateCurrentUserAvatar(new GUIManager.AvatarUpdatedListener() {
					@Override
					public void onUpdated(Bitmap bitmap) {
						if (bitmap == null) {
							imageViewAvatar.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_account_white"));
							isDefaultAvatar = true;
						} else {
							imageViewAvatar.setImageBitmap(bitmap);
							isDefaultAvatar = false;
						}
						smoothSwitchAvatarView(null, 100);
					}
				});
			} else {
				imageViewAvatar.setImageBitmap(currenavatar);
				isDefaultAvatar = false;
			}
		}
		smoothSwitchAvatarView(null, 100);
	}

	private BroadcastReceiver initSendThreadReceiver() {
		if (sendThreadReceiver == null) {
			sendThreadReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					if (intent == null) {
						return;
					}
					int status = intent.getIntExtra("status", SendForumThreadManager.STATUS_SEND_IDLE);
					updateTitleBarRight(status);
				}
			};
		}
		return sendThreadReceiver;
	}

	protected void updateTitleRightImg(MainView.WritePostStatus status) {
		if (status == null) {
			imageViewWritePost.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_title_writepost"));
			return;
		}
		switch (status) {
			case Failed: {
				imageViewWritePost.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_failed"));
			} break;
			case Success: {
				imageViewWritePost.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_success"));
			} break;
			case Normal: {
				imageViewWritePost.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_title_writepost"));
			} break;
			default: {
				imageViewWritePost.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_title_writepost"));
			} break;
		}
	}

	private void updateTitleBarRight(int status) {
		User user = BBSViewBuilder.getInstance().ensureLogin(false);
		if (!GUIManager.isLogin() || (user != null && user.allowPost != 1)) {
			updateTitleRightImg(MainView.WritePostStatus.Normal);
			return;
		}
		if (imageViewWritePost == null) {
			return;
		}
		if (status == SendForumThreadManager.STATUS_SEND_ING) {
			//todo
//			titleBar.setRightProgressBar();
		} else if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
			updateTitleRightImg(MainView.WritePostStatus.Failed);
		} else if (status == SendForumThreadManager.STATUS_SEND_SUCCESS) {
			updateTitleRightImg(MainView.WritePostStatus.Success);
			UIHandler.sendEmptyMessageDelayed(0, 2000, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					updateTitleRightImg(MainView.WritePostStatus.Normal);
					return false;
				}
			});
		} else {
			updateTitleRightImg(MainView.WritePostStatus.Normal);
		}
	}
}
