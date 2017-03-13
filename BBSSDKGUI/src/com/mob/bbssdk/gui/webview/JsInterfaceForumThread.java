package com.mob.bbssdk.gui.webview;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.pages.PageAttachmentViewer;
import com.mob.bbssdk.model.ForumPost;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 帖子详情JS交互对象
 */
public class JsInterfaceForumThread {
	private WeakReference<JsViewClient> refViewClient;
	private ForumThread forumThread;
	private Hashon hashon;

	public JsInterfaceForumThread(JsViewClient viewClient, ForumThread forumThread) {
		if (viewClient != null) {
			refViewClient = new WeakReference<JsViewClient>(viewClient);
			hashon = new Hashon();
		}
		this.forumThread = forumThread;
	}

	@JavascriptInterface
	public String getForumThreadDetails() {
		if (forumThread == null) {
			return null;
		}
		try {
			JSONObject object = new JSONObject();
			object.put("tid", forumThread.tid);
			object.put("fid", forumThread.fid);
			object.put("subject", forumThread.subject);
			object.put("message", forumThread.message);
			object.put("summary", forumThread.summary);
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
			return object.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	@JavascriptInterface
	public String getPosts(long fid, long tid, int page, int pageSize) {
		if (forumThread == null) {
			return null;
		}
		final List<ForumPost> list = new ArrayList<ForumPost>();
		ForumAPI forumAPI = BBSSDK.getApi(ForumAPI.class);
		forumAPI.getPostListByThreadId(fid, tid, page, pageSize, true, new APICallback<ArrayList<ForumPost>>() {
			public void onSuccess(API api, int action, ArrayList<ForumPost> result) {
				if (result != null) {
					list.addAll(result);
				}
			}

			public void onError(API api, int action, int errorCode, Throwable details) {

			}
		});
		JSONArray array = new JSONArray();
		try {
			JSONObject object;
			for (ForumPost item : list) {
				object = new JSONObject();
				object.put("pid", item.pid);
				object.put("tid", item.tid);
				object.put("createdOn", item.createdOn);
				object.put("authorId", item.authorId);
				object.put("author", item.author);
				object.put("avatar", item.avatar);
				object.put("message", item.message);
				object.put("position", item.position);
				if (item.prePost != null) {
					JSONObject prePostObj = new JSONObject();
					prePostObj.put("createdOn", item.prePost.createdOn);
					prePostObj.put("author", item.prePost.author);
					prePostObj.put("message", item.prePost.message);
					object.put("prePost", prePostObj);
				}
				array.put(object);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array.toString();
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

	public HashMap<String, Object> parseJsonToMap(String json) {
		try {
			return hashon.fromJson(json);
		} catch (Throwable t) {
			return null;
		}
	}
}
