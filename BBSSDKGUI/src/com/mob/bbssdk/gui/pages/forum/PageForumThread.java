package com.mob.bbssdk.gui.pages.forum;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.datadef.PageResult;
import com.mob.bbssdk.gui.datadef.ThreadListOrderType;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.ForumThreadView;
import com.mob.bbssdk.gui.views.ForumThreadViewType;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

/**
 * 版块帖子列表界面
 */
public class PageForumThread extends BasePageWithTitle {
	protected ForumThreadView forumThreadView;
	private TextView textViewTitle;
	private ImageView imageViewTitle;
	private ImageView imageViewSearch;
	private ImageView imageViewRefresh;
	private View viewMenuBg;
	protected View viewArrangeByReplyTime;
	protected View viewArrangeByPostTime;
	private ForumForum forumForum;
	protected ThreadListOrderType[] threadListOrderTypeCurrent = new ThreadListOrderType[4];
	private ForumThreadListView.OnItemClickListener itemClickListener;
	private BroadcastReceiver sendThreadReceiver;

	public PageForumThread() {
	}

	public void initPage(ForumForum forum){
		this.forumForum = forum;
	}
	/**
	 * 设置版块点击事件
	 */
	public void setItemClickListener(ForumThreadListView.OnItemClickListener listener) {
		itemClickListener = listener;
	}

	protected View buildMainView() {
		return null;
	}


	protected View onCreateContentView(final Context context) {
		titleBar.setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_titlebar_back_black"));
		titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_writepost"));
		titleBar.setBackgroundResource(ResHelper.getColorRes(context, "bbs_white"));
		titleBar.setAlpha(1.0f);
		View view = buildMainView();
		if(view == null) {
			view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_forum_forumthread"), null);
		}
		forumThreadView = (ForumThreadView) view.findViewById(getIdRes("bbs_subject_forumView"));
		//设置主题帖子列表的点击事件
		forumThreadView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (item != null) {
					if (itemClickListener != null) {
						itemClickListener.onItemClick(position, item);
					} else {
						PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
						pageForumThreadDetail.setForumThread(item);
						pageForumThreadDetail.show(activity);
					}
				}
			}
		});
		forumThreadView.setType(ForumThreadViewType.FORUM_SUBJECT);
		imageViewRefresh = (ImageView) view.findViewById(getIdRes("bbs_pagesubject_imageViewRefresh"));
		imageViewRefresh.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setCurrentOrderType(null);
				forumThreadView.refreshCurrentForumListView(null);
			}
		});
		viewMenuBg = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_bg_viewMenuBg"));
		hideListMenu();

		viewArrangeByReplyTime = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_viewArrangeByReplyTime"));
		viewArrangeByPostTime = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_viewArrangeByPostTime"));
		viewArrangeByReplyTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setCurrentOrderType( ThreadListOrderType.LAST_POST);
				forumThreadView.refreshCurrentForumListView(ThreadListOrderType.LAST_POST);
				hideListMenu();
			}
		});
		viewArrangeByPostTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentOrderType( ThreadListOrderType.CREATE_ON);
				forumThreadView.refreshCurrentForumListView(ThreadListOrderType.CREATE_ON);
				hideListMenu();
			}
		});
		viewMenuBg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hideListMenu();
			}
		});
		return view;
	}

	protected void setCurrentOrderType(ThreadListOrderType type) {
		int n = forumThreadView.getCurrentPagePos();
		if(n < 0 || n >= threadListOrderTypeCurrent.length) {
			return;
		}
		threadListOrderTypeCurrent[forumThreadView.getCurrentPagePos()] = type;
	}

	protected void onViewCreated(View contentView) {
		getContext().registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumThreadManager.BROADCAST_SEND_THREAD));
		updateTitleBarRight(SendForumThreadManager.getStatus(getContext()));
		//加载数据
		forumThreadView.loadData(forumForum.fid);
	}

	protected void onTitleRightClick(TitleBar titleBar) {
		if (checkLogin(true)) {
			//允许发帖
			User user = null;
			try {
				user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
			} catch (Exception e) {
				e.printStackTrace();
				user = null;
			}

			if (user != null && user.allowPost == 1) {
				int status = SendForumThreadManager.getStatus(activity);
				if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
					SendForumThreadManager.showFailedDialog(activity);
				} else if (status == SendForumThreadManager.STATUS_SEND_IDLE || status == SendForumThreadManager.STATUS_SEND_CACHED) {
					PageWriteThread pageWriteThread = BBSViewBuilder.getInstance().buildPageWriteThread();
					pageWriteThread.setForum(forumForum);
					pageWriteThread.show(getContext());
				}
			} else {//不允许发帖
				ToastUtils.showToast(getContext(),
						activity.getResources().getString(ResHelper.getStringRes(getContext(), "bbs_dont_allowpost")));
			}
		}
	}

	protected View getTitleCenterView() {
		View view = LayoutInflater.from(getContext()).inflate(getLayoutId("bbs_page_forum_titlecenter"), null);
		imageViewTitle = (ImageView) view.findViewById(getIdRes("bbs_subject_titlecenter_imageViewTitle"));
		imageViewSearch = (ImageView) view.findViewById(getIdRes("bbs_maintitlcenterview_imageViewSearch"));
		textViewTitle = (TextView) view.findViewById(getIdRes("bbs_subject_titlecenter_textView"));
		textViewTitle.setText(forumForum.name);
		imageViewSearch.setVisibility(View.GONE);
		imageViewSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageSearch pagesearch = BBSViewBuilder.getInstance().buildPageSearch();
				pagesearch.show(getContext());
			}
		});
		return view;
	}

	protected void onTitleClick(TitleBar titleBar) {
		if(viewMenuBg.getVisibility() == View.VISIBLE) {
			hideListMenu();
		} else {
			showListMenu();
		}
	}

	protected void showListMenu() {
		imageViewTitle.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_titlecetner_pullup"));
		viewMenuBg.setVisibility(View.VISIBLE);
	}

	private void hideListMenu() {
		viewMenuBg.setVisibility(View.GONE);
		imageViewTitle.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_titlecetner_dropdown"));
	}

	private boolean checkLogin(boolean gotoLogin) {
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		User user;
		try {
			user = api.getCurrentUser();
		} catch (Exception e) {
			user = null;
		}
		boolean isLogin = (user != null);
		if (isLogin) {
			return true;
		} else if (gotoLogin) {
			PageLogin pagelogin = BBSViewBuilder.getInstance().buildPageLogin();
			pagelogin.showForResult(getContext(), new FakeActivity() {
				public void onResult(HashMap<String, Object> data) {
					super.onResult(data);
					if (data != null) {
						Boolean refresh = (Boolean) data.get(PageResult.RESULT_LOGINSUCCESS_BOOLEAN);
						if (refresh != null && refresh && forumThreadView != null) {
							forumThreadView.loadData();
						}
					}
				}
			});
		}
		return false;
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

	private void updateTitleBarRight(int status) {
		if (titleBar == null) {
			return;
		}
		User user;
		try {
			user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
		} catch (Exception e) {
			user = null;
		}
		if (!checkLogin(false) || (user != null && user.allowPost != 1)) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_writepost"));
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
					titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_writepost"));
					return false;
				}
			});
		} else {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_writepost"));
		}
	}

	public void onDestroy() {
		if (sendThreadReceiver != null) {
			getContext().unregisterReceiver(sendThreadReceiver);
		}
	}

	public void onPause() {
		super.onPause();
		ForumThreadHistoryManager.getInstance().saveReaded();
	}
}
