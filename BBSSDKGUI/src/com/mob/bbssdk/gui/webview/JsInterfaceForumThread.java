package com.mob.bbssdk.gui.webview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.ForumPost;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 帖子详情JS交互对象
 */
public class JsInterfaceForumThread {
	private static final String TAG = "JsInterfaceForumThread";
	private WeakReference<JsViewClient> refViewClient;
	private ForumThread forumThread;
	private Hashon hashon;
	private Context context;
	private boolean hasMoreComment = true;

	public JsInterfaceForumThread(Context context, JsViewClient viewClient, ForumThread forumThread) {
		if (viewClient != null) {
			refViewClient = new WeakReference<JsViewClient>(viewClient);
			hashon = new Hashon();
		}
		this.forumThread = forumThread;
		this.context = context;
	}

	@JavascriptInterface
	public String getForumThreadDetails() {
		if (forumThread != null) {
			try {
				JSONObject object = new JSONObject();
				object.put("tid", forumThread.tid);
				object.put("fid", forumThread.fid);
				object.put("subject", forumThread.subject);
				object.put("forumName", forumThread.forumName);
				object.put("message", forumThread.message);
				object.put("summary", forumThread.summary);
				object.put("heatLevel", forumThread.heatLevel);
				object.put("displayOrder", forumThread.displayOrder);
				object.put("digest", forumThread.digest);
				object.put("highlight", forumThread.highlight);
				object.put("deviceName", forumThread.deviceName);
				object.put("lastPost", forumThread.lastPost);
				if (forumThread.images != null && forumThread.images.size() > 0) {
					try {
						JSONArray tmpArray = new JSONArray();
						tmpArray.put(forumThread.images);
						object.put("images", tmpArray);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if (forumThread.attachmentList != null && forumThread.attachmentList.size() > 0) {
					try {
						JSONArray tmpArray = new JSONArray();
						JSONObject tmp;
						for (ForumThreadAttachment item : forumThread.attachmentList) {
							tmp = new JSONObject();
							tmp.put("createdOn", item.createdOn);
							tmp.put("fileName", item.fileName);
							tmp.put("fileSize", item.fileSize);
							tmp.put("isImage", item.isImage);
							tmp.put("price", item.price);
							tmp.put("readPerm", item.readPerm);
							tmp.put("uid", item.uid);
							tmp.put("url", item.url);
							tmp.put("width", item.width);
							tmp.put("extension", item.extension);
							if (refViewClient != null && refViewClient.get() != null) {
								String filePath = PageAttachmentViewer.getExistsAttachmentPath(refViewClient.get().getAppContext(), item.url);
								if (!TextUtils.isEmpty(filePath)) {
									tmp.put("filePath", filePath);
								}
							}
							tmpArray.put(tmp);
						}
						object.put("attachments", tmpArray);
					} catch (JSONException e2) {
						e2.printStackTrace();
					}
				}
				object.put("author", forumThread.author);
				object.put("authorId", forumThread.authorId);
				object.put("avatar", forumThread.avatar);
				object.put("createdOn", forumThread.createdOn);
				object.put("replies", forumThread.replies);
				object.put("views", forumThread.views);

				User user = null;
				try {
					user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
				} catch (Exception e) {
					e.printStackTrace();
					user = null;
				}
				//user is valid and current user is not the author then display follow button.
				if(user != null && user.uid != forumThread.authorId) {
					object.put("follow", forumThread.follow);
				}
				object.put("recommend_add", forumThread.recommendadd);
				object.put("forumPic", forumThread.forumPic);
				return object.toString();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return null;
	}

	@JavascriptInterface
	public void getPosts(final long fid, final long tid, final int page, final int pageSize, final long authorId, final String callback) {
		if (forumThread == null) {
			loadJs(callback, (Object) null);
		} else {
			ForumAPI forumAPI = BBSSDK.getApi(ForumAPI.class);
			forumAPI.getPostListByThreadId(fid, tid, authorId, page, pageSize, false, new APICallback<ArrayList<ForumPost>>() {
				public void onSuccess(API api, int action, ArrayList<ForumPost> result) {
					if (result != null) {
						if(result.size() < pageSize) {
							hasMoreComment = false;
						}
						JSONArray array = new JSONArray();
						try {
							JSONObject object;
							if (result != null && result.size() > 0) {
								for (ForumPost item : result) {
									object = new JSONObject();
									object.put("pid", item.pid);
									object.put("tid", item.tid);
									object.put("createdOn", item.createdOn);
									object.put("authorId", item.authorId);
									object.put("author", item.author);
									object.put("avatar", item.avatar);
									object.put("message", item.message);
									object.put("deviceName", item.deviceName);
									object.put("position", item.position);
									if (item.prePost != null) {
										JSONObject prePostObj = new JSONObject();
										prePostObj.put("createdOn", item.prePost.createdOn);
										prePostObj.put("author", item.prePost.author);
										prePostObj.put("message", item.prePost.message);
										prePostObj.put("position", item.prePost.position);
										object.put("prePost", prePostObj);
									}
									array.put(object);
								}
								loadJs(callback, array.toString());
								return;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					loadJs(callback, (Object) null);
				}

				public void onError(API api, int action, int errorCode, Throwable details) {
					loadJs(callback, (Object) null);
				}
			});
		}
	}

	@JavascriptInterface
	public void openImage(String[] imageUrls, int index) {
		if (refViewClient != null && refViewClient.get() != null) {
			refViewClient.get().onItemImageClick(imageUrls, index);
		}
	}

	@JavascriptInterface
	public void openAttachment(String attachmentJsonStr) {
		if (refViewClient != null && refViewClient.get() != null) {
			HashMap<String, Object> map = hashon.fromJson(attachmentJsonStr);
			if (map != null && !map.isEmpty()) {
				ForumThreadAttachment attachment = new ForumThreadAttachment();
				attachment.width = ResHelper.forceCast(map.get("width"), 0);
				attachment.createdOn = ResHelper.forceCast(map.get("createdOn"), 0l);
				attachment.fileName = ResHelper.forceCast(map.get("fileName"));
				attachment.fileSize = ResHelper.forceCast(map.get("fileSize"), 0l);
				attachment.isImage = ResHelper.forceCast(map.get("isImage"), 0);
				attachment.price = ResHelper.forceCast(map.get("price"), 0f);
				attachment.readPerm = ResHelper.forceCast(map.get("readPerm"), 0);
				attachment.uid = ResHelper.forceCast(map.get("uid"), 0l);
				attachment.url = ResHelper.forceCast(map.get("url"));
				attachment.extension = ResHelper.forceCast(map.get("extension"));
				refViewClient.get().onItemAttachmentClick(attachment);
			}
		}
	}

	@JavascriptInterface
	public void downloadImages(String[] imageUrls) {
		if (refViewClient != null && refViewClient.get() != null) {
			refViewClient.get().downloadImages(imageUrls, refViewClient.get().getImageDownloadListener());
		}
	}

	@JavascriptInterface
	public void replyPost(String prePost) {
		HashMap<String, Object> map = parseJsonToMap(prePost);
		final ForumPost forumPost = new ForumPost();
		forumPost.author = ResHelper.forceCast(map.get("author"));
		forumPost.authorId = ResHelper.forceCast(map.get("authorId"), 0L);
		forumPost.createdOn = ResHelper.forceCast(map.get("createdOn"), 0L);
		forumPost.message = ResHelper.forceCast(map.get("message"));
		forumPost.deviceName = ResHelper.forceCast(map.get("deviceName"));
		forumPost.avatar = ResHelper.forceCast(map.get("avatar"));
		forumPost.pid = ResHelper.forceCast(map.get("pid"), 0L);
		forumPost.position = ResHelper.forceCast(map.get("position"), 0);
		forumPost.tid = ResHelper.forceCast(map.get("tid"), 0L);
		if (refViewClient != null && refViewClient.get() != null) {
			UIHandler.sendEmptyMessage(0, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					refViewClient.get().onReplyPostClick(forumPost);
					return false;
				}
			});
		}
	}

	@JavascriptInterface
	public void onImageLongPressed(final String imgPath) {
		if (refViewClient != null && refViewClient.get() != null) {
			UIHandler.sendEmptyMessage(0, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					refViewClient.get().onImageLongPressed(imgPath);
					return false;
				}
			});
		}
	}

	public void gotoPosts() {
		loadJs("BBSSDKNative.goComment");
	}

	/* 只看楼主 */
	public void getOwnerPosts() {
		loadJs("BBSSDKNative.updateCommentHtml", (forumThread == null ? 0L : forumThread.authorId), true);
	}

	public void getAllPosts() {
		loadJs("BBSSDKNative.updateCommentHtml", (forumThread == null ? 0L : forumThread.authorId), false);
	}

	public void articleLiked(long fid, long tid) {
		loadJs("BBSSDKNative.articleLiked", fid, tid);
	}

	public void addPost(ForumPost forumPost) {
		if(hasMoreComment) {
			//If there is more comment, then doesn't add the fake comment,
			//because the one will be loaded from server.
			return;
		}
		String post = null;
		try {
			JSONObject object = new JSONObject();
			object.put("author", forumPost.author);
			object.put("authorId", forumPost.authorId);
			object.put("avatar", forumPost.avatar);
			object.put("createdOn", forumPost.createdOn);
			object.put("fid", forumPost.fid);
			object.put("message", forumPost.message);
			object.put("pid", forumPost.pid);
			object.put("position", forumPost.position);
			object.put("tid", forumPost.tid);
			object.put("deviceName", forumPost.deviceName);
			if (forumPost.prePost != null) {
				JSONObject preObj = new JSONObject();
				preObj.put("createdOn", forumPost.prePost.createdOn);
				preObj.put("author", forumPost.prePost.author);
				preObj.put("message", forumPost.prePost.message);
				preObj.put("position", forumPost.prePost.position);
				object.put("prePost", preObj);
			}
			post = object.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		long authorId = 0;
		if (forumThread != null) {
			authorId = forumThread.authorId;
		}
		//todo if the comment list is not loaded completely.
		loadJs("BBSSDKNative.addNewCommentHtml", post, authorId);
	}

	public HashMap<String, Object> parseJsonToMap(String json) {
		try {
			return hashon.fromJson(json);
		} catch (Throwable t) {
			return null;
		}
	}

	private void loadJs(String callback, Object... data) {
		if (refViewClient != null && refViewClient.get() != null) {
			final StringBuilder jsSB = new StringBuilder();
			jsSB.append(callback);
			jsSB.append("(");
			if (data == null || data.length < 1) {
				jsSB.append("null");
			} else {
				for (int i = 0; i < data.length; i++) {
					if (data[i] == null) {
						jsSB.append("null");
					} else {
						jsSB.append(data[i]);
					}
					if (i != data.length - 1) {
						jsSB.append(", ");
					}
				}
			}
			jsSB.append(");");
			if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
				UIHandler.sendEmptyMessage(0, new Handler.Callback() {
					public boolean handleMessage(Message msg) {
						refViewClient.get().evaluateJavascript(jsSB.toString());
						return false;
					}
				});
			} else {
				refViewClient.get().evaluateJavascript(jsSB.toString());
			}
		}
	}

	@JavascriptInterface
	public void openAuthor(int authorId) {
		User user = null;
		try {
			user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
			user = null;
		}
		//"openAuthor. user.uid: " + user.uid + " authorid: " + authorId
		if(user != null && user.uid == authorId) {
			BBSViewBuilder.getInstance().buildPageUserProfile().show(context);
		} else {
			PageOtherUserProfile page = BBSViewBuilder.getInstance().buildPageOtherUserProfile();
			page.initPage(authorId);
			page.show(context);
		}
	}

	@JavascriptInterface
	public void followAuthor(int authorId, int flag, final String callback) {
		UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
		if (flag == 0) {
			User current = GUIManager.getCurrentUser();
			if(current != null && authorId == current.uid) {
				ToastUtils.showToast(context, ResHelper.getStringRes(context, "bbs_cant_follower_yourself"));
				return;
			}
			userAPI.followUser(authorId, false, new APICallback<Boolean>() {
				@Override
				public void onSuccess(API api, int action, Boolean result) {
					loadJs(callback, true);
					ToastUtils.showToast(context, ResHelper.getStringRes(context, "bbs_follow_success"));
				}

				@Override
				public void onError(API api, int action, int errorCode, Throwable details) {
					ErrorCodeHelper.toastError(context, errorCode, details);
					loadJs(callback, (Object) null);
				}
			});
		} else if (flag == 1) {
			userAPI.unfollowUser(authorId, false, new APICallback<Boolean>() {
				@Override
				public void onSuccess(API api, int action, Boolean result) {
					loadJs(callback, true);
					ToastUtils.showToast(context, ResHelper.getStringRes(context, "bbs_unfollow_success"));
				}

				@Override
				public void onError(API api, int action, int errorCode, Throwable details) {
					ErrorCodeHelper.toastError(context, errorCode, details);
					loadJs(callback, (Object) null);
				}
			});
		} else {
			//followAuthor error flag:
		}
	}

	@JavascriptInterface
	public void likeArticle(int fid, int tid, final String callback) {
		UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
		userAPI.recordLikePost(fid, tid, false, new APICallback<Boolean>() {
			@Override
			public void onSuccess(API api, int action, Boolean result) {
				loadJs(callback, true);
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(context, errorCode, details);
				loadJs(callback, (Object) null);
			}
		});
	}

	@JavascriptInterface
	public void openHref(String url) {
		PageWeb pageWeb = BBSViewBuilder.getInstance().buildPageWeb();
		pageWeb.setLink(url);
		pageWeb.show(context);
	}
}
