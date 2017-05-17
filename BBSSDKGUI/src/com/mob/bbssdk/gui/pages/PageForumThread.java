package com.mob.bbssdk.gui.pages;


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
import com.mob.bbssdk.gui.ForumThreadManager;
import com.mob.bbssdk.gui.PageResult;
import com.mob.bbssdk.gui.ThreadListOrderType;
import com.mob.bbssdk.gui.pages.user.PageLogin;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.ForumThreadView;
import com.mob.bbssdk.gui.views.MainView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

/**
 * 版块帖子列表界面
 */
public class PageForumThread extends BasePageWithTitle {
	private ForumThreadView forumThreadView;
	private TextView textViewTitle;
	private ImageView imageViewTitle;
	private ImageView imageViewRefresh;
	private View viewMenuBg;
	private View viewArrangeByReplyTime;
	private View viewArrangeByPostTime;
	private ForumForum forumForum;

	private MainView.ThreadItemClickListener itemClickListener;
	private BroadcastReceiver sendThreadReceiver;

	public PageForumThread(ForumForum forum) {
		this.forumForum = forum;
	}

	/**
	 * 设置版块点击事件
	 */
	public void setItemClickListener(MainView.ThreadItemClickListener listener) {
		itemClickListener = listener;
	}

	protected View onCreateContentView(final Context context) {
		titleBar.setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_back"));
		titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_writepost"));
		titleBar.setBackgroundResource(ResHelper.getColorRes(context, "bbs_white"));
		titleBar.setAlpha(1.0f);
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_subject"), null);
		forumThreadView = (ForumThreadView) view.findViewById(getIdRes("bbs_subject_forumView"));
		//设置主题帖子列表的点击事件
		forumThreadView.setOnItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (item != null) {
					if (itemClickListener != null) {
						itemClickListener.onItemClick(position, item);
					} else {
						PageForumThreadDetail pageForumThreadDetail = new PageForumThreadDetail();
						pageForumThreadDetail.setForumThread(item);
						pageForumThreadDetail.show(activity);
					}
				}
			}
		});
		imageViewRefresh = (ImageView) view.findViewById(getIdRes("bbs_pagesubject_imageViewRefresh"));
		imageViewRefresh.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				forumThreadView.refreshCurrentForumListView(null);
			}
		});
		viewMenuBg = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_bg_viewMenuBg"));
		hideListMenu();

		viewArrangeByReplyTime = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_viewArrangeByReplyTime"));
		viewArrangeByPostTime = view.findViewById(getIdRes("bbs_pagesubject_titlecenter_viewArrangeByPostTime"));
		viewArrangeByReplyTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				forumThreadView.refreshCurrentForumListView(ThreadListOrderType.LAST_POST);
				hideListMenu();
			}
		});
		viewArrangeByPostTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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

	protected void onViewCreated(View contentView) {
		getContext().registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumThreadManager.BROADCAST_SEND_THREAD));
		updateTitleBarRight(SendForumThreadManager.getStatus(getContext()));
		//加载数据
		forumThreadView.loadData(forumForum.fid);
	}

	protected void onTitleRightClick(TitleBar titleBar) {
		if (checkLogin(true)) {
			//允许发帖
			if (BBSSDK.getApi(UserAPI.class).getCurrentUser().allowPost == 1) {
				int status = SendForumThreadManager.getStatus(activity);
				if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
					SendForumThreadManager.showFailedDialog(activity);
				} else if (status == SendForumThreadManager.STATUS_SEND_IDLE || status == SendForumThreadManager.STATUS_SEND_CACHED) {
					PageWriteThread pageWriteThread = new PageWriteThread();
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
		View view = LayoutInflater.from(getContext()).inflate(getLayoutId("bbs_subject_titlecenter"), null);
		imageViewTitle = (ImageView) view.findViewById(getIdRes("bbs_subject_titlecenter_imageViewTitle"));
		textViewTitle = (TextView) view.findViewById(getIdRes("bbs_subject_titlecenter_textView"));
		textViewTitle.setText(forumForum.name);
		return view;
	}

	protected void onTitleClick(TitleBar titleBar) {
		if(viewMenuBg.getVisibility() == View.VISIBLE) {
			hideListMenu();
		} else {
			showListMenu();
		}
	}

	private void showListMenu() {
		imageViewTitle.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_titlecetner_pullup"));
		viewMenuBg.setVisibility(View.VISIBLE);
	}

	private void hideListMenu() {
		viewMenuBg.setVisibility(View.GONE);
		imageViewTitle.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_titlecetner_dropdown"));
	}

	private boolean checkLogin(boolean gotoLogin) {
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		boolean isLogin = (api.getCurrentUser() != null);
		if (isLogin) {
			return true;
		} else if (gotoLogin) {
			new PageLogin().showForResult(getContext(), new FakeActivity() {
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
		if (!checkLogin(false) || BBSSDK.getApi(UserAPI.class).getCurrentUser().allowPost != 1) {
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
		ForumThreadManager.getInstance(getContext()).saveReaded();
	}
}
