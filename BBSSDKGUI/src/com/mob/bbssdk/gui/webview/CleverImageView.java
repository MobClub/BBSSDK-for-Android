package com.mob.bbssdk.gui.webview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mob.bbssdk.gui.other.ImageGetter;


/**
 * Created by pan on 07/11/2017.
 */

public class CleverImageView extends SelectableRoundedImageView {
	private static final String TAG = "CleverImageView";

	protected String strUrl;
	protected Boolean enableCircle;

	public CleverImageView(Context context) {
		this(context, null);
	}

	public CleverImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CleverImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CleverImageView, defStyle, 0);
//        enableCircle = a.getBoolean(R.styleable.CleverImageView_enablecircle, false);
//        a.recycle();
		enableCircle = false;
		setOval(enableCircle);
	}

	public ImageGetter.Request setImageUrl(String url) {
		return setImageUrl(url, null, false);
	}

	public ImageGetter.Request setImageUrl(String url, Integer defaultres, boolean forceupdate) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		this.strUrl = url;
		return ImageGetter.loadPic(this, url, defaultres, forceupdate);
	}
}
