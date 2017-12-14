package com.mob.bbssdk.theme0.page.user;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.ErrorCode;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.datadef.PageResult;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.pages.account.PageReactiveConfirm;
import com.mob.bbssdk.gui.utils.OperationCallback;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

public class Theme0PageLogin extends PageLogin {
	private ImageView ivQQLogin;
	private ImageView ivWXLogin;
	private ImageView ivPhoneLogin;
	private View otherLoginView;
	private View wxLoginView;
	private View qqLoginView;
	private OperationCallback<HashMap<String, Object>> socialCallback;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_login"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setRightImageResource(getDrawableId("bbs_titlebar_close_white"));
		Theme0StyleModifier.modifyUniformBlueStyle(this);
		otherLoginView = contentView.findViewById(getIdRes("bbs_other_root"));
		wxLoginView = contentView.findViewById(getIdRes("bbs_login_wx_fl"));
		qqLoginView = contentView.findViewById(getIdRes("bbs_login_qq_fl"));
		ivQQLogin = (ImageView) contentView.findViewById(getIdRes("bbs_login_qq"));
		ivWXLogin = (ImageView) contentView.findViewById(getIdRes("bbs_login_wx"));
		ivPhoneLogin = (ImageView) contentView.findViewById(getIdRes("bbs_login_phone"));

		ivQQLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showLoadingDialog();
				GUIManager.loginQQ(new OperationCallback<HashMap<String, Object>>() {
					@Override
					public void onSuccess(HashMap<String, Object> data) {
						socialAuthLogin(data);
					}

					@Override
					public void onCancel() {
						dismissLoadingDialog();

					}

					@Override
					public void onFailed(int errorCode, Throwable details) {
						dismissLoadingDialog();
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}
		});
		ivWXLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showLoadingDialog();
				GUIManager.loginWeChat(new OperationCallback<HashMap<String, Object>>() {
					@Override
					public void onSuccess(HashMap<String, Object> data) {
						socialAuthLogin(data);
					}

					@Override
					public void onCancel() {
						dismissLoadingDialog();
					}

					@Override
					public void onFailed(int errorCode, Throwable details) {
						dismissLoadingDialog();
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}

				});
			}
		});
		initSocialLogin();
		initSocialCallback();
	}

	private void initSocialCallback() {
		socialCallback = new OperationCallback<HashMap<String, Object>>() {
			@Override
			public void onSuccess(HashMap<String, Object> data) {
				setResult(data);
				finish();
			}
		};
	}

	private void initSocialLogin() {
		Platform[] platforms = ShareSDK.getPlatformList();
		if (platforms == null) {
			return;
		}
		for (Platform p : platforms) {
			if ("QQ".equals(p.getName())) {
				qqLoginView.setVisibility(View.VISIBLE);
				otherLoginView.setVisibility(View.VISIBLE);
			}
			if ("Wechat".equals(p.getName())) {
				wxLoginView.setVisibility(View.VISIBLE);
				otherLoginView.setVisibility(View.VISIBLE);
			}
		}
	}

	private void socialAuthLogin(final HashMap<String, Object> socialInfo) {
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		String openid = String.valueOf(socialInfo.get("openid"));
		String unionid = String.valueOf(socialInfo.get("unionid"));
		String authType = String.valueOf(socialInfo.get("authType"));
		api.socialAuthLogin(openid, unionid, authType, null, "", "", -1, "",
				false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						dismissLoadingDialog();
						saveAccount(result.userName, "");
						//如果需要激活账号
						if (result.errorCode == ErrorCode.SDK_API_ERROR_USER_NOT_ACTIVATED) {
							PageReactiveConfirm page = BBSViewBuilder.getInstance().buildPageReactiveConfirm();
							page.initPage(result.userName, result.email);
							page.show(getContext());
						} else if (result.errorCode == ErrorCode.SDK_API_ERROR_NEED_SAFETY_VERIFICATION) {
							//回答问题

							ToastUtils.showToast(getContext(), getStringRes("bbs_error_code_605"));
						} else if (result.errorCode == ErrorCode.SDK_API_ERROR_NO_BIND_ACCOUNT) {
							//绑定账号
							Theme0PageBindLogin bindLogin = new Theme0PageBindLogin();
							bindLogin.setSocialInfo(socialInfo);
							bindLogin.setCallback(socialCallback);
							bindLogin.show(getContext());
						} else {
							GUIManager.sendLoginBroadcast();
							GUIManager.getInstance().forceUpdateCurrentUserAvatar(null);
							//登录成功
							ToastUtils.showToast(getContext(), getStringRes("bbs_login_success"));
							HashMap<String, Object> map = new HashMap<String, Object>();
							//force the MainView to refresh.
							map.put(PageResult.RESULT_LOGINSUCCESS_BOOLEAN, true);
							setResult(map);
							finish();
						}
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						dismissLoadingDialog();
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
	}
}
