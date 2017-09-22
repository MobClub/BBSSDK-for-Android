package com.mob.bbssdk.gui;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.MainView;
import com.mob.bbssdk.gui.views.MainViewInterface;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class BaseMainActivity extends BaseActivity {
	private MainViewInterface mainView;

	protected Integer getStatusBarColor() {
		return BBSViewBuilder.getInstance().getMainActivityStatusBarColor(this);
	}

	protected Integer getMainLayout() {
		return BBSViewBuilder.getInstance().getMainActivityLayout(this);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//通过AndroidManifest.xml设置主题，防止启动时有黑色背景框的效果。
		//setTheme(ResHelper.getStyleRes(this, "BBS_AppTheme"));
		Window window = getWindow();
		//21以下通过主题配置statusbar颜色
		if (Build.VERSION.SDK_INT >= 21) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			Integer color = getStatusBarColor();
			if(color == null) {
				color = getResources().getColor(ResHelper.getColorRes(this, "bbs_mainviewtitle_bg"));
			}
			window.setStatusBarColor(color);
		}
		Integer layout = getMainLayout();
		if(layout == null) {
			layout = ResHelper.getLayoutRes(this, "bbs_activity_main");
		}
		setContentView(layout);
		boolean allpermissiongranted = false;
		allpermissiongranted = checkPermissions();
		mainView = (MainViewInterface) findViewById(ResHelper.getIdRes(this, "mainView"));

		mainView.setThreadItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				if (item != null) {
					showDetailsView(item);
				}
			}
		});
		mainView.setForumItemClickListener(new MainView.ForumItemClickListener() {
			public void onItemClick(ForumForum forum) {
				if (forum != null) {
					showForumThreadView(forum);
				}
			}
		});
		//加载数据
		if (mainView != null) {
			mainView.onCreate();
		}
		//If all permissions have been granted then load the data, otherwise load data in onRequestPermissionsResult();
		if(allpermissiongranted) {
			mainView.loadData();
		}
	}

	protected void OnAttachmentClick(ForumThreadAttachment attachment) {
		PageAttachmentViewer page = BBSViewBuilder.getInstance().buildPageAttachmentViewer();
		page.setAttachment(attachment);
		page.show(this);
	}

	/* 展示详情页面 */
	private void showDetailsView(ForumThread forumThread) {
		PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
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
				OnAttachmentClick(attachment);
			}

			public void onItemImageClick(String[] imageUrls, int index) {
				//可重载打开图片的方式
				super.onItemImageClick(imageUrls, index);
			}
		});
		pageForumThreadDetail.show(this);
	}

	/* 展示帖子自版块列表 */
	private void showForumThreadView(ForumForum forum) {
		PageForumThread page = BBSViewBuilder.getInstance().buildPageForumThread();
		page.initPage(forum);
		page.setItemClickListener(new ForumThreadListView.OnItemClickListener() {
			public void onItemClick(int position, ForumThread item) {
				showDetailsView(item);
			}
		});
		page.show(this);
	}

	/**
	 *
	 * @return whether all permissions have been granted.
	 */
	private boolean checkPermissions() {
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
						return false;
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		boolean allpermissionsgranted = true;
		for(int result : grantResults) {
			if(result !=  PackageManager.PERMISSION_GRANTED) {
				allpermissionsgranted = false;
				break;
			}
		}
		if(!allpermissionsgranted) {
			ToastUtils.showToast(this, ResHelper.getStringRes(this, "bbs_should_grant_allpermissions"));
			finish();
		} else {
			mainView.loadData();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mainView != null) {
			mainView.updateTitleUserAvatar();
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		if (mainView != null) {
			mainView.onDestroy();
		}
	}
}
