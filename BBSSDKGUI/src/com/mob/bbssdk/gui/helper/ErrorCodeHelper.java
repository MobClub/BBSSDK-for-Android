package com.mob.bbssdk.gui.helper;


import android.content.Context;
import android.text.TextUtils;

import com.mob.bbssdk.ErrorCode;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
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

	public static boolean toastErrorCodeByRes(Context context, int errorcode) {
		if (context == null || errorcode == 0) {
			return false;
		}
		String errormsg = ErrorCodeHelper.getErrorCodeStr(context, errorcode);
		if (!TextUtils.isEmpty(errormsg)) {
			ToastUtils.showToast(context, "" + errorcode + " " + errormsg);
			return true;
		}
		return false;
	}

	public static void toastError(Context context, int errorcode, Throwable details) {
		if (errorcode == ErrorCode.SDK_API_USER_EXPIRED) {
			//token invalid.
			GUIManager.sendLogoutBroadcast();
			if (!GUIManager.isLoginShowing()) {
				ToastUtils.showToast(context, ResHelper.getStringRes(context, "bbs_tokeninvalid_relogin"));
				BBSViewBuilder.getInstance().buildPageLogin().show(context);
			}
		} else {
			if (!ErrorCodeHelper.toastErrorCodeByRes(context, errorcode)) {
				ToastUtils.showToast(context, "" + errorcode + " " + details.getMessage());
			}
		}
	}
}
