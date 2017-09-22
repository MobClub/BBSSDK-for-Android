package com.mob.bbssdk.sample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.utils.AppUtils;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.utils.CheckKeyUtils;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;

public class InitActivity extends Activity {
	private static final String SP_NAME = "sp_init";
	private static final int SP_VERSION = 1;
	private static final String SP_KEY = "sp_key";
	private static final String SP_SECRET = "sp_secret";
	private static final String REGREX_KEY = "[A-Za-z0-9]{1,32}";//不超过32个字符
	private static final String REGREX_SECRET = "[A-Za-z0-9]{1,64}";//不超过64个字符

	private EditText editTextKey;
	private EditText editTextSecret;
	private View layoutEnterOfficial;
	private String strKeyFromDisk;
	private String strSecretFromDisk;
	private SharePrefrenceHelper spInit;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(ResHelper.getLayoutRes(InitActivity.this, "activity_init"));
		editTextKey = (EditText) findViewById(ResHelper.getIdRes(InitActivity.this, "editTextKey"));
		editTextSecret = (EditText) findViewById(ResHelper.getIdRes(InitActivity.this, "editTextSecret"));
		layoutEnterOfficial = findViewById(ResHelper.getIdRes(InitActivity.this, "layoutEnterOfficial"));
		loadKeyAndSecret();
		if (!StringUtils.isEmpty(strKeyFromDisk)) {
			editTextKey.setText(strKeyFromDisk);
		}
		if (!StringUtils.isEmpty(strSecretFromDisk)) {
			editTextSecret.setText(strSecretFromDisk);
		}

		findViewById(ResHelper.getIdRes(InitActivity.this, "btnConfirm")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String key = editTextKey.getText().toString().trim();
				final String secret = editTextSecret.getText().toString().trim();
				//only check key and secret validation in release version.
				if(AppUtils.isReleaseVersion()) {
					CheckKeyUtils.checkKeyLegal(key, secret, new CheckKeyUtils.Callback() {
						public void onCallback(CheckKeyUtils.Info info) {
							if (info != null && info.status == 200) {
								if (info.hasApp != 1) {
									ToastUtils.showToast(getApplicationContext(), getStringByResName("bbs_init_appsecret_error"));
									return;
								}
								if (info.isSet != 1) {
									ToastUtils.showToast(getApplicationContext(), getStringByResName("bbs_init_pluginnotset_error"));
									return;
								}
								if (info.isInitialize != 1) {
									ToastUtils.showToast(getApplicationContext(), getStringByResName("bbs_init_bbssdknotinit_error"));
									return;
								}
								if (initAndStartMainActivity(key, secret)) {
									saveKeyAndSecret(key, secret);
								}
							} else {
								ToastUtils.showToast(getApplicationContext(), getStringByResName("bbs_init_appsecret_error"));
							}
						}
					});
				} else {
					if (initAndStartMainActivity(key, secret)) {
						saveKeyAndSecret(key, secret);
					}
				}
			}
		});
		layoutEnterOfficial.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initAndStartMainActivity(getStringByResName("BBS_APPKEY"), getStringByResName("BBS_APPSECRET"));
			}
		});
	}

	private boolean initAndStartMainActivity(String key, String secret) {
		if (StringUtils.isEmpty(key) || !key.matches(REGREX_KEY)) {
			ToastUtils.showToast(InitActivity.this, getStringByResName("bbs_init_illegalkey"));
			return false;
		}

		if (StringUtils.isEmpty(secret) || !secret.matches(REGREX_SECRET)) {
			ToastUtils.showToast(InitActivity.this, getStringByResName("bbs_init_illegalsecret"));
			return false;
		}

		MobSDK.init(InitActivity.this, key, secret);
		//init success or fail?
		finish();
		Intent intent = new Intent(InitActivity.this, MainActivity.class);
		startActivity(intent);
		return true;
	}

	private void loadKeyAndSecret() {
		spInit = new SharePrefrenceHelper(InitActivity.this);
		spInit.open(SP_NAME, SP_VERSION);
		strKeyFromDisk = (String) spInit.get(SP_KEY);
		strSecretFromDisk = (String) spInit.get(SP_SECRET);
	}

	private void saveKeyAndSecret(String key, String secret) {
		if (spInit != null) {
			spInit.put(SP_KEY, key);
			spInit.put(SP_SECRET, secret);
		}
	}

	private String getStringByResName(String name) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		return getString(ResHelper.getStringRes(InitActivity.this, name));
	}
}
