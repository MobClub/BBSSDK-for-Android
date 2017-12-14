package com.mob.bbssdk.gui.other;

import android.content.Context;
import android.content.DialogInterface;

import com.mob.bbssdk.gui.jimu.gui.Dialog;
import com.mob.bbssdk.gui.jimu.gui.Theme;

import java.util.HashMap;

public class ErrorDialog extends Dialog<ErrorDialog> {
	private String title;
	private String message;
	private String button;
	private boolean noPadding;

	public ErrorDialog(Context context, Theme theme) {
		super(context, theme);
	}

	protected void applyParams(HashMap<String, Object> params) {
		title = (String) params.get("title");
		message = (String) params.get("message");
		button = (String) params.get("button");
		noPadding = "true".equals(String.valueOf(params.get("noPadding")));
	}

	public String getTitle() {
		return title == null ? "" : title;
	}

	public String getMessage() {
		return message == null ? "" : message;
	}

	public String getButton() {
		return button == null ? "" : button;
	}

	public boolean isNoPadding() {
		return noPadding;
	}

	public static class Builder extends Dialog.Builder<ErrorDialog> {
		private Theme theme;
		private Throwable t;
		private OnDismissListener outListener;

		public Builder(Context context, Theme theme) {
			super(context, theme);
			this.theme = theme;
		}

		public synchronized void setTitle(String tvTitle) {
			set("title", tvTitle);
		}

		public synchronized void setMessage(String message) {
			set("message", message);
		}

		public synchronized void setButton(String text) {
			set("button", text);
		}

		public synchronized void noPadding() {
			set("noPadding", true);
		}

		public void setThrowable(Throwable t) {
			this.t = t;
		}

		public Builder setOnDismissListener(OnDismissListener onDismissListener) {
			outListener = onDismissListener;
			return this;
		}

		public ErrorDialog create() throws Throwable {
			ErrorDialog dialog = super.create();
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (outListener != null) {
						outListener.onDismiss(dialog);
					}
				}
			});
			return dialog;
		}
	}
}
