package com.mob.bbssdk.gui.jimu.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.mob.tools.MobLog;
import com.mob.tools.utils.ReflectHelper;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

public abstract class Dialog<D extends Dialog<D>> extends android.app.Dialog {
	private final Theme theme;
	private final D self;
	private final DialogAdapter<D> adapter;

	@SuppressWarnings("unchecked")
	public Dialog(Context context, Theme theme) {
		super(context, theme.getDialogStyle(context));
		this.theme = theme;
		self = (D) this;
		adapter = theme.getDialogAdapter(self);
	}

	protected Theme getTheme() {
		return theme;
	}

	protected void applyParams(HashMap<String, Object> params) {

	}

	final void init() {
		adapter.init(self);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter.onCreate(self, savedInstanceState);
	}

	public void dismiss() {
		adapter.onDismiss(self, new Runnable() {
			public void run() {
				dismissOfSuper();
			}
		});
	}

	private void dismissOfSuper() {
		try {
			super.dismiss();
		} catch (Throwable t) {
			MobLog.getInstance().w(t);
		}
	}

	public static abstract class Builder<D extends Dialog<D>> {
		private Context context;
		private Theme theme;
		private HashMap<String, Object> params;
		private boolean cancelable;
		private boolean touchOutsideCancelable;
		private OnCancelListener onCancelListener;
		private OnDismissListener onDismissListener;
		private OnKeyListener onKeyListener;

		public Builder(Context context, Theme theme) {
			this.context = context;
			this.theme = theme;
			params = new HashMap<String, Object>();
		}

		protected void set(String key, Object value) {
			params.put(key, value);
		}

		protected Object get(String key) {
			return params.get(key);
		}

		public Builder<D> setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder<D> setCanceledOnTouchOutside(boolean touchCancelable) {
			this.touchOutsideCancelable = touchCancelable;
			return this;
		}

		public Builder<D> setOnCancelListener(OnCancelListener onCancelListener) {
			this.onCancelListener = onCancelListener;
			return this;
		}

		public Builder<D> setOnDismissListener(OnDismissListener onDismissListener) {
			this.onDismissListener = onDismissListener;
			return this;
		}

		public Builder<D> setOnKeyListener(OnKeyListener onKeyListener) {
			this.onKeyListener = onKeyListener;
			return this;
		}

		public D create() throws Throwable {
			D dialog = createDialog(context, theme);
			dialog.applyParams(params);
			dialog.init();
			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(cancelable && touchOutsideCancelable);
			dialog.setOnCancelListener(onCancelListener);
			dialog.setOnDismissListener(onDismissListener);
			dialog.setOnKeyListener(onKeyListener);
			return dialog;
		}

		public D show() {
			try {
				if (context instanceof Activity) {
					Activity act = (Activity) context;
					if (!act.isFinishing()) {
						Boolean isDestroyed = false;
						if (Build.VERSION.SDK_INT > 16) {
							isDestroyed = ReflectHelper.invokeInstanceMethod(act, "isDestroyed");
						}

						if (!isDestroyed) {
							D dialog = create();
							dialog.show();
							return dialog;
						}
					}
				}
			} catch (Throwable t) {
				MobLog.getInstance().w(t);
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		private D createDialog(Context context, Theme theme) throws Throwable {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			Class<D> dialogClass = (Class<D>) type.getActualTypeArguments()[0];
			String clzName = ReflectHelper.importClass(dialogClass.getName());
			return (D) ReflectHelper.newInstance(clzName, context, theme);
		}
	}

}
