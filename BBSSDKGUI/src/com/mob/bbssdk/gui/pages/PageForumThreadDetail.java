package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.mob.bbssdk.gui.views.ForumThreadDetailView;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;

/**
 * 默认帖子详情界面
 */
public class PageForumThreadDetail extends SelectPicBasePageWithTitle {
	private ForumThreadDetailView forumThreadDetailView;
	private ForumThread forumThread;
	private JsViewClient jsViewClient;

	/**
	 * 设置文章对象
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	/**
	 * 设置Js交互事件，不设置采用默认处理方式
	 */
	public void setOnJsViewClient(JsViewClient jsViewClient) {
		this.jsViewClient = jsViewClient;
	}

	protected View onCreateContentView(Context context) {
		forumThreadDetailView = new ForumThreadDetailView(context);
		if (jsViewClient == null) {
			jsViewClient = new JsViewClient(context) {
				public void onItemAttachmentClick(ForumThreadAttachment attachment) {
					String extension = attachment.extension;
					if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension)
							|| "bmp".equals(extension) || "gif".equals(extension)) {
						//如果是图片，则直接打开图片
						onItemImageClick(new String[]{attachment.url}, 0);
						return;
					}
					PageAttachmentViewer page = new PageAttachmentViewer();
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
		return forumThreadDetailView;
	}

	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setLeftImageResourceDefaultBack();
		titleBar.setTitle(getStringRes("bbs_thread_details_title"));
		if (forumThread == null) {
			forumThreadDetailView.setLoadingFailed();
			return;
		}
		forumThreadDetailView.onCreate();
		forumThreadDetailView.setForumThread(forumThread);
		forumThreadDetailView.loadData();
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
