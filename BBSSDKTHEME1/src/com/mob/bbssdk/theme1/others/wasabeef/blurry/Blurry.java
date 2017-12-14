package com.mob.bbssdk.theme1.others.wasabeef.blurry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mob.bbssdk.theme1.others.wasabeef.blurry.internal.Blur;
import com.mob.bbssdk.theme1.others.wasabeef.blurry.internal.BlurFactor;
import com.mob.bbssdk.theme1.others.wasabeef.blurry.internal.BlurTask;
import com.mob.bbssdk.theme1.others.wasabeef.blurry.internal.Helper;

/**
 * Copyright (C) 2017 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Blurry {

	private static final String TAG = Blurry.class.getSimpleName();

	public static Composer with(Context context) {
		return new Composer(context);
	}

	public static void delete(ViewGroup target) {
		View view = target.findViewWithTag(TAG);
		if (view != null) {
			target.removeView(view);
		}
	}

	public static class Composer {

		private View blurredView;
		private Context context;
		private BlurFactor factor;
		private boolean async;
		private boolean animate;
		private int duration = 300;
		private ImageComposer.ImageComposerListener listener;

		public Composer(Context context) {
			this.context = context;
			blurredView = new View(context);
			blurredView.setTag(TAG);
			factor = new BlurFactor();
		}

		public Composer radius(int radius) {
			factor.radius = radius;
			return this;
		}

		public Composer sampling(int sampling) {
			factor.sampling = sampling;
			return this;
		}

		public Composer color(int color) {
			factor.color = color;
			return this;
		}

		public Composer async() {
			async = true;
			return this;
		}

		public Composer async(ImageComposer.ImageComposerListener listener) {
			async = true;
			this.listener = listener;
			return this;
		}

		public Composer animate() {
			animate = true;
			return this;
		}

		public Composer animate(int duration) {
			animate = true;
			this.duration = duration;
			return this;
		}

		public ImageComposer capture(View capture) {
			return new ImageComposer(context, capture, factor, async, listener);
		}

		public BitmapComposer from(Bitmap bitmap) {
			return new BitmapComposer(context, bitmap, factor, async, listener);
		}

		public void onto(final ViewGroup target) {
			factor.width = target.getMeasuredWidth();
			factor.height = target.getMeasuredHeight();

			if (async) {
				BlurTask task = new BlurTask(target, factor, new BlurTask.Callback() {
					@Override
					public void done(BitmapDrawable drawable) {
						addView(target, drawable);
					}
				});
				task.execute();
			} else {
				Drawable drawable = new BitmapDrawable(context.getResources(), Blur.of(target, factor));
				addView(target, drawable);
			}
		}

		private void addView(ViewGroup target, Drawable drawable) {
			Helper.setBackground(blurredView, drawable);
			target.addView(blurredView);

			if (animate) {
				Helper.animate(blurredView, duration);
			}
		}
	}

	public static class BitmapComposer {

		private Context context;
		private Bitmap bitmap;
		private BlurFactor factor;
		private boolean async;
		private ImageComposer.ImageComposerListener listener;

		public BitmapComposer(Context context, Bitmap bitmap, BlurFactor factor, boolean async
				, ImageComposer.ImageComposerListener listener) {
			this.context = context;
			this.bitmap = bitmap;
			this.factor = factor;
			this.async = async;
			this.listener = listener;
		}

		public void into(final ImageView target) {
			factor.width = bitmap.getWidth();
			factor.height = bitmap.getHeight();

			if (async) {
				BlurTask task = new BlurTask(target.getContext(), bitmap, factor, new BlurTask.Callback() {
					@Override
					public void done(BitmapDrawable drawable) {
						if (listener == null) {
							target.setImageDrawable(drawable);
						} else {
							listener.onImageReady(drawable);
						}
					}
				});
				task.execute();
			} else {
				Drawable drawable = new BitmapDrawable(context.getResources(),
						Blur.of(target.getContext(), bitmap, factor));
				target.setImageDrawable(drawable);
			}
		}
	}

	public static class ImageComposer {

		private Context context;
		private View capture;
		private BlurFactor factor;
		private boolean async;
		private ImageComposerListener listener;

		public ImageComposer(Context context, View capture, BlurFactor factor, boolean async,
							 ImageComposerListener listener) {
			this.context = context;
			this.capture = capture;
			this.factor = factor;
			this.async = async;
			this.listener = listener;
		}

		public void into(final ImageView target) {
			factor.width = capture.getMeasuredWidth();
			factor.height = capture.getMeasuredHeight();

			if (async) {
				BlurTask task = new BlurTask(capture, factor, new BlurTask.Callback() {
					@Override
					public void done(BitmapDrawable drawable) {
						if (listener == null) {
							target.setImageDrawable(drawable);
						} else {
							listener.onImageReady(drawable);
						}
					}
				});
				task.execute();
			} else {
				Drawable drawable = new BitmapDrawable(context.getResources(), Blur.of(capture, factor));
				target.setImageDrawable(drawable);
			}
		}

		public interface ImageComposerListener {
			void onImageReady(BitmapDrawable drawable);
		}
	}
}
