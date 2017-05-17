package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.views.ForumImageViewer;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 默认浏览图片界面，基于{@link BasePage}
 */
public class PageImageViewer extends BasePage implements ForumImageViewer.OnPageChangedListener {
	private TitleBar titleBar;
	private TextView tvCenter;
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

	protected boolean isFullScreen() {
		return true;
	}

	protected View onCreateView(Context context) {
		RelativeLayout rlContent = new RelativeLayout(context);
		rlContent.setBackgroundColor(0xCC000000);
		forumImageViewer = new ForumImageViewer(context);
		rlContent.addView(forumImageViewer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		titleBar = new TitleBar(context) {
			protected View getCenterView() {
				tvCenter = new TextView(getContext());
				tvCenter.setGravity(Gravity.CENTER);
				tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(getContext(), 16));
				tvCenter.setTextColor(0xffffffff);
				if (imageUrls != null && imageUrls.length > 0) {
					tvCenter.setText((index + 1) + "/" + imageUrls.length);
				}
				return tvCenter;
			}
		};
		titleBar.setLeftImageResourceDefaultClose();
		titleBar.setBackgroundColor(0xCC000000);
		int titleHeight = activity.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_bar_height"));
		rlContent.addView(titleBar, ViewGroup.LayoutParams.MATCH_PARENT, titleHeight);
		return rlContent;
	}

	protected void onViewCreated(View contentView) {
		requestFullScreen(true);
		forumImageViewer.setOnPageChangedListener(this);
		forumImageViewer.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				showSavePhotoDialog();
				return true;
			}
		});
		titleBar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int tag = ResHelper.forceCast(v.getTag(), 0);
				if (tag == TitleBar.TYPE_LEFT_IMAGE) {
					activity.onBackPressed();
				} else if (tag == TitleBar.TYPE_RIGHT_IMAGE) {
					showSavePhotoDialog();
				}
			}
		});
		if (imageUrls == null || imageUrls.length < 1) {
			forumImageViewer.setLoadingFailed();
			return;
		}
		forumImageViewer.setImageUrlsAndIndex(imageUrls, index);
		forumImageViewer.loadData();
	}

	public void onPageChanged(String imagePath, int position, boolean forceChanged) {
		if (curIndex == position && !forceChanged) {
			return;
		}
		curImagePath = imagePath;
		curIndex = position;
		if (imageUrls != null && imageUrls.length > 0) {
			tvCenter.setText((curIndex + 1) + "/" + imageUrls.length);
		}
		if (curImagePath != null && curImagePath.startsWith("file:///")) {
			//如果图片地址为本地地址，则显示更多按钮
			titleBar.setRightImageResourceDefaultMore();
		} else {
			titleBar.getRightImageView().setVisibility(View.GONE);
		}
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
