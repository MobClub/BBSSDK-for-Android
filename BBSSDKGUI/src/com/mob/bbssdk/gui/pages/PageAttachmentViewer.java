package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.utils.CommonUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.tools.network.FileDownloadListener;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;

/**
 * 默认下载和打开附件的界面
 */
public class PageAttachmentViewer extends BasePageWithTitle implements View.OnClickListener {
	private ForumThreadAttachment attachment;
	private String filePath = "";
	private boolean downloadFailed = false;
	private TextView tvName;
	private TextView tvLoad;
	private Button btnDownload;
	private ProgressBar pbDownload;
	private ImageView vCancelDownload;
	private View contentView;
	private ScrollView svLoading;

	private FileDownloadListener downloadListener;
	private LoadContentListener loadContentListener;
	private boolean hasSetDownloadImmediately = false;
	private boolean downloadImmediately = false;
	private boolean downloadSuccess = false;

	private DefaultChooserDialog chooserDialog;

	public void setAttachment(ForumThreadAttachment attachment) {
		this.attachment = attachment;
	}

	protected View onCreateContentView(Context context) {
		Resources resources = getContext().getResources();

		int nameTxtSize = resources.getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_attachment_name_txt_size"));
		int loadTxtSize = resources.getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_attachment_load_txt_size"));
		int btnTxtSize = resources.getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_attachment_btn_txt_size"));

		RelativeLayout rlContent = new RelativeLayout(context);

		contentView = initViewerContentView(context);
		if (contentView != null) {
			rlContent.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			contentView.setVisibility(View.GONE);
		}

		svLoading = new ScrollView(context);
		LinearLayout llLoading = new LinearLayout(context);
		llLoading.setGravity(Gravity.CENTER_HORIZONTAL);
		llLoading.setOrientation(LinearLayout.VERTICAL);
		tvName = new TextView(context);
		tvName.setGravity(Gravity.CENTER);
		tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, nameTxtSize);
		tvName.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_attachment_name_txt_color")));
		tvName.setCompoundDrawablePadding(ResHelper.dipToPx(context, 35));
		tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(context, "bbs_ic_file_unknown"), 0, 0);

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.topMargin = ResHelper.dipToPx(context, 75);
		llLoading.addView(tvName, llp);

		tvLoad = new TextView(context);
		tvLoad.setGravity(Gravity.CENTER);
		tvLoad.setTextSize(TypedValue.COMPLEX_UNIT_PX, loadTxtSize);
		tvLoad.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_attachment_load_txt_color")));
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.topMargin = ResHelper.dipToPx(context, 60);
		llLoading.addView(tvLoad, llp);

		btnDownload = new Button(context);
		btnDownload.setTextColor(resources.getColor(ResHelper.getColorRes(context, "bbs_attachment_btn_txt_color")));
		btnDownload.setGravity(Gravity.CENTER);
		btnDownload.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTxtSize);
		btnDownload.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_attachment_btn_bg"));
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 40));
		llp.topMargin = ResHelper.dipToPx(context, 60);
		int leftMargin = ResHelper.dipToPx(context, 80);
		llp.leftMargin = leftMargin;
		llp.rightMargin = leftMargin;
		llLoading.addView(btnDownload, llp);

		LinearLayout llPb = new LinearLayout(context);
		llPb.setOrientation(LinearLayout.HORIZONTAL);
		llPb.setGravity(Gravity.CENTER);
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		leftMargin = ResHelper.dipToPx(context, 30);
		llp.leftMargin = leftMargin;
		llp.rightMargin = leftMargin;
		llLoading.addView(llPb, llp);

		pbDownload = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		pbDownload.setIndeterminate(false);
		pbDownload.setMax(100);
		pbDownload.setProgressDrawable(getContext().getResources().getDrawable(ResHelper.getBitmapRes(context, "bbs_attachment_progressbar_bg")));
		llp = new LinearLayout.LayoutParams(0, ResHelper.dipToPx(context, 2));
		llp.weight = 1;
		llPb.addView(pbDownload, llp);

		vCancelDownload = new ImageView(context);
		int padding = ResHelper.dipToPx(context, 10);
		vCancelDownload.setPadding(padding, padding, padding, padding);
		vCancelDownload.setScaleType(ImageView.ScaleType.FIT_CENTER);
		vCancelDownload.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_ic_del"));
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.leftMargin = padding;
		llPb.addView(vCancelDownload, llp);

		svLoading.addView(llLoading, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		rlContent.addView(svLoading, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		tvLoad.setVisibility(View.GONE);
		btnDownload.setVisibility(View.GONE);
		pbDownload.setVisibility(View.GONE);
		vCancelDownload.setVisibility(View.GONE);

		btnDownload.setOnClickListener(this);
		vCancelDownload.setOnClickListener(this);

		return rlContent;
	}


	/**
	 * 初始化打开附件的VIEW，如果返回为空，则使用默认的打开方式，默认返回空
	 * 返回打开附件的view后，请重载{@link #loadContent(String, String, LoadContentListener)}方法进行打开附件
	 */
	protected View initViewerContentView(Context context) {
		return null;
	}

	/**
	 * 加载附件，如果加载成功与否请调用{@link LoadContentListener#onLoadFinished(boolean)}方法进行返回
	 * 如果不重载，默认返回加载失败
	 * 此方法在重载{@link #initViewerContentView(Context)}方法后生效
	 */
	protected void loadContent(String filePath, String extension, LoadContentListener loadContentListener) {
		loadContentListener.onLoadFinished(false);
	}

	/**
	 * 设置进入界面是否直接下载附件，不设置的话，默认小于10M的附件直接下载，大于或等于10M的不直接下载
	 */
	public void setDownloadImmediately(boolean downloadImmediately) {
		this.hasSetDownloadImmediately = true;
		this.downloadImmediately = downloadImmediately;
	}

	protected void onViewCreated(View contentView) {
		titleBar.getTitleTextView().setSingleLine(true);
		titleBar.getTitleTextView().setEllipsize(TextUtils.TruncateAt.MIDDLE);
		titleBar.setLeftImageResourceDefaultBack();
		loadData();
	}

	private void loadData() {
		if (attachment == null || TextUtils.isEmpty(attachment.url)) {
			tvLoad.setVisibility(View.GONE);
			btnDownload.setVisibility(View.GONE);
			pbDownload.setVisibility(View.GONE);
			vCancelDownload.setVisibility(View.GONE);
			tvName.setText(ResHelper.getStringRes(getContext(), "bbs_attachment_none"));
			return;
		}
		if (!hasSetDownloadImmediately) {
			//如果没有设置直接下载，则默认小于10M的附件直接下载，大于或等于10M的不直接下载
			downloadImmediately = attachment.fileSize < 10 * 1024 * 1024;
		}

		titleBar.setTitle(attachment.fileName);
		tvName.setText(attachment.fileName);

		String extension = attachment.extension;
		if ("pdf".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_pdf"), 0, 0);
		} else if ("doc".equals(extension) || "docx".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_word"), 0, 0);
		} else if ("xlsx".equals(extension) || "xls".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_excel"), 0, 0);
		} else if ("ppt".equals(extension) || "pptx".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_ppt"), 0, 0);
		} else if ("3gp".equals(extension) || "mp4".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_mp4"), 0, 0);
		} else if ("txt".equals(extension)) {
			tvName.setCompoundDrawablesWithIntrinsicBounds(0, ResHelper.getBitmapRes(getContext(), "bbs_ic_file_txt"), 0, 0);
		}

		btnDownload.setText(getContext().getResources().getString(ResHelper.getStringRes(getContext(), "bbs_attachment_btn_download"),
				CommonUtils.formatFileSize(attachment.fileSize)));

		filePath = getExistsAttachmentPath(getContext(), attachment.url);
		if (!TextUtils.isEmpty(filePath)) {
			//如果文件已经存在，则直接打开
			openDownloadedFile();
		} else if (downloadImmediately) {
			//如果文件不存在，则直接下载
			downloadFile(attachment.url);
		} else {
			//不直接下载，则显示下载按钮
			btnDownload.setVisibility(View.VISIBLE);
		}
	}

	private void downloadFile(final String url) {
		svLoading.setVisibility(View.VISIBLE);
		tvLoad.setVisibility(View.VISIBLE);
		tvLoad.setText(getContext().getResources().getString(ResHelper.getStringRes(getContext(), "bbs_attachment_download_ing"), "0",
				CommonUtils.formatFileSize(attachment.fileSize)));
		btnDownload.setVisibility(View.GONE);
		pbDownload.setProgress(0);
		pbDownload.setVisibility(View.VISIBLE);
		vCancelDownload.setVisibility(View.VISIBLE);
		vCancelDownload.setClickable(true);
		if (contentView != null) {
			contentView.setVisibility(View.GONE);
		}
		downloadFailed = false;
		new Thread() {
			public void run() {
				super.run();
				//1. 下载url对应的文件到本地，并缓存起来，并显示下载进度
				try {
					NetworkHelper networkHelper = new NetworkHelper();
					NetworkHelper.NetworkTimeOut timeout = new NetworkHelper.NetworkTimeOut();
					timeout.readTimout = 60000;
					timeout.connectionTimeout = 5000;
					filePath = networkHelper.downloadCache(getContext(), url, "attachment", true, timeout, initDownloadListener());
				} catch (Throwable t) {
					t.printStackTrace();
					downloadFailed = true;
					if (!TextUtils.isEmpty(filePath)) {
						File file = new File(filePath);
						if (file.exists()) {
							file.delete();
						}
					}
				}

				//2. 显示下载状态，并显示其他应用入口
				UIHandler.sendEmptyMessage(0, new Handler.Callback() {
					public boolean handleMessage(Message msg) {
						try {
							tvLoad.setVisibility(View.GONE);
							pbDownload.setVisibility(View.GONE);
							vCancelDownload.setVisibility(View.GONE);
							if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
								//filePath == null 表示下载已经取消了
								Toast.makeText(getContext(), ResHelper.getStringRes(getContext(),
										downloadFailed ? "bbs_attachment_download_failed" : "bbs_attachment_download_canceled"), Toast.LENGTH_SHORT).show();
								btnDownload.setText(getContext().getResources().getString(ResHelper.getStringRes(getContext(),
										"bbs_attachment_btn_download_again"), CommonUtils.formatFileSize(attachment.fileSize)));
								btnDownload.setVisibility(View.VISIBLE);
								downloadSuccess = false;
							} else {
								openDownloadedFile();
							}
						} catch (Throwable t) {
							t.printStackTrace();
						}
						return false;
					}
				});
			}
		}.start();
	}

	private void openDownloadedFile() {
		downloadSuccess = true;
		if (contentView != null) {
			//使用contentView去打开附件
			tvLoad.setVisibility(View.VISIBLE);
			tvLoad.setText(ResHelper.getStringRes(getContext(), "bbs_attachment_load_ing"));
			loadContent(filePath, attachment.extension, initLoadContentListener());
		} else {
			//如果不支持打开，则显示使用其他方式打开
			btnDownload.setVisibility(View.VISIBLE);
			btnDownload.setText(ResHelper.getStringRes(getContext(), "bbs_attachment_btn_open_with_other_app"));
		}
	}

	private FileDownloadListener initDownloadListener() {
		downloadListener = new FileDownloadListener() {
			public void onProgress(final int progress, final long curSize, final long totalSize) {
				if (progress > 0 && progress % 10 == 0) {
					UIHandler.sendEmptyMessage(0, new Handler.Callback() {
						public boolean handleMessage(Message msg) {
							tvLoad.setText(getContext().getResources().getString(ResHelper.getStringRes(getContext(), "bbs_attachment_download_ing"),
									String.valueOf(CommonUtils.formatFileSize(curSize)), CommonUtils.formatFileSize(totalSize)));
							pbDownload.setProgress(progress);
							return false;
						}
					});
				}
			}
		};
		return downloadListener;
	}

	private LoadContentListener initLoadContentListener() {
		if (loadContentListener == null) {
			loadContentListener = new LoadContentListener() {
				public void onLoadFinished(boolean success) {
					if (success) {
						//打开成功
						titleBar.setRightImageResourceDefaultMore();
						contentView.setVisibility(View.VISIBLE);
						svLoading.setVisibility(View.GONE);
					} else {
						//打开失败，则显示使用其他方式打开
						tvLoad.setVisibility(View.GONE);
						Toast.makeText(getContext(), ResHelper.getStringRes(getContext(), "bbs_attachment_load_failed"), Toast.LENGTH_SHORT).show();
						btnDownload.setText(ResHelper.getStringRes(getContext(), "bbs_attachment_btn_open_with_other_app"));
						btnDownload.setVisibility(View.VISIBLE);
					}
				}
			};
		}
		return loadContentListener;
	}

	public void onClick(View v) {
		if (v == btnDownload) {
			if (downloadSuccess) {
				openWidthOtherApp();
			} else {
				downloadFile(attachment.url);
			}
		} else if (v == vCancelDownload) {
			if (downloadListener != null) {
				vCancelDownload.setClickable(false);
				downloadListener.cancel();
				downloadListener = null;
			}
		}
	}

	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		if (chooserDialog == null) {
			chooserDialog = new DefaultChooserDialog(getContext(),
					new int[]{ResHelper.getStringRes(getContext(), "bbs_attachment_btn_open_with_other_app")});
			chooserDialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
				public void onItemClick(View v, int position) {
					if (position == 1) {
						openWidthOtherApp();
					}
				}
			});
		}
		chooserDialog.show();
	}

	private void openWidthOtherApp() {
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(attachment.extension);
		Uri uri = Uri.fromFile(new File(filePath));
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, mimeType);
		Intent newIntent = Intent.createChooser(intent, getContext().getResources()
				.getString(ResHelper.getStringRes(getContext(), "bbs_open_file_chooser")));
		getContext().startActivity(newIntent);
	}

	public static String getExistsAttachmentPath(Context context, String url) {
		if (TextUtils.isEmpty(url) || context == null) {
			return null;
		}
		String urlSuffixName = "";
		int lastIndexPos = url.lastIndexOf("/");
		if (lastIndexPos > 0) {
			urlSuffixName = url.substring(lastIndexPos + 1);
		}
		String path = ResHelper.getCachePath(context, "attachment");
		File file = new File(path, urlSuffixName);
		boolean fileExists = file.exists();
		if (!fileExists) {
			String name = Data.MD5(url);
			file = new File(path, name);
			fileExists =  file.exists();
			if (fileExists) {
				return file.getAbsolutePath();
			}
			int index = url.lastIndexOf('/');
			String lastPart = null;
			if (index > 0) {
				lastPart = url.substring(index + 1);
			}
			if (lastPart != null && lastPart.length() > 0) {
				int dot = lastPart.lastIndexOf('.');
				if (dot > 0 && lastPart.length() - dot < 10) {
					name += lastPart.substring(dot);
				}
			}
			file = new File(path, name);
			fileExists = file.exists();
		}
		if (fileExists) {
			return file.getAbsolutePath();
		}
		return null;
	}

	public interface LoadContentListener {
		void onLoadFinished(boolean success);
	}
}
