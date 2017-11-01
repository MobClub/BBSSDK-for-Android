package com.mob.bbssdk.gui.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.mob.bbssdk.gui.helper.ImageHelper;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ReflectHelper;

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

	public GlideImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public GlideImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
		BitmapTypeRequest<String> typerequest = Glide.with(context).load(strUrl).asBitmap();
		if (forceupdate) {
			//add time signature to force update the photo.
			typerequest.signature(new StringSignature("" + System.currentTimeMillis()));
		}
		typerequest.listener(new RequestListener<String, Bitmap>() {
			@Override
			public boolean onException(Exception e, String s, Target<Bitmap> target, boolean b) {
				if (nDefaultRes != null && nDefaultRes > 0) {
					setImageResource(nDefaultRes);
				}
				return false;
			}

			@Override
			public boolean onResourceReady(Bitmap bitmap, String s, Target<Bitmap> target, boolean b, boolean b1) {
				return false;
			}
		}).into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
				setImageBitmap(bitmap);
			}
		});
	}

	@Override
	public void setImageResource(int resId) {
		Bitmap bitmap = ImageHelper.loadBitmapById(getContext(), resId);
		setImageBitmap(bitmap);
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		super.setImageBitmap(getRoundedBitmap(bitmap));
	}

	public void setBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap);
	}

	private Bitmap getRoundedBitmap(Bitmap bitmap) {
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
