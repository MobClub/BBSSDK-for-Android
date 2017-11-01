package com.mob.bbssdk.gui.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

public class ImageHelper {

	public static Bitmap loadBitmapByName(Context context, String drawablename) {
		if (context == null || StringUtils.isEmpty(drawablename)) {
			throw new IllegalArgumentException("Invalid argument: context = " + context + " drawablename: " + drawablename);
		}
		Integer resid = ResHelper.getBitmapRes(context, drawablename);
		return loadBitmapById(context, resid);
	}

	public static Bitmap loadBitmapById(Context context, Integer resid) {
		if (context == null || resid == null || resid <= 0) {
			throw new IllegalArgumentException("Invalid argument: context = " + context + " resid: " + resid);
		}
		Resources res = context.getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, resid);
		return bitmap;
	}

	public static Bitmap getRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dstleft, dsttop, dstright, dstbottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dstleft = 0;
			dsttop = 0;
			dstright = width;
			dstbottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dstleft = 0;
			dsttop = 0;
			dstright = height;
			dstbottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dstleft, (int) dsttop, (int) dstright, (int) dstbottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}
