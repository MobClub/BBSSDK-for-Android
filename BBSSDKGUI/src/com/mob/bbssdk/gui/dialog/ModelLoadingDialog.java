package com.mob.bbssdk.gui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.views.IOSLoadingView;
import com.mob.tools.utils.ResHelper;

public class ModelLoadingDialog extends Dialog {
	private TextView tvWarning;

	public ModelLoadingDialog(Context context) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		init(context);
	}

	private void init(Context context) {
		setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
		setCancelable(false);//按返回键取消窗体
		Window window = getWindow();
		window.setWindowAnimations(ResHelper.getStyleRes(context, "BBS_AnimUpDown"));

		//setup view
		LinearLayout llItem = new LinearLayout(context);
		int padding = ResHelper.dipToPx(context, 30);
		llItem.setPadding(padding, padding, padding, padding);
		llItem.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_warning_dialog"));
		llItem.setOrientation(LinearLayout.VERTICAL);
		IOSLoadingView loadingView = new IOSLoadingView(context);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.gravity = Gravity.CENTER_HORIZONTAL;
		llItem.addView(loadingView, llp);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(ResHelper.dipToPx(getContext(), 400), ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		window.setContentView(llItem, params);
	}

	public void show() {
		super.show();
		//设置宽度全屏，需在show之后
		WindowManager.LayoutParams wlp = getWindow().getAttributes();
		wlp.gravity = Gravity.CENTER;
		wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(wlp);
	}
}
