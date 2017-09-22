package com.mob.bbssdk.gui.pages.profile;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.SelectPicBasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * 用户资料编辑界面
 */
public class PageInitProfile extends SelectPicBasePageWithTitle {
	protected GlideImageView aivAvatar;
	protected TextView textViewUserName;
	protected LinearLayout viewMale;
	protected LinearLayout viewFemale;
	protected LinearLayout viewKeepSecret;
	protected ImageView imageViewMale;
	protected ImageView imageViewFemale;
	protected ImageView imageViewKeepSecret;
	protected Button btnSubmit;
	protected View viewLater;
	protected User userInfo;

	protected Integer userGender;
	protected String userAvatar;

	public PageInitProfile() {
	}

	public void initPage(User user){
		this.userInfo = user;
	}

	@Override
	protected View onCreateContentView(final Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_profile_editprofile"), null);
		return view;
	}

	protected Integer getDefaultPortrait() {
		return null;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		final Context context = getContext();
		titleBar.setTitle(getStringRes("bbs_pagesetprofile_title"));
		titleBar.setLeftImageResourceDefaultBack();
		aivAvatar = (GlideImageView) contentView.findViewById(getIdRes("bbs_editprofile_aivAvatar"));
		aivAvatar.setExecuteRound();
		textViewUserName = (TextView) contentView.findViewById(getIdRes("bbs_editprofile_textViewUserName"));
		viewMale = (LinearLayout) contentView.findViewById(getIdRes("bbs_editprofile_viewMale"));
		viewFemale = (LinearLayout) contentView.findViewById(getIdRes("bbs_editprofile_viewFemale"));
		viewKeepSecret = (LinearLayout) contentView.findViewById(getIdRes("bbs_editprofile_viewKeepSecret"));
		imageViewMale = (ImageView) contentView.findViewById(getIdRes("bbs_editprofile_imageViewMale"));
		imageViewFemale = (ImageView) contentView.findViewById(getIdRes("bbs_editprofile_imageViewFemale"));
		imageViewKeepSecret = (ImageView) contentView.findViewById(getIdRes("bbs_editprofile_imageKeepSecret"));
		btnSubmit = (Button) contentView.findViewById(getIdRes("bbs_editprofile_btnSubmit"));
		viewLater = contentView.findViewById(getIdRes("bbs_editprofile_viewLater"));
		viewLater.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		aivAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				choose(true, 200, 200);
			}
		});
		Integer defaultportrait = getDefaultPortrait();
		if(defaultportrait == null) {
			defaultportrait = ResHelper.getBitmapRes(getContext(), "bbs_setprofile_nopic");
		}
		aivAvatar.execute(userInfo.avatar, getDefaultPortrait());
		if(userInfo.gender == 0) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_check"));
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		} else if(userInfo.gender == 1) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_check"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		} else if(userInfo.gender == 2) {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_check"));
		} else {
			imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		}
		userGender = userInfo.gender;
		viewKeepSecret.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 0;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_check"));
				imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			}
		});
		viewMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 1;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_setprofile_check"));
				imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			}
		});
		viewFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 2;
				imageViewKeepSecret.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
				imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
				imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_check"));
			}
		});
		textViewUserName.setText(userInfo.userName);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(userAvatar) && userInfo != null && userGender == userInfo.gender) {
					//如果用户信息没有发生变化，则提示
					ToastUtils.showToast(context, getStringRes("bbs_editprofile_toast_userinfo_not_changed"));
					return;
				}
				showLoadingDialog();
				BBSSDK.getApi(UserAPI.class).updateUserInfo(userGender, userAvatar, null, null, null, false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						ToastUtils.showToast(context, getStringRes("bbs_editprofile_success"));
						dismissLoadingDialog();
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("user", result);
						PageInitProfile.this.setResult(map);
						PageInitProfile.this.finish();
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(context, errorCode, details);
						dismissLoadingDialog();
					}
				});
			}
		});
	}

	protected void onPicGot(Uri source, String realpath) {

	}

	protected void onPicCrop(Bitmap bitmap) {
		aivAvatar.setBitmap(bitmap);
		try {
			userAvatar = BitmapHelper.saveBitmap(activity, bitmap);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
