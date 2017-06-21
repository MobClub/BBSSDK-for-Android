package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.pages.PageForumThread;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.ForumForum;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.gui.ScrollableListView;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 版块列表的View
 */
public class ForumForumView extends PullToRequestView {
	private static final String SHAREDPRE_NAME = "sharedpre_sticktopsubjectlist";
	private static final String SHAREDPRE_KEY_IDS = "forumIds";
	private static final Integer MAX_STICKTOP_COUNT = 8;
	private List<ForumForum> listStickTop;

	private int defaultForumPic;
	private int defaultTotalForumPic;
	private TextView tvStickTop;
	private LinearLayout llTopContainer;
	private MyGridView gvTopView;
	private BaseAdapter topAdapter;
	private boolean isEditMode = false;
	private int lastVisibleItem = 0;
	private OnItemClickListener onItemClickListener;
	private ForumItemAdapter forumItemAdapter;

	public ForumForumView(Context context) {
		this(context, null);
	}

	public ForumForumView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ForumForumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setBackgroundColor(0xFFFFFFFF);
		defaultForumPic = ResHelper.getBitmapRes(getContext(), "bbs_selectsubject_item_default");
		defaultTotalForumPic = ResHelper.getBitmapRes(getContext(), "bbs_selectsubject_item_all");
		initHeaderView(context);
		forumItemAdapter = new ForumItemAdapter(this) {
			protected void onRequest(int page, final RequestCallback callback) {
				BBSSDK.getApi(ForumAPI.class).getForumList(0, false, new APICallback<ArrayList<ForumForum>>() {
					public void onSuccess(API api, int action, ArrayList<ForumForum> result) {
						if (result == null || result.isEmpty()) {
							if (Build.VERSION.SDK_INT <= 17) {
								llTopContainer.setVisibility(View.GONE);
							} else if (forumItemAdapter.getListView().getHeaderViewsCount() > 0) {
								forumItemAdapter.getListView().removeHeaderView(llTopContainer);
							}
							callback.onFinished(true, false, null);
							return;
						}
						if (Build.VERSION.SDK_INT <= 17) {
							llTopContainer.setVisibility(View.VISIBLE);
						} else if (forumItemAdapter.getListView().getHeaderViewsCount() == 0) {
							forumItemAdapter.getListView().addHeaderView(llTopContainer);
						}
						listStickTop = getCachedTopForumList(result);
						refreshTopData();
						callback.onFinished(true, false, result);
					}

					public void onError(API api, int action, int errorCode, Throwable details) {
						if (Build.VERSION.SDK_INT <= 17) {
							llTopContainer.setVisibility(View.GONE);
						} else if (forumItemAdapter.getListView().getHeaderViewsCount() > 0) {
							forumItemAdapter.getListView().removeHeaderView(llTopContainer);
						}
						callback.onFinished(false, false, null);
					}
				});
			}
		};
		forumItemAdapter.setShowPageFooter(false);
		forumItemAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				if (isEditMode) {
					return;
				}
				position -= 1;
				ForumForum forum = forumItemAdapter.getItem(position);
				if (forum == null) {
					return;
				}
				if (onItemClickListener != null) {
					onItemClickListener.onItemClick(forum);
				} else {
					new PageForumThread(forum).show(getContext());
				}
			}
		});
		forumItemAdapter.getListView().setDivider(new ColorDrawable(0x00000000));
		forumItemAdapter.getListView().setSelector(new ColorDrawable(0x00000000));
		forumItemAdapter.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (isEditMode) {
					if (firstVisibleItem == 0 && lastVisibleItem != 0) {
						refreshTopData();
					}
					lastVisibleItem = firstVisibleItem;
				} else {
					lastVisibleItem = 0;
				}
			}
		});
		setAdapter(forumItemAdapter);
	}

	private void initHeaderView(Context context) {
		llTopContainer = new LinearLayout(context);
		llTopContainer.setOrientation(LinearLayout.VERTICAL);
		tvStickTop = new TextView(context);
		tvStickTop.setTextColor(0xffa3a2aa);
		tvStickTop.setPadding(ResHelper.dipToPx(context, 15), 0, 0, 0);
		tvStickTop.setGravity(Gravity.CENTER_VERTICAL);
		tvStickTop.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 12));
		tvStickTop.setText(ResHelper.getStringRes(context, "bbs_subjectsettings_sticksubject"));
		llTopContainer.addView(tvStickTop, ViewGroup.LayoutParams.WRAP_CONTENT, ResHelper.dipToPx(context, 40));

		gvTopView = new MyGridView(context);
		gvTopView.setNumColumns(4);
		gvTopView.setPadding(0, 0, 0, ResHelper.dipToPx(context, 15));
		gvTopView.setHorizontalSpacing(ResHelper.dipToPx(context, 10));
		gvTopView.setVerticalSpacing(ResHelper.dipToPx(context, 15));
		gvTopView.setSelector(new ColorDrawable(0x00000000));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llTopContainer.addView(gvTopView, llp);

		RelativeLayout rlEdit = new RelativeLayout(context);
		rlEdit.setBackgroundColor(0xFFF5F6FA);
		llTopContainer.addView(rlEdit, ViewGroup.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 30));

		TextView tvForumList = new TextView(context);
		tvForumList.setTextColor(0xffa3a2aa);
		tvForumList.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 12));
		tvForumList.setText(ResHelper.getStringRes(context, "bbs_subjectsettings_forumlist"));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.leftMargin = ResHelper.dipToPx(context, 15);
		rlp.addRule(CENTER_VERTICAL, TRUE);
		rlEdit.addView(tvForumList, rlp);

		final TextView tvEdit = new TextView(context);
		tvEdit.setTextColor(0xff1d8ac7);
		tvEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 12));
		tvEdit.setText(ResHelper.getStringRes(context, "bbs_subjectsettings_editsticktop"));
		rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.rightMargin = ResHelper.dipToPx(context, 15);
		rlp.addRule(CENTER_VERTICAL, TRUE);
		rlp.addRule(ALIGN_PARENT_RIGHT, TRUE);
		rlEdit.addView(tvEdit, rlp);

		tvEdit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isEditMode) {
					cacheTopForumList();
					isEditMode = false;
					tvEdit.setText(ResHelper.getStringRes(getContext(), "bbs_subjectsettings_editsticktop"));
					if (forumItemAdapter != null) {
						forumItemAdapter.notifyDataSetChanged();
					}
				} else {
					isEditMode = true;
					tvEdit.setText(ResHelper.getStringRes(getContext(), "bbs_subjectsettings_finishedit"));
					if (forumItemAdapter != null) {
						forumItemAdapter.notifyDataSetChanged();
					}
				}
			}
		});

		gvTopView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (topAdapter != null) {
					ForumForum forum = (ForumForum) topAdapter.getItem(position);
					if (forum != null) {
						if (onItemClickListener != null) {
							onItemClickListener.onItemClick(forum);
						} else {
							new PageForumThread(forum).show(getContext());
						}
					}
				}
			}
		});
	}

	/**
	 * 设置帖子项的点击回调。
	 *
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		onItemClickListener = listener;
	}

	public void loadData() {
		performPullingDown(true);
	}

	private void refreshTopData() {
		if (topAdapter == null) {
			topAdapter = new BaseAdapter() {
				public int getCount() {
					return listStickTop == null ? 0 : listStickTop.size();
				}

				public ForumForum getItem(int position) {
					return (listStickTop == null || position >= listStickTop.size()) ? null : listStickTop.get(position);
				}

				public long getItemId(int position) {
					return position;
				}

				public View getView(int position, View convertView, ViewGroup parent) {
					LinearLayout llContent = new LinearLayout(getContext());
					llContent.setOrientation(LinearLayout.VERTICAL);
					final AsyncImageView aivIcon = new AsyncImageView(getContext());
					int aivIconWidth = ResHelper.dipToPx(getContext(), 40);
					LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(aivIconWidth, aivIconWidth);
					llp.gravity = Gravity.CENTER_HORIZONTAL;
					llContent.addView(aivIcon, llp);

					TextView tvForumName = new TextView(getContext());
					tvForumName.setTextColor(0xff3a4045);
					tvForumName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(getContext(), 14));
					tvForumName.setEllipsize(TextUtils.TruncateAt.MIDDLE);
					tvForumName.setSingleLine();
					llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					llp.gravity = Gravity.CENTER_HORIZONTAL;
					llp.topMargin = ResHelper.dipToPx(getContext(), 8);
					llContent.addView(tvForumName, llp);

					ForumForum forum = getItem(position);
					if (forum != null) {
						tvForumName.setText(forum.name);
						if (!TextUtils.isEmpty(forum.forumPic)) {
							aivIcon.setRound(ResHelper.dipToPx(getContext(), 5));
							aivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
						}
						aivIcon.execute(forum.forumPic, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
					} else {
						aivIcon.setImageResource(defaultForumPic);
					}
					return llContent;
				}
			};
			gvTopView.setAdapter(topAdapter);
		}
		if (topAdapter.getCount() == 0) {
			gvTopView.setVisibility(View.GONE);
		} else {
			gvTopView.setVisibility(View.VISIBLE);
		}
		if(listStickTop != null && listStickTop.size() > 0) {
			if(tvStickTop != null) {
				tvStickTop.setVisibility(VISIBLE);
			}
		} else {
			if(tvStickTop != null) {
				tvStickTop.setVisibility(GONE);
			}
		}
		topAdapter.notifyDataSetChanged();
	}

	private List<ForumForum> getCachedTopForumList(ArrayList<ForumForum> forumList) {
		List<ForumForum> cachedList = new ArrayList<ForumForum>();
		SharePrefrenceHelper spHelper = new SharePrefrenceHelper(getContext().getApplicationContext());
		spHelper.open(SHAREDPRE_NAME);
		ArrayList<Long> ids = ResHelper.forceCast(spHelper.get(SHAREDPRE_KEY_IDS), null);
		if (ids != null && !ids.isEmpty()) {
			for (long id : ids) {
				for (ForumForum forum : forumList) {
					if (forum != null && forum.fid == id) {
						cachedList.add(forum);
						break;
					}
				}
			}
		}
		return cachedList;
	}

	private void cacheTopForumList() {
		SharePrefrenceHelper spHelper = new SharePrefrenceHelper(getContext().getApplicationContext());
		spHelper.open(SHAREDPRE_NAME);
		if (listStickTop != null && listStickTop.size() > 0) {
			List<Long> ids = new ArrayList<Long>();
			for (ForumForum forum : listStickTop) {
				if (forum != null) {
					ids.add(forum.fid);
				}
			}
			spHelper.put(SHAREDPRE_KEY_IDS, ids);
		} else {
			spHelper.remove(SHAREDPRE_KEY_IDS);
		}
	}

	private boolean addStickTopItemToView(ForumForum forum) {
		if (listStickTop.size() >= MAX_STICKTOP_COUNT) {
			return false;
		}
		for (ForumForum item : listStickTop) {
			if (item.fid == forum.fid) {
				return false;
			}
		}
		listStickTop.add(forum);
		refreshTopData();
		return true;
	}

	protected boolean removeStickTopItem(ForumForum forum) {
		if (listStickTop.size() <= 0) {
			return false;
		}
		ForumForum itemFound = null;
		for (ForumForum item : listStickTop) {
			if (item.fid == forum.fid) {
				itemFound = item;
				break;
			}
		}
		if (itemFound != null) {
			listStickTop.remove(itemFound);
			refreshTopData();
			return true;
		}
		return false;
	}

	public interface OnItemClickListener {
		/**
		 *
		 * @param forum
		 */
		void onItemClick(ForumForum forum);
	}

	public abstract class ForumItemAdapter extends BasePagedItemAdapter<ForumForum> {
		public ForumItemAdapter(PullToRequestView view) {
			super(view);
		}

		//兼容sdk 17以下的listView不能在setAdapter之后调用addHeaderView方法的问题
		protected ScrollableListView onNewListView(Context context) {
			ScrollableListView listView = super.onNewListView(context);
			if (Build.VERSION.SDK_INT <= 17 && listView != null && listView.getHeaderViewsCount() == 0) {
				listView.addHeaderView(llTopContainer);
				llTopContainer.setVisibility(View.GONE);
			}
			return listView;
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
				view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_subject_list_item"), viewGroup, false);
				viewHolder = new ViewHolder(view);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			final ForumForum forum = getItem(position);
			if (forum != null) {
				AsyncImageView aivIcon = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_asyImageView"));
				TextView tvTitle = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewTitle"));
				TextView tvLable = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewLabel"));
				TextView tvDes = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewDes"));
				final TextView tvStick = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewStick"));
				final TextView tvUnStick = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewUnstick"));
				View vEdit = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewEdit"));
				if (isFling()) {
					aivIcon.setRound(0);
					aivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
					aivIcon.execute(null, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
				} else {
					if (!TextUtils.isEmpty(forum.forumPic)) {
						aivIcon.setRound(ResHelper.dipToPx(getContext(), 5));
						aivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
					}
					aivIcon.execute(forum.forumPic, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
				}
				tvTitle.setText(forum.name);
				tvDes.setText(forum.description);
				if (isEditMode) {
					vEdit.setVisibility(View.VISIBLE);
					boolean found = false;
					for (ForumForum item : listStickTop) {
						if (item.fid == forum.fid) {
							tvUnStick.setVisibility(VISIBLE);
							tvStick.setVisibility(GONE);
							found = true;
							break;
						}
					}
					if (!found) {
						tvUnStick.setVisibility(GONE);
						tvStick.setVisibility(VISIBLE);
					}
				} else {
					vEdit.setVisibility(View.GONE);
				}
				tvStick.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (addStickTopItemToView(forum)) {
							tvStick.setVisibility(GONE);
							tvUnStick.setVisibility(VISIBLE);
						} else {
							ToastUtils.showToast(getContext(), getResources().getString(
									ResHelper.getStringRes(getContext(), "bbs_pagesubjectview_cantadd_toomanyitems")));
						}
					}
				});
				tvUnStick.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						removeStickTopItem(forum);
						tvStick.setVisibility(VISIBLE);
						tvUnStick.setVisibility(GONE);
					}
				});
			}
			return view;
		}
	}

	public class MyGridView extends GridView {
		public MyGridView(Context context) {
			super(context);
		}

		public MyGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public MyGridView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		}
	}
}
