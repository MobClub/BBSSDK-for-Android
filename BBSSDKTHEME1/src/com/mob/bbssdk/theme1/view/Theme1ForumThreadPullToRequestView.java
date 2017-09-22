package com.mob.bbssdk.theme1.view;


import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.datadef.ThreadListOrderType;
import com.mob.bbssdk.gui.datadef.ThreadListSelectType;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;


public class Theme1ForumThreadPullToRequestView extends BBSPullToRequestView<ForumThread> {

	private ThreadListOrderType orderType = ThreadListOrderType.CREATE_ON;
	private ThreadListSelectType selectType = ThreadListSelectType.LATEST;
	public ViewGroup layoutTab;
	private ViewGroup layoutTitle;
	private ImageView imageViewDropDown;
	private View viewMarkLatest;
	private View viewMarkHot;
	private View viewMarkEssence;
	private View viewMarkSticktop;
	private TextView textViewLatest;
	private TextView textViewHot;
	private TextView textViewEssence;
	private TextView textViewSticktop;
	private ForumForum forumForum;
	private GlideImageView imageViewBackground;
	private TextView textViewTitle;
	private TextView textViewDescription;
	private GlideImageView imageViewIcon;
	ForumThreadUpdateListner forumThreadUpdateListner;

	public Theme1ForumThreadPullToRequestView(Context context) {
		super(context);
	}

	public Theme1ForumThreadPullToRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1ForumThreadPullToRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initData(ForumForum forum) {
		this.forumForum = forum;
	}

	@Override
	protected View getContentView(int position, View convertView, ViewGroup parent) {
		Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_forumthread");
		final ForumThread forumthread = getItem(position);
		final View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(forumthread, convertView, parent, layout);
		TextView textViewTime = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewMainViewThreadTime"));
		if (textViewTime != null) {
			textViewTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(getContext(), forumthread.createdOn));
		}
		TextView textViewPageLike = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewPageLike"));
		if (textViewPageLike != null) {
			textViewPageLike.setText("" + forumthread.recommendadd);
		}
		ListViewItemBuilder.getInstance().setThreadViewClickListener(forumthread, view);
		return view;
	}

	@Override
	protected View getContentHeader(ViewGroup viewGroup, View viewprev) {
		if(viewprev != null) {
			return viewprev;
		}
		View view  = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_header_forumthread"), viewGroup, false);
		layoutTitle = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutTitle"));
		imageViewDropDown = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewDropDown"));
		layoutTab = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutTab"));
		viewMarkLatest = view.findViewById(ResHelper.getIdRes(getContext(), "viewMarkLatest"));
		viewMarkHot = view.findViewById(ResHelper.getIdRes(getContext(), "viewMarkHot"));
		viewMarkEssence = view.findViewById(ResHelper.getIdRes(getContext(), "viewMarkEssence"));
		viewMarkSticktop = view.findViewById(ResHelper.getIdRes(getContext(), "viewMarkSticktop"));

		textViewLatest = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewLatest"));
		textViewLatest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(ThreadListSelectType.LATEST);
			}
		});
		textViewHot = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewHot"));
		textViewHot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(ThreadListSelectType.HEATS);
			}
		});
		textViewEssence = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewEssence"));
		textViewEssence.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(ThreadListSelectType.DIGEST);
			}
		});
		textViewSticktop = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewSticktop"));
		textViewSticktop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(ThreadListSelectType.DISPLAY_ORDER);
			}
		});

		imageViewBackground = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewBackground"));
		textViewTitle = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewTitle"));
		textViewDescription = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewDescription"));
		imageViewIcon = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewIcon"));
		imageViewIcon.setExecuteRound();
		if (forumForum != null) {
			if (forumForum.name != null) {
				textViewTitle.setText(forumForum.name);
			}
			if (forumForum.description != null) {
				textViewDescription.setText(Html.fromHtml(forumForum.description));
			}
			imageViewBackground.execute(forumForum.forumBigPic);
			imageViewIcon.execute(forumForum.forumPic);
		}
		layoutTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popFilterMenu(imageViewDropDown);
			}
		});
		return view;
	}

	public void popFilterMenu(View rootview) {
		int style = ResHelper.getStyleRes(getContext(), "BBS_PopupMenu");
		Context wrapper = new ContextThemeWrapper(getContext(), style);
		//Creating the instance of PopupMenu
		PopupMenu popup = new PopupMenu(wrapper, rootview);
		//Inflating the Popup using xml file
		popup.getMenuInflater().inflate(PageForumThreadDetail.getMenuRes(getContext(), "bbs_popup_forumthread"), popup.getMenu());

		//registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				//only one menu item.
				if (item.getItemId() == ResHelper.getIdRes(getContext(), "action_arrangebycomment")) {
					orderType = ThreadListOrderType.LAST_POST;
					performPullingDown(true);
				} else if (item.getItemId() == ResHelper.getIdRes(getContext(), "action_arrangebypost")) {
					orderType = ThreadListOrderType.CREATE_ON;
					performPullingDown(true);
				}
				return true;
			}
		});
		popup.show();
	}

	public void clickTab(ThreadListSelectType type) {
		this.selectType = type;
		updateTabView(viewMarkLatest, viewMarkHot, viewMarkEssence, viewMarkSticktop);
		if (forumThreadUpdateListner != null) {
			forumThreadUpdateListner.OnTabUpdated(this.selectType);
		}
		getBasePagedItemAdapter().getDataSet().clear();
		refreshQuiet();
	}

	public void updateTabView(View latest, View hot, View essence, View sticktop) {
		if (this.selectType == null) {
			return;
		}
		if (this.selectType == ThreadListSelectType.LATEST) {
			latest.setVisibility(VISIBLE);
			hot.setVisibility(INVISIBLE);
			essence.setVisibility(INVISIBLE);
			sticktop.setVisibility(INVISIBLE);
		} else if (this.selectType == ThreadListSelectType.HEATS) {
			latest.setVisibility(INVISIBLE);
			hot.setVisibility(VISIBLE);
			essence.setVisibility(INVISIBLE);
			sticktop.setVisibility(INVISIBLE);
		} else if (this.selectType == ThreadListSelectType.DIGEST) {
			latest.setVisibility(INVISIBLE);
			hot.setVisibility(INVISIBLE);
			essence.setVisibility(VISIBLE);
			sticktop.setVisibility(INVISIBLE);
		} else if (this.selectType == ThreadListSelectType.DISPLAY_ORDER) {
			latest.setVisibility(INVISIBLE);
			hot.setVisibility(INVISIBLE);
			essence.setVisibility(INVISIBLE);
			sticktop.setVisibility(VISIBLE);
		}
	}

	@Override
	protected void init() {
		super.init();
		setHaveContentHeader(true);
		setOnRequestListener(new BBSPullToRequestView.OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				ForumAPI contentAPI = BBSSDK.getApi(ForumAPI.class);
				contentAPI.getThreadListByForumId(forumForum.fid, selectType.getValue(), orderType.getValue(), page, nDefaultLoadOnceCount, false,
						new APICallback<ArrayList<ForumThread>>() {
							@Override
							public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
								callback.onFinished(true, (result != null && result.size() == nDefaultLoadOnceCount), result);
							}

							@Override
							public void onError(API api, int action, int errorCode, Throwable details) {
								callback.onFinished(false, false, null);
							}
						});
			}
		});
	}

	public void setForumThreadUpdateListner(ForumThreadUpdateListner listener) {
		this.forumThreadUpdateListner = listener;
	}

	public interface ForumThreadUpdateListner {
		void OnTabUpdated(ThreadListSelectType tab);
	}
}
