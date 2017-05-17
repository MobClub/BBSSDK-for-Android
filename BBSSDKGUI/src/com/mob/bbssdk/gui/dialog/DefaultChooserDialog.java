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

import java.util.ArrayList;
import java.util.List;

public class DefaultChooserDialog extends Dialog {
	private SparseArray<TextView> tvItems;
	private List<String> listItems = new ArrayList<String>();
	private static final Integer ITEM_DISMISS = -999;

	public DefaultChooserDialog(Context context, int[] strResIds) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		for (int id : strResIds) {
			listItems.add(context.getResources().getString(id));
		}
		init(context);
	}

	public DefaultChooserDialog(Context context, String[] strArray) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		for (String str : strArray) {
			listItems.add(str);
		}
		init(context);
	}

	public DefaultChooserDialog(Context context, List<String> list) {
		super(context, ResHelper.getStyleRes(context, "BBS_Dialog"));
		listItems.addAll(list);
		init(context);
	}

	private void init(Context context) {
		setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
		setCancelable(true);//按返回键取消窗体
		Window window = getWindow();
		window.setWindowAnimations(ResHelper.getStyleRes(context, "BBS_AnimUpDown"));

		//setup view
		LinearLayout containLayout = new LinearLayout(context);
		containLayout.setOrientation(LinearLayout.VERTICAL);
		int leftPadding = ResHelper.dipToPx(context, 16);
		containLayout.setPadding(leftPadding, 0, leftPadding, leftPadding / 2);
		window.setContentView(containLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		if (listItems.size() > 0) {
			LinearLayout itemContainer = new LinearLayout(context);
			itemContainer.setOrientation(LinearLayout.VERTICAL);
			itemContainer.setBackgroundResource(ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg"));
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			containLayout.addView(itemContainer, llp);

			//添加item
			int dividerHeight = context.getResources()
					.getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_divider_height"));
			int itemHeight = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_item_height"));
			int txtSize = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_chooser_dialog_txt_size"));
			int txtColor = context.getResources().getColor(ResHelper.getColorRes(context, "bbs_chooser_dialog_txt_color"));
			int length = listItems.size();
			tvItems = new SparseArray<TextView>(length);
			int topBgRes = ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg_top");
			int middleBgRes = ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg_middle");
			int bottomBgRes = ResHelper.getBitmapRes(context, "bbs_chooser_dialog_item_bg_bottom");
			for (int i = 0; i < length; i++) {
				int tag = i + 1;
				TextView tvItem = new TextView(context);
				tvItem.setTag(tag);
				tvItem.setGravity(Gravity.CENTER);
				tvItem.setTextColor(txtColor);
				tvItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
				tvItem.setText(listItems.get(i));
				if (i == 0 && length > 1) {
					tvItem.setBackgroundResource(topBgRes);
				} else if (i == length - 1 && length > 1) {
					tvItem.setBackgroundResource(bottomBgRes);
				} else {
					tvItem.setBackgroundResource(middleBgRes);
				}
				llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
				itemContainer.addView(tvItem, llp);

				if (i < length - 1) {
					View vLine1 = new View(context);
					vLine1.setBackgroundColor(0xFFB7B7B7);
					llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight);
					itemContainer.addView(vLine1, llp);
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
			llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
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
					int position = ResHelper.forceCast(v.getTag(), ITEM_DISMISS);
					if(position == ITEM_DISMISS) {
						itemClickListener.onDismiss();
					} else {
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

	public static abstract class OnItemClickListener {
		/**
		 * dialog 的item click回调方法
		 *
		 * @param v
		 * @param position 0是cancel 有效item从1开始
		 */
		public abstract void onItemClick(View v, int position);

		public void onDismiss() {

		}
	}

}
