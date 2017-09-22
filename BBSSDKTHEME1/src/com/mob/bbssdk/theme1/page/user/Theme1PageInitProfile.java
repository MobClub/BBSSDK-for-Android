package com.mob.bbssdk.theme1.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.profile.PageInitProfile;
import com.mob.tools.utils.ResHelper;

public class Theme1PageInitProfile extends PageInitProfile {

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_editprofile"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
//		Theme0StyleModifier.modifyUniformWhiteStyle(this);

		if (userInfo.gender == 0) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
		} else if (userInfo.gender == 1) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
		} else if (userInfo.gender == 2) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
		} else {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
		}
		viewKeepSecret.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 0;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			}
		});
		viewMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 1;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
			}
		});
		viewFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 2;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme1_setprofile_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme1_setprofile_check"));
			}
		});
	}

	@Override
	protected Integer getDefaultPortrait() {
		return ResHelper.getBitmapRes(getContext(), "bbs_theme1_login_defaultportrait");
	}
}
