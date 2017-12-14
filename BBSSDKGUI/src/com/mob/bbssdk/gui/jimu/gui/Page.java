package com.mob.bbssdk.gui.jimu.gui;

import android.view.KeyEvent;
import android.view.View;

import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;

public abstract class Page<P extends Page<P>> extends FakeActivity {
	private final Theme theme;
	private final P self;
	private final PageAdapter<P> adapter;

	@SuppressWarnings("unchecked")
	public Page(Theme theme) {
		this.theme = theme;
		self = (P) this;
		adapter = theme.getPageAdapter(self);
		adapter.setPage(self);
	}

	protected PageAdapter<P> myAdapter() {
		return adapter;
	}

	public Theme getTheme() {
		return theme;
	}

	public void onCreate() {
		adapter.onCreate(self, activity);
	}

	public void onStart() {
		adapter.onStart(self, activity);
	}

	public void onPause() {
		adapter.onPause(self, activity);
		DeviceHelper.getInstance(activity).hideSoftInput(activity.getWindow().getDecorView());
	}

	public void onResume() {
		adapter.onResume(self, activity);
	}

	public void onStop() {
		adapter.onStop(self, activity);
	}

	public void onRestart() {
		adapter.onRestart(self, activity);
	}

	public void onDestroy() {
		adapter.onDestroy(self, activity);
	}

	public void setContentView(View view) {
		adapter.onSetContentView(view, self, activity);
		super.setContentView(view);
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		return adapter.onKeyEvent(keyCode, event);
	}

}
