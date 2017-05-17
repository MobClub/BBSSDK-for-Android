package com.mob.bbssdk.gui.ptrlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.mob.tools.utils.ResHelper;

public class ChrysanthemumView extends RelativeLayout {
	private View chrysanthemum;
	private View finish;

	public ChrysanthemumView(Context context) {
		super(context);
		init(context);
	}

	public ChrysanthemumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ChrysanthemumView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		chrysanthemum = initChrysanthemum(context);
		int px38 = ResHelper.dipToPx(getContext(), 19);
		LayoutParams lp = new LayoutParams(px38, px38);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(chrysanthemum, lp);

		finish = new View(context);
		finish.setBackgroundResource(ResHelper.getBitmapRes(getContext(), "bbs_header_load_finished"));
		addView(finish, lp);
		finish.setVisibility(View.INVISIBLE);
	}

	private View initChrysanthemum(Context context) {
		return new View(context) {
			private int[] frames = {0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330};
			private int frameInterval = 60;

			private int index;
			private Bitmap chrysanthemum;
			private Rect srcRect;
			private RectF dstRect;
			private Paint paint;

			protected void onDraw(Canvas canvas) {
				if (dstRect == null) {
					init(getWidth(), getHeight());
				}

				canvas.save();
				canvas.rotate(frames[index], dstRect.width() / 2, dstRect.height() / 2);
				canvas.drawBitmap(chrysanthemum, srcRect, dstRect, paint);
				canvas.restore();

				index++;
				if (index == frames.length) {
					index = 0;
				}

				if (getVisibility() == VISIBLE) {
					postInvalidateDelayed(frameInterval);
				}
			}

			private void init(float width, float height) {
				chrysanthemum = BitmapFactory.decodeResource(getResources(), ResHelper.getBitmapRes(getContext(), "bbs_header_loading"));
				srcRect = new Rect(0, 0, chrysanthemum.getWidth(), chrysanthemum.getHeight());

				float left = (width - srcRect.width()) / 2f;
				float top = (height - srcRect.height()) / 2f;
				dstRect = new RectF(left, top, width, height);

				paint = new Paint();
				paint.setAntiAlias(true);
			}
		};
	}

	public void showFinish(final Runnable onShow, final Runnable onDismiss) {
		chrysanthemum.setVisibility(View.INVISIBLE);
		if (onShow != null) {
			onShow.run();
		}
		finish.setVisibility(View.VISIBLE);
		chrysanthemum.post(onDismiss);
	}

	public void reset() {
		finish.clearAnimation();
		finish.setVisibility(View.INVISIBLE);
		chrysanthemum.setVisibility(VISIBLE);
	}

}
