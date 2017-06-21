package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.model.User;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.utils.ResHelper;

/** 标题栏 */
public class TitleBar extends FrameLayout {
	public static final int TYPE_TITLE = 0;
	public static final int TYPE_LEFT_IMAGE = 1;
	public static final int TYPE_LEFT_TEXT = 2;
	public static final int TYPE_RIGHT_IMAGE = 3;
	public static final int TYPE_RIGHT_TEXT = 4;
	public static final int TYPE_RIGHT_PB = 5;

	private ImageView ivLeft;
	private AsyncImageView ivAvatar;
	private TextView tvLeft;
	private TextView tvRight;
	private ImageView ivRight;
	private View viewCenter;
	private ProgressBar pbRight;

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
		TypedValue outvalue = new TypedValue();
		getContext().getResources().getValue(ResHelper.getResId(context, "dimen", "bbs_title_bg_alpha"), outvalue, true);
		setAlpha(outvalue.getFloat());
		int titleHeight = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_bar_height"));
		ivLeft = new ImageView(context);
		ivLeft.setScaleType(ScaleType.CENTER);
		LayoutParams lp = new LayoutParams(titleHeight, titleHeight);
		addView(ivLeft, lp);
		ivLeft.setVisibility(GONE);

		int avatarSize = ResHelper.dipToPx(getContext(), 28);
		if (avatarSize > titleHeight) {
			avatarSize = titleHeight;
		}
		ivAvatar = new AsyncImageView(context);
		ivAvatar.setRound(avatarSize / 2);
		ivAvatar.setUseCacheOption(true, true);
		ivAvatar.setCompressOptions(200, 200, 70, 0L);
		lp = new LayoutParams(avatarSize, avatarSize);
		lp.leftMargin = (titleHeight - avatarSize) / 2;
		lp.topMargin = (titleHeight - avatarSize) / 2;
		addView(ivAvatar, lp);
		ivAvatar.setVisibility(GONE);

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

		pbRight = new ProgressBar(context);
		pbRight.setIndeterminate(true);
		int drawableId = ResHelper.getBitmapRes(context, "bbs_anim_rotate");
		pbRight.setIndeterminateDrawable(context.getResources().getDrawable(drawableId));
		int pbHeight = ResHelper.dipToPx(context, 30);
		lp = new LayoutParams(pbHeight, pbHeight);
		lp.rightMargin = (titleHeight - pbHeight) / 2;
		lp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		addView(pbRight, lp);
		pbRight.setVisibility(GONE);

		viewCenter = getCenterView();
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, titleHeight);
		lp.leftMargin = titleHeight;
		lp.rightMargin = titleHeight;
		addView(viewCenter, lp);
	}

	protected View getCenterView() {
		Context context = getContext();
		int colorTvTitle = getResources().getColor(ResHelper.getColorRes(context, "bbs_title_txt_title"));
		TextView tvTitle = new TextView(context);
		tvTitle.setGravity(Gravity.CENTER);
		tvTitle.setSingleLine();
		tvTitle.setTextColor(colorTvTitle);
		int tvTitleSize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_txt_size"));
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTitleSize);
		return tvTitle;
	}

	/**
	 * 设置标题栏左边图标为默认的返回图标
	 *
	 */
	public void setLeftImageResourceDefaultBack() {
		setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_subject_back_black"));
	}

	/**
	 * 设置标题栏左边图标为默认的关闭图标
	 *
	 */
	public void setLeftImageResourceDefaultClose() {
		setLeftImageResource(ResHelper.getBitmapRes(getContext(), "bbs_titlebar_close_black"));
	}

	/**
	 * 设置标题栏右边图标为默认的更多图标
	 *
	 */
	public void setRightImageResourceDefaultMore() {
		setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_more"));
	}

	/**
	 * 设置标题栏左边图标资源id
	 *
	 */
	public void setLeftImageResource(int resId) {
		ivLeft.setImageResource(resId);
		ivLeft.setVisibility(VISIBLE);
	}

	/**
	 * 设置标题栏左边用户头像默认图标。
	 *
	 * @param defaultResId 未登录的资源id
	 * @param defaultUserId 默认已登录的用户头像资源id
	 */
	public void setLeftUserAvatar(int defaultResId, int defaultUserId) {
		User user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
		if (user == null || TextUtils.isEmpty(user.avatar)) {
			ivLeft.setImageResource(defaultResId);
			ivLeft.setVisibility(View.VISIBLE);
			ivAvatar.setVisibility(View.GONE);
		} else {
			ivAvatar.execute(null, defaultUserId);
			ivAvatar.execute(user.avatar, defaultUserId);
			ivAvatar.setVisibility(View.VISIBLE);
			ivLeft.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边按钮文本
	 *
	 * @param text
	 */
	public void setTvRight(String text) {
		tvRight.setText(text);
		tvRight.setVisibility(VISIBLE);
		pbRight.setVisibility(View.GONE);
	}

	/**
	 * 设置左边按钮文本
	 *
	 * @param text
	 */
	public void setTvLeft(String text) {
		tvLeft.setText(text);
		tvLeft.setVisibility(VISIBLE);
	}

	/**
	 * 设置右边图片资源id
	 *
	 * @param resId
	 */
	public void setRightImageResource(int resId) {
		ivRight.setImageResource(resId);
		ivRight.setVisibility(VISIBLE);
		pbRight.setVisibility(View.GONE);
	}

	/**
	 * 设置右边进度条
	 *
	 */
	public void setRightProgressBar() {
		tvRight.setVisibility(View.GONE);
		ivRight.setVisibility(View.GONE);
		pbRight.setVisibility(View.VISIBLE);
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
		if(viewCenter instanceof TextView) {
			return (TextView) viewCenter;
		} else {
			return null;
		}
	}

	/**
	 * 设置标题文本资源id
	 *
	 * @param resId
	 */
	public void setTitle(int resId) {
		if(viewCenter instanceof TextView) {
			TextView tv = (TextView) viewCenter;
			tv.setText(resId);
		}
	}

	/**
	 * 设置标题文本
	 *
	 * @param text
	 */
	public void setTitle(String text) {
		if(viewCenter instanceof TextView) {
			TextView tv = (TextView) viewCenter;
			tv.setText(text);
		}
	}

	/**
	 * 设置点击事件回调
	 *
	 * @param l
	 */
	public void setOnClickListener(final OnClickListener l) {
		OnClickListener ocl = new OnClickListener() {
			public void onClick(View v) {
				if (v.equals(ivLeft) || v.equals(ivAvatar)) {
					setTag(TYPE_LEFT_IMAGE);
				} else if (v.equals(tvLeft)) {
					setTag(TYPE_LEFT_TEXT);
				} else if (v.equals(tvRight)) {
					setTag(TYPE_RIGHT_TEXT);
				} else if (v.equals(ivRight)) {
					setTag(TYPE_RIGHT_IMAGE);
				} else if (v.equals(viewCenter)) {
					setTag(TYPE_TITLE);
				} else if (v.equals(pbRight)) {
					setTag(TYPE_RIGHT_PB);
				}
				if (l != null) {
					l.onClick(TitleBar.this);
				}
			}
		};
		tvLeft.setOnClickListener(ocl);
		ivLeft.setOnClickListener(ocl);
		ivAvatar.setOnClickListener(ocl);
		viewCenter.setOnClickListener(ocl);
		tvRight.setOnClickListener(ocl);
		ivRight.setOnClickListener(ocl);
	}
}
