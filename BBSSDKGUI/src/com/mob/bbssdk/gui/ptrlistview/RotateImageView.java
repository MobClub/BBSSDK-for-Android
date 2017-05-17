package com.mob.bbssdk.gui.ptrlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotateImageView extends ImageView {
	private float rotation;

	public RotateImageView(Context context) {
		super(context);
	}

	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setRotation(float rotation) {
		if (Build.VERSION.SDK_INT >= 11) {
			super.setRotation(rotation);
		} else {
			this.rotation = rotation;
			postInvalidate();
		}
	}

	protected void onDraw(Canvas canvas) {
		if (Build.VERSION.SDK_INT >= 11) {
			super.onDraw(canvas);
		} else {
			canvas.save();
			canvas.rotate(rotation, getWidth() / 2, getHeight() / 2);
			super.onDraw(canvas);
			canvas.restore();
		}
	}

}
