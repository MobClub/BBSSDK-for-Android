package com.mob.bbssdk.gui.pages.user;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.ErrorCode;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSRegex;
import com.mob.bbssdk.gui.ErrorCodeHelper;
import com.mob.bbssdk.gui.PageResult;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;

import java.util.HashMap;

/**
 * 用户注册界面
 */
public class PageRegister extends BasePageWithTitle {
	private static final String TAG = "PageRegister";

	private EditText editTextUserName;
	private EditText editTextMail;
	private EditText editTextPassword;
	private Button btnRegister;
	private TextView btnBackLogin;

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		finish();
	}

	@Override
	protected View onCreateContentView(Context context) {
		titleBar.setTitle(getStringRes("bbs_pageregister_title"));
		titleBar.setRightImageResource(getDrawableId("bbs_titlebar_close"));

		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_register"), null);
		editTextUserName = (EditText) view.findViewById(getIdRes("bbs_register_edittextusername"));
		editTextMail = (EditText) view.findViewById(getIdRes("bbs_register_edittextmail"));
		editTextPassword = (EditText) view.findViewById(getIdRes("bbs_register_edittextpassword"));
		btnRegister = (Button) view.findViewById(getIdRes("bbs_register_btnregister"));
		btnBackLogin = (TextView) view.findViewById(getIdRes("bbs_register_btnbacklogin"));
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
		btnBackLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {

	}

	private void register() {
		String name = editTextUserName.getText().toString();
		String pwd = editTextPassword.getText().toString();
		String mail = editTextMail.getText().toString();
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(mail)) {
			toastStringRes("bbs_invalid_username_pwd_mail");
			return;
		}
		if(!mail.matches(BBSRegex.EMAIL_REGEX)) {
			toastStringRes("bbs_email_wrongformat");
			return;
		}
		final String strmail = mail;
		final String strname = name;
		BBSSDK.getApi(UserAPI.class).register(strname, pwd, strmail, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				//200 直接注册成功登录
				ToastUtils.showToast(getContext(), getStringRes("bbs_register_success"));
				finish();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(PageResult.RESULT_REGISTERSUCCESS_BOOLEAN, true);
				setResult(map);
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				//606需要用户激活账号
				if(errorCode == ErrorCode.SDK_API_ERROR_USER_NOT_ACTIVATED) {
					new PageRegisterConfirm(strname, strmail).show(activity);
				} else {
					ErrorCodeHelper.toastError(getContext(), errorCode, details);
				}
			}
		});
	}
}
