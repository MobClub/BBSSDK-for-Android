package com.mob.bbssdk.theme1.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurUtils {
	private static final float BITMAP_SCALE = 1f;
	private static final float BLUR_RADIUS = 25f;

	public static Bitmap blur(Context context, Bitmap bitmap) {
		return blur(context, bitmap, null, null);
	}

	public static Bitmap blur(Context context, Bitmap image, Float scale, Float blurradius) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (scale == null) {
				scale = BITMAP_SCALE;
			}
			if (blurradius == null) {
				blurradius = BLUR_RADIUS;
			}
			int width = Math.round(image.getWidth() * scale);
			int height = Math.round(image.getHeight() * scale);

			Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
			Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

			RenderScript rs = RenderScript.create(context);
			ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
			Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
			theIntrinsic.setRadius(blurradius);
			theIntrinsic.setInput(tmpIn);
			theIntrinsic.forEach(tmpOut);
			tmpOut.copyTo(outputBitmap);

			return outputBitmap;
		} else {
			Bitmap outputBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
			outputBitmap.eraseColor(Color.WHITE);
			return outputBitmap;
		}
	}
}
