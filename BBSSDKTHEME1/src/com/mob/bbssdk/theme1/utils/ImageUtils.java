package com.mob.bbssdk.theme1.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {

	public static Bitmap screenShot(View view) {
		return getBitmapOFRootView(view);
	}

	public static Bitmap screenShot(Context context) {
		return getBitmapOFActivity(context);
	}

	public static Bitmap getBitmapOFView(View v) {
		v.setDrawingCacheEnabled(true);
		return v.getDrawingCache();
	}

	public static Bitmap getBitmapOFRootView(View v) {
		return getBitmapOFActivity(v.getContext());
	}

	public static Bitmap getBitmapOFActivity(Context context) {
		if (context == null) {
			return null;
		}
		if (!(context instanceof Activity)) {
			return null;
		}
		Activity activity = (Activity) context;
		View rootview = activity.getWindow().getDecorView().getRootView();
		rootview.setDrawingCacheEnabled(true);
		Bitmap bitmap = rootview.getDrawingCache();
		return bitmap;
	}

	public void saveImage(String location, Bitmap bmp) {
		if (TextUtils.isEmpty(location)) {
			location = Environment.getExternalStorageDirectory()
					+ File.pathSeparator + SystemClock.currentThreadTimeMillis() + ".jpg";
		}
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		File file = new File(location);
		try {
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(bytes.toByteArray());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
