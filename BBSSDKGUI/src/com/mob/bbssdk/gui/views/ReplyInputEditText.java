package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 *
 */

public class ReplyInputEditText extends EditText {
	KeyPreImeListener keyPreImeListener;

	public ReplyInputEditText(Context context) {
		super(context);
	}

	public ReplyInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReplyInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (keyPreImeListener != null) {
				keyPreImeListener.OnKeyPreImeBack();
			}
			// do your stuff
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

	public void setOnKeyPreImeListener(KeyPreImeListener listener) {
		this.keyPreImeListener = listener;
	}

	public interface KeyPreImeListener {
		void OnKeyPreImeBack();
	}
}
