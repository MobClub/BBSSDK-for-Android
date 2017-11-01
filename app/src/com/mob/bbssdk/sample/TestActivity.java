package com.mob.bbssdk.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.EmojiManager;
import com.mob.tools.utils.ResHelper;

import pl.droidsonroids.gif.GifImageView;

/**
 *
 */

public class TestActivity extends Activity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobSDK.init(this);
		setContentView(ResHelper.getLayoutRes(this, "layout_test"));
		GifImageView gifImageView = (GifImageView) findViewById(ResHelper.getIdRes(this, "gifImageView"));

		EmojiManager.getInstance().setGifImageView(gifImageView, ":)");
	}
}
