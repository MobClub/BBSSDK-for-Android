package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.datadef.ThreadListOrderType;
import com.mob.bbssdk.gui.datadef.ThreadListSelectType;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * 帖子列表的View
 */
public class ForumThreadListView extends BBSPullToRequestView<ForumThread> {
	private static final int MAX_PAGE_SIZE = 20;
	private Integer pageSize = 10;
	private Long forumId = 0l;
	private ThreadListOrderType orderType = ThreadListOrderType.CREATE_ON;
	private ThreadListSelectType selectType = ThreadListSelectType.LATEST;
	private ForumAPI contentAPI;
	protected OnItemClickListener itemClickListener;
	private ForumThreadViewType forumThreadViewType = ForumThreadViewType.FORUM_MAIN;

	public void setType(ForumThreadViewType type) {
		if (type != null) {
			forumThreadViewType = type;
		}
	}

	public ForumThreadListView(Context context) {
		super(context);
		init();
	}

	public ForumThreadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ForumThreadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}


	//根据是否阅读过设置title的字体颜色
	public void setThreadReaded(View view, boolean readed) {
		if (view == null) {
			return;
		}
		Context context = view.getContext();
		TextView tvTitle = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_item_forumpost_textViewTitle"));
		if (!readed) {
			if (tvTitle != null) {
				tvTitle.setTextColor(context.getResources().getColor(ResHelper.getColorRes(context, "bbs_postitem_title")));
			}
		} else {
			int color = context.getResources().getColor(ResHelper.getColorRes(context, "bbs_postitem_titleclicked"));
			if (tvTitle != null) {
				tvTitle.setTextColor(color);
			}
		}
	}

	@Override
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		final View view = ListViewItemBuilder.getInstance().buildThreadView(getItem(position), convertView, parent);
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
//
//		//主页里面显示论坛名称，但是不显示标签，板块板块列表里面相反。
//		//主页不展示时间。板块列表展示时间。
//		if(view.getTag() instanceof ListViewItemBuilder.ThreadViewHolder) {
//			ListViewItemBuilder.ThreadViewHolder viewref = (ListViewItemBuilder.ThreadViewHolder)view.getTag();
//			if (ForumThreadViewType.FORUM_MAIN == forumThreadViewType) {
//				if (viewref.tvSubject != null) {
//					viewref.tvSubject.setVisibility(VISIBLE);
//				}
//				if (viewref.layoutLabel != null) {
//					viewref.layoutLabel.setVisibility(GONE);
//				}
//				if (viewref.tvLeftTime != null) {
//					viewref.tvLeftTime.setVisibility(GONE);
//				}
//				if (viewref.tvRightTime != null) {
//					viewref.tvRightTime.setVisibility(GONE);
//				}
//			} else {
//				if (viewref.tvSubject != null) {
//					viewref.tvSubject.setVisibility(GONE);
//				}
//				if (viewref.layoutLabel != null) {
//					//当有图标有效时才展示，否则会有多余的margin
//					if (forumthread.heatLevel > 0 || forumthread.digest > 0 || forumthread.displayOrder > 0) {
//						viewref.layoutLabel.setVisibility(VISIBLE);
//					} else {
//						viewref.layoutLabel.setVisibility(GONE);
//					}
//				}
//				if (viewref.tvLeftTime != null) {
//					viewref.tvLeftTime.setVisibility(VISIBLE);
//				}
//				if (viewref.tvRightTime != null) {
//					viewref.tvRightTime.setVisibility(GONE);
//				}
//			}
//		}
		return view;
	}

	protected void init() {
		super.init();
		setOnRequestListener(new OnRequestListener() {
			@Override
			public void onRequest(int page,final BasePagedItemAdapter.RequestCallback callback) {
				contentAPI = BBSSDK.getApi(ForumAPI.class);
				contentAPI.getThreadListByForumId(forumId, selectType.getValue(), orderType.getValue(), page, pageSize, false,
						new APICallback<ArrayList<ForumThread>>() {
							@Override
							public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
								callback.onFinished(true, (result != null && result.size() == ForumThreadListView.this.pageSize), result);
							}

							@Override
							public void onError(API api, int action, int errorCode, Throwable details) {
								callback.onFinished(false, false, null);
							}
						});
			}
		});
//
//		ColorDrawable dividerColor = new ColorDrawable(getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_divider")));
//		forumPostItemAdapter.getListView().setDivider(dividerColor);
//		int dividerHeight = getResources().getDimensionPixelOffset(ResHelper.getResId(getContext(), "dimen", "bbs_forum_thread_list_divider_height"));
//		forumPostItemAdapter.getListView().setDividerHeight(dividerHeight);
//		if (Build.VERSION.SDK_INT > 17) {
//			forumPostItemAdapter.getListView().addHeaderView(new ViewStub(getContext()));
//		}
//		forumPostItemAdapter.getListView().setHeaderDividersEnabled(true);
//		forumPostItemAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//				if (Build.VERSION.SDK_INT > 17) {
//					position -= 1;
//				}
//				ForumThread item = forumPostItemAdapter.getItem(position);
//				//有可能用户点击加载更多的footer，导致获取item为空
//				if (item != null) {
//					if (itemClickListener != null) {
//						itemClickListener.onItemClick(position, item);
//					}
//					ForumThreadHistoryManager.getInstance().addReadedThread(item);
//					forumPostItemAdapter.setThreadReaded(view, true);
//				}
//			}
//		});
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		itemClickListener = listener;
	}

	public void setLoadParams(Long fid, ThreadListSelectType selecttype, ThreadListOrderType ordertype, Integer pagesize) {
		if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		if (fid != null) {
			this.forumId = fid;
		}
		if (selecttype != null) {
			this.selectType = selecttype;
		}
		if (ordertype != null) {
			this.orderType = ordertype;
		}
		if (pagesize != null) {
			this.pageSize = pagesize;
		}
	}

	public void refreshData() {
		performPullingDown(true);
	}

	public void startLoadData() {
		performPullingDown(true);
	}

	public interface OnItemClickListener {
		void onItemClick(int position, ForumThread item);
	}

	public Integer getPostItemLayoutId() {
		return null;
	}
}
