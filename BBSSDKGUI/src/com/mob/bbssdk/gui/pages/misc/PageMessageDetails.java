package com.mob.bbssdk.gui.pages.misc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.model.Notification;

public class PageMessageDetails extends BasePageWithTitle {
	private Notification notification;

	public void setNotification(Notification noti) {
		this.notification = noti;
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_messagedetails"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pagemymsgdetails_title"));
		TextView textViewTitle = (TextView) findViewById(getIdRes("textViewTitle"));
		TextView textViewBody = (TextView) findViewById(getIdRes("textViewBody"));
		textViewTitle.setText(notification.title);
		textViewBody.setText(notification.note);
	}

}
