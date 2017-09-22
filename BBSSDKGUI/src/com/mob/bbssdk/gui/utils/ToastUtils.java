package com.mob.bbssdk.gui.utils;


import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

	public static void showToast(Context context, String str) {
		if (TextUtils.isEmpty(str)) {
			return;
		}
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int resid) {
		String str = context.getString(resid);
		showToast(context, str);
	}
}
