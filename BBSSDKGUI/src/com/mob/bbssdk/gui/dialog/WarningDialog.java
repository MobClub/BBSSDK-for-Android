package com.mob.bbssdk.gui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

public class WarningDialog extends Dialog {
	private TextView tvWarning;

	public WarningDialog(Context context) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		init(context);
	}

	private void init(Context context) {
		setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
		setCancelable(true);//按返回键取消窗体
		Window window = getWindow();
		window.setWindowAnimations(ResHelper.getStyleRes(context, "BBS_AnimUpDown"));

		//setup view
		LinearLayout llItem = new LinearLayout(context);
		int padding = ResHelper.dipToPx(context, 30);
		llItem.setPadding(padding, padding, padding, padding);
		llItem.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_warning_dialog"));
		llItem.setOrientation(LinearLayout.VERTICAL);
		ImageView ivClose = new ImageView(context);
		ivClose.setImageResource(ResHelper.getBitmapRes(context, "bbs_ic_warning_error"));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.gravity = Gravity.CENTER_HORIZONTAL;
		llItem.addView(ivClose, llp);

		tvWarning = new TextView(context);
		tvWarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 12));
		tvWarning.setTextColor(0xff3a4045);
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.gravity = Gravity.CENTER_HORIZONTAL;
		llp.topMargin = ResHelper.dipToPx(context, 30);
		llItem.addView(tvWarning, llp);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(ResHelper.dipToPx(getContext(), 400), ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		window.setContentView(llItem, params);

		ivClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setWarningText(String warningStr) {
		tvWarning.setText(warningStr);
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
