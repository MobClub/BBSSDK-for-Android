package com.mob.bbssdk.theme1.page;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mob.bbssdk.theme1.utils.AnimUtils;
import com.mob.tools.utils.ResHelper;

public class AnotherActivity extends Activity {
	private LinearLayout layoutContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(ResHelper.getLayoutRes(this, "bbs_theme1_activity_another"));
		layoutContainer = (LinearLayout) findViewById(ResHelper.getIdRes(this, "layoutContainer"));
		Drawable drawable = new BitmapDrawable(getResources(), BlurEffectManager.getInstance().getBlurFrontPage());
		layoutContainer.setBackgroundDrawable(drawable);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AnimUtils.playFadeOutAnim(AnotherActivity.this);
	}
}
