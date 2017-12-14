package com.mob.bbssdk.gui.helper;


import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.utils.FileUtils;
import com.mob.bbssdk.model.User;

import java.io.File;
import java.io.IOException;

public enum StorageFile {
	UserAvatar("UserAvatar.obj"),
	AccountCache("AccountCache.obj"),
	SearchHistory("SearchHistory.obj"),
	ThreadReadedHistory("ThreadReadedHistory.obj");

	private String strFileName;
	private String strFilePath;
	StorageFile(String filename) {
		this.strFileName = filename;
		this.strFilePath = FilePath.FILE_DATA_FOLDER + this.strFileName;
		FileUtils.createOrExistsFile(strFilePath);
	}

	public String getFilePath() {
		//Return relative avatar file path while getting UserAvatar file path.
		if(this == UserAvatar) {
			User user = GUIManager.getCurrentUser();
			String filename = "";
			if (user != null) {
				filename =  "UserAvatar" + user.uid + ".obj";
			} else {
				filename = this.strFileName;
			}
			return FilePath.FILE_DATA_FOLDER + filename;
		}
		return strFilePath;
	}
}
