package com.mob.bbssdk.sample;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mob.bbssdk.gui.pages.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.PageForumThreadDetail;
import com.mob.bbssdk.gui.views.ForumMenuView;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.bbssdk.sample.viewer.PageOfficeViewer;
import com.mob.bbssdk.sample.viewer.PagePDFViewer;
import com.mob.bbssdk.sample.viewer.PageVideoViewer;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置沉浸式风格
		setTheme(ResHelper.getStyleRes(this, "BBS_AppTheme"));
		Window window = getWindow();
		if (Build.VERSION.SDK_INT >= 21) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		}

		LinearLayout flContent = new LinearLayout(this);
		flContent.setBackgroundResource(ResHelper.getColorRes(this, "bbs_title_bg"));
		flContent.setOrientation(LinearLayout.VERTICAL);
		flContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		TitleBar titleBar = new TitleBar(this);
		titleBar.setTitle(R.string.app_name);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		flContent.addView(titleBar, llp);

		ForumMenuView forumMenuView = new ForumMenuView(this);
		forumMenuView.setBackgroundResource(ResHelper.getColorRes(this, "bbs_bg"));
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
		llp.weight = 1;
		flContent.addView(forumMenuView, llp);

		if (Build.VERSION.SDK_INT >= 14) {
			flContent.setFitsSystemWindows(true);
		}

		//设置界面
		setContentView(flContent);
		//检查使用权限
		checkPermissions();

		//设置主题帖子列表的点击事件
		forumMenuView.setThreadListItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (item != null) {
					showDetailsView(item);
				}
			}
		});

		//加载数据
		forumMenuView.loadData();
	}

	/* 展示详情页面 */
	private void showDetailsView(ForumThread forumThread) {
		PageForumThreadDetail pageForumThreadDetail = new PageForumThreadDetail();
		pageForumThreadDetail.setForumThread(forumThread);
		//设置JS交互事件，默认事件不包含打开PDF、word等格式的附件功能
		pageForumThreadDetail.setOnJsViewClient(new JsViewClient(this) {
			public void onItemAttachmentClick(ForumThreadAttachment attachment) {
				String extension = attachment.extension;
				if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension)
						|| "bmp".equals(extension) || "gif".equals(extension)) {
					//如果是图片，则直接打开图片
					onItemImageClick(new String[]{attachment.url}, 0);
					return;
				}
				//其它格式的文件，采用不同的方式打开
				PageAttachmentViewer page;
				if ("pdf".equals(extension)) {
					page = new PagePDFViewer();
				} else if ("doc".equals(extension) || "docx".equals(extension) || "xlsx".equals(extension)
						|| "xls".equals(extension) || "txt".equals(extension)) {
					page = new PageOfficeViewer();
				} else if ("3gp".equals(extension) || "mp4".equals(extension)) {
					page = new PageVideoViewer();
				} else {
					page = new PageAttachmentViewer();
				}
				page.setAttachment(attachment);
				page.show(MainActivity.this);
			}

			public void onItemImageClick(String[] imageUrls, int index) {
				//可重载打开图片的方式
				super.onItemImageClick(imageUrls, index);
			}
		});
		pageForumThreadDetail.show(this);
	}

	/* 检查使用权限 */
	private void checkPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				PackageManager pm = getPackageManager();
				PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
				ArrayList<String> list = new ArrayList<String>();
				for (String p : pi.requestedPermissions) {
					if (!DeviceHelper.getInstance(this).checkPermission(p)) {
						list.add(p);
					}
				}
				if (list.size() > 0) {
					String[] permissions = list.toArray(new String[list.size()]);
					if (permissions != null) {
						requestPermissions(permissions, 1);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
}
