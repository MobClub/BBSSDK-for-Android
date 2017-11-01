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
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.dialog.YesNoDialog;
import com.mob.bbssdk.model.ForumPost;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

public class SendForumPostManager {
	private static final String CACHE_POST = "cache_tmp_post";
	public static final String BROADCAST_SEND_POST = "com.mob.bbssdk.broadcast.SEND_POST";
	public static final int STATUS_SEND_IDLE = 0;
	public static final int STATUS_SEND_ING = 1;
	public static final int STATUS_SEND_SUCCESS = 2;
	public static final int STATUS_SEND_FAILED = 3;
	public static final int STATUS_SEND_CACHED = 4;

	private Context appContext;
	private long fid;
	private long tid;
	private ForumPost prePost;
	private String message;
	private String[] imgList;

	public SendForumPostManager(Context context, long fid, long tid, ForumPost prePost, String message, String[] imgList) {
		appContext = context.getApplicationContext();
		this.fid = fid;
		this.tid = tid;
		this.prePost = prePost;
		this.message = message;
		this.imgList = imgList;
	}

	/*
	* 发帖子回复
	*/
	public void sendPost() {
		sendBroadCast(appContext, STATUS_SEND_ING, null, null);
		new Thread() {
			public void run() {
				ForumAPI forumAPI = com.mob.bbssdk.BBSSDK.getApi(ForumAPI.class);
				UploadImgManager uploadImgManager = new UploadImgManager();
				final StringBuilder imgSb = new StringBuilder();
				if (imgList != null && imgList.length > 0) {
					//上传图片
					uploadImgManager.uploadImg(imgList);
					if (uploadImgManager.failedImages.isEmpty()) {
						for (String item : imgList) {
							imgSb.append("<br /><img src='");
							imgSb.append(uploadImgManager.successImages.get(item));
							imgSb.append("' />");
						}
					}
				}
				if (!uploadImgManager.failedImages.isEmpty()) {
					//有上传失败的图片，直接返回
					String msgStr = appContext.getResources().getString(ResHelper.getStringRes(appContext, "bbs_tip_upload_img_failed"));
					sendBroadCast(appContext, STATUS_SEND_FAILED, msgStr, null);
					return;
				}
				//3. 请求发帖
				String content = message + imgSb.toString();
				forumAPI.createPost(fid, tid, prePost, content, true, new APICallback<ForumPost>() {
					public void onSuccess(API api, int action, final ForumPost result) {
						sendBroadCast(appContext, STATUS_SEND_SUCCESS, null, result);
					}

					public void onError(API api, int action, final int errorCode, final Throwable details) {
						String errorMsg = ErrorCodeHelper.getErrorCodeStr(appContext, errorCode);
						if (TextUtils.isEmpty(errorMsg)) {
							errorMsg = details == null ? null : details.getMessage();
						}
						if (TextUtils.isEmpty(errorMsg)) {
							errorMsg = appContext.getResources().getString(ResHelper.getStringRes(appContext, "bbs_tip_net_timeout"));
						}
						sendBroadCast(appContext, STATUS_SEND_FAILED, errorMsg, null);
					}
				});
			}
		}.start();
	}

