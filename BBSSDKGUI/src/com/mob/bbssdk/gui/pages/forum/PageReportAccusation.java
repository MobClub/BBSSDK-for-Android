package com.mob.bbssdk.gui.pages.forum;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.utils.StringUtils;

public class PageReportAccusation extends BasePageWithTitle {
	protected RadioGroup radioGroup;
	protected Button btnSubmit;
	protected Long nPid;
	protected Long nFid;

	public void initPage(Long pid, Long fid){
		this.nPid = pid;
		this.nFid = fid;
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_forum_reportaccusation"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResourceDefaultBack();
		titleBar.setTitle(getStringRes("bbs_reportaccusation_title"));
		radioGroup = (RadioGroup) contentView.findViewById(getIdRes("radioGroup"));
		btnSubmit = (Button) contentView.findViewById(getIdRes("btnSubmit"));

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nPid == null || nFid == null || nPid <= 0 || nFid <= 0) {
					return;
				}
				String text = getSelectText();
				if (StringUtils.isEmpty(text)) {
					return;
				}
				showLoadingDialog();
				BBSSDK.getApi(UserAPI.class).resportAccusation("post", nPid, nFid, text, false, new APICallback<Boolean>() {
					@Override
					public void onSuccess(API api, int action, Boolean result) {
						ToastUtils.showToast(getContext(), getStringRes("bbs_reportaccusation_submitsuccess"));
						dismissLoadingDialog();
						finish();
					}

					@Override
					public void onError(API api, int action, int errorCode, Throwable details) {
						dismissLoadingDialog();
						ErrorCodeHelper.toastError(getContext(), errorCode, details);
					}
				});
			}
		});
	}

	protected String getSelectText() {
		RadioButton button = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
		if (button == null) {
			return "";
		} else {
			return button.getText().toString();
		}
	}
}
