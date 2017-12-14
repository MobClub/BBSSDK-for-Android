package com.mob.bbssdk.gui.other.ums;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mob.bbssdk.gui.jimu.gui.Dialog;
import com.mob.bbssdk.gui.jimu.gui.DialogAdapter;
import com.mob.tools.utils.ResHelper;

public class ScrollUpAndDownDialogAdapter<D extends Dialog<D>> extends DialogAdapter<D> {
	private LinearLayout llBody;
	private TranslateAnimation scrollUpAnim;
	private TranslateAnimation scrollDownAnim;

	public void init(D dialog) {
		llBody = new LinearLayout(dialog.getContext());
		llBody.setBackgroundColor(0xffffffff);
		llBody.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.setContentView(llBody, lp);
		initBodyView(llBody);
		initAnimations();
	}

	protected void initBodyView(LinearLayout llBody) {

	}

	private void initAnimations() {
		scrollUpAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		scrollUpAnim.setDuration(500);

		scrollDownAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1);
		scrollDownAnim.setDuration(300);
	}

	public void onCreate(D dialog, Bundle savedInstanceState) {
		Window window = dialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.BOTTOM;
		wlp.width = ResHelper.getScreenWidth(dialog.getContext());

		llBody.startAnimation(scrollUpAnim);
	}

	public void onDismiss(D dialog, final Runnable afterDismiss) {
		scrollDownAnim.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				afterDismiss.run();
			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
		llBody.startAnimation(scrollDownAnim);
	}

}
