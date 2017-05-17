package com.mob.bbssdk.gui.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.ErrorCodeHelper;
import com.mob.bbssdk.gui.dialog.YesNoDialog;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;
import java.util.Map;

public class SendForumThreadManager {
	private static final String CACHE_THREAD = "cache_tmp_thread";
	public static final String BROADCAST_SEND_THREAD = "com.mob.bbssdk.broadcast.SEND_THREAD";
	public static final int STATUS_SEND_IDLE = 0;
	public static final int STATUS_SEND_ING = 1;
	public static final int STATUS_SEND_SUCCESS = 2;
	public static final int STATUS_SEND_FAILED = 3;
	public static final int STATUS_SEND_CACHED = 4;

	private Context appContext;
	private long fid;
	private String subject;
	private String message;
	private String[] imgList;

	public SendForumThreadManager(Context context, long fid, String subject, String message, String[] imgList) {
		appContext = context.getApplicationContext();
		this.fid = fid;
		this.subject = subject;
		this.message = message;
		this.imgList = imgList;
	}

	public void sendThread() {
		sendBroadCast(appContext, STATUS_SEND_ING, null);
		new Thread() {
			public void run() {
				ForumAPI forumAPI = com.mob.bbssdk.BBSSDK.getApi(ForumAPI.class);
				String realMessage = message;
				UploadImgManager uploadImgManager = new UploadImgManager();
				if (imgList != null && imgList.length > 0) {
					//上传图片
					uploadImgManager.uploadImg(imgList);
					if (uploadImgManager.failedImages.isEmpty()) {
						//替换上传成功的图片的src值为网络地址
						for (Map.Entry<String, String> item : uploadImgManager.successImages.entrySet()) {
							String oldImg = "<img src=\"" + item.getKey() + "\" alt=\"\">";
							String newImg = "<img src=\"" + item.getValue() + "\" alt=\"\">";
							realMessage = message.replaceAll(oldImg, newImg);
							message = realMessage;
						}
					}
				}
				if (!uploadImgManager.failedImages.isEmpty()) {
					//有上传失败的图片，直接返回
					String msgStr = appContext.getResources().getString(ResHelper.getStringRes(appContext, "bbs_tip_upload_img_failed"));
					sendBroadCast(appContext, STATUS_SEND_FAILED, msgStr);
					return;
				}
				//所有图片上传完成后，调用上传文章的接口
				forumAPI.createThread(fid, subject, realMessage, true, new APICallback<ForumThread>() {
					public void onSuccess(API api, int action, ForumThread result) {
						sendBroadCast(appContext, STATUS_SEND_SUCCESS, null);
					}

					public void onError(API api, int action, final int errorCode, final Throwable details) {
						String errorMsg = ErrorCodeHelper.getErrorCodeStr(appContext, errorCode);
						if (TextUtils.isEmpty(errorMsg)) {
							errorMsg = details == null ? null : details.getMessage();
						}
						if (TextUtils.isEmpty(errorMsg)) {
							errorMsg = appContext.getResources().getString(ResHelper.getStringRes(appContext, "bbs_tip_net_timeout"));
						}
						sendBroadCast(appContext, STATUS_SEND_FAILED, errorMsg);
					}
				});
			}
		}.start();
	}

	private static void sendBroadCast(final Context context, final int status, final String failedMsg) {
		UIHandler.sendEmptyMessage(0, new Handler.Callback() {
			public boolean handleMessage(Message msg) {
				setStatus(context, status, failedMsg);
				Intent intent = new Intent(BROADCAST_SEND_THREAD);
				intent.putExtra("status", status);
				if (failedMsg != null) {
					intent.putExtra("failedMsg", failedMsg);
				}
				context.sendBroadcast(intent);

				int toastMsgRes = 0;
				if (status == STATUS_SEND_ING) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_pagewritethread_send_ing");
				} else if (status == STATUS_SEND_SUCCESS) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_pagewritethread_send_success");
					clearCache(context);
				} else if (status == STATUS_SEND_FAILED) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_pagewritethread_send_failed");
				}
				if (toastMsgRes > 0) {
					Toast.makeText(context, toastMsgRes, Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
	}

	public synchronized static int getStatus(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		return sp.getInt("status");
	}

	public synchronized static void setStatus(Context context, int status, String failedMsg) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		sp.putInt("status", status);
		sp.putString("failedMsg", failedMsg);
	}

	public synchronized static HashMap<String, Object> getThreadCache(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		ForumForum forum = ResHelper.forceCast(sp.get("ForumForum"));
		String subject = sp.getString("subject");
		String message = sp.getString("message");
		String failedMsg = sp.getString("failedMsg");
		int status = sp.getInt("status");
		String[] imgList= ResHelper.forceCast(sp.get("imgList"));
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ForumForum", forum);
		map.put("subject", subject);
		map.put("message", message);
		map.put("imgList", imgList);
		map.put("failedMsg", failedMsg);
		map.put("status", status);
		return map;
	}

	public synchronized static void saveCache(Context context, ForumForum forum, String subject, String message, String[] imgList) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		sp.put("ForumForum", forum);
		sp.putString("subject", subject);
		sp.putString("message", message);
		sp.put("imgList", imgList);
	}

	/**
	 * 回复成功后，清空缓存
	 */
	public synchronized static void clearCache(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		sp.remove("ForumForum");
		sp.remove("subject");
		sp.remove("message");
		sp.remove("imgList");
		sp.remove("status");
		sp.remove("failedMsg");
	}

	public static void showFailedDialog(final Context context) {
		YesNoDialog.Builder builder = new YesNoDialog.Builder(context);
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_THREAD);
		String failedMsg = ResHelper.forceCast(sp.getString("failedMsg"));
		String netFailedStr = TextUtils.isEmpty(failedMsg) ? context.getResources().
				getString(ResHelper.getStringRes(context, "bbs_tip_net_timeout")) : failedMsg;
		String failedStr = context.getResources().getString(ResHelper.getStringRes(context, "bbs_pagewritethread_send_failed_tip"));
		builder.setMessage(failedStr + netFailedStr);
		builder.setYes(context.getResources().getString(ResHelper.getStringRes(context, "bbs_common_resend")));
		builder.setCancelable(false);
		builder.setOnClickListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					HashMap<String, Object> map = SendForumThreadManager.getThreadCache(context);
					ForumForum forum = ResHelper.forceCast(map.get("ForumForum"));
					long fid = 0;
					if (forum != null) {
						fid = forum.fid;
					}
					String subject = ResHelper.forceCast(map.get("subject"));
					String message = ResHelper.forceCast(map.get("message"));
					String[] imgList = ResHelper.forceCast(map.get("imgList"));
					if (fid < 0 || TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)) {
						setStatus(context, STATUS_SEND_IDLE, null);
						Toast.makeText(context, ResHelper.getStringRes(context, "bbs_tip_get_cache_failed"), Toast.LENGTH_SHORT).show();
						return;
					}
					new SendForumThreadManager(context, fid, subject, message, imgList).sendThread();
				} else if (which == DialogInterface.BUTTON_NEGATIVE) {
					HashMap<String, Object> map = SendForumThreadManager.getThreadCache(context);
					String failedMsg = ResHelper.forceCast(map.get("failedMsg"));
					setStatus(context, STATUS_SEND_CACHED, failedMsg);
					sendBroadCast(context, STATUS_SEND_CACHED, failedMsg);
				}
			}
		});
		builder.show();
	}
}
