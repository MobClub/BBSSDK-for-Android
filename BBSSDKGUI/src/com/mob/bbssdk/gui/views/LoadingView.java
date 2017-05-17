package com.mob.bbssdk.gui.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class LoadingView extends AnimView {
	private Paint paintDraw;
	private float fWidth = 0f;
	private float fHeight = 0f;
	private float fMaxRadius = 8;
	private int circularCount = 5;
	private float fAnimatedValue = 1.0f;
	private int nJumpValue = 0;
	private float circularX;

	public LoadingView(Context context) {
		super(context);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		fWidth = getMeasuredWidth();
		fHeight = getMeasuredHeight();
		circularX = fWidth / circularCount;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int currentpost = nJumpValue % circularCount;
		for (int i = 0; i < circularCount; i++) {
			if (i == currentpost) {
				canvas.drawCircle(i * circularX + circularX / 2f, fHeight / 2,
						fMaxRadius * 2f, paintDraw);
			} else if (i == (currentpost - 1 + circularCount) % circularCount) {
				canvas.drawCircle(i * circularX + circularX / 2f, fHeight / 2,
						fMaxRadius * 1.5f, paintDraw);
			} else if (i == (currentpost - 2 + circularCount) % circularCount) {
				canvas.drawCircle(i * circularX + circularX / 2f, fHeight / 2,
						fMaxRadius * 1.2f, paintDraw);
			} else {
				canvas.drawCircle(i * circularX + circularX / 2f, fHeight / 2,
						fMaxRadius, paintDraw);
			}
		}
	}

	private void initPaint() {
		paintDraw = new Paint();
		paintDraw.setAntiAlias(true);
		paintDraw.setStyle(Paint.Style.FILL);
		paintDraw.setColor(Color.WHITE);
	}

	public void setViewColor(int color) {
		paintDraw.setColor(color);
		postInvalidate();
	}

	@Override
	protected void OnAnimationRepeat(Animator animation) {
		nJumpValue++;
	}

	@Override
	protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
		fAnimatedValue = (Float) valueAnimator.getAnimatedValue();
		if (fAnimatedValue < 0.2) {
			fAnimatedValue = 0.2f;
		}
		invalidate();
	}

	@Override
	protected int OnStopAnim() {
		fAnimatedValue = 0f;
		nJumpValue = 0;
		return 0;
	}

	@Override
	protected int SetAnimRepeatMode() {
		return ValueAnimator.RESTART;
	}

	@Override
	protected void InitPaint() {
		initPaint();
	}

	@Override
	protected void AinmIsRunning() {

	}

	@Override
	protected int SetAnimRepeatCount() {
		return ValueAnimator.INFINITE;
	}
}
