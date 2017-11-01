package com.mob.bbssdk.gui.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.mob.bbssdk.gui.helper.FilePath;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareUtils {
	private static final String DEFAULT_SHARE_RES = "bbs_default_sharepic";
	private static final String DEFAULT_SHARE_FILE_NAME = "DefaultSharePic.png";
	private static final String DEFAULT_SHARE_FILE_DIR = FilePath.FILE_FOLDER;

	public static void startShare(Context context, String title, String titleurl
			, String text, String imagedir, String url, String comment
			, String webtitle, String siteurl) {
		if (context == null) {
			return;
		}
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
		//oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(titleurl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//微信分享必须要有图片才能正常的分享链接！！！确保imageurl或者imagepath有一个参数是有效的。
		if (StringUtils.isEmpty(imagedir)){
			if(ensureDefaultShareExist(context)) {
				oks.setImagePath(DEFAULT_SHARE_FILE_DIR + DEFAULT_SHARE_FILE_NAME);//本地图片。确保SDcard下面存在此张图片
			} else {
				//No valid shared pic on disk
				return;
			}
		} else {
			oks.setImageUrl(imagedir);//网络图片。
		}
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(comment);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(webtitle);
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(siteurl);

		// 启动分享GUI
		oks.show(context);
	}


	public static boolean ensureDefaultShareExist(Context context) {
		Resources resources = context.getResources();
		Bitmap bm = BitmapFactory.decodeResource(resources, ResHelper.getBitmapRes(context, DEFAULT_SHARE_RES));
		File file = new File(DEFAULT_SHARE_FILE_DIR, DEFAULT_SHARE_FILE_NAME);
		if (!file.exists()) {
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				try {
					outStream.flush();
					outStream.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			return true;
		}
		return false;
	}

	public static void share(String title, String titleurl
			, String text, String imagedir, String imageUrl, String url, String platformName, PlatformActionListener listener) {
//		QQ.NAME;
//		Wechat.NAME
//		SinaWeibo.NAMENAME;
		Platform p = ShareSDK.getPlatform(platformName);
		if (p != null) {
			p.setPlatformActionListener(listener);
		}
		Platform.ShareParams sp = new Platform.ShareParams();
		if (TextUtils.isEmpty(imagedir)) {
			sp.setImageUrl(imageUrl);
		} else {
			sp.setImagePath(imagedir);
		}
		sp.setTitle(title);
		sp.setTitleUrl(titleurl);
		sp.setText(text);
		sp.setUrl(url);
		p.share(sp);
	}
}
