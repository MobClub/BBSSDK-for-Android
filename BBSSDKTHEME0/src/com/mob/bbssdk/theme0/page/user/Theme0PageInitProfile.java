package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.profile.PageInitProfile;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.tools.utils.ResHelper;

public class Theme0PageInitProfile extends PageInitProfile {
	public Theme0PageInitProfile() {
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_editprofile"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		Theme0StyleModifier.modifyUniformWhiteStyle(this);

		if (userInfo.gender == 0) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_check"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
		} else if (userInfo.gender == 1) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme0_check"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
		} else if (userInfo.gender == 2) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme0_check"));
		} else {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
		}
		viewKeepSecret.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 0;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_check"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			}
		});
		viewMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 1;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme0_check"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
			}
		});
		viewFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 2;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_theme0_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_theme0_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_theme0_check"));
			}
		});
		aivAvatar.setImageResource(getDefaultPortrait());
	}

	@Override
	protected Integer getDefaultPortrait() {
		return ResHelper.getBitmapRes(getContext(), "bbs_theme0_setprofile_defaultportrait");
	}
}
