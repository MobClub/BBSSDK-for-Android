package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mob.bbssdk.gui.utils.ScreenUtils;

/**
 *
 */

public class EmojiInputView extends LinearLayout {
	public EmojiInputView(Context context) {
		super(context);
	}

	public EmojiInputView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public EmojiInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected void init() {
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dpToPx(300)));
		setBackgroundColor(Color.WHITE);
	}
}
