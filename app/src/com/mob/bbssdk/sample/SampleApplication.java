package com.mob.bbssdk.sample;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mob.MobApplication;

public class SampleApplication extends MobApplication {
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//DEMO中由于添加了打开PDF、word等附件的方式，添加了很多第三方库，导致dex方法数超过了64K，所以需要使用多dex的方式
		MultiDex.install(this);
	}

}
