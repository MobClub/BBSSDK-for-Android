package com.mob.bbssdk.theme1.page.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.ErrorCode;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.datadef.PageResult;
import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.pages.account.PageReactiveConfirm;
import com.mob.bbssdk.gui.utils.OperationCallback;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserQuestion;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yychen on 2017/11/16.
 */

public class Theme1PageBindLogin extends BasePageWithTitle {

	private EditText editTextUserName;
	private EditText editTextPassword;
	private Button btnLogin;
	private TextView viewRegister;
	private TextView textViewQuestion;
	private EditText editTextAnswer;
	private ImageView viewDropDown;
	private LinearLayout layoutAnswerQuestion;
	private ArrayList<UserQuestion> listUserQuestion;
	private UserQuestion currentQuestion = null;
	private HashMap<String, Object> socialInfo;
	private OperationCallback<HashMap<String, Object>> callback;

	@Override
	protected void onTitleLeftClick(TitleBar titleBar) {
		finish();
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_activity_bind"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setStatusBarColor(BBSViewBuilder.getInstance().getStatusBarColor(getContext()));
		titleBar.setTitle(ResHelper.getStringRes(getContext(), "bbs_other_login_bind_title"));
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		editTextUserName = (EditText) contentView.findViewById(getIdRes("bbs_login_edittextusername"));
		editTextPassword = (EditText) contentView.findViewById(getIdRes("bbs_login_edittextpassword"));

		textViewQuestion = (TextView) contentView.findViewById(getIdRes("bbs_login_textviewquestion"));
		editTextAnswer = (EditText) contentView.findViewById(getIdRes("bbs_login_edittextanswer"));
		viewDropDown = (ImageView) contentView.findViewById(getIdRes("bbs_login_viewdropdown"));
		layoutAnswerQuestion = (LinearLayout) contentView.findViewById(getIdRes("bbs_login_layoutAnswerQuestion"));
		btnLogin = (Button) contentView.findViewById(getIdRes("bbs_bind_login"));
		viewRegister = (TextView) contentView.findViewById(getIdRes("bbs_unbind_login"));
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				socialAuthLogin(0);
			}
		});
		viewRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				socialAuthLogin(1);
			}
		});
		viewDropDown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listUserQuestion == null || listUserQuestion.size() == 0) {
					return;
				}
				List<String> list = new ArrayList<String>();
				for (UserQuestion question : listUserQuestion) {
					list.add(question.question);
				}
				DefaultChooserDialog dialog = new DefaultChooserDialog(getContext(), list);
				dialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
					@Override
					public void onItemClick(View v, int position) {
						if (position == 0) {
							setQuestion(null);
						} else {
							setQuestion(position - 1);
						}
					}
				});
				dialog.show();
			}
		});
		readAccount();
	}

	public void setSocialInfo(HashMap<String, Object> socialInfo) {
		this.socialInfo = socialInfo;
	}

	public void setCallback(OperationCallback<HashMap<String, Object>> callback) {
		this.callback = callback;
	}

	protected void saveAccount(String name, String password) {
		GUIManager.Account account = new GUIManager.Account();
		account.strUserName = name;
		account.strPassword = password;
		GUIManager.getInstance().saveAccount(account);
	}

	protected void readAccount() {
		GUIManager.Account account = GUIManager.getInstance().getAccount();
		if (account == null) {
			return;
		}
		if (account != null && !StringUtils.isEmpty(account.strUserName) && !StringUtils.isEmpty(account.strPassword)) {
			editTextUserName.setText(account.strUserName);
			editTextPassword.setText(account.strPassword);
		}
	}

	private void socialAuthLogin(int createNew) {
		//用户名密码支持中文，只检查邮箱是否符合格式
		final String name = editTextUserName.getText().toString().trim();
		final String pwd = editTextPassword.getText().toString().trim();
		String answer = editTextAnswer.getText().toString().trim();
		if (createNew == 0) {
			if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
				toastStringRes("bbs_invalid_username_pwd");
				return;
			}
		}
		UserAPI api = BBSSDK.getApi(UserAPI.class);
		showLoadingDialog();
		String openid = String.valueOf(socialInfo.get("openid"));
		String unionid = String.valueOf(socialInfo.get("unionid"));
		String authType = String.valueOf(socialInfo.get("authType"));
		api.socialAuthLogin(openid, unionid, authType, createNew, name, pwd, currentQuestion == null ? 0 : currentQuestion.questionId,
				currentQuestion == null ? "" : answer, false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						dismissLoadingDialog();
						saveAccount(result.userName, pwd);
						//如果需要激活账号
						if (result.errorCode == ErrorCode.SDK_API_ERROR_USER_NOT_ACTIVATED) {
							PageReactiveConfirm page = BBSViewBuilder.getInstance().buildPageReactiveConfirm();
							page.initPage(result.userName, result.email);
							page.show(getContext());
						} else if (result.errorCode == ErrorCode.SDK_API_ERROR_NEED_SAFETY_VERIFICATION) {
							//回答问题
							listUserQuestion = result.questionList;
							if (layoutAnswerQuestion != null) {
								layoutAnswerQuestion.setVisibility(View.VISIBLE);
							}
							ToastUtils.showToast(getContext(), getStringRes("bbs_error_code_605"));
						} else {
							GUIManager.sendLoginBroadcast();
							GUIManager.getInstance().forceUpdateCurrentUserAvatar(null);
							//登录成功
							ToastUtils.showToast(getContext(), getStringRes("bbs_login_success"));
							HashMap<String, Object> map = new HashMap<String, Object>();
							//force the MainView to refresh.
							map.put(PageResult.RESULT_LOGINSUCCESS_BOOLEAN, true);
							finish();
							if (null != callback) {
								callback.onSuccess(map);
							}
						}
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						dismissLoadingDialog();
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
	}

	private void setQuestion(Integer pos) {
		if (pos == null || pos < 0) {
			textViewQuestion.setText("");
			currentQuestion = null;
			return;
		}
		if (listUserQuestion == null || listUserQuestion.size() == 0) {
			textViewQuestion.setText("");
			currentQuestion = null;
			return;
		}
		if (pos < 0 || pos >= listUserQuestion.size()) {
			return;
		}
		currentQuestion = listUserQuestion.get(pos);
		textViewQuestion.setText(currentQuestion.question);
	}

}
