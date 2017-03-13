package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.gui.webview.BaseWebView;
import com.mob.tools.utils.ResHelper;

/**
 * 默认打开外链的界面，基于{@link BasePageWithTitle}
 */
public class PageWeb extends BasePageWithTitle {
	private ProgressBar progressBar;
	private WebView webView;
	private String link;

	public void setLink(String link) {
		this.link = link;
	}

	protected View onCreateContentView(Context context) {
		RelativeLayout relativeLayout = new RelativeLayout(context);

		webView = new BaseWebView(context);
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (view != null) {
					titleBar.setTitle(view.getTitle());
				}
				checkCanGoBack();
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
		relativeLayout.addView(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setIndeterminate(false);
		progressBar.setMax(100);
		progressBar.setProgressDrawable(getContext().getResources().getDrawable(ResHelper.getBitmapRes(context, "bbs_webview_progressbar_bg")));
		relativeLayout.addView(progressBar, LinearLayout.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 2));
		return relativeLayout;
	}

	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResourceDefaultClose();
		titleBar.getTitleTextView().setEllipsize(TextUtils.TruncateAt.MIDDLE);
		webView.loadUrl(link);
	}

	private void checkCanGoBack() {
		if (webView.canGoBack()) {
			titleBar.setLeftImageResourceDefaultBack();
		} else {
			titleBar.setLeftImageResourceDefaultClose();
		}
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		if (webView != null && keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
			checkCanGoBack();
			return true;
		}
		return super.onKeyEvent(keyCode, event);
	}

	protected void onTitleLeftClick(TitleBar titleBar) {
		if (webView != null && webView.canGoBack()) {
			webView.goBack();
			checkCanGoBack();
		} else {
			super.onTitleLeftClick(titleBar);
		}
	}

	public void onDestroy() {
		super.onDestroy();
	}
}
