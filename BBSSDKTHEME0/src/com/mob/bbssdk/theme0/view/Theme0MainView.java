package com.mob.bbssdk.theme0.view;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.views.MainView;
import com.mob.tools.utils.ResHelper;

public class Theme0MainView extends MainView {

	public Theme0MainView(Context context) {
		super(context);
	}

	public Theme0MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme0MainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void updateTitleUserAvatar() {
		if (titleBar != null) {
			titleBar.setLeftUserAvatar(ResHelper.getBitmapRes(getContext(), "bbs_theme0_title_header"),
					ResHelper.getBitmapRes(getContext(), "bbs_login_account"));
		}
	}

	@Override
	protected void updateTitleRightImg(WritePostStatus status) {
		if(status == null) {
			titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme0_title_writepost"));
			return;
		}
		switch (status) {
			case Failed: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_failed"));
			} break;
			case Success: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_ic_writethread_success"));
			} break;
			case Normal: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme0_title_writepost"));
			} break;
			default: {
				titleBar.setRightImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme0_title_writepost"));
			} break;
		}
	}

	@Override
	protected View getMainView() {
		return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_main"), null);
	}

	@Override
	protected void onViewCreated() {
		//蓝色线性渐变色背景
		GradientDrawable drawable = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xFF5b7ef0, 0xFF6687f2});
		drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		titleBar.setBackgroundDrawable(drawable);
		//蓝色纯色背景
		// titleBar.setBackgroundColor(getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_theme0_blue")));
	}

	@Override
	protected View getTitleBarView() {
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_maintitlecenterview"), null);
		return view;
	}

	@Override
	protected void OnClickTabBtn0() {
		super.OnClickTabBtn0();
		textViewTab0.setAlpha(1f);
		textViewTab1.setAlpha(0.8f);
	}

	@Override
	protected void OnClickTabBtn1() {
		super.OnClickTabBtn1();
		textViewTab0.setAlpha(0.8f);
		textViewTab1.setAlpha(1f);
	}
}