	private static void sendBroadCast(final Context context, final int status, final String failedMsg, final ForumPost forumPost) {
		UIHandler.sendEmptyMessage(0, new Handler.Callback() {
			public boolean handleMessage(Message msg) {
				setStatus(context, status, failedMsg);
				Intent intent = new Intent(BROADCAST_SEND_POST);
				intent.putExtra("status", status);
				if (failedMsg != null) {
					intent.putExtra("failedMsg", failedMsg);
				}
				if (forumPost != null) {
					intent.putExtra("forumPost", forumPost);
				}
				context.sendBroadcast(intent);

				int toastMsgRes = 0;
				if (status == STATUS_SEND_ING) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_writepost_send_ing");
				} else if (status == STATUS_SEND_SUCCESS) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_writepost_send_success");
					clearCache(context);
				} else if (status == STATUS_SEND_FAILED) {
					toastMsgRes = ResHelper.getStringRes(context, "bbs_writepost_send_failed");
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
		sp.open(CACHE_POST);
		return sp.getInt("status");
	}

	public synchronized static void setStatus(Context context, int status, String failedMsg) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		sp.putInt("status", status);
		sp.putString("failedMsg", failedMsg);
	}

	public synchronized static void clearPostStatus(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		sp.putInt("status", 0);
		sp.putString("failedMsg", "");
	}

	public synchronized static HashMap<String, Object> getPostCache(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		long fid = sp.getLong("fid");
		long tid = sp.getLong("tid");
		ForumPost prePost = ResHelper.forceCast(sp.get("prePost"));
		String message = sp.getString("message");
		String[] imgList = ResHelper.forceCast(sp.get("imgList"));
		String failedMsg = sp.getString("failedMsg");
		int status = sp.getInt("status");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fid", fid);
		map.put("tid", tid);
		map.put("prePost", prePost);
		map.put("message", message);
		map.put("imgList", imgList);
		map.put("failedMsg", failedMsg);
		map.put("status", status);
		return map;
	}

	public synchronized static void saveCache(Context context, long fid, long tid, ForumPost prePost, String message, String[] imgList) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		sp.putLong("fid", fid);
		sp.putLong("tid", tid);
		sp.put("prePost", prePost);
		sp.putString("message", message);
		sp.put("imgList", imgList);
	}

	/**
	 * 回复成功后，清空缓存
	 */
	public synchronized static void clearCache(Context context) {
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		sp.remove("fid");
		sp.remove("tid");
		sp.remove("prePost");
		sp.remove("message");
		sp.remove("imgList");
		sp.remove("status");
		sp.remove("failedMsg");
	}

	public static void showFailedDialog(final Context context) {
		YesNoDialog.Builder builder = new YesNoDialog.Builder(context);
		SharePrefrenceHelper sp = new SharePrefrenceHelper(context);
		sp.open(CACHE_POST);
		String failedMsg = ResHelper.forceCast(sp.getString("failedMsg"));
		String netFailedStr = TextUtils.isEmpty(failedMsg) ? context.getResources().
				getString(ResHelper.getStringRes(context, "bbs_tip_net_timeout")) : failedMsg;
		String failedStr = context.getResources().getString(ResHelper.getStringRes(context, "bbs_writepost_send_failed_tip"));
		builder.setMessage(failedStr + netFailedStr);
		builder.setYes(context.getResources().getString(ResHelper.getStringRes(context, "bbs_common_resend")));
		builder.setCancelable(false);
		builder.setOnClickListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					HashMap<String, Object> map = SendForumPostManager.getPostCache(context);
					long fid = ResHelper.forceCast(map.get("fid"), 0L);
					long tid = ResHelper.forceCast(map.get("tid"), 0L);
					ForumPost prePost = ResHelper.forceCast(map.get("prePost"));
					String message = ResHelper.forceCast(map.get("message"));
					String[] imgList = ResHelper.forceCast(map.get("imgList"));
					if (fid < 0 || tid < 0 || (TextUtils.isEmpty(message) && (imgList == null || imgList.length == 0))) {
						setStatus(context, STATUS_SEND_IDLE, null);
						Toast.makeText(context, ResHelper.getStringRes(context, "bbs_tip_get_cache_failed"), Toast.LENGTH_SHORT).show();
						return;
					}
					new SendForumPostManager(context, fid, tid, prePost, message, imgList).sendPost();
				} else if(which == DialogInterface.BUTTON_NEGATIVE) {
					HashMap<String, Object> map = SendForumPostManager.getPostCache(context);
					String failedMsg = ResHelper.forceCast(map.get("failedMsg"));
					setStatus(context, STATUS_SEND_CACHED, failedMsg);
					sendBroadCast(context, STATUS_SEND_CACHED, failedMsg, null);
				}
			}
		});
		builder.show();
	}
}
