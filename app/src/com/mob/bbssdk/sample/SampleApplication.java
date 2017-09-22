package com.mob.bbssdk.sample;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mob.MobSDK;
import com.mob.bbssdk.theme1.BBSTheme1;
import com.mob.tools.utils.ResHelper;

public class SampleApplication extends Application {
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//DEMO中由于添加了打开PDF、word等附件的方式，添加了很多第三方库，导致dex方法数超过了64K，所以需要使用多dex的方式
		MultiDex.install(this);
	}

	public void onCreate() {
		super.onCreate();

		int uiType = 1;
		try {
			int uiTypeResId = ResHelper.getStringRes(this, "BBS_UI_TYPE");
			if (uiTypeResId > 0) {
				//如果设置了BBS_UI_TYPE，则表示是打包工程，则不启动InitActivity，BBSSDK初始化在这里
				String key = getString(ResHelper.getStringRes(this, "BBS_APPKEY"));
				String secret = getString(ResHelper.getStringRes(this, "BBS_APPSECRET"));
				MobSDK.init(this, key, secret);
				uiType = Integer.parseInt(getString(uiTypeResId));
			}
		} catch (Throwable t) {
			uiType = 1;
		}
		BBSTheme1.init();

//		if (uiType == 2) {
//			BBSTheme1.init();
//		} else {
//			BBSTheme0.init();
//		}
	}
}
