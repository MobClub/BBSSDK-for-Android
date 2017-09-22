package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.theme1.page.Theme1StyleModifier;

public class Theme1PageUserProfileDetails extends BasePageWithTitle {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_userprofiledetails"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofiledetails_title"));
		Theme1StyleModifier.modifyUniformWhiteStyle(this);

		TextView textViewName = (TextView) contentView.findViewById(getIdRes("textViewName"));
		TextView textViewGender = (TextView) contentView.findViewById(getIdRes("textViewGender"));
		TextView textViewMail = (TextView) contentView.findViewById(getIdRes("textViewMail"));
		TextView textViewGroup = (TextView) contentView.findViewById(getIdRes("textViewGroup"));
		TextView textViewStatus = (TextView) contentView.findViewById(getIdRes("textViewStatus"));

		contentView.findViewById(getIdRes("layoutSignature")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				new Theme0PageEditSignature().show(getContext());
			}
		});
	}
}
