package com.mob.bbssdk.gui.jimu.gui;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

public abstract class PageAdapter<P extends Page<P>> {
	private P page;

	final void setPage(P page) {
		this.page = page;
	}

	public void onCreate(P page, Activity activity) {

	}

	public void onStart(P page, Activity activity) {

	}

	public void onPause(P page, Activity activity) {

	}

	public void onResume(P page, Activity activity) {

	}

	public void onStop(P page, Activity activity) {

	}

	public void onRestart(P page, Activity activity) {

	}

	public void onDestroy(P page, Activity activity) {

	}

	public void onSetContentView(View view, P page, Activity activity) {

	}

	public P getPage() {
		return page;
	}

	public final void finish() {
		if (page != null) {
			page.finish();
		}
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		return false;
	}

}
