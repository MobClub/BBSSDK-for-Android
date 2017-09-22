package com.mob.bbssdk.gui.other.ums;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.mob.jimu.gui.Page;
import com.mob.jimu.gui.PageAdapter;

public class DefaultThemePageAdapter<P extends Page<P>> extends PageAdapter<P> {

	public void onCreate(P page, Activity activity) {
		Window window = activity.getWindow();
		window.setBackgroundDrawable(new ColorDrawable(0xffffffff));
	}

}
