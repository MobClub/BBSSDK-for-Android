package com.mob.bbssdk.gui.webview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SelectableRoundedImageView extends ImageView {

	public static final String TAG = "SelectableRoundedIV";

	private int nResource = 0;

	private static final ScaleType[] SCALE_TYPE_ARRAY = {
			ScaleType.MATRIX,
			ScaleType.FIT_XY,
			ScaleType.FIT_START,
			ScaleType.FIT_CENTER,
			ScaleType.FIT_END,
			ScaleType.CENTER,
			ScaleType.CENTER_CROP,
			ScaleType.CENTER_INSIDE
	};

	// Set default scale type to FIT_CENTER, which is default scale type of
	// original ImageView.
	private ScaleType scaleType = ScaleType.FIT_CENTER;

	private float fLeftTopCornerRadius = 0.0f;
	private float fRightTopCornerRadius = 0.0f;
	private float fLeftBottomCornerRadius = 0.0f;
	private float fRightBottomCornerRadius = 0.0f;

	private float fBorderWidth = 0.0f;
	private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	private ColorStateList borderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);

	private boolean isOval = false;

	private Drawable mmDrawable;

	private float[] mmRad = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

	public SelectableRoundedImageView(Context context) {
		super(context);
	}

	public SelectableRoundedImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SelectableRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.SelectableRoundedImageView, defStyle, 0);

//        final int index = a.getInt(R.styleable.SelectableRoundedImageView_android_scaleType, -1);
		final int index = -1;
		if (index >= 0) {
			setScaleType(SCALE_TYPE_ARRAY[index]);
		}

//        fLeftTopCornerRadius = a.getDimensionPixelSize(
//                R.styleable.SelectableRoundedImageView_sriv_left_top_corner_radius, 0);
//        fRightTopCornerRadius = a.getDimensionPixelSize(
//                R.styleable.SelectableRoundedImageView_sriv_right_top_corner_radius, 0);
//        fLeftBottomCornerRadius = a.getDimensionPixelSize(
//                R.styleable.SelectableRoundedImageView_sriv_left_bottom_corner_radius, 0);
//        fRightBottomCornerRadius = a.getDimensionPixelSize(
//                R.styleable.SelectableRoundedImageView_sriv_right_bottom_corner_radius, 0);
		fLeftTopCornerRadius = 0;
		fRightTopCornerRadius = 0;
		fLeftBottomCornerRadius = 0;
		fRightBottomCornerRadius = 0;
		if (fLeftTopCornerRadius < 0.0f || fRightTopCornerRadius < 0.0f
				|| fLeftBottomCornerRadius < 0.0f || fRightBottomCornerRadius < 0.0f) {
			throw new IllegalArgumentException("radius values cannot be negative.");
		}

		mmRad = new float[]{
				fLeftTopCornerRadius, fLeftTopCornerRadius,
				fRightTopCornerRadius, fRightTopCornerRadius,
				fRightBottomCornerRadius, fRightBottomCornerRadius,
				fLeftBottomCornerRadius, fLeftBottomCornerRadius};

//        fBorderWidth = a.getDimensionPixelSize(
//                R.styleable.SelectableRoundedImageView_sriv_border_width, 0);
		fBorderWidth = 0;
		if (fBorderWidth < 0) {
			throw new IllegalArgumentException("border width cannot be negative.");
		}

//        borderColor = a
//                .getColorStateList(R.styleable.SelectableRoundedImageView_sriv_border_color);
		borderColor = null;
		if (borderColor == null) {
			borderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
		}

//        isOval = a.getBoolean(R.styleable.SelectableRoundedImageView_sriv_oval, false);
		isOval = false;
