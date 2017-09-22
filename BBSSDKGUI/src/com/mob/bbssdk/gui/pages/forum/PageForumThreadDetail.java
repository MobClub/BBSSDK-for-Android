package com.mob.bbssdk.gui.pages.forum;

import android.content.Context;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.SelectPicBasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.ForumThreadDetailView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.ResHelper;

/**
 * 默认帖子详情界面
 */
public class PageForumThreadDetail extends SelectPicBasePageWithTitle {
	protected ForumThreadDetailView forumThreadDetailView;
	protected ForumThread forumThread;
	private JsViewClient jsViewClient;

	/**
	 * 设置文章对象
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	public void setForumThread(long fid, long tid, String author) {
		if(fid <= 0 || tid <= 0) {
			this.forumThread = null;
			return;
		}
		this.forumThread = new ForumThread();
		this.forumThread.fid = fid;
		this.forumThread.tid = tid;
		this.forumThread.author = author;
	}

	/**
	 * 设置Js交互事件，不设置采用默认处理方式
	 */
	public void setOnJsViewClient(JsViewClient jsViewClient) {
		this.jsViewClient = jsViewClient;
	}

	@Override
	protected View onCreateContentView(Context context) {
		forumThreadDetailView = new ForumThreadDetailView(context);
		return forumThreadDetailView;
	}

	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		if (forumThreadDetailView == null) {
			throw new IllegalArgumentException("forumThreadDetailView is not initialized!");
		}
		if (jsViewClient == null) {
			jsViewClient = new JsViewClient(getContext()) {
				public void onItemAttachmentClick(ForumThreadAttachment attachment) {
					String extension = attachment.extension;
					if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension)
							|| "bmp".equals(extension) || "gif".equals(extension)) {
						//如果是图片，则直接打开图片
						onItemImageClick(new String[]{attachment.url}, 0);
						return;
					}
					PageAttachmentViewer page = BBSViewBuilder.getInstance().buildPageAttachmentViewer();
					page.setAttachment(attachment);
					page.show(getContext());
				}

				public void onItemImageClick(String[] imageUrls, int index) {
					//可重载打开图片的方式
					super.onItemImageClick(imageUrls, index);
				}
			};
		}
		forumThreadDetailView.setJsViewClient(jsViewClient);
		forumThreadDetailView.setChoosePicListener(new ForumThreadDetailView.ChoosePicClickListener() {
			public void onChooseClick() {
				choose();
			}
		});

		titleBar.setLeftImageResourceDefaultBack();
		titleBar.setTitle(getStringRes("bbs_thread_details_title"));
		titleBar.setRightImageResource(getDrawableId("bbs_title_more"));
		if (forumThread == null) {
			forumThreadDetailView.setLoadingFailed();
			return;
		}
		forumThreadDetailView.onCreate();
		forumThreadDetailView.setForumThread(forumThread);
		forumThreadDetailView.loadData();
	}

	public static Integer getMenuRes(Context context, String resname) {
		return ResHelper.getResId(context, "menu", resname);
	}

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		int style = ResHelper.getStyleRes(getContext(), "BBS_PopupMenu");
		Context wrapper = new ContextThemeWrapper(getContext(), style);
		//Creating the instance of PopupMenu
		PopupMenu popup = new PopupMenu(wrapper, titleBar.getRightImageView());
		//Inflating the Popup using xml file
		popup.getMenuInflater().inflate(getMenuRes(getContext(), "bbs_popup_threaddetail"), popup.getMenu());

		//registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				//only one menu item.
				if(forumThread == null) {
					return true;
				}

				User user = null;
				try {
					user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
				} catch (Exception e) {
					user = null;
				}
				if (user != null && user.uid == forumThread.authorId) {
					ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_cant_reportaccusation_self"));
					return true;
				}
				PageReportAccusation page = BBSViewBuilder.getInstance().buildPageReportAccusation();
				page.initPage(forumThread.tid, forumThread.fid);
				page.show(getContext());
				return true;
			}
		});
		popup.show();
	}

	protected void onPicGot(Uri source, String realpath) {
		forumThreadDetailView.setSelectedPicPath(realpath);
	}

	public void onDestroy() {
		super.onDestroy();
		if (forumThreadDetailView != null) {
			forumThreadDetailView.onDestroy();
		}
	}
}
