package com.mob.bbssdk.gui.pages.user;


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
import com.mob.bbssdk.gui.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.SelectPicBasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.User;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.util.HashMap;

/**
 * 用户资料编辑界面
 */
public class PageEditProfile extends SelectPicBasePageWithTitle {
	private AsyncImageView aivAvatar;
	private TextView textViewUserName;
	private LinearLayout viewMale;
	private LinearLayout viewFemale;
	private ImageView imageViewMale;
	private ImageView imageViewFemale;
	private Button btnSubmit;
	private View viewLater;
	private User userInfo;

	private Integer userGender;
	private String userAvatar;

	PageEditProfile(User user) {
		this.userInfo = user;
	}

	@Override
	protected View onCreateContentView(final Context context) {
		titleBar.setTitle(getStringRes("bbs_pagesetprofile_title"));
		titleBar.setLeftImageResourceDefaultBack();
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_editprofile"), null);
		aivAvatar = (AsyncImageView) view.findViewById(getIdRes("bbs_editprofile_aivAvatar"));
		aivAvatar.setRound(ResHelper.dipToPx(getContext(), 94) / 2);
		aivAvatar.setCompressOptions(200, 200, 70, 0L);
		textViewUserName = (TextView) view.findViewById(getIdRes("bbs_editprofile_textViewUserName"));
		viewMale = (LinearLayout) view.findViewById(getIdRes("bbs_editprofile_viewMale"));
		viewFemale = (LinearLayout) view.findViewById(getIdRes("bbs_editprofile_viewFemale"));
		imageViewMale = (ImageView) view.findViewById(getIdRes("bbs_editprofile_imageViewMale"));
		imageViewFemale = (ImageView) view.findViewById(getIdRes("bbs_editprofile_imageViewFemale"));
		btnSubmit = (Button) view.findViewById(getIdRes("bbs_editprofile_btnSubmit"));
		viewLater = view.findViewById(getIdRes("bbs_editprofile_viewLater"));
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
		aivAvatar.execute(userInfo.avatar, ResHelper.getBitmapRes(context, "bbs_setprofile_nopic"));
		if(userInfo.gender == 1) {
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_check"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		} else if(userInfo.gender == 2) {
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_check"));
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		} else {
			imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
		}
		userGender = userInfo.gender;
		viewMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 1;
				imageViewMale.setImageResource(getDrawableId("bbs_setprofile_check"));
				imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
			}
		});
		viewFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userGender = 2;
				imageViewFemale.setImageResource(getDrawableId("bbs_setprofile_check"));
				imageViewMale.setImageResource(getDrawableId("bbs_setprofile_uncheck"));
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
				BBSSDK.getApi(UserAPI.class).updateUserInfo(userGender, userAvatar, false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						if (!TextUtils.isEmpty(userAvatar)) {
							//成功后删除剪切过的临时图片
							new File(userAvatar).delete();
							if (result != null && aivAvatar != null) {
								//从缓存中移除用户头像，方便用户头像实时更新
								aivAvatar.removeRamCache(result.avatar);
							}
						}
						ToastUtils.showToast(context, getStringRes("bbs_editprofile_success"));
						dismissLoadingDialog();
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("user", result);
						PageEditProfile.this.setResult(map);
						PageEditProfile.this.finish();
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						ErrorCodeHelper.toastError(context, errorCode, details);
						dismissLoadingDialog();
					}
				});
			}
		});
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
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
