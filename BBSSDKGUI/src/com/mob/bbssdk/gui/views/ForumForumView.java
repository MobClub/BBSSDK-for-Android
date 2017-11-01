package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.utils.StringUtils;
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
	protected int defaultForumPic;
	protected int defaultTotalForumPic;
	protected LinearLayout layoutStickTop;
	protected TextView textViewEditStickTop;
	private LinearLayout llTopContainer;
	protected boolean isEditMode = false;
	private int lastVisibleItem = 0;
	private OnItemClickListener onItemClickListener;

	private ForumFormGridView gridViewStickTop;
	protected List<ForumForum> listStickTop;
	private BaseAdapter adapterStickTop;

	protected ForumAdapter adapterForum;
	protected ArrayList<ForumForum> listForum;

	protected ArrayList<ForumForum> listForumAll;

	@Override
	protected float getTopFadingEdgeStrength() {
		return super.getTopFadingEdgeStrength();
	}

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

	protected void init(Context context) {
		setBackgroundColor(0xFFFFFFFF);
		defaultForumPic = ResHelper.getBitmapRes(getContext(), "bbs_selectsubject_item_default");
		defaultTotalForumPic = ResHelper.getBitmapRes(getContext(), "bbs_selectsubject_item_all");

		View view = buildMainLayoutView();
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_view_forumforum"), null);
		}
		llTopContainer = (LinearLayout) view.findViewById(ResHelper.getIdRes(getContext(), "layoutContainer"));
		gridViewStickTop = (ForumFormGridView) view.findViewById(ResHelper.getIdRes(getContext(), "myGridView"));
		layoutStickTop = (LinearLayout) view.findViewById(ResHelper.getIdRes(getContext(), "layoutStickTop"));
		textViewEditStickTop = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewEditStickTop"));
		textViewEditStickTop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isEditMode) {
					cacheTopForumList();
					isEditMode = false;
					textViewEditStickTop.setText(ResHelper.getStringRes(getContext(), "bbs_subjectsettings_editsticktop"));
					if (adapterForum != null) {
						adapterForum.notifyDataSetChanged();
					}
				} else {
					isEditMode = true;
					textViewEditStickTop.setText(ResHelper.getStringRes(getContext(), "bbs_subjectsettings_finishedit"));
					if (adapterForum != null) {
						adapterForum.notifyDataSetChanged();
					}
				}
				refreshListViews();
			}
		});

		if (gridViewStickTop != null) {
			gridViewStickTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (adapterStickTop != null) {
						ForumForum forum = (ForumForum) adapterStickTop.getItem(position);
						if (forum != null) {
							if (onItemClickListener != null) {
								onItemClickListener.onItemClick(forum);
							} else {
								PageForumThread page = BBSViewBuilder.getInstance().buildPageForumThread();
								page.initPage(forum);
								page.show(getContext());
							}
						}
					}
				}
			});
			adapterStickTop = new StickTopAdapter();
			gridViewStickTop.setAdapter(adapterStickTop);
		}
		adapterForum = new ForumAdapter(this);
		adapterForum.setShowPageFooter(false);
		adapterForum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				if (isEditMode) {
					return;
				}
				position -= 1;
				ForumForum forum = adapterForum.getItem(position);
				if (forum == null) {
					return;
				}
				if (onItemClickListener != null) {
					onItemClickListener.onItemClick(forum);
				} else {
					PageForumThread page = BBSViewBuilder.getInstance().buildPageForumThread();
					page.initPage(forum);
					page.show(getContext());
				}
			}
		});
		adapterForum.getListView().setDivider(new ColorDrawable(0x00000000));
		adapterForum.getListView().setSelector(new ColorDrawable(0x00000000));
		adapterForum.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				if (isEditMode) {
//					if (firstVisibleItem == 0 && lastVisibleItem != 0) {
//						refreshStickTopView();
//					}
//					lastVisibleItem = firstVisibleItem;
//				} else {
//					lastVisibleItem = 0;
//				}
			}
		});
		listForum = adapterForum.getDataSet();
		setAdapter(adapterForum);
	}

	protected View buildMainLayoutView() {
		return null;
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

	protected void refreshListViews() {
		refreshForumView();
		refreshStickTopView();
	}

	protected void refreshForumView() {
		adapterForum.notifyDataSetChanged();
	}

	protected void refreshStickTopView() {
		if (listStickTop.size() == 0) {
			gridViewStickTop.setVisibility(View.GONE);
		} else {
			gridViewStickTop.setVisibility(View.VISIBLE);
		}
		if (listStickTop != null && listStickTop.size() > 0) {
			if (layoutStickTop != null) {
				layoutStickTop.setVisibility(VISIBLE);
			}
		} else {
			if (layoutStickTop != null) {
				layoutStickTop.setVisibility(GONE);
			}
		}
		adapterStickTop.notifyDataSetChanged();
	}

	protected boolean addStickTopItemToView(ForumForum forum) {
		if (listStickTop.size() >= MAX_STICKTOP_COUNT) {
			return false;
		}
		//add the item to stick top list.
		for (ForumForum item : listStickTop) {
			if (item.fid == forum.fid) {
				return false;
			}
		}
		listStickTop.add(forum);
		refreshListViews();
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
			refreshListViews();
			return true;
		}
		return false;
	}

	public interface OnItemClickListener {
		void onItemClick(ForumForum forum);
	}

	protected View buildForumItemView(ForumForum forum, ViewGroup viewGroup) {
		return null;
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

	public class StickTopAdapter extends BaseAdapter {
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
			final GlideImageView aivIcon = new GlideImageView(getContext());
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
					aivIcon.setExecuteRound(ResHelper.dipToPx(getContext(), 5));
					aivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
				}
				aivIcon.execute(forum.forumPic, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
			} else {
				aivIcon.setImageResource(defaultForumPic);
			}
			return llContent;
		}
	}

	protected void OnForumItemViewCreated(ForumForum forum, View view) {

	}

	public class ForumAdapter extends BasePagedItemAdapter<ForumForum> {
		public ForumAdapter(PullToRequestView view) {
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

		public void refresh() {
			performPullingDown(true);
		}

		@Override
		public void setLoadEmpty(boolean isError) {
			emptyView.setVisibility(View.GONE);
		}

		protected void onRequest(int page, final RequestCallback callback) {
			BBSSDK.getApi(ForumAPI.class).getForumList(0, false, new APICallback<ArrayList<ForumForum>>() {
				public void onSuccess(API api, int action, ArrayList<ForumForum> result) {
					if (result == null || result.isEmpty()) {
						if (Build.VERSION.SDK_INT <= 17) {
							llTopContainer.setVisibility(View.GONE);
						} else if (adapterForum.getListView().getHeaderViewsCount() > 0) {
							adapterForum.getListView().removeHeaderView(llTopContainer);
						}
						callback.onFinished(true, false, null);
						return;
					}
					if (Build.VERSION.SDK_INT <= 17) {
						llTopContainer.setVisibility(View.VISIBLE);
					} else if (adapterForum.getListView().getHeaderViewsCount() == 0) {
						adapterForum.getListView().addHeaderView(llTopContainer);
					}
					listStickTop = getCachedTopForumList(result);
					listForumAll = result;
					callback.onFinished(true, false, result);
					refreshStickTopView();
				}

				public void onError(API api, int action, int errorCode, Throwable details) {
					if (Build.VERSION.SDK_INT <= 17) {
						llTopContainer.setVisibility(View.GONE);
					} else if (adapterForum.getListView().getHeaderViewsCount() > 0) {
						adapterForum.getListView().removeHeaderView(llTopContainer);
					}
					callback.onFinished(false, false, null);
				}
			});
		}

		public View getContentView(int position, View convertView, ViewGroup viewGroup) {
			View view = convertView;
			final ViewHolder viewHolder;
			if (view == null) {
				view = buildForumItemView(getItem(position), viewGroup);
				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_item_forumsetting"), viewGroup, false);
				}
				viewHolder = new ViewHolder(view);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			final ForumForum forum = getItem(position);
			if (forum != null) {
				GlideImageView aivIcon = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_asyImageView"));
				TextView tvTitle = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewTitle"));
				TextView tvLable = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewLabel"));
				TextView tvDes = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_textViewDes"));
				final View viewStick = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewStick"));
				final View viewUnstick = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewUnstick"));
				View vEdit = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewEdit"));
				View viewDivider = viewHolder.getView(ResHelper.getIdRes(getContext(), "bbs_subject_listitem_viewDivider"));

//				if (isFling()) {
//					aivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
//					aivIcon.execute(null, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
//				} else {
				if (!TextUtils.isEmpty(forum.forumPic)) {
					aivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
				}
				aivIcon.execute(forum.forumPic, forum.fid == 0 ? defaultTotalForumPic : defaultForumPic);
//				}
				tvTitle.setText(forum.name);
				if(StringUtils.isEmpty(forum.description)) {
					forum.description = "";
				}
				tvDes.setText(Html.fromHtml(forum.description));
				if (isEditMode) {
					vEdit.setVisibility(View.VISIBLE);
					boolean found = false;
					for (ForumForum item : listStickTop) {
						if (item.fid == forum.fid) {
							viewUnstick.setVisibility(VISIBLE);
							viewStick.setVisibility(GONE);
							found = true;
							break;
						}
					}
					if (!found) {
						viewUnstick.setVisibility(GONE);
						viewStick.setVisibility(VISIBLE);
					}
				} else {
					vEdit.setVisibility(View.GONE);
				}
				viewStick.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (addStickTopItemToView(forum)) {
							viewStick.setVisibility(GONE);
							viewUnstick.setVisibility(VISIBLE);
						} else {
							ToastUtils.showToast(getContext(), getResources().getString(
									ResHelper.getStringRes(getContext(), "bbs_pagesubjectview_cantadd_toomanyitems")));
						}
					}
				});
				viewUnstick.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						removeStickTopItem(forum);
						viewStick.setVisibility(VISIBLE);
						viewUnstick.setVisibility(GONE);
					}
				});
			}
			OnForumItemViewCreated(forum, view);
			return view;
		}
	}
}
