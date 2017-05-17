package com.mob.bbssdk.gui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

import java.util.HashMap;
import java.util.Map;

public class YesNoDialog extends Dialog implements DialogInterface, OnClickListener {
	private TextView tvCancel;
	private TextView tvOK;
	private TextView tvTitle;
	private TextView tvMessage;
	private OnClickListener ocl;

	public YesNoDialog(Context context) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		init(context);
	}

	public YesNoDialog(Context context, int themeResId) {
		super(context, themeResId);
		init(context);
	}

	protected YesNoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	private void init(Context context) {
		Window window = getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.CENTER;
		wlp.width = context.getResources().getDisplayMetrics().widthPixels - ResHelper.dipToPx(context, 50) * 2;

		//setup view
		LinearLayout containLayout = new LinearLayout(context);
		containLayout.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_dialog_prompt"));
		containLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setContentView(containLayout, lp);

		//title
		tvTitle = new TextView(context);
		tvTitle.setGravity(Gravity.CENTER);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 18));
		tvTitle.setTextColor(0xFF111111);
		tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		tvTitle.setText(ResHelper.getStringRes(context, "bbs_common_prompt"));
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 25));
		lp.topMargin = ResHelper.dipToPx(context, 13);
		lp.gravity = Gravity.CENTER_HORIZONTAL;
		containLayout.addView(tvTitle, lp);

		//content msg
		tvMessage = new TextView(context);
		int paddingMsg = ResHelper.dipToPx(context, 50);
		tvMessage.setPadding(paddingMsg, 0, paddingMsg, 0);
		tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 13));
		tvMessage.setTextColor(0xff000000);
		tvMessage.setMaxLines(2);
		tvMessage.setGravity(Gravity.CENTER);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 40));
		lp.bottomMargin = ResHelper.dipToPx(context, 13);
		lp.gravity = Gravity.CENTER_HORIZONTAL;
		containLayout.addView(tvMessage, lp);

		//line
		View vLine = new View(context);
		vLine.setBackgroundColor(0x7f979797);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 1));
		containLayout.addView(vLine, lp);

		LinearLayout llBottom = new LinearLayout(context);
		llBottom.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_dialog_tv_ok"));
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 43));
		containLayout.addView(llBottom, lp);

		//btnCancel
		tvCancel = new TextView(context);
		tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 18));
		tvCancel.setTextColor(0xff239cd5);
		tvCancel.setGravity(Gravity.CENTER);
		tvCancel.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_dialog_btn_left"));
		tvCancel.setText(ResHelper.getStringRes(context, "bbs_common_cancel"));
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.weight = 1;
		llBottom.addView(tvCancel, lp);
		tvCancel.setOnClickListener(this);

		vLine = new View(context);
		vLine.setBackgroundColor(0x7f979797);
		lp = new LayoutParams(ResHelper.dipToPx(context, 1), LayoutParams.MATCH_PARENT);
		llBottom.addView(vLine, lp);

		//btnOK
		tvOK = new TextView(context);
		tvOK.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.dipToPx(context, 18));
		tvOK.setTextColor(0xff239cd5);
		tvOK.setGravity(Gravity.CENTER);
		tvOK.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_bg_dialog_btn_right"));
		tvOK.setText(ResHelper.getStringRes(context, "bbs_common_ok"));
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.weight = 1;
		llBottom.addView(tvOK, lp);
		tvOK.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (ocl != null) {
			if (v.equals(tvOK)) {
				ocl.onClick(this, BUTTON_POSITIVE);
			} else if (v.equals(tvCancel)) {
				ocl.onClick(this, BUTTON_NEGATIVE);
			}
		}
		dismiss();
	}

	public void applyParams(Map<String, Object> paramsMap) {
		if (paramsMap.containsKey("title")) {
			Object title = paramsMap.get("title");
			if (title instanceof Integer) {
				tvTitle.setText((Integer) title);
			} else {
				tvTitle.setText(title.toString());
			}
		}
		if (paramsMap.containsKey("msg")) {
			Object msg = paramsMap.get("msg");
			if (msg instanceof Integer) {
				tvMessage.setText((Integer) msg);
			} else {
				tvMessage.setText(msg.toString());
			}
		}
		if (paramsMap.containsKey("yes")) {
			Object msg = paramsMap.get("yes");
			if (msg instanceof Integer) {
				tvOK.setText((Integer) msg);
			} else {
				tvOK.setText(msg.toString());
			}
		}
		if (paramsMap.containsKey("no")) {
			Object msg = paramsMap.get("no");
			if (msg instanceof Integer) {
				tvCancel.setText((Integer) msg);
			} else {
				tvCancel.setText(msg.toString());
			}
		}
	}

	public static class Builder {
		private Context context;
		private Map<String, Object> paramsMap = null;
		private boolean cancelable = true;
		private boolean touchOutsideCancelable = false;
		private OnCancelListener onCancelListener;
		private OnDismissListener onDismissListener;
		private OnKeyListener onKeyListener;
		private OnClickListener onClickListener;

		public Builder(Context context) {
			this.context = context;
			paramsMap = new HashMap<String, Object>();
		}

		public Builder setTitle(int titleId) {
			paramsMap.put("title", titleId);
			return this;
		}

		public Builder setTitle(CharSequence title) {
			if (title != null) {
				paramsMap.put("title", title);
			}
			return this;
		}

		public Builder setYes(int strId) {
			paramsMap.put("yes", strId);
			return this;
		}

		public Builder setYes(CharSequence str) {
			if (str != null) {
				paramsMap.put("yes", str);
			}
			return this;
		}

		public Builder setNo(int strId) {
			paramsMap.put("no", strId);
			return this;
		}

		public Builder setNo(CharSequence str) {
			if (str != null) {
				paramsMap.put("no", str);
			}
			return this;
		}

		public Builder setMessage(int messageId) {
			paramsMap.put("msg", messageId);
			return this;
		}

		public Builder setMessage(CharSequence message) {
			if (message != null) {
				paramsMap.put("msg", message);
			}
			return this;
		}

		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder setCanceledOnTouchOutside(boolean touchCancelable) {
			this.touchOutsideCancelable = touchCancelable;
			return this;
		}

		public Builder setOnCancelListener(OnCancelListener onCancelListener) {
			this.onCancelListener = onCancelListener;
			return this;
		}

		public Builder setOnDismissListener(OnDismissListener onDismissListener) {
			this.onDismissListener = onDismissListener;
			return this;
		}

		public Builder setOnKeyListener(OnKeyListener onKeyListener) {
			this.onKeyListener = onKeyListener;
			return this;
		}

		public Builder setOnClickListener(OnClickListener listener) {
			onClickListener = listener;
			return this;
		}

		public YesNoDialog create() {
			final YesNoDialog dialog = new YesNoDialog(context);
			dialog.applyParams(paramsMap);
			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(cancelable && touchOutsideCancelable);

			dialog.ocl = onClickListener;
			dialog.setOnCancelListener(onCancelListener);
			dialog.setOnDismissListener(onDismissListener);
			dialog.setOnKeyListener(onKeyListener);
			return dialog;
		}

		public YesNoDialog show() {
			final YesNoDialog dialog = create();
			dialog.show();
			return dialog;
		}
	}
}
