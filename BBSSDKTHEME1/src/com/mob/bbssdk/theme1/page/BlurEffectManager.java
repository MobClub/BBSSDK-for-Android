package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.graphics.Bitmap;

import com.mob.bbssdk.theme1.utils.BlurUtils;

public class BlurEffectManager {
	private static BlurEffectManager singleInstance;

	private Bitmap bitmapFronPage;
	private Bitmap bitmapBlurFrontPage;

	public static synchronized BlurEffectManager getInstance() {
		if (singleInstance == null) {
			singleInstance = new BlurEffectManager();
		}
		return singleInstance;
	}

	private BlurEffectManager() {

	}

	public void setFrontPageBackground(Context context, Bitmap bitmap) {
		bitmapFronPage = bitmap;
		bitmapBlurFrontPage = BlurUtils.blur(context, bitmap);
	}

	public Bitmap getBitmapFronPage() {
		return bitmapFronPage;
	}

	public Bitmap getBlurFrontPage() {
		return bitmapBlurFrontPage;
	}
}
