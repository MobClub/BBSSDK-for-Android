package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.mob.bbssdk.gui.webview.BaseWebView;
import com.mob.bbssdk.gui.webview.JsInterfaceForumImage;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * 浏览图片的View
 */
public class ForumImageViewer extends BaseView {
	private BaseWebView webView;
	private JsInterfaceForumImage jsInterfaceForumImage;
	private String[] imageUrls = null;
	private int index = 0;
	private JsViewClient jsViewClient;
	private OnPageChangedListener onPageChangedListener;

	public ForumImageViewer(Context context) {
		super(context);
	}

	public ForumImageViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumImageViewer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		webView = new BaseWebView(context, attrs, defStyleAttr);
		initWebView();

		jsViewClient = new JsViewClient(context) {
			public void showImage(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
				String js;
				if (success) {
					if (jsInterfaceForumImage.getCurrentIndex() == index && ForumImageViewer.this.index == index && onPageChangedListener != null) {
						//如果当前页与index相同，并且跟第一次进入的index也相同，则执行一次onPageChanged方法，因为默认第一次进入界面时不会执行onPageChanged方法
						onPageChangedListener.onPageChanged("file:///" + imagePath, index);
					}
					js = "window.BBSSDKNative.showImage(" + index + ", \"" + Data.MD5(imageUrl) + "\", \"file:///" + imagePath + "\", " + true + ");";
				} else {
					js = "window.BBSSDKNative.showImage(" + index + ", \"" + Data.MD5(imageUrl) + "\", " + false + ", " + true + ");";
				}
				evaluateJavascript(js);
			}
		};
		jsViewClient.setWebView(webView);
		return webView;
	}

	public void setOnLongClickListener(OnLongClickListener l) {
		webView.setOnLongClickListener(l);
	}

	private void initWebView() {
		webView.setWebChromeClient(new WebChromeClient() {
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				if (jsInterfaceForumImage != null && Build.VERSION.SDK_INT < 17) {
					if ("getImageUrlsAndIndex".equals(message)) {
						result.confirm(jsInterfaceForumImage.getImageUrlsAndIndex());
						return true;
					} else if ("setCurrentImageSrc".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumImage.parseJsonToMap(defaultValue);
						String imagePath = ResHelper.forceCast(map.get("imageSrc"));
						int curIndex = ResHelper.forceCast(map.get("index"), -1);
						jsInterfaceForumImage.setCurrentImageSrc(imagePath, curIndex);
						result.confirm();
						return true;
					} else if ("downloadImages".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumImage.parseJsonToMap(defaultValue);
						String[] imageUrls = ResHelper.forceCast(map.get("imageUrls"));
						jsInterfaceForumImage.downloadImages(imageUrls);
						result.confirm();
						return true;
					}
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
	}

	public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
		this.onPageChangedListener = onPageChangedListener;
	}

	public void setLoadingFailed() {
		setLoadingStatus(LoadingView.LOAD_STATUS_FAILED);
	}

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

	/**
	 * 浏览图片，浏览前请调用{@link #setImageUrlsAndIndex(String[], int)}设置内容
	 */
	public void loadData() {
		if (imageUrls == null || imageUrls.length < 1) {
			setLoadingFailed();
			return;
		}
		setLoadingStatus(LoadingView.LOAD_STATUS_SUCCESS);
		if (index < 0) {
			index = 0;
		}
		if (index > imageUrls.length) {
			index = imageUrls.length - 1;
		}
		loadHtml(imageUrls, index);
	}

	private void loadHtml(String[] imageUrls, int index) {
		jsInterfaceForumImage = new JsInterfaceForumImage(imageUrls, index, jsViewClient, onPageChangedListener);
		if (Build.VERSION.SDK_INT >= 17) {
			webView.addJavascriptInterface(jsInterfaceForumImage, "forumImage");
		}
		webView.loadUrl("file:///android_asset/html/details/imgshow.html");
	}

	public interface OnPageChangedListener {
		void onPageChanged(String imagePath, int position);
	}
}
