package com.mob.bbssdk.gui.pages.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.other.ImagePicker;
import com.mob.bbssdk.gui.other.PhotoCropPage;
import com.mob.bbssdk.gui.other.ums.SingleChoiceView.DefaultTheme;
import com.mob.bbssdk.gui.pages.SelectPicBasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;

public class PageUserProfileDetails extends SelectPicBasePageWithTitle {

	private static final String TAG = "ProfileDetails";

	protected User userInfo;
	protected GlideImageView aivAvatar;
	protected TextView textViewName;
	protected TextView textViewGender;
	protected TextView textViewSignature;
	protected TextView textViewLocation;
	protected TextView textViewBirthday;
	protected TextView textViewMail;
	protected TextView textViewGroup;
	protected TextView textViewStatus;
	protected String userAvatar;
	protected LinearLayout layoutAvatar;
	protected View layoutSignature;
	protected View layoutGender;
	protected View layoutLocation;
	protected View layoutBirthday;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_misc_theme0userprofiledetails"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofiledetails_title"));

		layoutAvatar = (LinearLayout) contentView.findViewById(getIdRes("layoutAvatar"));
		aivAvatar = (GlideImageView) contentView.findViewById(getIdRes("aivAvatar"));
		aivAvatar.setExecuteRound();
		textViewName = (TextView) contentView.findViewById(getIdRes("textViewName"));
		textViewGender = (TextView) contentView.findViewById(getIdRes("textViewGender"));
		textViewSignature = (TextView) contentView.findViewById(getIdRes("textViewSignature"));
		textViewLocation = (TextView) contentView.findViewById(getIdRes("textViewLocation"));
		textViewBirthday = (TextView) contentView.findViewById(getIdRes("textViewBirthday"));
		textViewMail = (TextView) contentView.findViewById(getIdRes("textViewMail"));
		textViewGroup = (TextView) contentView.findViewById(getIdRes("textViewGroup"));
		textViewStatus = (TextView) contentView.findViewById(getIdRes("textViewStatus"));
		layoutSignature = contentView.findViewById(getIdRes("layoutSignature"));
		layoutGender = contentView.findViewById(getIdRes("layoutGender"));
		layoutLocation = contentView.findViewById(getIdRes("layoutLocation"));
		layoutBirthday = contentView.findViewById(getIdRes("layoutBirthday"));
		layoutAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseImg(new ImagePicker.OnImageGotListener() {
					@Override
					public void onOmageGot(String id, String[] url) {
						if (url != null && url.length > 0) {
							int resId = ResHelper.getBitmapRes(getContext(), "umssdk_default_avatar");
							try {
								userAvatar = url[0];
								sumbitPic();
							} catch (Throwable throwable) {
								throwable.printStackTrace();
							}
						}
					}
				});
//				choose(true, 200, 200);
			}
		});
		User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			finish();
			return;
		}
		updateUserInfo(user);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateInfoFromServer();
	}

	protected void OnInfoUpdated() {

	}

	protected void chooseImg(final ImagePicker.OnImageGotListener listener) {
		String[] strarray = getContext().getResources().getStringArray(
				ResHelper.getStringArrayRes(getContext(), "bbs_chooserpic_items"));
		final DefaultChooserDialog dialog = new DefaultChooserDialog(getContext(), strarray);
		dialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				if (position == 0) {
					dialog.dismiss();
				} else if (position == 2) { //从相册选择
					PhotoCropPage page = new PhotoCropPage(new DefaultTheme());
					page.showForAlbum(listener);
					dialog.dismiss();
				} else if (position == 1) {
					PhotoCropPage page = new PhotoCropPage(new DefaultTheme());
					page.showForCamera(listener);
					dialog.dismiss();
				} else {
					dialog.dismiss();
				}
			}
		});
		dialog.show();
	}

	protected void updateUserInfo(User user) {
		userInfo = user;
		aivAvatar.setImageBitmap(GUIManager.getInstance().getCurrentUserAvatar());
		textViewName.setText(userInfo.userName);
		textViewGender.setText(DataConverterHelper.getGenderInfo(getContext(), userInfo));
		textViewSignature.setText(userInfo.sightml == null ? "" : Html.fromHtml(userInfo.sightml));
		textViewLocation.setText(DataConverterHelper.getShortLoationText(userInfo));
		textViewBirthday.setText(DataConverterHelper.getBirthday(userInfo));
		textViewMail.setText(userInfo.email);
		textViewGroup.setText(userInfo.groupName);
		textViewStatus.setText(DataConverterHelper.getEmailStatusText(getContext(), userInfo));
		OnInfoUpdated();
	}

	protected void updateInfoFromServer() {
		User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			finish();
			return;
		}
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		api.getUserInfo(user.userName, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				updateUserInfo(result);
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {

			}
		});
	}

	protected void onPicGot(Uri source, String realpath) {
		//not used.
	}

	protected void onPicCrop(Bitmap bitmap) {
		//not used.
//		try {
//			userAvatar = BitmapHelper.saveBitmap(activity, bitmap);
//			sumbitPic(bitmap);
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
	}

	protected void sumbitPic() {
		final Context context = getContext();
		showLoadingDialog();
		BBSSDK.getApi(UserAPI.class).updateUserInfo(null, userAvatar, null, null, null, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				ToastUtils.showToast(context, getStringRes("bbs_editprofile_success"));
				dismissLoadingDialog();
				Bitmap bitmap = null;
				try {
					bitmap = BitmapHelper.getBitmap(userAvatar);
					GUIManager.getInstance().setCurrentUserAvatar(bitmap);
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
				//reload the details.
				updateInfoFromServer();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(context, errorCode, details);
				dismissLoadingDialog();
//				aivAvatar.execute(userInfo.avatar, null);
			}
		});
	}
}
