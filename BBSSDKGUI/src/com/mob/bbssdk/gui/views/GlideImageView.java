package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mob.bbssdk.gui.helper.ImageHelper;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ReflectHelper;
import com.mob.bbssdk.gui.other.ImageGetter;

public class GlideImageView extends ImageView {
	private static final String TAG = "GlideImageView";
	private int roundedCorner = 0;
	private boolean roundedPic = false;
	private String strUrl;
	private Integer nDefaultRes;

	public GlideImageView(Context context) {
		super(context);
		init(null, 0);
	}

	public GlideImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr);
	}

	public int[] getStyleables(String name) {
		Context context = getContext();
		if (name == null || name.length() == 0) {
			throw new IllegalStateException("No valid styleables name provided!");
		}
		try {
			String className = ReflectHelper.importClass("com.mob.bbssdk.gui.R$styleable");
			Object res = ReflectHelper.getStaticField(className, name);
			if (res == null) {
				return new int[0];
			} else {
				if (res.getClass().isArray()) {
					return (int[]) ((int[]) res);
				} else {
					return new int[]{((Integer) res).intValue()};
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

	public int getStyleable(String name) {
		Context context = getContext();
		if (name == null || name.length() == 0) {
			throw new IllegalStateException("No valid styleables name provided!");
		}
		try {
			String className = ReflectHelper.importClass("com.mob.bbssdk.gui.R$styleable");
			Object res = ReflectHelper.getStaticField(className, name);
			if (res == null) {
				return 0;
			} else {
				return (Integer) res;
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return 0;
		}
	}

	private void init(AttributeSet attrs, int defStyle) {
		Context context = getContext();
		TypedArray array = getContext().obtainStyledAttributes(attrs, getStyleables("GlideImageViewStyle"), defStyle, 0);
		roundedCorner = (int) array.getDimension(getStyleable("GlideImageViewStyle_roundCorner"), roundedCorner);
		roundedPic = array.getBoolean(getStyleable("GlideImageViewStyle_roundedPic"), roundedPic);
		array.recycle();
	}

	public void execute(String url) {
		execute(url, 0);
	}

	public void execute(String url, final Integer defaultres) {
		execute(url, defaultres, false);
	}

	public void execute(String url, final Integer defaultres, final Integer errorres) {
		execute(url, defaultres, false);
	}

	public void executeForce(String url, final Integer defaultres) {
		execute(url, defaultres, true);
	}

	public void execute(String url, final Integer defaultres, boolean forceupdate) {
		this.strUrl = url;
		this.nDefaultRes = defaultres;
		loadImage(forceupdate);
	}

	public void loadImage() {
		loadImage(false);
	}

	public void loadImage(boolean forceupdate) {
		if (StringUtils.isEmpty(strUrl)) {
			if (nDefaultRes != null && nDefaultRes > 0) {
				setImageResource(nDefaultRes);
			} else {
				//"Invalid load params! strUrl: " + strUrl + " nDefaultRes: " + nDefaultRes
				return;
			}
			return;
		}
		Context context = getContext();
		if(null == nDefaultRes) {
			nDefaultRes = -1;
		}
		Bitmap bm = BitmapFactory.decodeResource(getResources(), nDefaultRes);
		ImageGetter.loadPic(this, strUrl, bm, forceupdate);
	}

	@Override
	public void setImageResource(int resId) {
		Bitmap bitmap = ImageHelper.loadBitmapById(getContext(), resId);
		setImageBitmap(bitmap);
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		super.setImageBitmap(getRoundedBitmap(bitmap));
	}

	public void setBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap);
	}

	private Bitmap getRoundedBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		Bitmap bitmapround = bitmap;
		if (roundedPic) {
			bitmapround = ImageHelper.getRoundBitmap(bitmap);
		} else if (roundedCorner > 0) {
			bitmapround = ImageHelper.getRoundedCornerBitmap(bitmap, roundedCorner);
		}
		return bitmapround;
	}

	public static class NoSupportOperation extends RuntimeException {
		NoSupportOperation(String str) {
			super(str);
		}
	}

	public void setLoadParam(String url, Integer defaultres) {
		this.strUrl = url;
		this.nDefaultRes = defaultres;
	}

	/*
		by dp
	*/
	public void setExecuteRound(Integer corner) {
		roundedCorner = ScreenUtils.dpToPx(corner);
	}

	public void setExecuteRound() {
		roundedPic = true;
	}
}
