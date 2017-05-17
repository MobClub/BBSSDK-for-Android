package com.mob.bbssdk.gui.pages.user;


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
import com.mob.bbssdk.gui.ErrorCodeHelper;
import com.mob.bbssdk.gui.PageResult;
import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserQuestion;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户登录界面
 */
public class PageLogin extends BasePageWithTitle {
	private EditText editTextUserName;
	private EditText editTextPassword;
	private TextView viewForgetPassword;
	private Button btnLogin;
	private TextView viewRegister;
	private TextView textViewQuestion;
	private EditText editTextAnswer;
	private ImageView viewDropDown;
	private LinearLayout layoutAnswerQuestion;
	private ArrayList<UserQuestion> listUserQuestion;
	private UserQuestion currentQuestion = null;

	@Override
	protected void onTitleRightClick(TitleBar titleBar) {
		super.onTitleRightClick(titleBar);
		finish();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}

	@Override
	protected View onCreateContentView(final Context context) {
		titleBar.setTitle(getStringRes("bbs_pagelogin_title"));
		titleBar.setRightImageResource(getDrawableId("bbs_titlebar_close"));
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_login"), null);
		editTextUserName = (EditText) view.findViewById(getIdRes("bbs_login_edittextusername"));
		editTextPassword = (EditText) view.findViewById(getIdRes("bbs_login_edittextpassword"));
		viewForgetPassword = (TextView) view.findViewById(getIdRes("bbs_login_viewforgetpassword"));

		textViewQuestion = (TextView) view.findViewById(getIdRes("bbs_login_textviewquestion"));
		editTextAnswer = (EditText) view.findViewById(getIdRes("bbs_login_edittextanswer"));
		viewDropDown = (ImageView) view.findViewById(getIdRes("bbs_login_viewdropdown"));
		layoutAnswerQuestion = (LinearLayout) view.findViewById(getIdRes("bbs_login_layoutAnswerQuestion"));
		btnLogin = (Button) view.findViewById(getIdRes("bbs_login_btnlogin"));
		viewRegister = (TextView) view.findViewById(getIdRes("bbs_login_viewregister"));
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
		viewForgetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PageRetrievePassword().show(getContext());
			}
		});
		viewRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PageRegister().showForResult(getContext(), PageLogin.this);
			}
		});
		view.findViewById(getIdRes("bbs_login_viewdropdown")).setOnClickListener(new View.OnClickListener() {
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
						if(position == 0) {
							setQuestion(null);
						} else {
							setQuestion(position - 1);
						}
					}
				});
				dialog.show();
			}
		});
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {

	}

	private void login() {
		//用户名密码支持中文，只检查邮箱是否符合格式
		String name = editTextUserName.getText().toString().trim();
		String pwd = editTextPassword.getText().toString().trim();
		String answer = editTextAnswer.getText().toString().trim();
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
			toastStringRes("bbs_invalid_username_pwd");
			return;
		}

		UserAPI api = BBSSDK.getApi(UserAPI.class);
		showLoadingDialog();
		api.login(name, pwd, currentQuestion == null ? 0 : currentQuestion.questionId,
				currentQuestion == null ? "" : answer, false, new APICallback<User>() {
					@Override
					public void onSuccess(API api, int action, User result) {
						dismissLoadingDialog();
						//如果需要激活账号
						if(result.errorCode == ErrorCode.SDK_API_ERROR_USER_NOT_ACTIVATED) {
							new PageReactiveConfirm(result.userName, result.email).show(getContext());
						} else if (result.errorCode == ErrorCode.SDK_API_ERROR_NEED_SAFETY_VERIFICATION) {
								//回答问题
							listUserQuestion = result.questionList;
							if(layoutAnswerQuestion != null) {
								layoutAnswerQuestion.setVisibility(View.VISIBLE);
							}
							ToastUtils.showToast(getContext(), getStringRes("bbs_error_code_1205"));
						} else {
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

	private void setQuestion(Integer pos) {
		if(pos == null || pos < 0) {
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

	@Override
	public void onResult(HashMap<String, Object> data) {
		super.onResult(data);
		if (data != null) {
			Boolean finish = ResHelper.forceCast(data.get(PageResult.RESULT_REGISTERSUCCESS_BOOLEAN));
			if(finish != null && finish) {
				finish();
				HashMap<String, Object> map = new HashMap<String, Object>();
				//force the MainView to refresh.
				map.put(PageResult.RESULT_LOGINSUCCESS_BOOLEAN, true);
				setResult(map);
			}
		}
	}
}
