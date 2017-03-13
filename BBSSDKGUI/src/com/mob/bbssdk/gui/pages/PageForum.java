package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import com.mob.bbssdk.gui.views.ForumMenuView;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;

import java.util.ArrayList;

/**
 * 默认论坛界面
 */
public class PageForum extends BasePageWithTitle {
	private ForumMenuView forumMenuView;
	private int titleResId = 0;
	private String titleStr = null;

	/**
	 * 设置论坛标题
	 */
	public void setTitle(int resId) {
		titleResId = resId;
	}

	/**
	 * 设置论坛标题
	 */
	public void setTitle(String title) {
		titleStr = title;
	}

	protected View onCreateContentView(Context context) {
		forumMenuView = new ForumMenuView(context);
		return forumMenuView;
	}

	protected void onViewCreated(View contentView) {
		checkPermissions();
		if (titleResId > 0) {
			titleBar.setTitle(titleResId);
		} else {
			titleBar.setTitle(titleStr);
		}
		forumMenuView.loadData();
	}

	/* 检查必要权限 */
	private void checkPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				PackageManager pm = activity.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
				ArrayList<String> list = new ArrayList<String>();
				for (String p : pi.requestedPermissions) {
					if (!DeviceHelper.getInstance(activity).checkPermission(p)) {
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
