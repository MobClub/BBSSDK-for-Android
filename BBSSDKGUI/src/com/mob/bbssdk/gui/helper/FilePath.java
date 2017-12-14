package com.mob.bbssdk.gui.helper;


import android.content.ContextWrapper;
import android.os.Environment;

import com.mob.MobSDK;

import java.io.File;

public class FilePath {
	public static final String FILE_FOLDER = Environment.getExternalStorageDirectory()
			+ File.separator + "bbs" + File.separator;
	public static final String FILE_DATA_FOLDER = MobSDK.getContext().getFilesDir() + File.separator;

	static {
		File file = new File(FILE_DATA_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
