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
import android.widget.LinearLayout;

import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.PageResult;
import com.mob.bbssdk.gui.pages.PageForumThread;
import com.mob.bbssdk.gui.pages.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.PageWriteThread;
import com.mob.bbssdk.gui.pages.user.PageLogin;
import com.mob.bbssdk.gui.pages.user.PageUserProfile;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

/**
 * 主页的主界面
 */
public class MainView extends LinearLayout {
	protected TitleBar titleBar;
	private ForumThreadView forumThreadView;
	private ForumForumView forumForumView;
	private View viewBtn0;
	private View viewBtn1;
	private View viewMark0;
	private View viewMark1;
	private ThreadItemClickListener threadItemClickListener;
	private ForumItemClickListener forumItemClickListener;

	private BroadcastReceiver sendThreadReceiver;

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

	protected void init(final Context context) {
		setOrientation(LinearLayout.VERTICAL);
		titleBar = new TitleBar(context) {
			@Override
			protected View getCenterView() {
				View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(context, "bbs_maintitlecenterview"), null);
				viewBtn0 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewBtn0"));
				viewBtn1 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewBtn1"));
				viewMark0 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewMark0"));
				viewMark1 = view.findViewById(ResHelper.getIdRes(context, "bbs_maintitlcenterview_viewMark1"));

				viewBtn0.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						viewMark0.setVisibility(View.VISIBLE);
						viewMark1.setVisibility(View.INVISIBLE);
						forumThreadView.setVisibility(View.VISIBLE);
						forumForumView.setVisibility(View.GONE);
					}
				});
				viewBtn1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						viewMark0.setVisibility(View.INVISIBLE);
						viewMark1.setVisibility(View.VISIBLE);
						forumThreadView.setVisibility(View.GONE);
						forumForumView.setVisibility(View.VISIBLE);
					}
				});
				viewMark0.setVisibility(View.VISIBLE);
				viewMark1.setVisibility(View.INVISIBLE);
				return view;
			}
		};
		titleBar.setLeftUserAvatar(ResHelper.getBitmapRes(getContext(), "bbs_title_header"),
				ResHelper.getBitmapRes(getContext(), "bbs_login_account"));
		titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
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
							if (checkLogin()) {
								new PageUserProfile().show(getContext());
							}
						} break;
						case TitleBar.TYPE_RIGHT_IMAGE://fall through
						case TitleBar.TYPE_RIGHT_TEXT: {
							if (checkLogin()) {
								//允许发帖
								if (BBSSDK.getApi(UserAPI.class).getCurrentUser().allowPost == 1) {
									int status = SendForumThreadManager.getStatus(context);
									if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
										SendForumThreadManager.showFailedDialog(context);
									} else if (status == SendForumThreadManager.STATUS_SEND_IDLE
											|| status == SendForumThreadManager.STATUS_SEND_CACHED) {
										new PageWriteThread().show(getContext());
									}
								} else {//不允许发帖
									ToastUtils.showToast(getContext(),
											getResources().getString(ResHelper.getStringRes(getContext(), "bbs_dont_allowpost")));
								}
							}
						} break;
					}
				}
			}
		});
		titleBar.setBackgroundResource(ResHelper.getColorRes(getContext(), "bbs_mainviewtitle_bg"));
		addView(titleBar, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		View view = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_main"), null);
		forumThreadView = (ForumThreadView) view.findViewById(ResHelper.getIdRes(context, "bbs_main_forumView"));
		//设置主题帖子列表的点击事件
		forumThreadView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (threadItemClickListener != null) {
					threadItemClickListener.onItemClick(position, item);
				} else {
					PageForumThreadDetail pageForumThreadDetail = new PageForumThreadDetail();
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
					PageForumThread pageForumThread = new PageForumThread(forum);
					pageForumThread.show(getContext());
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
	}

	private boolean isLogin() {
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		return (api.getCurrentUser() != null);
	}

	private boolean checkLogin() {
		if (isLogin()) {
			return true;
		} else {
			new PageLogin().showForResult(getContext(), new FakeActivity() {
				@Override
				public void onResult(HashMap<String, Object> data) {
					super.onResult(data);
					if (data != null) {
						Boolean refresh = (Boolean) data.get(PageResult.RESULT_LOGINSUCCESS_BOOLEAN);
						if (refresh != null && refresh) {
							forumThreadView.refreshData();
						}
					}
				}
			});
			return false;
		}
	}

	/**
	 * 加载数据
	 */
	public void loadData() {
		forumThreadView.loadData();
	}

	/**
	 *	设置帖子列表item点击事件，默认使用{@link com.mob.bbssdk.gui.pages.PageForumThreadDetail}来显示帖子详情界面
	 */
	public void setThreadItemClickListener(ThreadItemClickListener listener) {
		threadItemClickListener = listener;
	}

	/**
	 * 设置版块界面点击事件，黑夜使用{@link com.mob.bbssdk.gui.pages.PageForumThread}来显示帖子列表界面
	 */
	public void setForumItemClickListener(ForumItemClickListener listener) {
		forumItemClickListener = listener;
	}

	private void updateTitleBarRight(int status) {
		if (!isLogin() || BBSSDK.getApi(UserAPI.class).getCurrentUser().allowPost != 1) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
			return;
		}
		if (titleBar == null) {
			return;
		}
		if (status == SendForumThreadManager.STATUS_SEND_ING) {
			titleBar.setRightProgressBar();
		} else if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_failed"));
		} else if (status == SendForumThreadManager.STATUS_SEND_SUCCESS) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_success"));
			UIHandler.sendEmptyMessageDelayed(0, 2000, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
					return false;
				}
			});
		} else {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_title_writepost"));
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

	public interface ThreadItemClickListener {
		void onItemClick(int position, ForumThread item);
	}

	public interface ForumItemClickListener {
		void onItemClick(ForumForum forum);
	}
}
