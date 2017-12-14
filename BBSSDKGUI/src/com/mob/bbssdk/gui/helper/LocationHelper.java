package com.mob.bbssdk.gui.helper;


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LocationHelper {
	private static final String TAG = "LocationHelper";

	private List<LocationDBHelper.Item> listProvinces = new ArrayList<LocationDBHelper.Item>();

	public List<LocationDBHelper.Item> getListProvinces() {
		return listProvinces;
	}

	public LocationHelper(Context context) {
		try {
			saveDBFromAssetsToData(context, "district-full.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LocationDBHelper locationDbHelper = new LocationDBHelper(context, "district-full.db");
		listProvinces = locationDbHelper.getAllProvince();
	}

	private static void saveDBFromAssetsToData(Context context, String dbfilename) throws Exception {
		File newfile = context.getDatabasePath(dbfilename);
		if (newfile.exists()) {
			return;
		}
		newfile.createNewFile();
		InputStream initialStream = context.getAssets().open("databases/" + dbfilename);
		OutputStream outStream = new FileOutputStream(newfile);
		byte[] buffer = new byte[8 * 1024];
		int bytesRead;
		while ((bytesRead = initialStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		initialStream.close();
		outStream.close();
	}
}
