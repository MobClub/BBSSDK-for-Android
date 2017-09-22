package com.mob.bbssdk.gui.builder;


import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.utils.AppUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.FavoriteThread;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.Notification;
import com.mob.tools.utils.ResHelper;

import java.lang.reflect.Field;


public class ListViewItemBuilder {
	private static final String TAG = "ListViewItemBuilder";
	private static ListViewItemBuilder listViewItemBuilder;

	public static synchronized void init(ListViewItemBuilder viewbuilder) {
		if (listViewItemBuilder != null) {
			throw new IllegalAccessError("You can only init listViewItemBuilder once!");
		}
		if (viewbuilder == null) {
			listViewItemBuilder = new ListViewItemBuilder();
		} else {
			listViewItemBuilder = viewbuilder;
		}
	}

	public static ListViewItemBuilder getInstance() {
		if (listViewItemBuilder == null) {
			init(null);
		}
		return listViewItemBuilder;
	}

	public ListViewItemBuilder() {
	}

	protected Integer getThreadItemLayoutId(Context context) {
		return null;
	}

	protected Integer getFavoriteItemLayoutId(Context context) {
		return null;
	}

	protected Integer getMessageItemLayoutId(Context context) {
		return null;
	}

	static <T extends View> T findViewByResName(View view, String resname) {
		return (T) view.findViewById(ResHelper.getIdRes(view.getContext(), resname));
	}

	public View buildListItemView(Object item, View convertview, ViewGroup viewgroup) {
		if (item instanceof ForumThread) {
			View view = buildThreadView((ForumThread) item, convertview, viewgroup);
			setThreadViewClickListener((ForumThread) item, view);
			return view;
		} else if (item instanceof FavoriteThread) {
			View view = buildFavoriteView((FavoriteThread) item, convertview, viewgroup);
			setFavoriteViewClickListener((FavoriteThread) item, view);
			return view;
		} else if(item instanceof Notification) {
			View view = buildMessageView((Notification) item, convertview, viewgroup);
			setMessageViewClickListener((Notification) item, view);
			return view;
		} else {
			Context context = viewgroup.getContext();
			return new View(context);
//			throw new IllegalArgumentException("Doesn't support data type: " + item.getClass().getName() + "!");
		}
	}

	public void setMessageViewClickListener(final Notification item, final View view) {

	}

