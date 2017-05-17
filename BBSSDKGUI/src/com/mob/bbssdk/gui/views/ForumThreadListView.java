package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.ForumThreadManager;
import com.mob.bbssdk.gui.ThreadListOrderType;
import com.mob.bbssdk.gui.ThreadListSelectType;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 帖子列表的View
 */
public class ForumThreadListView extends PullToRequestView {
	private static final int MAX_PAGE_SIZE = 20;
	private Integer pageSize = 10;
	private Long forumId = 0l;
	private ThreadListOrderType orderType = ThreadListOrderType.CREATE_ON;
	private ThreadListSelectType selectType = ThreadListSelectType.LATEST;
	private ForumAPI contentAPI;
	private OnItemClickListener itemClickListener;
	private ForumPostItemAdapter forumPostItemAdapter;

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

	private void init() {
		contentAPI = BBSSDK.getApi(ForumAPI.class);
		forumPostItemAdapter = new ForumPostItemAdapter(this) {
			protected void onRequest(int page, final RequestCallback callback) {
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
		};

		ColorDrawable dividerColor = new ColorDrawable(getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_divider")));
		forumPostItemAdapter.getListView().setDivider(dividerColor);
		int dividerHeight = getResources().getDimensionPixelOffset(ResHelper.getResId(getContext(), "dimen", "bbs_forum_thread_list_divider_height"));
		forumPostItemAdapter.getListView().setDividerHeight(dividerHeight);
		if (Build.VERSION.SDK_INT > 17) {
			forumPostItemAdapter.getListView().addHeaderView(new ViewStub(getContext()));
		}
		forumPostItemAdapter.getListView().setHeaderDividersEnabled(true);
		forumPostItemAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				if (Build.VERSION.SDK_INT > 17) {
					position -= 1;
				}
				ForumThread item = forumPostItemAdapter.getItem(position);
				if (itemClickListener != null) {
					itemClickListener.onItemClick(position, item);
				}
				ForumThreadManager.getInstance(getContext()).addReadThreadId(item.tid);
				setThreadReaded(view, true);
			}
		});
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
		forumPostItemAdapter.getListView().setSelection(0);
		performPullingDown(true);
	}

	public void startLoadData() {
		setAdapter(forumPostItemAdapter);
		performPullingDown(true);
	}

	public interface OnItemClickListener {
		void onItemClick(int position, ForumThread item);
	}

	public abstract class ForumPostItemAdapter extends BasePagedItemAdapter<ForumThread> {
		private static final long IMG_MAX_BYTES = 50 * 1024;
		private static final long USER_AVATAR_CACHE_TIME = 12 * 60 * 60 * 1000;
		private static final int IMG_QUALITY = 70;
		private int defaultPicResId;
		private int errorPicResId;
		private int picWidth;
		private int avatarWidth;

		public ForumPostItemAdapter(PullToRequestView view) {
			super(view);
			picWidth = ResHelper.dipToPx(getContext(), 100);
			avatarWidth = ResHelper.dipToPx(getContext(), 25);
			defaultPicResId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_pic");
			errorPicResId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_error_pic");
		}

		protected int getEmptyViewDrawableId() {
			return ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_data");
		}

		protected int getErrorViewDrawableId() {
			return ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_net");
		}

		protected int getEmptyViewStrId() {
			return ResHelper.getStringRes(getContext(), "bbs_empty_view_str");
		}

		protected int getErrorViewStrId() {
			return ResHelper.getStringRes(getContext(), "bbs_error_view_str");
		}

		public void refresh() {
			performPullingDown(true);
		}

		public View getContentView(int position, View convertView, ViewGroup viewGroup) {
			View view = convertView;
			final ViewHolder viewHolder;
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_forum_post_item"), viewGroup, false);
				viewHolder = new ViewHolder(view);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			ForumThread thread = getItem(position);

			if (thread != null) {
				AsyncImageView aivAvatar = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_aivAvatar"));
				aivAvatar.setCompressOptions(avatarWidth, avatarWidth, IMG_QUALITY, IMG_MAX_BYTES);
				aivAvatar.setRound(avatarWidth / 2);
				//设置用户头像仅使用内存缓存而不缓存到磁盘
				aivAvatar.setUseCacheOption(true, true, USER_AVATAR_CACHE_TIME);
				TextView tvAuthor = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewAuthor"));
				//主题
				TextView tvSubject = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewSubject"));
				TextView tvTitle = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewTitle"));
				TextView tvSummary = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewSummary"));

				View viewPicContainer = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_viewPicContainer"));
				final AsyncImageView aivPic = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_aivPic"));
				aivPic.setCompressOptions(picWidth, picWidth, IMG_QUALITY, IMG_MAX_BYTES);
				View viewPicMask = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_viewPicMask"));
				TextView textViewPicNum = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewPicNum"));

				TextView tvPageComment = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewPageComment"));
				TextView textViewPageView = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewPageView"));
				TextView tvTime = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_textViewTime"));
				ImageView ivHead = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_imageViewHeat"));
				ImageView ivEssence = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_imageViewEssence"));
				ImageView ivSticktop = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_forum_post_item_imageViewSticktop"));
				if (isFling()) {
					aivAvatar.execute(null, ResHelper.getBitmapRes(ForumThreadListView.this.getContext(), "bbs_login_account"));
				} else {
					aivAvatar.execute(thread.avatar, ResHelper.getBitmapRes(ForumThreadListView.this.getContext(), "bbs_login_account"));
				}
				if (tvAuthor != null) {
					tvAuthor.setText(thread.author);
				}
				if (tvSubject != null) {
					if(TextUtils.isEmpty(thread.forumName)) {
						tvSubject.setVisibility(GONE);
					} else {
						tvSubject.setVisibility(VISIBLE);
						tvSubject.setText(thread.forumName);
					}
				}
				if (tvTitle != null) {
					tvTitle.setText(thread.subject);
				}
				if (tvSummary != null) {
					tvSummary.setText(thread.summary);
				}
				if (tvPageComment != null) {
					tvPageComment.setText(String.valueOf(thread.replies));
				}
				if (textViewPageView != null) {
					textViewPageView.setText(String.valueOf(thread.views));
				}
				if (tvTime != null) {
					tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(thread.createdOn * 1000));
				}
				if (ivHead != null) {
					if (thread.heatLevel > 0) {
						ivHead.setVisibility(VISIBLE);
					} else {
						ivHead.setVisibility(GONE);
					}
				}
				if(ivEssence != null){
					if(thread.digest > 0) {
						ivEssence.setVisibility(VISIBLE);
					} else {
						ivEssence.setVisibility(GONE);
					}
				}
				if(ivSticktop != null) {
					if(thread.displayOrder > 0) {
						ivSticktop.setVisibility(VISIBLE);
					} else {
						ivSticktop.setVisibility(GONE);
					}
				}

				if (viewPicContainer != null) {
					if (thread.images != null && thread.images.size() > 0) {
						viewPicContainer.setVisibility(View.VISIBLE);
						if (thread.images.size() == 1) {
							viewPicMask.setVisibility(GONE);
							textViewPicNum.setVisibility(GONE);
						} else {
							viewPicMask.setVisibility(VISIBLE);
							textViewPicNum.setVisibility(VISIBLE);
							textViewPicNum.setText("+" + thread.images.size());
						}
						if (aivPic != null) {
							if (isFling()) {
								aivPic.execute(null, defaultPicResId);
							} else {
								aivPic.execute(thread.images.get(0), defaultPicResId, errorPicResId);
							}
						}
					} else {
						viewPicContainer.setVisibility(View.GONE);
					}
				}
				setThreadReaded(view, ForumThreadManager.getInstance(getContext()).isThreadReaded(thread.tid));
			}
			return view;
		}
	}
	//根据是否阅读过设置title和subject的字体颜色
	private static void setThreadReaded(View view, boolean readed) {
		if(view == null) {
			return;
		}
		Context context = view.getContext();
		TextView tvSummary = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_forum_post_item_textViewSummary"));
		TextView tvTitle = (TextView) view.findViewById(ResHelper.getIdRes(context, "bbs_forum_post_item_textViewTitle"));
		if(!readed) {
			if(tvTitle != null) {
				tvTitle.setTextColor(context.getResources().getColor(ResHelper.getColorRes(context, "bbs_postlist_title_color")));
			}
			if(tvSummary != null) {
				tvSummary.setTextColor(context.getResources().getColor(ResHelper.getColorRes(context, "bbs_postlist_summary_color")));
			}
		} else {
			int color = context.getResources().getColor(ResHelper.getColorRes(context, "bbs_threaditem_readed"));
			if(tvTitle != null) {
				tvTitle.setTextColor(color);
			}
			if(tvSummary != null) {
				tvSummary.setTextColor(color);
			}
		}
	}
}
