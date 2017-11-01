package com.mob.bbssdk.theme1.page;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

public class Theme1StyleModifier {
//	//修改User相关的界面样式，白色title，蓝色渐变背景
//	public static void modifyUniformBlueStyle(BasePageWithTitle basepage) {
//		if (basepage == null) {
//			return;
//		}
//		Context context = basepage.getContext();
//		TitleBar titlebar = basepage.getTitleBar();
//		LinearLayout mainlayout = basepage.getMainActivityLayout();
//		if (titlebar == null) {
//			throw new IllegalArgumentException("titleBar is not initialized!");
//		} else {
//			titlebar.setBackgroundColor(Color.TRANSPARENT);
//			titlebar.getTitleTextView().setTextColor(context.getResources().getColor(basepage.getColorId("bbs_white")));
//			setTextSize(titlebar.getTitleTextView(), "bbs_theme0_title_txt_size");
//		}
//
//		if (mainlayout == null) {
//			throw new IllegalArgumentException("mainLayout is not initialized!");
//		} else {
//			Drawable drawable = context.getResources().getDrawable(basepage.getDrawableId("bbs_theme0_bg_userpage"));
//			int sdk = android.os.Build.VERSION.SDK_INT;
//			if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//				mainlayout.setBackgroundDrawable(drawable);
//			} else {
//				mainlayout.setBackground(drawable);
//			}
//		}
//		basepage.setStatusBarColor(context.getResources().getColor(basepage.getColorId("bbs_theme0_statusbar_blue")));
//	}

	//修改User Confirm相关的界面样式，灰色title，白色背景
	public static void modifyUniformWhiteStyle(BasePageWithTitle basepage) {
		if (basepage == null) {
			return;
		}
		Context context = basepage.getContext();
		TitleBar titlebar = basepage.getTitleBar();
		LinearLayout mainlayout = basepage.getMainLayout();
		if (titlebar == null) {
			throw new IllegalArgumentException("titleBar is not initialized!");
		} else {
			titlebar.setBackgroundColor(Color.TRANSPARENT);
			if(titlebar.getTitleTextView() != null) {
				titlebar.getTitleTextView().setTextColor(context.getResources().getColor(basepage.getColorId("bbs_theme1_pagetext")));
				setTextSize(titlebar.getTitleTextView(), "bbs_theme1_title_txt_size");
			}
		}

		if (mainlayout == null) {
			throw new IllegalArgumentException("mainLayout is not initialized!");
		} else {
			ColorDrawable drawable = new ColorDrawable(context.getResources().getColor(basepage.getColorId("bbs_white")));
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				mainlayout.setBackgroundDrawable(drawable);
			} else {
				mainlayout.setBackground(drawable);
			}
		}
		basepage.setStatusBarColor(context.getResources().getColor(basepage.getColorId("bbs_theme1_statusbar_grey")));
	}

	public static void setTextSize(TextView textview, String strdimen) {
		if (textview == null || StringUtils.isEmpty(strdimen)) {
			throw new IllegalArgumentException("Illegal argument to setTextSize()!");
		}
		Context context = textview.getContext();
		int tvTitleSize = context.getResources().getDimensionPixelSize(ResHelper.getResId(context, "dimen", "bbs_title_txt_size"));
		textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTitleSize);
	}

	public static void setTextColor(TextView textview, String colorid) {
		if (textview == null || StringUtils.isEmpty(colorid)) {
			throw new IllegalArgumentException("Illegal argument to setTextColor()!");
		}
		Context context = textview.getContext();
		textview.setTextColor(context.getResources().getColor(ResHelper.getColorRes(context, colorid)));
	}
}
