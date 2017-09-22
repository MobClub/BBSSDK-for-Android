package com.mob.bbssdk.sample.viewer;

import android.content.Context;
import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.gui.webview.BaseWebView;
import com.mob.bbssdk.sample.utils.OfficeConverter;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;

/**
 * 打开Office文件
 */
public class PageOfficeViewer extends PageAttachmentViewer {
	private WebView webView;
	private ProgressBar progressBar;
	private String htmlPath;

	protected View initViewerContentView(Context context) {
		Resources resources = context.getResources();
		RelativeLayout rl = new RelativeLayout(context);

		webView = new BaseWebView(context);
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				progressBar.setProgress(newProgress);
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else if (progressBar.getVisibility() == View.GONE) {
					progressBar.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		rl.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setIndeterminate(false);
		progressBar.setMax(100);
		progressBar.setProgressDrawable(resources.getDrawable(ResHelper.getBitmapRes(context, "bbs_webview_progressbar_bg")));
		rl.addView(progressBar, LinearLayout.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 2));
		return rl;
	}

	protected void loadContent(final String filePath, final String extension, final LoadContentListener loadContentListener) {
		htmlPath = null;
		//2. 将文件转成html
		String tmpPath = ResHelper.getCachePath(getContext(), "attachment/html");
		final String fileMd5 = Data.MD5(filePath);
		String htmlName = fileMd5 + ".html";
		final File htmlFile = new File(tmpPath, htmlName);
		htmlPath = htmlFile.getAbsolutePath();
		if (htmlFile.exists()) {
			//如果已经转换过了，则直接使用webview打开html
			webView.loadUrl("file:///" + htmlPath);
			loadContentListener.onLoadFinished(true);
		} else {
			//如果没有转换，则起线程开始转换
			new Thread() {
				public void run() {
					try {
						String imagePath = ResHelper.getCachePath(getContext(), "attachment/html/img/" + fileMd5);
						OfficeConverter converter = new OfficeConverter(filePath, extension, htmlPath, imagePath);
						converter.convertToHtml();
					} catch (Throwable t) {
						htmlFile.delete();
						t.printStackTrace();
					}
					new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
						public boolean handleMessage(Message msg) {
							if (!TextUtils.isEmpty(htmlPath) && new File(htmlPath).exists()) {
								//转换成功，则使用webview打开html
								webView.loadUrl("file:///" + htmlPath);
								loadContentListener.onLoadFinished(true);
							} else {
								loadContentListener.onLoadFinished(false);
							}
							return false;
						}
					});
				}
			}.start();
		}
	}
}
