package com.mob.bbssdk.gui.helper;


import java.io.File;
import java.io.IOException;

public enum StorageFile {
	UserAvatar("UserAvatar.obj"),
	AccountCache("AccountCache.obj"),
	SearchHistory("SearchHistory.obj"),
	ThreadReadedHistory("ThreadReadedHistory.obj");

	private String strFilePath;

	StorageFile(String filename) {
		this.strFilePath = FilePath.FILE_FOLDER + filename;

		File file = new File(this.strFilePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFilePath() {
		return strFilePath;
	}
}