	public void setThreadViewClickListener(final ForumThread item, final View view) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (item != null) {
					PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
					pageForumThreadDetail.setForumThread(item);
					pageForumThreadDetail.show(view.getContext());
				}
			}
		});
	}

	public void setFavoriteViewClickListener(final FavoriteThread item, final View view) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (item != null) {
					PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
					pageForumThreadDetail.setForumThread(item.fid, item.tid, item.author);
					pageForumThreadDetail.show(view.getContext());
				}
			}
		});
	}

	public View buildThreadView(ForumThread thread, View convertview, ViewGroup viewgroup) {
		Integer layoutid = getThreadItemLayoutId(viewgroup.getContext());
		return buildLayoutThreadView(thread, convertview, viewgroup, layoutid);
	}

	public View buildLayoutThreadView(ForumThread thread, View convertview, ViewGroup viewgroup, Integer layoutid) {
		Context context = viewgroup.getContext();
		if (layoutid == null) {
			layoutid = ResHelper.getLayoutRes(context, "bbs_item_defaultthread");
		}
		ThreadViewHolder viewholder;
		if (convertview == null || convertview.getTag() == null || !(convertview.getTag() instanceof ThreadViewHolder)) {
			convertview = LayoutInflater.from(context).inflate(layoutid, viewgroup, false);
			viewholder = new ThreadViewHolder();
			convertview.setTag(viewholder);
			viewholder.layoutHeader = findViewByResName(convertview, "bbs_item_forumpost_layoutHeader");
			viewholder.layoutCommentView = findViewByResName(convertview, "bbs_item_forumpost_layoutCommentView");
			viewholder.aivAvatar = findViewByResName(convertview, "bbs_item_forumpost_aivAvatar");
			viewholder.tvAuthor = findViewByResName(convertview, "bbs_item_forumpost_textViewAuthor");
			viewholder.tvSubject = findViewByResName(convertview, "bbs_item_forumpost_textViewSubject");
			viewholder.tvTitle = findViewByResName(convertview, "bbs_item_forumpost_textViewTitle");
			viewholder.tvSummary = findViewByResName(convertview, "bbs_item_forumpost_textViewSummary");
			viewholder.viewPicContainer = findViewByResName(convertview, "bbs_item_forumpost_viewPicContainer");
			viewholder.aivPic = findViewByResName(convertview, "bbs_item_forumpost_aivPic");
			viewholder.viewPicMask = findViewByResName(convertview, "bbs_item_forumpost_viewPicMask");
			viewholder.textViewPicNum = findViewByResName(convertview, "bbs_item_forumpost_textViewPicNum");
			viewholder.tvPageComment = findViewByResName(convertview, "bbs_item_forumpost_textViewPageComment");
			viewholder.textViewPageView = findViewByResName(convertview, "bbs_item_forumpost_textViewPageView");
			viewholder.tvLeftTime = findViewByResName(convertview, "bbs_item_forumpost_textViewLeftTime");
			viewholder.tvRightTime = findViewByResName(convertview, "bbs_item_forumpost_textViewRightTime");
			viewholder.ivHead = findViewByResName(convertview, "bbs_item_forumpost_imageViewHeat");
			viewholder.ivEssence = findViewByResName(convertview, "bbs_item_forumpost_imageViewEssence");
			viewholder.ivSticktop = findViewByResName(convertview, "bbs_item_forumpost_imageViewSticktop");
			viewholder.layoutLabel = findViewByResName(convertview, "bbs_item_forumpost_layoutLabel");
			viewholder.imageViewPageCommentLabel = findViewByResName(convertview, "bbs_item_forumpost_imageViewPageCommentLabel");
			viewholder.imageViewPageViewLabel = findViewByResName(convertview, "bbs_item_forumpost_imageViewPageViewLabel");
			ensureNoNullField(ThreadViewHolder.class, viewholder);
		} else {
			viewholder = (ThreadViewHolder) convertview.getTag();
		}
		int defaultPicResId = ResHelper.getBitmapRes(context, "bbs_ic_def_no_pic");
		int errorPicResId = ResHelper.getBitmapRes(context, "bbs_ic_def_error_pic");
		try {
			viewholder.aivAvatar.setExecuteRound();//小头像圆形
			//设置用户头像仅使用内存缓存而不缓存到磁盘
			viewholder.aivAvatar.execute(thread.avatar, ResHelper.getBitmapRes(context, "bbs_login_account"));
			loadHtml(viewholder.tvAuthor, thread.author);
			if (TextUtils.isEmpty(thread.forumName)) {
				viewholder.tvSubject.setVisibility(View.GONE);
			} else {
				viewholder.tvSubject.setVisibility(View.VISIBLE);
				viewholder.tvSubject.setText(thread.forumName);
			}
			loadHtml(viewholder.tvTitle, thread.subject);
			loadHtml(viewholder.tvSummary, thread.summary);
			viewholder.tvPageComment.setText(String.valueOf(thread.replies));
			viewholder.textViewPageView.setText(String.valueOf(thread.views));
			viewholder.tvLeftTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(context, thread.createdOn));
			viewholder.tvRightTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(context, thread.createdOn));
			if (thread.heatLevel > 0) {
				viewholder.ivHead.setVisibility(View.VISIBLE);
			} else {
				viewholder.ivHead.setVisibility(View.GONE);
			}
			if (thread.digest > 0) {
				viewholder.ivEssence.setVisibility(View.VISIBLE);
			} else {
				viewholder.ivEssence.setVisibility(View.GONE);
			}
			if (thread.displayOrder > 0) {
				viewholder.ivSticktop.setVisibility(View.VISIBLE);
			} else {
				viewholder.ivSticktop.setVisibility(View.GONE);
			}

			if (thread.images != null && thread.images.size() > 0) {
				viewholder.viewPicContainer.setVisibility(View.VISIBLE);
				if (thread.images.size() == 1) {
					viewholder.viewPicMask.setVisibility(View.GONE);
					viewholder.textViewPicNum.setVisibility(View.GONE);
				} else {
					viewholder.viewPicMask.setVisibility(View.VISIBLE);
					viewholder.textViewPicNum.setVisibility(View.VISIBLE);
					viewholder.textViewPicNum.setText("+" + thread.images.size());
				}
				if (viewholder.aivPic != null) {
					viewholder.aivPic.execute(thread.images.get(0), defaultPicResId, errorPicResId);
				}
			} else {
				viewholder.viewPicContainer.setVisibility(View.GONE);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertview;
	}

	public View buildMessageView(Notification item, View convertview, ViewGroup viewgroup) {
		Context context = viewgroup.getContext();
		MessageViewHolder viewholder;
		if (convertview == null || convertview.getTag() == null || !(convertview.getTag() instanceof MessageViewHolder)) {
			Integer layoutid = getMessageItemLayoutId(context);
			if (layoutid == null) {
				layoutid = ResHelper.getLayoutRes(context, "bbs_item_message");
			}
			convertview = LayoutInflater.from(context).inflate(layoutid, viewgroup, false);
			viewholder = new MessageViewHolder();
			convertview.setTag(viewholder);
			viewholder.aivTitle = (GlideImageView) convertview.findViewById(ResHelper.getIdRes(context, "aivTitle"));
			viewholder.aivTitle.setExecuteRound();
			viewholder.textViewTitleName = (TextView) convertview.findViewById(ResHelper.getIdRes(context, "textViewTitleName"));
			viewholder.textViewTitleContent = (TextView) convertview.findViewById(ResHelper.getIdRes(context, "textViewTitleContent"));
			viewholder.textViewBodyName = (TextView) convertview.findViewById(ResHelper.getIdRes(context, "textViewBodyName"));
			viewholder.textViewBodyContent = (TextView) convertview.findViewById(ResHelper.getIdRes(context, "textViewBodyContent"));
			viewholder.textViewTime = (TextView) convertview.findViewById(ResHelper.getIdRes(context, "textViewTime"));
			viewholder.viewUnreadedMark = convertview.findViewById(ResHelper.getIdRes(context, "viewUnreadedMark"));
			ensureNoNullField(MessageViewHolder.class, viewholder);
		} else {
			viewholder = (MessageViewHolder) convertview.getTag();
		}
		try {
			if (item.isnew <= 0) {
				viewholder.viewUnreadedMark.setVisibility(View.GONE);
			} else {
				viewholder.viewUnreadedMark.setVisibility(View.VISIBLE);
			}

			viewholder.textViewTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(context, item.dateline));
			switch (item.getType()) {
				case COMMENT: {
					viewholder.aivTitle.setImageResource(ResHelper.getBitmapRes(context, "bbs_message_comment"));
					viewholder.textViewTitleName.setText(item.author);
					viewholder.textViewTitleContent.setText(ResHelper.getStringRes(context, "bbs_message_comment"));
					viewholder.textViewBodyContent.setText(Html.fromHtml(item.title));

					viewholder.textViewTitleName.setVisibility(View.VISIBLE);
					viewholder.textViewTitleContent.setVisibility(View.VISIBLE);
					viewholder.textViewBodyName.setVisibility(View.GONE);
					viewholder.textViewBodyContent.setVisibility(View.VISIBLE);
				} break;
				case SYSTEM: {
					viewholder.aivTitle.setImageResource(ResHelper.getBitmapRes(context, "bbs_message_system"));
					viewholder.textViewTitleName.setText(ResHelper.getStringRes(context, "bbs_message_system"));
					viewholder.textViewBodyContent.setText(item.note == null ? "" : Html.fromHtml(item.note));

					viewholder.textViewTitleName.setVisibility(View.VISIBLE);
					viewholder.textViewTitleContent.setVisibility(View.GONE);
					viewholder.textViewBodyName.setVisibility(View.GONE);
					viewholder.textViewBodyContent.setVisibility(View.VISIBLE);
				} break;
				case MOB_LIKE: {
					viewholder.aivTitle.execute(item.avatar);
					viewholder.textViewTitleName.setText(item.author);
					viewholder.textViewTitleContent.setText(ResHelper.getStringRes(context, "bbs_message_like"));
					viewholder.textViewBodyContent.setText(item.title);

					viewholder.textViewTitleName.setVisibility(View.VISIBLE);
					viewholder.textViewTitleContent.setVisibility(View.VISIBLE);
					viewholder.textViewBodyName.setVisibility(View.GONE);
					viewholder.textViewBodyContent.setVisibility(View.VISIBLE);
				} break;
				case MOB_FOLLOW: {
					viewholder.aivTitle.execute(item.avatar);
					viewholder.textViewBodyName.setText(item.author);
					viewholder.textViewBodyContent.setText(ResHelper.getStringRes(context, "bbs_message_follow"));

					viewholder.textViewTitleName.setVisibility(View.GONE);
					viewholder.textViewTitleContent.setVisibility(View.GONE);
					viewholder.textViewBodyName.setVisibility(View.VISIBLE);
					viewholder.textViewBodyContent.setVisibility(View.VISIBLE);
				} break;
				case MOB_NOTICE: {
					viewholder.aivTitle.setImageResource(ResHelper.getBitmapRes(context, "bbs_message_system"));
					viewholder.textViewTitleName.setText(ResHelper.getStringRes(context, "bbs_message_system"));
					viewholder.textViewBodyContent.setText(item.note == null ? "" : Html.fromHtml(item.note));

					viewholder.textViewTitleName.setVisibility(View.VISIBLE);
					viewholder.textViewTitleContent.setVisibility(View.GONE);
					viewholder.textViewBodyName.setVisibility(View.GONE);
					viewholder.textViewBodyContent.setVisibility(View.VISIBLE);
				} break;
			}
			viewholder.textViewTime.setVisibility(View.VISIBLE);
			viewholder.aivTitle.setVisibility(View.VISIBLE);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertview;
	}

	public View buildFavoriteView(FavoriteThread fthread, View convertview, ViewGroup viewgroup) {
		return buildLayoutFavoriteView(fthread, convertview, viewgroup, null);
	}

	public View buildLayoutFavoriteView(FavoriteThread fthread, View convertview, ViewGroup viewgroup, Integer layoutid) {
		Context context = viewgroup.getContext();
		FavoriteViewHolder viewholder;
		if (convertview == null || convertview.getTag() == null || !(convertview.getTag() instanceof FavoriteViewHolder)) {
			if(layoutid == null) {
				layoutid = getFavoriteItemLayoutId(context);
				if (layoutid == null) {
					layoutid = ResHelper.getLayoutRes(context, "bbs_item_defaultfavorite");
				}
			}
			convertview = LayoutInflater.from(context).inflate(layoutid, viewgroup, false);
			viewholder = new FavoriteViewHolder();
			convertview.setTag(viewholder);
			viewholder.aivAvatar = findViewByResName(convertview, "bbs_item_favorite_aivAvatar");
			viewholder.aivPic = findViewByResName(convertview, "bbs_item_favorite_aivPic");
			viewholder.textViewAuthor = findViewByResName(convertview, "bbs_item_favorite_textViewAuthor");
			viewholder.textViewTitle = findViewByResName(convertview, "bbs_item_favorite_textViewTitle");
			viewholder.textViewSubject = findViewByResName(convertview, "bbs_item_favorite_textViewSubject");
			viewholder.textViewRightTime = findViewByResName(convertview, "bbs_item_favorite_textViewRightTime");
			ensureNoNullField(FavoriteViewHolder.class, viewholder);
		} else {
			viewholder = (FavoriteViewHolder) convertview.getTag();
		}
		try {
			viewholder.aivAvatar.setExecuteRound();
			//设置用户头像仅使用内存缓存而不缓存到磁盘
			viewholder.aivAvatar.execute(fthread.avatar, ResHelper.getBitmapRes(context, "bbs_login_account"));
			int defaultPicResId = ResHelper.getBitmapRes(context, "bbs_ic_def_no_pic");
			int errorPicResId = ResHelper.getBitmapRes(context, "bbs_ic_def_error_pic");
			if (fthread.images != null && fthread.images.size() > 0) {
				viewholder.aivPic.execute(fthread.images.get(0), defaultPicResId, errorPicResId);
			} else {
				viewholder.aivPic.setVisibility(View.GONE);
			}
			viewholder.textViewAuthor.setText(fthread.author);
			viewholder.textViewTitle.setText(fthread.subject);
			viewholder.textViewSubject.setText(fthread.forumName);
			viewholder.textViewRightTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(context, fthread.createdOn));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertview;
	}

	public static class MessageViewHolder {
		public GlideImageView aivTitle;
		public TextView textViewTitleName;
		public TextView textViewTitleContent;
		public TextView textViewBodyName;
		public TextView textViewBodyContent;
		public TextView textViewTime;
		public View viewUnreadedMark;
	}

	public static class FavoriteViewHolder {
		public GlideImageView aivAvatar;
		public GlideImageView aivPic;
		public TextView textViewAuthor;
		public TextView textViewTitle;
		public TextView textViewSubject;
		public TextView textViewRightTime;
	}

	public static class ThreadViewHolder {
		public ViewGroup layoutHeader;
		public ViewGroup layoutCommentView;
		public GlideImageView aivAvatar;
		public TextView tvAuthor;
		public TextView tvSubject;
		public TextView tvTitle;
		public TextView tvSummary;
		public View viewPicContainer;
		public GlideImageView aivPic;
		public View viewPicMask;
		public TextView textViewPicNum;
		public TextView tvPageComment;
		public TextView textViewPageView;
		public TextView tvLeftTime;
		public TextView tvRightTime;
		public ImageView ivHead;
		public ImageView ivEssence;
		public ImageView ivSticktop;
		public LinearLayout layoutLabel;
		public ImageView imageViewPageCommentLabel;
		public ImageView imageViewPageViewLabel;
	}

	static class NullFieldException extends RuntimeException {

	}

	public static boolean ensureNoNullField(Class<?> cls, Object obj) {
		//Doesn't check in release.
		if(AppUtils.isReleaseVersion()) {
			return true;
		}
		if (cls == null || obj == null || !cls.isInstance(obj)) {
			throw new IllegalArgumentException("Class: " + cls + ",object: " + obj);
		}
		for (Field field : cls.getDeclaredFields()) {
			try {
				try {
					Object fieldvalue = field.get(obj);
					if (fieldvalue == null) {
						throw new NullFieldException();
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Class: " + cls + " Field: " + field + " Object: " + obj + " doesn't match.");
			}
		}
		return true;
	}

	public View buildFollowerItemView(View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		return LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(MobSDK.getContext(), "bbs_item_follower"), parent, false);
	}

	public View buildFollowingsItemView(View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		return LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(MobSDK.getContext(), "bbs_item_following"), parent, false);
	}

	public static void loadHtml(TextView textview, String html) {
		textview.setText(Html.fromHtml(html));
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			textview.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
//		} else {
//			textview.setText(Html.fromHtml(html));
//		}
	}
}
