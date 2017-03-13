package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

/** 标题栏 */
public class TitleBar extends FrameLayout {
	public static final int TYPE_TITLE = 0;
	public static final int TYPE_LEFT_IMAGE = 1;
	public static final int TYPE_LEFT_TEXT = 2;
	public static final int TYPE_RIGHT_IMAGE = 3;
	public static final int TYPE_RIGHT_TEXT = 4;

	private ImageView ivLeft;
	private TextView tvLeft;
	private TextView tvRight;
	private ImageView ivRight;
	private TextView tvTitle;

	public TitleBar(Context context) {
		super(context);
		init(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setBackgroundResource(ResHelper.getColorRes(context, "bbs_title_bg"));
		int titleHeight = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_bar_height"));
		ivLeft = new ImageView(context);
		ivLeft.setScaleType(ScaleType.CENTER);
		LayoutParams lp = new LayoutParams(titleHeight, titleHeight);
		addView(ivLeft, lp);
		ivLeft.setVisibility(GONE);

		int colorTvBtn = getResources().getColor(ResHelper.getColorRes(context, "bbs_title_txt_btn"));
		tvLeft = new TextView(context);
		tvLeft.setGravity(Gravity.CENTER);
		int paddingLeft = ResHelper.dipToPx(context, 10);
		tvLeft.setPadding(paddingLeft, 0, paddingLeft, 0);
		tvLeft.setSingleLine();
		tvLeft.setTextColor(colorTvBtn);
		int tvLeftSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_left_txt_size"));
		tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvLeftSize);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, titleHeight);
		lp.gravity = Gravity.LEFT;
		addView(tvLeft, lp);
		tvLeft.setVisibility(GONE);

		tvRight = new TextView(context);
		tvRight.setGravity(Gravity.CENTER);
		tvRight.setPadding(paddingLeft, 0, paddingLeft, 0);
		tvRight.setSingleLine();
		tvRight.setTextColor(colorTvBtn);
		int tvRightSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_right_txt_size"));
		tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvRightSize);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, titleHeight);
		lp.gravity = Gravity.RIGHT;
		addView(tvRight, lp);
		tvRight.setVisibility(GONE);

		ivRight = new ImageView(context);
		ivRight.setScaleType(ScaleType.CENTER);
		lp = new LayoutParams(titleHeight, titleHeight);
		lp.gravity = Gravity.RIGHT;
		addView(ivRight, lp);
		ivRight.setVisibility(GONE);

		int colorTvTitle = getResources().getColor(ResHelper.getColorRes(context, "bbs_title_txt_title"));

		tvTitle = new TextView(context);
		tvTitle.setGravity(Gravity.CENTER);
		tvTitle.setSingleLine();
		tvTitle.setTextColor(colorTvTitle);
		int tvTitleSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_txt_size"));
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTitleSize);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, titleHeight);
		lp.leftMargin = titleHeight;
		lp.rightMargin = titleHeight;
		addView(tvTitle, lp);
	}

	/**
	 * 设置标题栏左边图标为默认的返回图标
	 */
	public void setLeftImageResourceDefaultBack() {
		setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_back"));
	}

	/**
	 * 设置标题栏左边图标为默认的关闭图标
	 */
	public void setLeftImageResourceDefaultClose() {
		setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_close"));
	}

	/**
	 * 设置标题栏右边图标为默认的更多图标
	 */
	public void setRightImageResourceDefaultMore() {
		setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_more"));
	}

	public void setLeftImageResource(int resId) {
		ivLeft.setImageResource(resId);
		ivLeft.setVisibility(VISIBLE);
	}

	public void setTvRight(String text) {
		tvRight.setText(text);
		tvRight.setVisibility(VISIBLE);
	}

	public void setTvLeft(String text) {
		tvLeft.setText(text);
		tvLeft.setVisibility(VISIBLE);
	}

	public void setRightImageResource(int resId) {
		ivRight.setImageResource(resId);
		ivRight.setVisibility(VISIBLE);
	}

	public ImageView getLeftImageView() {
		return ivLeft;
	}

	public ImageView getRightImageView() {
		return ivRight;
	}

	public TextView getLeftTextView() {
		return tvLeft;
	}

	public TextView getRightTextView() {
		return tvRight;
	}

	public TextView getTitleTextView() {
		return tvTitle;
	}

	public void setTitle(int resId) {
		tvTitle.setText(resId);
	}

	public void setTitle(String text) {
		tvTitle.setText(text);
	}

	public void setOnClickListener(final OnClickListener l) {
		OnClickListener ocl = new OnClickListener() {
			public void onClick(View v) {
				if (v.equals(ivLeft)) {
					setTag(TYPE_LEFT_IMAGE);
				} else if (v.equals(tvLeft)) {
					setTag(TYPE_LEFT_TEXT);
				} else if (v.equals(tvRight)) {
					setTag(TYPE_RIGHT_TEXT);
				} else if (v.equals(ivRight)) {
					setTag(TYPE_RIGHT_IMAGE);
				} else if (v.equals(tvTitle)) {
					setTag(TYPE_TITLE);
				}
				if (l != null) {
					l.onClick(TitleBar.this);
				}
			}
		};
		tvLeft.setOnClickListener(ocl);
		ivLeft.setOnClickListener(ocl);
		tvTitle.setOnClickListener(ocl);
		tvRight.setOnClickListener(ocl);
		ivRight.setOnClickListener(ocl);
	}
}
