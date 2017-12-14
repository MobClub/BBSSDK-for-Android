package com.mob.bbssdk.gui.jimu.biz;

public class BizCallback {

	public void deliverOK(Object data) {
		onResultOk(data);
	}

	protected void onResultOk(Object data) {

	}

	public void deliverError(Throwable t) {
		onResultError(t);
	}

	protected void onResultError(Throwable t) {

	}

	public void deliverProgress(int percents) {
		onResultProgress(percents);
	}

	protected void onResultProgress(int percents) {

	}

}
