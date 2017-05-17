package com.mob.bbssdk.gui.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mob.tools.utils.ResHelper;

/**
 * 开关按钮
 */
public class ToggleImageButton extends ImageView {
	private boolean isPressed = false;
	private OnPressListener pressListener;

	public ToggleImageButton(Context context) {
		super(context);
		initView();
	}

	public ToggleImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ToggleImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPressed) {
					setUnpressed();
				} else {
					setPressed();
				}
				if (pressListener != null) {
					pressListener.onPressed(ToggleImageButton.this, isPressed);
				}
			}
		});
	}

	public void setPressed() {
		isPressed = true;
		setBackgroundColor(getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_blue")));
	}

	public void setUnpressed() {
		isPressed = false;
		setBackgroundColor(getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_white")));
	}

	public void setPressListener(OnPressListener listener) {
		this.pressListener = listener;
	}

	public interface OnPressListener {
		void onPressed(View view, boolean pressed);
	}
}
