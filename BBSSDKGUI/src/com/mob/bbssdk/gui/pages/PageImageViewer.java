package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.views.ForumImageViewer;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 默认浏览图片界面，基于{@link BasePageWithTitle}
 */
public class PageImageViewer extends BasePageWithTitle implements ForumImageViewer.OnPageChangedListener {
	private ForumImageViewer forumImageViewer;
	private String[] imageUrls = null;
	private int index;
	private String curImagePath = null;
	private int curIndex = -1;
	private DefaultChooserDialog chooserDialog;

	/**
	 * 设置图片集以及当前图片的index
	 *
	 * @param imageUrls 图片集
	 * @param index     当前所在的index
	 */
	public void setImageUrlsAndIndex(String[] imageUrls, int index) {
		this.imageUrls = imageUrls;
		this.index = index;
	}

	protected View onCreateContentView(Context context) {
		forumImageViewer = new ForumImageViewer(context);
		forumImageViewer.setOnPageChangedListener(this);
		forumImageViewer.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				showSavePhotoDialog();
				return true;
			}
		});
		return forumImageViewer;
	}

	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResourceDefaultClose();
		if (imageUrls == null || imageUrls.length < 1) {
			forumImageViewer.setLoadingFailed();
			return;
		}
		forumImageViewer.setImageUrlsAndIndex(imageUrls, index);
		forumImageViewer.loadData();
	}

	public void onPageChanged(String imagePath, int position) {
		if (curIndex == position) {
			return;
		}
		curImagePath = imagePath;
		curIndex = position;
		if (curImagePath != null && curImagePath.startsWith("file:///")) {
			//如果图片地址为本地地址，则显示更多按钮
			titleBar.setRightImageResourceDefaultMore();
		} else {
			titleBar.getRightImageView().setVisibility(View.GONE);
		}
	}

	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		showSavePhotoDialog();
	}

	private void showSavePhotoDialog() {
		if (curImagePath != null && curImagePath.startsWith("file:///")) {
			//如果图片地址为本地地址，则显示保存对话框
			if (chooserDialog == null) {
				chooserDialog = new DefaultChooserDialog(getContext(),
						new int[]{ResHelper.getStringRes(getContext(), "bbs_save_photo_to_local")});
				chooserDialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
					public void onItemClick(View v, int position) {
						if (position == 1) {
							savePhoto(curImagePath.substring(8));
						}
					}
				});
			}
			chooserDialog.show();
		}
	}

	private void savePhoto(String filePath) {
		File originFile = new File(filePath);
		if (originFile.exists()) {
			try {
				String originFileName = originFile.getName();
				int suffixIndex = originFileName.indexOf(".");
				String fileName = "pic_bbs_";
				if (suffixIndex > 0) {
					fileName += originFileName.substring(0, suffixIndex) + ".jpg";
				} else {
					fileName += originFileName + ".jpg";
				}
				String path = ResHelper.getCachePath(activity.getApplicationContext(), "pictures");
				File file = new File(path, fileName);
				FileOutputStream fos = null;
				FileInputStream is = null;
				try {
					is = new FileInputStream(originFile);
					fos = new FileOutputStream(file);
					byte[] tmp = new byte[256];
					int len = is.read(tmp);
					while (len > 0) {
						fos.write(tmp, 0, len);
						len = is.read(tmp);
					}
					fos.flush();
				} catch (Throwable t) {
					if (file.exists()) {
						file.delete();
					}
				} finally {
					try {
						fos.close();
						is.close();
					} catch (Throwable t) {
					}
				}
				if (file.exists()) {
					//通知系统扫描图片
					activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
					Toast.makeText(activity.getApplicationContext(),
							ResHelper.getStringRes(getContext(), "bbs_toast_save_pic_success"), Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		Toast.makeText(activity.getApplicationContext(), ResHelper.getStringRes(getContext(), "bbs_toast_save_pic_failed"), Toast.LENGTH_SHORT).show();
	}

	public void onDestroy() {
		super.onDestroy();
	}
}
