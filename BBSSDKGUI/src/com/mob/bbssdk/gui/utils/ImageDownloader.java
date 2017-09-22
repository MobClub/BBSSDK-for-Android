package com.mob.bbssdk.gui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.gui.BitmapProcessor;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;
import java.lang.ref.WeakReference;

//import com.loopj.android.image.SmartImageTask;
//import com.loopj.android.image.WebImage;

/**
 * 图片下载，缓存同AsyncImage
 */
public class ImageDownloader {
//	private static final int LOADING_THREADS = 4;
//	private static ExecutorService threadPool = Executors.newFixedThreadPool(4);
//
//	public static void downloadImage(final Context context, final String imageUrl, final SmartImageTask.OnCompleteHandler listener) {
//		SmartImageTask currentTask = new SmartImageTask(context, new WebImage(imageUrl));
//		currentTask.setOnCompleteHandler(listener);
//		threadPool.execute(currentTask);
//	}

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

	public static void loadImage(String url, ImageView imageview) {
		if(StringUtils.isEmpty(url) || imageview == null) {
			return;
		}
		final WeakReference<ImageView> weakreference = new WeakReference<ImageView>(imageview);
		downloadImage(imageview.getContext(), 0, url, new ImageDownloaderListener() {
			@Override
			public void onResult(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
				ImageView imageview = weakreference.get();
				if(imageview != null) {
					imageview.setImageBitmap(bitmap);
				}
			}
		});
	}

	public interface ImageDoneListner {
		void OnFinish(boolean success, Bitmap bitmap);
	}

	public static void loadImage(final Context context, String url, ImageDoneListner listner) {
		if(StringUtils.isEmpty(url)) {
			return;
		}
		final WeakReference<ImageDoneListner> weakreference = new WeakReference<ImageDoneListner>(listner);
		downloadImage(context, 0, url, new ImageDownloaderListener() {
			@Override
			public void onResult(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
				ImageDoneListner imageview = weakreference.get();
				if(imageview != null) {
					imageview.OnFinish(success, bitmap);
				}
			}
		});
	}

	public static void loadCircleImage(String url, final ImageView imageview) {
		if(StringUtils.isEmpty(url) || imageview == null) {
			return;
		}
		Context context = imageview.getContext();
		downloadImage(context, 0, url, new ImageDownloaderListener() {
			@Override
			public void onResult(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
				if(success) {
					imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
					Bitmap rounded = com.mob.bbssdk.gui.helper.ImageHelper.getRoundBitmap(bitmap);
					imageview.setImageBitmap(rounded);
				}
			}
		});
	}
}
