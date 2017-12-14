package com.mob.bbssdk.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.webview.CleverImageView;
import com.mob.tools.utils.ResHelper;


/**
 *
 */

public class TestActivity extends Activity {
	private static final String IMAGE_URL = "https://lh6.googleusercontent.com/"
			 + "-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/"
			 + "s240-c/A%252520Photographer.jpg";
	CleverImageView cleverImageView;
	Button btnTest;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobSDK.init(this);
		setContentView(ResHelper.getLayoutRes(this, "layout_test"));
		cleverImageView = (CleverImageView) findViewById(ResHelper.getIdRes(this, "cleverImageView"));
		btnTest = (Button) findViewById(ResHelper.getIdRes(this, "btnTest"));
		btnTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//                cleverImageView.setOval(false);
				cleverImageView.setBorderColor(Color.BLUE);
				cleverImageView.setBorderWidthDP(2);
				cleverImageView.setCornerRadiiDP(10, 5, 20, 30);
				cleverImageView.setImageUrl(IMAGE_URL);
			}
		});
	}
}
