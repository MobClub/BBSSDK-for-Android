package com.mob.bbssdk.gui.webview;

import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.mob.bbssdk.gui.views.ForumImageViewer;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.UIHandler;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 图片浏览JS交互对象
 */
public class JsInterfaceForumImage {
	private Hashon hashon;
	private String[] imageUrls;
	private int index;
	private WeakReference<JsViewClient> refViewClient;
	private ForumImageViewer.OnPageChangedListener onPageChangedListener;
	private int curIndex;

	public JsInterfaceForumImage(String[] imageUrls, int index, JsViewClient viewClient, ForumImageViewer.OnPageChangedListener listener) {
		hashon = new Hashon();
		this.imageUrls = imageUrls;
		this.index = index;
		this.curIndex = index;
		refViewClient = new WeakReference<JsViewClient>(viewClient);
		this.onPageChangedListener = listener;
	}

	@JavascriptInterface
	public String getImageUrlsAndIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("imageUrls", imageUrls);
		map.put("index", index);
		return new Hashon().fromHashMap(map);
	}

	@JavascriptInterface
	public void setCurrentImageSrc(final String imageSrc, final int index) {
		curIndex = index;
		if (onPageChangedListener != null) {
			new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					onPageChangedListener.onPageChanged(imageSrc, index, false);
					return false;
				}
			});
		}
	}

	@JavascriptInterface
	public void downloadImages(String[] imageUrls) {
		if (refViewClient != null && refViewClient.get() != null) {
			refViewClient.get().downloadImages(imageUrls, refViewClient.get().getImageDownloadListener());
		}
	}

	@JavascriptInterface
	public void onImageLongPressed(final String imagePath) {
		if (refViewClient != null && refViewClient.get() != null) {
			new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					refViewClient.get().onImageLongPressed(imagePath);
					return false;
				}
			});
		}
	}

	public int getCurrentIndex() {
		return curIndex;
	}

	public HashMap<String, Object> parseJsonToMap(String json) {
		try {
			return hashon.fromJson(json);
		} catch (Throwable t) {
			return null;
		}
	}
}