//        a.recycle();

		updateDrawable();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		invalidate();
	}

	@Override
	public ScaleType getScaleType() {
		return scaleType;
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		super.setScaleType(scaleType);
		this.scaleType = scaleType;
		updateDrawable();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		nResource = 0;
		mmDrawable = SelectableRoundedCornerDrawable.fromDrawable(drawable, getResources());
		super.setImageDrawable(mmDrawable);
		updateDrawable();
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		nResource = 0;
		mmDrawable = SelectableRoundedCornerDrawable.fromBitmap(bm, getResources());
		super.setImageDrawable(mmDrawable);
		updateDrawable();
	}

	@Override
	public void setImageResource(int resId) {
		if (nResource != resId) {
			nResource = resId;
			mmDrawable = resolveResource();
			super.setImageDrawable(mmDrawable);
			updateDrawable();
		}
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		setImageDrawable(getDrawable());
	}

	private Drawable resolveResource() {
		Resources rsrc = getResources();
		if (rsrc == null) {
			return null;
		}

		Drawable d = null;

		if (nResource != 0) {
			try {
				d = rsrc.getDrawable(nResource);
			} catch (NotFoundException e) {
				// Don't try again.
				nResource = 0;
			}
		}
		return SelectableRoundedCornerDrawable.fromDrawable(d, getResources());
	}

	private void updateDrawable() {
		if (mmDrawable == null) {
			return;
		}

		((SelectableRoundedCornerDrawable) mmDrawable).setScaleType(scaleType);
		((SelectableRoundedCornerDrawable) mmDrawable).setCornerRadii(mmRad);
		((SelectableRoundedCornerDrawable) mmDrawable).setBorderWidth(fBorderWidth);
		((SelectableRoundedCornerDrawable) mmDrawable).setBorderColor(borderColor);
		((SelectableRoundedCornerDrawable) mmDrawable).setOval(isOval);
	}

	public float getCornerRadius() {
		return fLeftTopCornerRadius;
	}

	/**
	 * Set radii for each corner.
	 *
	 * @param leftTop     The desired radius for left-top corner in dip.
	 * @param rightTop    The desired desired radius for right-top corner in dip.
	 * @param leftBottom  The desired radius for left-bottom corner in dip.
	 * @param rightBottom The desired radius for right-bottom corner in dip.
	 */
	public void setCornerRadiiDP(float leftTop, float rightTop, float leftBottom, float rightBottom) {
		final float density = getResources().getDisplayMetrics().density;

		final float lt = leftTop * density;
		final float rt = rightTop * density;
		final float lb = leftBottom * density;
		final float rb = rightBottom * density;

		mmRad = new float[]{lt, lt, rt, rt, rb, rb, lb, lb};
		updateDrawable();
	}

	public float getBorderWidth() {
		return fBorderWidth;
	}

	/**
	 * Set border width.
	 *
	 * @param width The desired width in dip.
	 */
	public void setBorderWidthDP(float width) {
		float scaledWidth = getResources().getDisplayMetrics().density * width;
		if (fBorderWidth == scaledWidth) {
			return;
		}

		fBorderWidth = scaledWidth;
		updateDrawable();
		invalidate();
	}

	public int getBorderColor() {
		return borderColor.getDefaultColor();
	}

	public void setBorderColor(int color) {
		setBorderColor(ColorStateList.valueOf(color));
	}

	public ColorStateList getBorderColors() {
		return borderColor;
	}

	public void setBorderColor(ColorStateList colors) {
		if (borderColor.equals(colors)) {
			return;
		}

		borderColor = (colors != null) ? colors : ColorStateList
				.valueOf(DEFAULT_BORDER_COLOR);
		updateDrawable();
		if (fBorderWidth > 0) {
			invalidate();
		}
	}

	public boolean isOval() {
		return isOval;
	}

	public void setOval(boolean oval) {
		isOval = oval;
		updateDrawable();
		invalidate();
	}

	static class SelectableRoundedCornerDrawable extends Drawable {

		private static final String TAG = "SelectableRoundCornD";
		private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

		private RectF mmBounds = new RectF();
		private RectF mmBorderBounds = new RectF();

		private final RectF mmBitmapRect = new RectF();
		private final int mmBitmapWidth;
		private final int mmBitmapHeight;

		private final Paint mmBitmapPaint;
		private final Paint mmBorderPaint;

		private BitmapShader mmBitmapShader;

		private float[] mmRadii = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
		private float[] mmBorderRadii = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

		private boolean mmOval = false;

		private float mmBorderWidth = 0;
		private ColorStateList mmBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
		// Set default scale type to FIT_CENTER, which is default scale type of
		// original ImageView.
		private ScaleType mmScaleType = ScaleType.FIT_CENTER;

		private Path mbPath = new Path();
		private Bitmap mbBitmap;
		private boolean mmBoundsConfigured = false;

		public SelectableRoundedCornerDrawable(Bitmap bitmap, Resources r) {
			mbBitmap = bitmap;
			mmBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

			if (bitmap != null) {
				mmBitmapWidth = bitmap.getScaledWidth(r.getDisplayMetrics());
				mmBitmapHeight = bitmap.getScaledHeight(r.getDisplayMetrics());
			} else {
				mmBitmapWidth = mmBitmapHeight = -1;
			}

			mmBitmapRect.set(0, 0, mmBitmapWidth, mmBitmapHeight);

			mmBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mmBitmapPaint.setStyle(Paint.Style.FILL);
			mmBitmapPaint.setShader(mmBitmapShader);

			mmBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mmBorderPaint.setStyle(Paint.Style.STROKE);
			mmBorderPaint.setColor(mmBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
			mmBorderPaint.setStrokeWidth(mmBorderWidth);
		}

		public static SelectableRoundedCornerDrawable fromBitmap(Bitmap bitmap, Resources r) {
			if (bitmap != null) {
				return new SelectableRoundedCornerDrawable(bitmap, r);
			} else {
				return null;
			}
		}

		public static Drawable fromDrawable(Drawable drawable, Resources r) {
			if (drawable != null) {
				if (drawable instanceof SelectableRoundedCornerDrawable) {
					return drawable;
				} else if (drawable instanceof LayerDrawable) {
					LayerDrawable ld = (LayerDrawable) drawable;
					final int num = ld.getNumberOfLayers();
					for (int i = 0; i < num; i++) {
						Drawable d = ld.getDrawable(i);
						ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d, r));
					}
					return ld;
				}

				Bitmap bm = drawableToBitmap(drawable);
				if (bm != null) {
					return new SelectableRoundedCornerDrawable(bm, r);
				} else {
				}
			}
			return drawable;
		}

		public static Bitmap drawableToBitmap(Drawable drawable) {
			if (drawable == null) {
				return null;
			}

			if (drawable instanceof BitmapDrawable) {
				return ((BitmapDrawable) drawable).getBitmap();
			}

			Bitmap bitmap;
			int width = Math.max(drawable.getIntrinsicWidth(), 2);
			int height = Math.max(drawable.getIntrinsicHeight(), 2);
			try {
				bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
				drawable.draw(canvas);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				bitmap = null;
			}
			return bitmap;
		}

		@Override
		public boolean isStateful() {
			return mmBorderColor.isStateful();
		}

		@Override
		protected boolean onStateChange(int[] state) {
			int newColor = mmBorderColor.getColorForState(state, 0);
			if (mmBorderPaint.getColor() != newColor) {
				mmBorderPaint.setColor(newColor);
				return true;
			} else {
				return super.onStateChange(state);
			}
		}

		private void configureBounds(Canvas canvas) {
			// I have discovered a truly marvelous explanation of this,
			// which this comment space is too narrow to contain. :)
			// If you want to understand what's going on here,
			// See http://www.joooooooooonhokim.com/?p=289
			Rect clipBounds = canvas.getClipBounds();
			Matrix canvasMatrix = canvas.getMatrix();

			if (ScaleType.CENTER == mmScaleType) {
				mmBounds.set(clipBounds);
			} else if (ScaleType.CENTER_CROP == mmScaleType) {
				applyScaleToRadii(canvasMatrix);
				mmBounds.set(clipBounds);
			} else if (ScaleType.FIT_XY == mmScaleType) {
				Matrix m = new Matrix();
				m.setRectToRect(mmBitmapRect, new RectF(clipBounds), Matrix.ScaleToFit.FILL);
				mmBitmapShader.setLocalMatrix(m);
				mmBounds.set(clipBounds);
			} else if (ScaleType.FIT_START == mmScaleType || ScaleType.FIT_END == mmScaleType
					|| ScaleType.FIT_CENTER == mmScaleType || ScaleType.CENTER_INSIDE == mmScaleType) {
				applyScaleToRadii(canvasMatrix);
				mmBounds.set(mmBitmapRect);
			} else if (ScaleType.MATRIX == mmScaleType) {
				applyScaleToRadii(canvasMatrix);
				mmBounds.set(mmBitmapRect);
			}
		}

		private void applyScaleToRadii(Matrix m) {
			float[] values = new float[9];
			m.getValues(values);
			for (int i = 0; i < mmRadii.length; i++) {
				mmRadii[i] = mmRadii[i] / values[0];
			}
		}

		private void adjustCanvasForBorder(Canvas canvas) {
			Matrix canvasMatrix = canvas.getMatrix();
			final float[] values = new float[9];
			canvasMatrix.getValues(values);

			final float scaleFactorX = values[0];
			final float scaleFactorY = values[4];
			final float translateX = values[2];
			final float translateY = values[5];

			final float newScaleX = mmBounds.width()
					/ (mmBounds.width() + mmBorderWidth + mmBorderWidth);
			final float newScaleY = mmBounds.height()
					/ (mmBounds.height() + mmBorderWidth + mmBorderWidth);

			canvas.scale(newScaleX, newScaleY);
			if (ScaleType.FIT_START == mmScaleType || ScaleType.FIT_END == mmScaleType
					|| ScaleType.FIT_XY == mmScaleType || ScaleType.FIT_CENTER == mmScaleType
					|| ScaleType.CENTER_INSIDE == mmScaleType || ScaleType.MATRIX == mmScaleType) {
				canvas.translate(mmBorderWidth, mmBorderWidth);
			} else if (ScaleType.CENTER == mmScaleType || ScaleType.CENTER_CROP == mmScaleType) {
				// First, make translate values to 0
				canvas.translate(
						-translateX / (newScaleX * scaleFactorX),
						-translateY / (newScaleY * scaleFactorY));
				// Then, set the final translate values.
				canvas.translate(-(mmBounds.left - mmBorderWidth), -(mmBounds.top - mmBorderWidth));
			}
		}

		private void adjustBorderWidthAndBorderBounds(Canvas canvas) {
			Matrix canvasMatrix = canvas.getMatrix();
			final float[] values = new float[9];
			canvasMatrix.getValues(values);

			final float scaleFactor = values[0];

			float viewWidth = mmBounds.width() * scaleFactor;
			mmBorderWidth = (mmBorderWidth * mmBounds.width()) / (viewWidth - (2 * mmBorderWidth));
			mmBorderPaint.setStrokeWidth(mmBorderWidth);

			mmBorderBounds.set(mmBounds);
			mmBorderBounds.inset(-mmBorderWidth / 2, -mmBorderWidth / 2);
		}

		private void setBorderRadii() {
			for (int i = 0; i < mmRadii.length; i++) {
				if (mmRadii[i] > 0) {
					mmBorderRadii[i] = mmRadii[i];
					mmRadii[i] = mmRadii[i] - mmBorderWidth;
				}
			}
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.save();
			if (!mmBoundsConfigured) {
				configureBounds(canvas);
				if (mmBorderWidth > 0) {
					adjustBorderWidthAndBorderBounds(canvas);
					setBorderRadii();
				}
				mmBoundsConfigured = true;
			}

			if (mmOval) {
				if (mmBorderWidth > 0) {
					adjustCanvasForBorder(canvas);
					mbPath.addOval(mmBounds, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBitmapPaint);
					mbPath.reset();
					mbPath.addOval(mmBorderBounds, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBorderPaint);
				} else {
					mbPath.addOval(mmBounds, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBitmapPaint);
				}
			} else {
				if (mmBorderWidth > 0) {
					adjustCanvasForBorder(canvas);
					mbPath.addRoundRect(mmBounds, mmRadii, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBitmapPaint);
					mbPath.reset();
					mbPath.addRoundRect(mmBorderBounds, mmBorderRadii, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBorderPaint);
				} else {
					mbPath.addRoundRect(mmBounds, mmRadii, Path.Direction.CW);
					canvas.drawPath(mbPath, mmBitmapPaint);
				}
			}
			canvas.restore();
		}

		public void setCornerRadii(float[] radii) {
			if (radii == null) {
				return;
			}

			if (radii.length != 8) {
				throw new ArrayIndexOutOfBoundsException("radii[] needs 8 values");
			}

			for (int i = 0; i < radii.length; i++) {
				mmRadii[i] = radii[i];
			}
		}

		@Override
		public int getOpacity() {
			return (mbBitmap == null || mbBitmap.hasAlpha() || mmBitmapPaint.getAlpha() < 255) ? PixelFormat.TRANSLUCENT
					: PixelFormat.OPAQUE;
		}

		@Override
		public void setAlpha(int alpha) {
			mmBitmapPaint.setAlpha(alpha);
			invalidateSelf();
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			mmBitmapPaint.setColorFilter(cf);
			invalidateSelf();
		}

		@Override
		public void setDither(boolean dither) {
			mmBitmapPaint.setDither(dither);
			invalidateSelf();
		}

		@Override
		public void setFilterBitmap(boolean filter) {
			mmBitmapPaint.setFilterBitmap(filter);
			invalidateSelf();
		}

		@Override
		public int getIntrinsicWidth() {
			return mmBitmapWidth;
		}

		@Override
		public int getIntrinsicHeight() {
			return mmBitmapHeight;
		}

		public float getBorderWidth() {
			return mmBorderWidth;
		}

		public void setBorderWidth(float width) {
			mmBorderWidth = width;
			mmBorderPaint.setStrokeWidth(width);
		}

		public int getBorderColor() {
			return mmBorderColor.getDefaultColor();
		}

		public void setBorderColor(int color) {
			setBorderColor(ColorStateList.valueOf(color));
		}

		public ColorStateList getBorderColors() {
			return mmBorderColor;
		}

		/**
		 * Controls border color of this ImageView.
		 *
		 * @param colors The desired border color. If it's null, no border will be
		 *               drawn.
		 */
		public void setBorderColor(ColorStateList colors) {
			if (colors == null) {
				mmBorderWidth = 0;
				mmBorderColor = ColorStateList.valueOf(Color.TRANSPARENT);
				mmBorderPaint.setColor(Color.TRANSPARENT);
			} else {
				mmBorderColor = colors;
				mmBorderPaint.setColor(mmBorderColor.getColorForState(getState(),
						DEFAULT_BORDER_COLOR));
			}
		}

		public boolean isOval() {
			return mmOval;
		}

		public void setOval(boolean oval) {
			mmOval = oval;
		}

		public ScaleType getScaleType() {
			return mmScaleType;
		}

		public void setScaleType(ScaleType scaleType) {
			if (scaleType == null) {
				return;
			}
			mmScaleType = scaleType;
		}
	}

}
