package com.mob.bbssdk.gui.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mob.bbssdk.gui.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class FileHelper {
	StorageFile storageFile;
	String strFilePath;

	public FileHelper(StorageFile file) {
		this.storageFile = file;
	}

	protected boolean createIfnotExist() {
		strFilePath = storageFile.getFilePath();
		return FileUtils.createOrExistsFile(strFilePath);
	}

	public void saveBitmap(Bitmap bitmap) {
		createIfnotExist();
		FileOutputStream fileoutstream = null;
		try {
			fileoutstream = new FileOutputStream(new File(strFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileoutstream);
		try {
			fileoutstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveObj(Object obj) {
		createIfnotExist();
		FileOutputStream fileoutstream = null;
		try {
			fileoutstream = new FileOutputStream(new File(strFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		ObjectOutputStream objoutstream = null;
		try {
			objoutstream = new ObjectOutputStream(fileoutstream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			objoutstream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			objoutstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileoutstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap readBitmap() {
		if (createIfnotExist()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(strFilePath, options);
		return bitmap;
	}

	public <T> T readObj() throws ClassCastException {
		createIfnotExist();
		FileInputStream fileinputstream = null;
		try {
			fileinputstream = new FileInputStream(strFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		ObjectInputStream objinputstream = null;
		try {
			objinputstream = new ObjectInputStream(fileinputstream);
		} catch (IOException e) {
//			e.printStackTrace();
			return null;
		}

		if (objinputstream == null) {
			return null;
		} else {
			try {
				try {
					T t = (T) objinputstream.readObject();
					return t;
					//doesn't know why this catch doesn't hit the ClassCaseException.
				} catch (ClassCastException e) {
					e.printStackTrace();
					clearContent();
					return null;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					objinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fileinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void clearContent() {
		PrintWriter pw = null;
		if (!FileUtils.isFileExists(strFilePath)) {
			return;
		}
		try {
			pw = new PrintWriter(strFilePath);
			pw.print("");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
