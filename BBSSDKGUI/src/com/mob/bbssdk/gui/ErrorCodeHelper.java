package com.mob.bbssdk.gui;


import android.content.Context;
import android.text.TextUtils;

import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.tools.utils.ResHelper;

public class ErrorCodeHelper {
	private static final String TAG = "ErrorCodeHelper";

	public static String getErrorCodeStr(Context context, Integer errorcode) {
		if (context == null || errorcode == null) {
			return "";
		}
		String name = "bbs_error_code_" + errorcode;
		int id = ResHelper.getStringRes(context, name);
		if (id == 0) {
			return "";
		}
		return context.getString(id);
	}

	public static boolean toastErrorCode(Context context, int errorcode) {
		if (context == null || errorcode == 0) {
			return false;
		}
		String errormsg = ErrorCodeHelper.getErrorCodeStr(context, errorcode);
		if (!TextUtils.isEmpty(errormsg)) {
			ToastUtils.showToast(context, errormsg);
			return true;
		}
		return false;
	}

	public static void toastError(Context context, int errorcode, Throwable details) {
		if (!ErrorCodeHelper.toastErrorCode(context, errorcode)) {
			if (details != null && !TextUtils.isEmpty(details.getMessage())) {
				ToastUtils.showToast(context, details.getMessage());
			} else {
				ToastUtils.showToast(context, context.getString(
						ResHelper.getStringRes(context, "bbs_error_code_unknown")));
			}
		}
	}
}
