package com.mob.bbssdk.gui.utils;

import android.text.TextUtils;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.tools.utils.BitmapHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UploadImgManager {
	private static final int MAX_CACHED_SIZE = 20;
	private static LinkedHashMap<String, String> uploadedImages;

	public List<String> failedImages = null;
	public Map<String, String> successImages = null;

	static {
		uploadedImages = new LinkedHashMap<String, String>() {
			protected boolean removeEldestEntry(Entry<String, String> eldest) {
				return size() > MAX_CACHED_SIZE;
			}
		};
	}

	public UploadImgManager() {
		failedImages = new ArrayList<String>();
		successImages = new HashMap<String, String>();
	}

	public void uploadImg(String[] imgList) {
		for (final String item : imgList) {
			//如果已经上传过的图片，则直接添加
			final String tmpItem;
			if (item.startsWith("file:///")) {
				tmpItem = item.substring(7);
			} else {
				tmpItem = item;
			}
			String imgUrl = uploadedImages.get(tmpItem);
			if (!TextUtils.isEmpty(imgUrl)) {
				successImages.put(item, imgUrl);
			} else {
				String compressedFilePath;
				try {
					compressedFilePath = BitmapHelper.saveBitmapByCompress(tmpItem, 1280, 720, 70);
				} catch (Throwable t) {
					t.printStackTrace();
					compressedFilePath = tmpItem;
				}
				final String filePath = compressedFilePath;
				BBSSDK.getApi(ForumAPI.class).uploadImage(filePath, true, new APICallback<String>() {
					public void onSuccess(API api, int action, String result) {
						//上传成功后，删除临时文件
						new File(filePath).delete();
						if (TextUtils.isEmpty(result)) {
							failedImages.add(item);
							return;
						}
						//保存已经上传的图片
						uploadedImages.put(tmpItem, result);
						successImages.put(item, result);
					}

					public void onError(API api, int action, int errorCode, Throwable details) {
						new File(filePath).delete();
						failedImages.add(item);
					}
				});
			}
		}
	}
}
