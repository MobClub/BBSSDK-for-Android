package com.mob.bbssdk.gui.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class EmailUtils {

	public static boolean openLoginPageFromEmail(Context context, String mail) {
		if (TextUtils.isEmpty(mail)) {
			return false;
		}
		String url = EmailUtils.getLoginUrlFromEmail(mail);
		if (TextUtils.isEmpty(url)) {
			return false;
		}
		Uri uri;
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			uri = Uri.parse("http://" + url);
		} else {
			uri = Uri.parse(url);
		}
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
		return true;
	}

	public static String getLoginUrlFromEmail(String mail) {
		String strsuffix = mail.split("@")[1];
		strsuffix = strsuffix.toLowerCase();
		if ("163.com".equals(strsuffix)) {
			return "mail.163.com";
		} else if ("vip.163.com".equals(strsuffix)) {
			return "vip.163.com";
		} else if ("126.com".equals(strsuffix)) {
			return "mail.126.com";
		} else if ("qq.com".equals(strsuffix) || "vip.qq.com".equals(strsuffix) || "foxmail.com".equals(strsuffix)) {
			return "mail.qq.com";
		} else if ("gmail.com".equals(strsuffix)) {
			return "mail.google.com";
		} else if ("sohu.com".equals(strsuffix)) {
			return "mail.sohu.com";
		} else if ("tom.com".equals(strsuffix)) {
			return "mail.tom.com";
		} else if ("vip.sina.com".equals(strsuffix)) {
			return "vip.sina.com";
		} else if ("sina.com.cn".equals(strsuffix) || "sina.com".equals(strsuffix)) {
			return "mail.sina.com.cn";
		} else if ("tom.com".equals(strsuffix)) {
			return "mail.tom.com";
		} else if ("yahoo.com.cn".equals(strsuffix) || "yahoo.cn".equals(strsuffix)) {
			return "mail.cn.yahoo.com";
		} else if ("tom.com".equals(strsuffix)) {
			return "mail.tom.com";
		} else if ("yeah.net".equals(strsuffix)) {
			return "www.yeah.net";
		} else if ("21cn.com".equals(strsuffix)) {
			return "mail.21cn.com";
		} else if ("hotmail.com".equals(strsuffix)) {
			return "www.hotmail.com";
		} else if ("sogou.com".equals(strsuffix)) {
			return "mail.sogou.com";
		} else if ("188.com".equals(strsuffix)) {
			return "www.188.com";
		} else if ("139.com".equals(strsuffix)) {
			return "mail.10086.cn";
		} else if ("189.cn".equals(strsuffix)) {
			return "webmail15.189.cn/webmail";
		} else if ("wo.com.cn".equals(strsuffix)) {
			return "mail.wo.com.cn/smsmail";
		} else if ("139.com".equals(strsuffix)) {
			return "mail.10086.cn";
		} else {
			return "";
		}
	}
}
