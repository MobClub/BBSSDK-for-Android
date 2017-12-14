package com.mob.bbssdk.gui.other.ums.pickers;

import android.content.Context;

import com.mob.bbssdk.gui.jimu.gui.Dialog;
import com.mob.bbssdk.gui.jimu.gui.Theme;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleValuePicker extends Dialog<SingleValuePicker> {
	private boolean showTitle;
	private int[] selections;
	private int levelCount;
	private ArrayList<Choice> topLevel;
	private OnSelectedListener listener;

	public SingleValuePicker(Context context, Theme theme) {
		super(context, theme);
	}

	protected void applyParams(HashMap<String, Object> params) {
		showTitle = "true".equals(String.valueOf(params.get("showTitle")));
		selections = (int[]) params.get("selections");

		@SuppressWarnings("unchecked")
		ArrayList<Choice> choices = (ArrayList<Choice>) params.get("choices");
		if (choices == null || choices.isEmpty()) {
			return;
		}

		topLevel = choices;
		Choice tmp = new Choice();
		tmp.children = topLevel;
		levelCount = findLevelCount(tmp, 0);
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	private int findLevelCount(Choice c, int levelCount) {
		if (c.children.isEmpty()) {
			return levelCount;
		} else {
			int maxSubLC = levelCount + 1;
			for (Choice subc : c.children) {
				int subLevelCount = findLevelCount(subc, levelCount + 1);
				if (subLevelCount > maxSubLC) {
					maxSubLC = subLevelCount;
				}
			}
			return maxSubLC;
		}
	}

	public int getLevelCount() {
		return levelCount;
	}

	public ArrayList<Choice> getChoices() {
		return topLevel;
	}

	public void setOnSelectedListener(OnSelectedListener l) {
		listener = l;
	}

	public OnSelectedListener getOnSelectedListener() {
		return listener;
	}

	public int[] getSelections() {
		return selections;
	}

	public static interface OnSelectedListener {
		public void onSelected(int[] selections);
	}

	public static class Builder extends Dialog.Builder<SingleValuePicker> {
		private OnSelectedListener listener;

		public Builder(Context context, Theme theme) {
			super(context, theme);
		}

		public synchronized void setChoices(ArrayList<Choice> choices) {
			set("choices", choices);
		}

		public void setSelections(int[] selections) {
			set("selections", selections);
		}

		public void setOnSelectedListener(OnSelectedListener l) {
			listener = l;
		}

		public void showTitle() {
			set("showTitle", true);
		}

		public SingleValuePicker create() throws Throwable {
			SingleValuePicker dialog = super.create();
			dialog.setOnSelectedListener(listener);
			return dialog;
		}
	}
}
