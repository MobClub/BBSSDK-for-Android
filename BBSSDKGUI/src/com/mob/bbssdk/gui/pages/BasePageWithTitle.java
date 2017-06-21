package com.mob.bbssdk.gui.pages;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mob.bbssdk.gui.dialog.ModelLoadingDialog;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.tools.utils.ResHelper;

/**
 * 带标题栏的界面基类
 * 标题栏的背景颜色和内容的背景颜色可通过bbs_color.xml中的bbs_title_bg和bbs_bg属性进行修改
 */
public abstract class BasePageWithTitle extends BasePage {
	protected TitleBar titleBar;
	protected View vLine;
	private ModelLoadingDialog modelLoadingDialog;

	protected int getStatusBarColor() {
		return getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_mainviewtitle_bg"));
	}

	protected View onCreateView(Context context) {
		LinearLayout flContent = new LinearLayout(context);
		flContent.setBackgroundResource(ResHelper.getColorRes(context, "bbs_title_bg"));
		flContent.setOrientation(LinearLayout.VERTICAL);
		flContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		titleBar = new TitleBar(context) {
			@Override
			protected View getCenterView() {
				View centerview = getTitleCenterView();
				if (centerview == null) {
					return super.getCenterView();
				}
				return centerview;
			}
		};
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		flContent.addView(titleBar, llp);

		vLine = new View(context);
		vLine.setBackgroundResource(ResHelper.getColorRes(context, "bbs_mainviewtitle_bg"));
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

	protected View getTitleCenterView() {
		return null;
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
						case TitleBar.TYPE_LEFT_IMAGE://fall through
						case TitleBar.TYPE_LEFT_TEXT: {
							onTitleLeftClick(titleBar);
						} break;
						case TitleBar.TYPE_RIGHT_IMAGE://fall through
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

	public String getStringRes(String name) {
		if (TextUtils.isEmpty(name)) {
			return "";
		}
		return getContext().getString(ResHelper.getStringRes(getContext(), name));
	}

	public Integer getDrawableId(String name) {
		int resid = ResHelper.getBitmapRes(getContext(), name);
		return resid;
	}

	public Integer getColorId(String name) {
		return ResHelper.getColorRes(getContext(), name);
	}

	public Integer getLayoutId(String name) {
		return ResHelper.getLayoutRes(getContext(), name);
	}

	public int getIdRes(String name) {
		return ResHelper.getIdRes(getContext(), name);
	}

	public void toastStringRes(String name) {
		if (TextUtils.isEmpty(name)) {
			return;
		}
		ToastUtils.showToast(getContext(), getStringRes(name));
	}

	protected void showLoadingDialog() {
		if (modelLoadingDialog != null && modelLoadingDialog.isShowing()) {
			return;
		}
		modelLoadingDialog = new ModelLoadingDialog(getContext());
		modelLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if(modelLoadingDialog == null) {
			return;
		}
		modelLoadingDialog.dismiss();
	}
}
