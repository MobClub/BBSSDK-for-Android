package com.mob.bbssdk.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mob.tools.utils.ResHelper;

public class SplashActivity extends Activity {

	private ImageView ivSplash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(ResHelper.getLayoutRes(this, "activity_splash"));

		ivSplash = (ImageView) findViewById(ResHelper.getIdRes(this, "ivSplash"));

		ivSplash.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, InitActivity.class));
				finish();
			}
		}, 1500);
	}
}
