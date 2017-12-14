package com.mob.bbssdk.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mob.MobSDK;
import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.datadef.PageResult;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.helper.FileHelper;
import com.mob.bbssdk.gui.helper.StorageFile;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.utils.OperationCallback;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.bbssdk.gui.other.ImageGetter;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class GUIManager {

	public static final String BROADCAST_LOGIN = "com.mob.bbssdk.broadcast.LOGIN";
	public static final String BROADCAST_LOGOUT = "com.mob.bbssdk.broadcast.LOGOUT";

	private static GUIManager bbsGUIManager;
	private boolean isPermissionGranted = false;
	private Bitmap currentUserAvatar = null;
	private FileHelper avatarFileHelper = null;
	//	private HashMap<Class<? extends BasePage>, Class<? extends BasePage>> typeMap;
	private FileHelper fileHelperAccount;
	private static boolean isClearingCache = false;

	public static boolean isShareEnable = true;

	public static class Account implements Serializable {
		public String strUserName;
		public String strPassword;
	}

	public static synchronized void init(GUIManager viewbuilder) {
		if (bbsGUIManager != null) {
			throw new IllegalAccessError("You can only init bbsGUIManager once!");
		}
		if (viewbuilder == null) {
			bbsGUIManager = new GUIManager();
		} else {
			bbsGUIManager = viewbuilder;
		}
	}

	public static GUIManager getInstance() {
		if (bbsGUIManager == null) {
			init(null);
		}
		return bbsGUIManager;
	}

	public GUIManager() {
		init();
	}

	public void init() {
		fileHelperAccount = new FileHelper(StorageFile.AccountCache);
		avatarFileHelper = new FileHelper(StorageFile.UserAvatar);
		currentUserAvatar = readAvatar();
	}

	public void permissionGranted(boolean granted) {
		isPermissionGranted = granted;
	}

	public boolean isPermissionGranted() {
		return isPermissionGranted;
	}

	public void saveAccount(Account account) {
		//doesn't save account in this version.
//		fileHelperAccount.saveObj(account);
	}

	public void clearAccount() {
		fileHelperAccount.clearContent();
		clearAvatar();
	}

	public Account getAccount() {
		//doesn't save account in this version.
		return null;
//		return fileHelperAccount.readObj();
	}

	private void clearAvatar() {
		currentUserAvatar = null;
		avatarFileHelper.clearContent();
	}

	private void saveAvatar(Bitmap bitmap) {
		avatarFileHelper.saveBitmap(bitmap);
	}

	private Bitmap readAvatar() {
		Bitmap result = avatarFileHelper.readBitmap();
		if (result == null) {
			forceUpdateCurrentUserAvatar(null);
		}
		return result;
	}

	public Bitmap getCurrentUserAvatar() {
		return currentUserAvatar;
	}

	public void setCurrentUserAvatar(Bitmap bitmap) {
		currentUserAvatar = bitmap;
		saveAvatar(bitmap);
	}

	public void forceUpdateCurrentUserAvatar(final AvatarUpdatedListener listener) {
		User user = null;
		try {
			user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
		} catch (Exception e) {
//			e.printStackTrace();
			user = null;
		}
		if (user != null && !StringUtils.isEmpty(user.avatar)) {
			ImageGetter.loadPic(user.avatar, new ImageGetter.ImageGotListener() {
				@Override
				public void OnImageGot(Bitmap bitmap) {
					currentUserAvatar = bitmap;
					saveAvatar(bitmap);
					if (listener != null) {
						listener.onUpdated(currentUserAvatar);
					}
				}
			}, true);
		}
	}

	public interface AvatarUpdatedListener {
		void onUpdated(Bitmap bitmap);
	}

	public static User getCurrentUser() {
		User user = null;
		try {
			user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
			return user;
		} catch (Exception e) {
			//This will never happen?!
		}
		return null;
	}

	public static void writePost(Context context, LoginListener listener) {
		if (ensureLogin(context, listener)) {
			//允许发帖
			User user = null;
			try {
				user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
			} catch (Exception e) {
				//This will never happen?!
				return;
			}
			if (user.allowPost == 1) {
				int status = SendForumThreadManager.getStatus(context);
				if (status == SendForumThreadManager.STATUS_SEND_FAILED) {
					SendForumThreadManager.showFailedDialog(context);
				} else if (status == SendForumThreadManager.STATUS_SEND_IDLE
						|| status == SendForumThreadManager.STATUS_SEND_CACHED) {
					PageWriteThread writethread = BBSViewBuilder.getInstance().buildPageWriteThread();
					writethread.show(context);
				}
			} else {//不允许发帖
				ToastUtils.showToast(context,
						context.getResources().getString(ResHelper.getStringRes(context, "bbs_dont_allowpost")));
			}
		}
	}

	public interface LoginListener {
		void OnLoggedIn();

		void OnCancel();
	}

	public static boolean ensureLogin(final Context context, final LoginListener listener) {
		if (GUIManager.isLogin()) {
			return true;
		} else {
			GUIManager.showLogin(context, listener);
			return false;
		}
	}

	public static void showLogin(final Context context, final LoginListener listener) {
		PageLogin pagelogin = BBSViewBuilder.getInstance().buildPageLogin();
		pagelogin.showForResult(context, new FakeActivity() {
			@Override
			public void onResult(HashMap<String, Object> data) {
				super.onResult(data);
				if (data != null) {
					Boolean login = (Boolean) data.get(PageResult.RESULT_LOGINSUCCESS_BOOLEAN);
					if (login != null && listener != null) {
						if (login) {
							listener.OnLoggedIn();
						} else {
							listener.OnCancel();
						}
					}
				}
			}
		});
	}

	public static boolean isLogin() {
		return (BBSViewBuilder.getInstance().ensureLogin(false) != null);
	}

	public interface ClearCacheListener {
		void CacheCleared();
	}

	public static synchronized boolean clearCache(final ClearCacheListener listener) {
		if (isClearingCache) {
			return false;
		}
		isClearingCache = true;
		AsyncTask task = new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] objects) {
				// This method must be called on a background thread.
				ImageGetter.clearDiskCache();
				ForumThreadHistoryManager.getInstance().clearReaded();
//				BitmapProcessor.deleteCacheDir(false);
				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				super.onPostExecute(o);
				if (listener != null) {
					listener.CacheCleared();
				}
				isClearingCache = false;
			}
		};
		task.execute();
		return true;
	}

	public static long getImageCacheSizeInByte() {
		File dir = new File(ImageGetter.getPhotoCacheDir());
		long size = 0;
		File[] files = dir.listFiles();
		for (File f : files) {
			size = size + f.length();
		}
		return size;
	}

	public static String getCacheSizeText() {
		float size = getImageCacheSizeInByte();
		if (size < 1024) {
			return String.format(Locale.CHINA, "%.02f", size) + " B";
		} else if (size / 1024 < 1000) {
			return String.format(Locale.CHINA, "%.02f", size / 1024) + " KB";
		} else {
			return String.format(Locale.CHINA, "%.02f", size / 1024 / 1204) + " MB";
		}
	}

	public static void logout(final Context context, final APICallback<Boolean> callback) {
		BBSSDK.getApi(UserAPI.class).logout(false, new APICallback<Boolean>() {
			@Override
			public void onSuccess(API api, int action, Boolean result) {
				ToastUtils.showToast(context, ResHelper.getStringRes(context, "bbs_logout"));
				if (callback != null) {
					callback.onSuccess(api, action, result);
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(MobSDK.getContext(), errorCode, details);
				if (callback != null) {
					callback.onError(api, action, errorCode, details);
				}
			}
		});
		ForumThreadHistoryManager.getInstance().clearReaded();
		GUIManager.getInstance().clearAccount();
		sendLogoutBroadcast();
	}

	public static boolean isLoginShowing() {
		return PageLogin.isLoginShowing();
	}

	public static void sendLoginBroadcast() {
		Intent intent = new Intent();
		intent.setAction(GUIManager.BROADCAST_LOGIN);
		MobSDK.getContext().sendBroadcast(intent);
		getInstance().init();
	}

	public static void sendLogoutBroadcast() {
		Intent intent = new Intent();
		intent.setAction(GUIManager.BROADCAST_LOGOUT);
		MobSDK.getContext().sendBroadcast(intent);
	}

	public static void loginQQ(final OperationCallback<HashMap<String, Object>> callback) {
		Platform plat = ShareSDK.getPlatform("QQ");
		plat.removeAccount(true);
		plat.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("openid", platform.getDb().getUserId());
				map.put("unionid", platform.getDb().get("unionid"));
				map.put("authType", "qq");
				map.putAll(hashMap);
				callback.onSuccess(map);
			}

			@Override
			public void onError(Platform platform, final int i, final Throwable throwable) {
				callback.onFailed(i, throwable);
			}

			@Override
			public void onCancel(Platform platform, int i) {
				callback.onCancel();
			}
		});
		plat.showUser(null);
	}

	public static void loginWeChat(final OperationCallback<HashMap<String, Object>> callback) {
		Platform plat = ShareSDK.getPlatform("Wechat");
		plat.removeAccount(true);
		plat.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("authType", "wechat");
				map.putAll(hashMap);
				callback.onSuccess(map);
			}

			@Override
			public void onError(Platform platform, final int i, final Throwable throwable) {
				callback.onFailed(i, throwable);
			}

			@Override
			public void onCancel(Platform platform, int i) {
				callback.onCancel();
			}
		});
		plat.showUser(null);
	}
}
