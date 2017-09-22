package com.mob.bbssdk.gui.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.forum.PageImageViewer;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.utils.ImageDownloader;
import com.mob.bbssdk.model.ForumPost;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.tools.utils.Data;

import java.lang.ref.WeakReference;

/**
 * JS与View交互的基本处理事件
 */
public class JsViewClient {
	private WeakReference<Context> refContext;
	private ImageDownloader.ImageDownloaderListener imageDownloaderListener;
	private WeakReference<WebView> refWebView;

	public JsViewClient(Context context) {
		if (context != null) {
			refContext = new WeakReference<Context>(context.getApplicationContext());
		}
	}

	public void setWebView(WebView webView) {
		refWebView = new WeakReference<WebView>(webView);
	}

	/**
	 * 打开附件
	 * 默认通过跳转到外部软件的方式打开
	 */
	public void onItemAttachmentClick(ForumThreadAttachment attachment) {
		if (refContext != null && refContext.get() != null && attachment != null) {
			PageAttachmentViewer page = BBSViewBuilder.getInstance().buildPageAttachmentViewer();
			page.setAttachment(attachment);
			page.show(refContext.get());
		}
	}

	/**
	 * 打开图片
	 * 默认通过内部实现的方式打开
	 */
	public void onItemImageClick(String[] imageUrls, int index) {
		if (refContext != null && refContext.get() != null && imageUrls != null && imageUrls.length > 0) {
			PageImageViewer page = BBSViewBuilder.getInstance().buildPageImageViewer();
			page.setImageUrlsAndIndex(imageUrls, index);
			page.show(refContext.get());
		}
	}

	/**
	 * 下载网页图片
	 *
	 * @param imageUrls 网页所有图片地址
	 */
	public void downloadImages(String[] imageUrls, ImageDownloader.ImageDownloaderListener downloaderListener) {
		if (refContext != null && refContext.get() != null && imageUrls != null && imageUrls.length > 0) {
			int size = imageUrls.length;
			for (int i = 0; i < size; i++) {
				ImageDownloader.downloadImage(refContext.get(), i, imageUrls[i], downloaderListener);
			}
		}
	}

	/**
	 * 显示图片(下载成功后，显示图片)
	 *
	 * @param success   图片是否下载成功
	 * @param index     图片索引
	 * @param imageUrl  图片url地址
	 * @param imagePath 下载后图片本地地址
	 * @param bitmap    图片
	 */
	public void showImage(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
		String js;
		if (success) {
			js = "window.BBSSDKNative.showImage(" + index + ", \"" + Data.MD5(imageUrl) + "\", \"file:///" + imagePath + "\");";
		} else {
			js = "window.BBSSDKNative.showImage(" + index + ", \"" + Data.MD5(imageUrl) + "\");";
		}
		evaluateJavascript(js);
	}

	/**
	 * 回复回帖（如果自己实现此功能请重写并返回true）
	 * @param forumPost	回帖内容
	 * @return 返回true则用户自己拦截，否则采用{@link PageForumThreadDetail}中的innerJsViewClient处理，默认返回false
	 */
	public boolean onReplyPostClick(ForumPost forumPost) {
		return false;
	}

	public boolean onImageLongPressed(String imagePath) {
		return false;
	}

	public ImageDownloader.ImageDownloaderListener getImageDownloadListener() {
		if (imageDownloaderListener == null) {
			imageDownloaderListener = new ImageDownloader.ImageDownloaderListener() {
				public void onResult(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
					showImage(success, index, imageUrl, imagePath, bitmap);
				}
			};
		}
		return imageDownloaderListener;
	}

	public Context getAppContext() {
		return refContext == null ? null : refContext.get();
	}

	public void evaluateJavascript(String javascript) {
		if (refWebView != null && refWebView.get() != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				refWebView.get().evaluateJavascript(javascript, new ValueCallback<String>() {
					public void onReceiveValue(String value) {

					}
				});
			} else {
				refWebView.get().loadUrl("javascript:" + javascript);
			}
		}
	}
}
