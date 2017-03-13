package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

/** 请求列表为空的View */
public class EmptyView extends RelativeLayout {
	private TextView tvEmpty;
	private ImageView ivEmpty;
	private OnClickListener onRefreshClickListener;

	private int emptyDrawableId;
	private int errorDrawableId;
	private int errorStrId;
	private int emptyStrId;

	public EmptyView(Context context) {
		super(context);
		init(context);
	}

	public EmptyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		tvEmpty = new TextView(context);
		int tvEmptySize = getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_empty_view_txt_size"));
		tvEmpty.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvEmptySize);
		int padding = ResHelper.dipToPx(context, 10);
		tvEmpty.setPadding(padding, padding, padding, padding);
		int tvEmptyColor = getResources().getColor(ResHelper.getColorRes(context, "bbs_empty_view_txt_color"));
		tvEmpty.setTextColor(tvEmptyColor);
		tvEmpty.setId(ResHelper.getIdRes(context, "tvEmpty"));

		ivEmpty = new ImageView(context);
		ivEmpty.setScaleType(ImageView.ScaleType.CENTER);

		LayoutParams rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.addRule(CENTER_IN_PARENT, TRUE);
		addView(tvEmpty, rlp);

		rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.addRule(CENTER_HORIZONTAL, TRUE);
		rlp.addRule(ABOVE, tvEmpty.getId());
		addView(ivEmpty, rlp);

		OnClickListener ocl = new OnClickListener() {
			public void onClick(View view) {
				tvEmpty.setClickable(false);
				ivEmpty.setClickable(false);
				if (onRefreshClickListener != null) {
					onRefreshClickListener.onClick(view);
				}
			}
		};
		tvEmpty.setClickable(false);
		ivEmpty.setClickable(false);
		tvEmpty.setOnClickListener(ocl);
		ivEmpty.setOnClickListener(ocl);
	}

	public void setEmpty(boolean isError) {
		try {
			if (isError) {
				// 设置加载数据出错了
				tvEmpty.setText(errorStrId);
				if (errorDrawableId > 0) {
					ivEmpty.setImageResource(errorDrawableId);
				}
				tvEmpty.setClickable(true);
				ivEmpty.setClickable(true);
			} else {
				// 设置加载数据成功,但是服务器返回数据为空
				tvEmpty.setText(emptyStrId);
				if (emptyDrawableId > 0) {
					ivEmpty.setImageResource(emptyDrawableId);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void setOnRetryClickListener(OnClickListener ocl) {
		onRefreshClickListener = ocl;
	}

	public void setEmptyImage(int emptyDrawableId, int errorDrawableId) {
		this.emptyDrawableId = emptyDrawableId;
		this.errorDrawableId = errorDrawableId;
	}

	public void setEmptyText(int emptyStrId, int errorStrId) {
		this.emptyStrId = emptyStrId;
		this.errorStrId = errorStrId;
	}
}
