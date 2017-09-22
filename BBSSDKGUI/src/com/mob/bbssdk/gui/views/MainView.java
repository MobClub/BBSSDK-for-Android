package com.mob.bbssdk.gui.views;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageSearch;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

/**
 * 主页的主界面
 */
public class MainView extends LinearLayout implements MainViewInterface {
	protected TitleBar titleBar;
	private ForumThreadView forumThreadView;
	private ForumForumView forumForumView;
	private View viewBtn0;
	private View viewBtn1;
	private View viewMark0;
	private View viewMark1;
	private ImageView imageViewSearch;
	protected TextView textViewTab0;
	protected TextView textViewTab1;
	private ForumThreadListView.OnItemClickListener threadItemClickListener;
	private ForumItemClickListener forumItemClickListener;
	private  GUIManager.LoginListener loginListener;

	private BroadcastReceiver sendThreadReceiver;

	public enum WritePostStatus {
		Normal,
		Failed,
		Success
	}

	public MainView(Context context) {
		super(context);
		init(context);
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public TitleBar getTitleBar() {
		return titleBar;
	}

	/**
	 * 更新标题栏用户头像
	 */
	public void updateTitleUserAvatar() {
		if (titleBar != null) {
			titleBar.setLeftUserAvatar(ResHelper.getBitmapRes(getContext(), "bbs_title_header"),
					ResHelper.getBitmapRes(getContext(), "bbs_login_account"));
		}
	}

	protected void updateTitleRightImg(WritePostStatus status) {
		if (status == null) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
			return;
		}
		switch (status) {
			case Failed: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_failed"));
			} break;
			case Success: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_success"));
			} break;
			case Normal: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
			} break;
			default: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
			} break;
		}
	}

	protected View getMainView() {
		return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_view_main"), null);
	}

	protected View getTitleBarView() {
		return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_view_maintitlecenter"), null);
	}

	protected void OnClickTabBtn0() {

	}

	protected void OnClickTabBtn1() {

	}

	protected void OnClickSearch() {
		PageSearch pagesearch = BBSViewBuilder.getInstance().buildPageSearch();
		pagesearch.show(getContext());
	}

	protected void init(final Context context) {
		setOrientation(LinearLayout.VERTICAL);
		loginListener = new GUIManager.LoginListener() {
			@Override
			public void OnLoggedIn() {
				if(forumForumView != null) {
					forumThreadView.refreshData();
				}
			}
			@Override
			public void OnCancel() {

			}
		};

		titleBar = new TitleBar(context) {
			@Override
			protected View getCenterView() {
				View view = getTitleBarView();
				viewBtn0 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewBtn0"));
				viewBtn1 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewBtn1"));
				viewMark0 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewMark0"));
				viewMark1 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewMark1"));
				imageViewSearch = (ImageView) view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_imageViewSearch"));
				textViewTab0 = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_textViewTab0"));
				textViewTab1 = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_textViewTab1"));
				viewBtn0.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						viewMark0.setVisibility(View.VISIBLE);
						viewMark1.setVisibility(View.INVISIBLE);
						forumThreadView.setVisibility(View.VISIBLE);
						forumForumView.setVisibility(View.GONE);
						OnClickTabBtn0();
					}
				});
				viewBtn1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						viewMark0.setVisibility(View.INVISIBLE);
						viewMark1.setVisibility(View.VISIBLE);
						forumThreadView.setVisibility(View.GONE);
						forumForumView.setVisibility(View.VISIBLE);
						OnClickTabBtn1();
					}
				});
				imageViewSearch.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						OnClickSearch();
					}
				});
				viewMark0.setVisibility(View.VISIBLE);
				viewMark1.setVisibility(View.INVISIBLE);
				OnClickTabBtn0();//init the Initialization display effect.
				return view;
			}
		};
		titleBar.setLeftUserAvatar(ResHelper.getBitmapRes(getContext(), "bbs_title_header"),
				ResHelper.getBitmapRes(getContext(), "bbs_login_account"));
		updateTitleRightImg(WritePostStatus.Normal);
		titleBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view == titleBar) {
					int tag = ResHelper.forceCast(view.getTag(), 0);
					switch (tag) {
						case TitleBar.TYPE_TITLE: {
						} break;
						case TitleBar.TYPE_LEFT_IMAGE://fall through
						case TitleBar.TYPE_LEFT_TEXT: {
							if(GUIManager.isLogin()) {
								BBSViewBuilder.getInstance().buildPageUserProfile().show(getContext());
							} else {
								GUIManager.showLogin(getContext(), loginListener);
							}
						} break;
						case TitleBar.TYPE_RIGHT_IMAGE://fall through
						case TitleBar.TYPE_RIGHT_TEXT: {
							GUIManager.writePost(getContext(), loginListener);
						} break;
					}
				}
			}
		});
		titleBar.setBackgroundResource(ResHelper.getColorRes(getContext(), "bbs_mainviewtitle_bg"));
		addView(titleBar, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		View view = getMainView();
		forumThreadView = (ForumThreadView) view.findViewById(ResHelper.getIdRes(context, "bbs_main_forumView"));
		//设置主题帖子列表的点击事件
		forumThreadView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (threadItemClickListener != null) {
					threadItemClickListener.onItemClick(position, item);
				} else {
					PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
					pageForumThreadDetail.setForumThread(item);
					pageForumThreadDetail.show(getContext());
				}
			}
		});

		forumForumView = (ForumForumView) view.findViewById(ResHelper.getIdRes(context, "bbs_main_forumSubjectView"));
		forumForumView.setOnItemClickListener(new ForumForumView.OnItemClickListener() {
			public void onItemClick(ForumForum forum) {
				if (forumItemClickListener != null) {
					forumItemClickListener.onItemClick(forum);
				} else {
					PageForumThread page = BBSViewBuilder.getInstance().buildPageForumThread();
					page.initPage(forum);
					page.show(getContext());
				}
			}
		});
		forumForumView.loadData();
		forumThreadView.setVisibility(View.VISIBLE);
		forumForumView.setVisibility(View.GONE);

		addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if (Build.VERSION.SDK_INT >= 14) {
			setFitsSystemWindows(true);
		}
		onViewCreated();
	}

	protected void onViewCreated() {

	}

	/**
	 * 加载数据
	 */
	public void loadData() {
		forumThreadView.loadData();
	}

	/**
	 * 设置帖子列表item点击事件，默认使用{@link PageForumThreadDetail}来显示帖子详情界面
	 */
	public void setThreadItemClickListener(ForumThreadListView.OnItemClickListener listener) {
		threadItemClickListener = listener;
	}

	/**
	 * 设置版块界面点击事件，黑夜使用{@link PageForumThread}来显示帖子列表界面
	 */
	public void setForumItemClickListener(ForumItemClickListener listener) {
		forumItemClickListener = listener;
	}

	private void updateTitleBarRight(int status) {
		User user = BBSViewBuilder.getInstance().ensureLogin(false);
		if (!GUIManager.isLogin() || (user != null && user.allowPost != 1)) {
			updateTitleRightImg(WritePostStatus.Normal);
			return;
		}
		if (titleBar == null) {
			return;
		}
		if (status == SendForumThreadManager.STATUS_SEND_ING) {
			titleBar.setRightProgressBar();
		} else if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
			updateTitleRightImg(WritePostStatus.Failed);
		} else if (status == SendForumThreadManager.STATUS_SEND_SUCCESS) {
			updateTitleRightImg(WritePostStatus.Success);
			UIHandler.sendEmptyMessageDelayed(0, 2000, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					updateTitleRightImg(WritePostStatus.Normal);
					return false;
				}
			});
		} else {
			updateTitleRightImg(WritePostStatus.Normal);
		}
	}

	public void onCreate() {
		getContext().registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumThreadManager.BROADCAST_SEND_THREAD));
		updateTitleBarRight(SendForumThreadManager.getStatus(getContext()));
	}

	public void onDestroy() {
		if (sendThreadReceiver != null) {
			getContext().unregisterReceiver(sendThreadReceiver);
		}
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

	public interface ForumItemClickListener {
		void onItemClick(ForumForum forum);
	}
}
