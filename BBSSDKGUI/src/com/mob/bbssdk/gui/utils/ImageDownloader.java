package com.mob.bbssdk.gui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.mob.tools.gui.BitmapProcessor;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;

/**
 * 图片下载，缓存同AsyncImage
 */
public class ImageDownloader {
	public static void downloadImage(final Context context, final int index, final String imageUrl, final ImageDownloaderListener downloaderListener) {
		BitmapProcessor.prepare(context);
		if (TextUtils.isEmpty(imageUrl)) {
			new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					downloaderListener.onResult(false, index, imageUrl, null, null);
					return false;
				}
			});
			return;
		}

		final File file = new File(ResHelper.getImageCachePath(context), Data.MD5(imageUrl));
		if (file.exists()) {
			final Bitmap bm = BitmapProcessor.getBitmapFromCache(imageUrl);
			if (bm != null && !bm.isRecycled()) {
				new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
					public boolean handleMessage(Message msg) {
						downloaderListener.onResult(true, index, imageUrl, file.getAbsolutePath(), bm);
						return false;
					}
				});
				return;
			}
		}

		BitmapProcessor.process(imageUrl, new BitmapProcessor.BitmapCallback() {
			public void onImageGot(final String url, final Bitmap bm) {
				new UIHandler().sendEmptyMessage(0, new Handler.Callback() {
					public boolean handleMessage(Message msg) {
						if (!file.exists() || bm == null) {
							downloaderListener.onResult(false, index, url, null, null);
						} else {
							downloaderListener.onResult(true, index, url, file.getAbsolutePath(), bm);
						}
						return false;
					}
				});
			}
		});
	}

	public interface ImageDownloaderListener {
		void onResult(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap);
	}
}
