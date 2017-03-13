package com.mob.bbssdk.gui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

public class DefaultChooserDialog extends Dialog {
	private SparseArray<TextView> tvItems;

	public DefaultChooserDialog(Context context, int[] strResIds) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		init(context, strResIds);
	}

	private void init(Context context, int[] strResIds) {
		setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
		setCancelable(true);//按返回键取消窗体
		Window window = getWindow();
		window.setWindowAnimations(ResHelper.getStyleRes(context, "BBS_AnimUpDown"));

		//setup view
		LinearLayout containLayout = new LinearLayout(context);
		containLayout.setOrientation(LinearLayout.VERTICAL);
		int leftPadding = ResHelper.dipToPx(context, 16);
		containLayout.setPadding(leftPadding, 0, leftPadding, leftPadding);
		window.setContentView(containLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		if (strResIds != null && strResIds.length > 0) {
			int dividerHeight = context.getResources()
					.getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_divider_height"));
			int itemHeight = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_item_height"));
			int txtSize = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_txt_size"));
			int txtColor = context.getResources().getColor(ResHelper.getColorRes(context, "bbs_chooser_dialog_txt_color"));
			int length = strResIds.length;
			tvItems = new SparseArray<TextView>(length);
			for (int i = 0; i < length; i++) {
				int tag = i + 1;
				TextView tvItem = new TextView(context);
				tvItem.setTag(tag);
				tvItem.setGravity(Gravity.CENTER);
				tvItem.setTextColor(txtColor);
				tvItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
				tvItem.setText(strResIds[i]);
				tvItem.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg"));
				LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
				containLayout.addView(tvItem, llp);

				if (i < length - 1) {
					View vLine1 = new View(context);
					vLine1.setBackgroundResource(0x00000000);
					llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight);
					containLayout.addView(vLine1, llp);
				}
				tvItems.put(tag, tvItem);
			}

			TextView tvCancel = new TextView(context);
			tvCancel.setTag(0);
			tvCancel.setGravity(Gravity.CENTER);
			tvCancel.setTextColor(context.getResources().getColor(ResHelper.getColorRes(context, "bbs_chooser_dialog_cancel_txt_color")));
			tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
			tvCancel.setText(ResHelper.getStringRes(context, "bbs_cancel"));
			tvCancel.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg"));
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
			llp.topMargin = ResHelper.dipToPx(context, 10);
			containLayout.addView(tvCancel, llp);

			tvItems.put(0, tvCancel);
		}
	}

	public void show() {
		super.show();
		//设置宽度全屏，需在show之后
		WindowManager.LayoutParams wlp = getWindow().getAttributes();
		wlp.gravity = Gravity.BOTTOM;
		wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
		wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(wlp);
	}

	public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
		if (tvItems == null || tvItems.size() < 1) {
			return;
		}
		View.OnClickListener ocl = new View.OnClickListener() {
			public void onClick(View v) {
				if (itemClickListener != null) {
					int position = ResHelper.forceCast(v.getTag(), -1);
					if (position != 0) {
						itemClickListener.onItemClick(v, position);
					}
				}
				dismiss();
			}
		};
		int size = tvItems.size();
		for (int i = 0; i < size; i++) {
			tvItems.get(i).setOnClickListener(ocl);
		}
	}

	public interface OnItemClickListener {
		void onItemClick(View v, int position);
	}

}
