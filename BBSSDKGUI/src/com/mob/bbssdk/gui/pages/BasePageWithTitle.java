package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.tools.utils.ResHelper;

/**
 * 带标题栏的界面基类
 * 标题栏的背景颜色和内容的背景颜色可通过bbs_color.xml中的bbs_title_bg和bbs_bg属性进行修改
 */
public abstract class BasePageWithTitle extends BasePage {
	protected TitleBar titleBar;
	protected View vLine;

	protected View onCreateView(Context context) {
		LinearLayout flContent = new LinearLayout(context);
		flContent.setBackgroundResource(ResHelper.getColorRes(context, "bbs_title_bg"));
		flContent.setOrientation(LinearLayout.VERTICAL);
		flContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		titleBar = new TitleBar(context);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		flContent.addView(titleBar, llp);

		vLine = new View(context);
		vLine.setBackgroundResource(ResHelper.getColorRes(context, "bbs_title_under_line_bg"));
		flContent.addView(vLine, ViewGroup.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(context, 1) / 2);
		vLine.setVisibility(View.GONE);

		View contentView = onCreateContentView(context);
		contentView.setBackgroundResource(ResHelper.getColorRes(context, "bbs_bg"));
		llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
		llp.weight = 1;
		flContent.addView(contentView, llp);

		titleBar.setOnClickListener(newTitleBarClickListener());
		return flContent;
	}

	protected abstract View onCreateContentView(Context context);

	private View.OnClickListener newTitleBarClickListener() {
		return new View.OnClickListener() {
			public void onClick(View view) {
				if (view == titleBar) {
					int tag = ResHelper.forceCast(view.getTag(), 0);
					switch (tag) {
						case TitleBar.TYPE_TITLE: {
							onTitleClick(titleBar);
						} break;
						case TitleBar.TYPE_LEFT_IMAGE: {
							onTitleLeftClick(titleBar);
						} break;
						case TitleBar.TYPE_RIGHT_IMAGE:
						case TitleBar.TYPE_RIGHT_TEXT: {
							onTitleRightClick(titleBar);
						} break;
					}
				}
			}
		};
	}

	protected void onTitleClick(TitleBar titleBar) {

	}

	protected void onTitleLeftClick(TitleBar titleBar) {
		activity.onBackPressed();
	}

	protected void onTitleRightClick(TitleBar titleBar) {

	}
}
