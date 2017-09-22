package com.mob.bbssdk.gui.other.ums;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class FlywheelView extends View {
	private static final int MAX_VELOCITY = 5000; // Pixes/Second
	private static final int ACCELERATION = 8000; // Pixes/Second^2
	private ArrayList<String> data;
	private int itemHeight;
	private Paint normalPaint;
	private Paint selectedPaint;
	private int maximumVelocity;
	private boolean scrolling;
	private float downY;
	private Scroller scroller;
	private VelocityTracker velocityTracker;
	private int selection;
	private int sepHeight;
	private OnSelectedListener listener;

	public FlywheelView(Context context) {
		super(context);
		init(context);
	}

	public FlywheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FlywheelView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		data = new ArrayList<String>();
		itemHeight = ResHelper.dipToPx(getContext(), 37);
		sepHeight = ResHelper.dipToPx(getContext(), 1);

		normalPaint = new Paint();
		normalPaint.setColor(0xffcccccc);
		normalPaint.setTextSize(ResHelper.dipToPx(getContext(), 14));
		normalPaint.setAntiAlias(true);
		normalPaint.setTextAlign(Paint.Align.CENTER);

		selectedPaint = new Paint();
		selectedPaint.setColor(0xffe3564b);
		selectedPaint.setTextSize(ResHelper.dipToPx(getContext(), 16));
		selectedPaint.setAntiAlias(true);
		selectedPaint.setTextAlign(Paint.Align.CENTER);

		ViewConfiguration configuration = ViewConfiguration.get(context);
		maximumVelocity = configuration.getScaledMaximumFlingVelocity();
		setOnTouchListener(newOnTouchListener());
		newScroller();
	}

	private void newScroller() {
		scroller = new Scroller(getContext(), new Interpolator() {
			public float getInterpolation(float t) {
				return 1 - (1 - t) * (1 - t) / 2;
			}
		});
	}

	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
		if (this.itemHeight <= 0) {
			this.itemHeight = ResHelper.dipToPx(getContext(), 37);
		}
	}

	public void setData(ArrayList<String> data, int selection) {
		this.data.clear();
		this.selection = selection;
		newScroller();
		if (data != null) {
			this.data.addAll(data);
		}
		scroller.startScroll(0, 0, 0, itemHeight * selection, 0);
		postInvalidate();
	}

	private OnTouchListener newOnTouchListener() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						scrolling = false;
						if (velocityTracker == null) {
							velocityTracker = VelocityTracker.obtain();
						}
						velocityTracker.addMovement(event);
						downY = event.getY();
					} return true;
					case MotionEvent.ACTION_MOVE: {
						scrolling = true;
						if (velocityTracker != null) {
							velocityTracker.addMovement(event);
						}
						float curY = event.getY();
						float deltaY = curY - downY;
						downY = curY;
						handleScroll(deltaY, 0);
					} return true;
					case MotionEvent.ACTION_CANCEL:
					case MotionEvent.ACTION_UP: {
						if (velocityTracker != null) {
							if (scrolling) {
								velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
								float velocityY = velocityTracker.getYVelocity();
								if (velocityY > MAX_VELOCITY) {
									velocityY = MAX_VELOCITY;
								} else if (velocityY < -MAX_VELOCITY) {
									velocityY = -MAX_VELOCITY;
								}
								float duration = Math.abs(velocityY / ACCELERATION);
								float deltaY = ACCELERATION * duration * duration / 2;
								deltaY = velocityY > 0 ? deltaY : (-deltaY);
								handleScroll(deltaY, (int) (duration * 1000));
							} else {
								float offsetY = event.getY() - getHeight() / 2;
								int targetItem = (int) (Math.abs(offsetY) / itemHeight + 0.5f);
								int deltaY = targetItem * itemHeight;
								deltaY = offsetY > 0 ? (-deltaY) : deltaY;
								handleScroll(deltaY, 100);
							}
							velocityTracker.recycle();
							velocityTracker = null;
						}
						scrolling = false;
					} break;
				}
				return false;
			}
		};
	}

	private void handleScroll(float deltaY, int durationUMs) {
		int scrollY = scroller.getCurrY();
		if (deltaY > 0) { // 向下
			deltaY = Math.max(scrollY - deltaY, 0) - scrollY;
		} else if (deltaY < 0) { // 向上
			deltaY = Math.min(scrollY - deltaY, itemHeight * data.size() - itemHeight) - scrollY;
		} else {
			return;
		}

		scroller.abortAnimation();
		scroller.startScroll(0, scrollY, 0, (int) deltaY, durationUMs);
		invalidate();
	}

	public void computeScroll() {
		float scrollY = scroller.getCurrY();
		int newSelection = (int) (scrollY / itemHeight + 0.5f);
		if (scroller.computeScrollOffset()) {
			postInvalidate();
		} else if (!scrolling) {
			int finalScrollY = newSelection * itemHeight;
			if (finalScrollY != scrollY) {
				float deltaY = finalScrollY - scrollY;
				int duration = (int) (Math.sqrt(2 * deltaY / ACCELERATION) * 1000);
				scroller.startScroll(0, (int) scrollY, 0, (int) deltaY, duration);
				postInvalidate();
			}

			if (newSelection != selection) {
				selection = newSelection;
				if (listener != null) {
					listener.onSelected(this, selection);
				}
			}
		}
	}

	protected void onDraw(Canvas canvas) {
		ArrayList<String> dataToDraw;
		if (data.isEmpty()) {
			dataToDraw = new ArrayList<String>();
			dataToDraw.add("----");
		} else {
			dataToDraw = data;
		}

		int width = getWidth();
		int height = getHeight();
		int scrollY = scroller.getCurrY();
		int clipTop = (height - itemHeight) / 2 + sepHeight;
		int clipBottom = (height + itemHeight) / 2 - sepHeight;
		for (int i = 0, size = dataToDraw.size(); i < size; i++) {
			int offset = height / 2 - scrollY + itemHeight * i;
			String text = dataToDraw.get(i);
			Rect bounds = new Rect();
			normalPaint.getTextBounds(text, 0, text.length(), bounds);
			int textCenter = offset + bounds.height() / 2;

			canvas.save();
			canvas.clipRect(0, 0, width, clipTop);
			canvas.drawText(text, width / 2, textCenter, normalPaint);
			canvas.restore();
			canvas.save();
			canvas.clipRect(0, clipTop, width, clipBottom);
			canvas.drawText(text, width / 2, textCenter, selectedPaint);
			canvas.restore();
			canvas.save();
			canvas.clipRect(0, clipBottom, width, height);
			canvas.drawText(text, width / 2, textCenter, normalPaint);
			canvas.restore();
		}

		float strokWidth = normalPaint.getStrokeWidth();
		normalPaint.setStrokeWidth(sepHeight);
		int offset = (height - itemHeight) / 2;
		canvas.drawLine(0, offset, width, offset, normalPaint);
		offset += itemHeight - sepHeight;
		canvas.drawLine(0, offset, width, offset, normalPaint);
		normalPaint.setStrokeWidth(strokWidth);
	}

	public void setOnSelectedListener(OnSelectedListener l) {
		listener = l;
	}

	public String getSelection() {
		if (data != null && data.size() > selection) {
			return data.get(selection);
		}
		return null;
	}

	public static interface OnSelectedListener {
		public void onSelected(FlywheelView view, int selection);
	}

}
