package com.mob.bbssdk.gui.helper;


import android.os.Environment;

import java.io.File;

public class FilePath {
	public static final String FILE_FOLDER = Environment.getExternalStorageDirectory()
			+ File.separator + "bbs" + File.separator;
	public static final String FILE_DATA_FOLDER = Environment.getDataDirectory().getAbsolutePath() + File.separator;

	static {
		File file = new File(FILE_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
