package com.mob.bbssdk.gui.utils;

public class OperationCallback<T> {

	/** 成功时会被调用 */
	public void onSuccess(T data) {

	}

	/** 失败时会被调用 */
	public void onFailed(int errorCode, Throwable details) {

	}

	/** 取消时会被调用 */
	public void onCancel() {

	}

}
