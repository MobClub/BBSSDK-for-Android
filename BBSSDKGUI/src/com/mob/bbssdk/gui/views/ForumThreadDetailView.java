package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.webview.BaseWebView;
import com.mob.bbssdk.gui.webview.JsInterfaceForumThread;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * 帖子详情的View
 */
public class ForumThreadDetailView extends BaseView {
	private BaseWebView webView;
	private ProgressBar progressBar;
	private ForumThread forumThread;
	private JsViewClient jsViewClient;
	private JsInterfaceForumThread jsInterfaceForumThread;

	public ForumThreadDetailView(Context context) {
		super(context);
	}

	public ForumThreadDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumThreadDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		RelativeLayout relativeLayout = new RelativeLayout(context, attrs, defStyleAttr);

		webView = new BaseWebView(context, attrs, defStyleAttr);
		initWebView();
		relativeLayout.addView(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setIndeterminate(false);
		progressBar.setMax(100);
		progressBar.setProgressDrawable(getResources().getDrawable(ResHelper.getBitmapRes(context, "bbs_webview_progressbar_bg")));
		relativeLayout.addView(progressBar, LinearLayout.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 2));

		jsViewClient = new JsViewClient(context) {
		};
		jsViewClient.setWebView(webView);

		return relativeLayout;
	}

	private void initWebView() {
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://") || url.startsWith("https://")) {
					PageWeb pageWeb = new PageWeb();
					pageWeb.setLink(url);
					pageWeb.show(getContext());
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress < 10) {
					newProgress = 10;
				}
				progressBar.setProgress(newProgress);
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else if (progressBar.getVisibility() == View.GONE) {
					progressBar.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}

			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				if (jsInterfaceForumThread != null && Build.VERSION.SDK_INT < 17) {
					if ("getForumThreadDetails".equals(message)) {
						result.confirm(jsInterfaceForumThread.getForumThreadDetails());
						return true;
					} else if ("getPosts".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						long fid = ResHelper.forceCast(map.get("fid"), 0L);
						long tid = ResHelper.forceCast(map.get("tid"), 0L);
						int page = ResHelper.forceCast(map.get("page"), 0);
						int pageSize = ResHelper.forceCast(map.get("pageSize"), 0);
						result.confirm(jsInterfaceForumThread.getPosts(fid, tid, page, pageSize));
						return true;
					} else if ("openImage".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						String[] imageUrls = ResHelper.forceCast(map.get("imageUrls"));
						int index = ResHelper.forceCast(map.get("index"));
						jsInterfaceForumThread.openImage(imageUrls, index);
						result.confirm();
						return true;
					} else if ("openAttachment".equals(message)) {
						jsInterfaceForumThread.openAttachment(defaultValue);
						result.confirm();
						return true;
					} else if ("downloadImages".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						String[] imageUrls = ResHelper.forceCast(map.get("imageUrls"));
						jsInterfaceForumThread.downloadImages(imageUrls);
						result.confirm();
						return true;
					}
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
	}

	public void setJsViewClient(JsViewClient jsViewClient) {
		this.jsViewClient = jsViewClient;
		jsViewClient.setWebView(webView);
	}

	public void setLoadingFailed() {
		progressBar.setProgress(100);
		progressBar.setVisibility(View.GONE);
		setLoadingStatus(LoadingView.LOAD_STATUS_FAILED);
	}

	/**
	 * 设置主题帖内容
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	/**
	 * 加载帖子详情，加载前请调用{@link #setForumThread(ForumThread)}设置主题帖内容
	 */
	public void loadData() {
		progressBar.setProgress(0);
		progressBar.setVisibility(View.VISIBLE);
		if (forumThread == null) {
			setLoadingFailed();
			return;
		}
		setLoadingStatus(LoadingView.LOAD_STATUS_SUCCESS);
		loadHtml(forumThread);
	}

	private void loadHtml(ForumThread detail) {
		jsInterfaceForumThread = new JsInterfaceForumThread(jsViewClient, detail);
		if (Build.VERSION.SDK_INT >= 17) {
			webView.addJavascriptInterface(jsInterfaceForumThread, "forumThread");
		}
		webView.loadUrl("file:///android_asset/html/details/index.html");
	}
}
