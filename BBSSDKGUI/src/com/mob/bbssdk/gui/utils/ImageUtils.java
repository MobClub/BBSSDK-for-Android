package com.mob.bbssdk.gui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageUtils {
	public static void savePhotoToNative(Context context, String filePath) {
		File originFile = new File(filePath);
		if (originFile.exists()) {
			try {
				String originFileName = originFile.getName();
				int suffixIndex = originFileName.indexOf(".");
				String fileName = "pic_bbs_";
				if (suffixIndex > 0) {
					fileName += originFileName.substring(0, suffixIndex) + ".jpg";
				} else {
					fileName += originFileName + ".jpg";
				}
				String path = ResHelper.getCachePath(context.getApplicationContext(), "pictures");
				File file = new File(path, fileName);
				FileOutputStream fos = null;
				FileInputStream is = null;
				try {
					is = new FileInputStream(originFile);
					fos = new FileOutputStream(file);
					byte[] tmp = new byte[256];
					int len = is.read(tmp);
					while (len > 0) {
						fos.write(tmp, 0, len);
						len = is.read(tmp);
					}
					fos.flush();
				} catch (Throwable t) {
					if (file.exists()) {
						file.delete();
					}
				} finally {
					try {
						fos.close();
						is.close();
					} catch (Throwable t) {
					}
				}
				if (file.exists()) {
					//通知系统扫描图片
					context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
					Toast.makeText(context.getApplicationContext(),
							ResHelper.getStringRes(context, "bbs_toast_save_pic_success"), Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		Toast.makeText(context.getApplicationContext(), ResHelper.getStringRes(context, "bbs_toast_save_pic_failed"), Toast.LENGTH_SHORT).show();
	}
}
