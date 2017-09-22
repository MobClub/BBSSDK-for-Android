package com.mob.bbssdk.gui.other.ums;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mob.bbssdk.gui.other.ums.pickers.Choice;
import com.mob.bbssdk.gui.other.ums.pickers.SingleValuePicker;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

public class SingleValuePickerAdapter extends ScrollUpAndDownDialogAdapter<SingleValuePicker>
		implements FlywheelView.OnSelectedListener, OnClickListener {
	private SingleValuePicker dialog;
	private FlywheelView[] wheels;
	private int[] selections;
	private TextView tvCancel;
	private TextView tvTitle;
	private TextView tvConfirm;

	public void init(SingleValuePicker dialog) {
		this.dialog = dialog;
		super.init(dialog);
	}

	protected void initBodyView(LinearLayout llBody) {
		Context context = llBody.getContext();
		LinearLayout llTitle = new LinearLayout(context);
		int dp45 = ResHelper.dipToPx(context, 45);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, dp45);
		llBody.addView(llTitle, lp);

		tvCancel = new TextView(context);
		tvCancel.setText(ResHelper.getStringRes(context, "bbs_umssdk_default_cancel"));
		tvCancel.setTextColor(0xff494949);
		tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		tvCancel.setOnClickListener(this);
		tvCancel.setGravity(Gravity.CENTER);
		int dp15 = ResHelper.dipToPx(context, 15);
		tvCancel.setPadding(dp15, 0, dp15, 0);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		llTitle.addView(tvCancel, lp);

		tvTitle = new TextView(context);
		tvTitle.setTextColor(0xff494949);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		tvTitle.setGravity(Gravity.CENTER);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.weight = 1;
		llTitle.addView(tvTitle, lp);
		if (!dialog.isShowTitle()) {
			tvTitle.setVisibility(View.INVISIBLE);
		}

		tvConfirm = new TextView(context);
		tvConfirm.setText(ResHelper.getStringRes(context, "bbs_umssdk_default_confirm"));
		tvConfirm.setTextColor(0xff494949);
		tvConfirm.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		tvConfirm.setOnClickListener(this);
		tvConfirm.setGravity(Gravity.CENTER);
		tvConfirm.setPadding(dp15, 0, dp15, 0);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		llTitle.addView(tvConfirm, lp);

		View vSep = new View(context);
		vSep.setBackgroundColor(0xfff7f7f7);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 1));
		llBody.addView(vSep, lp);

		LinearLayout llWheels = new LinearLayout(context);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.bottomMargin = ResHelper.dipToPx(context, 14);
		llBody.addView(llWheels, lp);

		wheels = new FlywheelView[dialog.getLevelCount()];
		selections = dialog.getSelections();
		if (selections == null) {
			selections = new int[wheels.length];
		}
		if (selections.length < wheels.length) {
			int[] tmp = new int[wheels.length];
			System.arraycopy(selections, 0, tmp, 0, selections.length);
			selections = tmp;
		}

		int dp37 = ResHelper.dipToPx(context, 37);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, dp37 * 5);
		lp.weight = 1;
		ArrayList<Choice> choices = dialog.getChoices();
		for (int i = 0; i < wheels.length; i++) {
			wheels[i] = new FlywheelView(context);
			wheels[i].setItemHeight(dp37);
			wheels[i].setOnSelectedListener(this);
			llWheels.addView(wheels[i], lp);
			if (choices == null || choices.isEmpty()) {
				wheels[i].setData(new ArrayList<String>(), 0);
				wheels[i].setTag(new ArrayList<String>());
			} else {
				wheels[i].setData(toTexts(choices), selections[i]);
				wheels[i].setTag(choices);
				choices = choices.get(selections[i]).children;
			}
		}
	}

	private ArrayList<String> toTexts(ArrayList<Choice> choices) {
		ArrayList<String> ret = new ArrayList<String>();
		for (Choice c : choices) {
			ret.add(c.text);
		}
		return ret;
	}

	public void onSelected(FlywheelView view, int selection) {
		for (int i = 0; i < wheels.length; i++) {
			if (view.equals(wheels[i])) {
				if (selections[i] != selection) {
					if(selection == -1) {
						selection = 0;
					}
					selections[i] = selection;
					if (i < wheels.length - 1) {
						@SuppressWarnings("unchecked")
						ArrayList<Choice> choices = (ArrayList<Choice>) wheels[i].getTag();
						FlywheelView nextWheel = wheels[i + 1];
						if (choices.isEmpty()) {
							nextWheel.setData(new ArrayList<String>(), 0);
							nextWheel.setTag(new ArrayList<Choice>());
						} else {
							Choice choice = choices.get(selection);
							nextWheel.setData(toTexts(choice.children), 0);
							nextWheel.setTag(choice.children);
						}
						onSelected(nextWheel, -1);
					}
				}
				break;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (FlywheelView wheel : wheels) {
			sb.append(wheel.getSelection());
		}
		tvTitle.setText(sb.toString());
	}

	public void onClick(View v) {
		if (v.equals(tvConfirm)) {
			if (dialog.getOnSelectedListener() != null) {
				dialog.getOnSelectedListener().onSelected(selections);
			}
		}
		dialog.dismiss();
	}

}
