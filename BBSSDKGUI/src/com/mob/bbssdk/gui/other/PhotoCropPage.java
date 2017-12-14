package com.mob.bbssdk.gui.other;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.jimu.gui.Page;
import com.mob.bbssdk.gui.jimu.gui.Theme;
import com.mob.bbssdk.gui.other.ImagePicker.OnImageGotListener;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PhotoCropPage extends Page<PhotoCropPage> {
	private static final int REQ_PICK_IMAGE_FROM_CAMERA = 1;
	private static final int REQ_PICK_IMAGE_FROM_ALBUM = 2;

	private boolean cameraType;
	private String output;
	private String pickResult;

	public PhotoCropPage(Theme theme) {
		super(theme);
	}

	public void showForCamera(OnImageGotListener listener) {
		setListenerAndShow(true, listener);
	}

	public void showForAlbum(OnImageGotListener listener) {
		setListenerAndShow(false, listener);
	}

	private void setListenerAndShow(final boolean cameraType, final OnImageGotListener listener) {
		boolean permissionGranted = false;
		try {
			permissionGranted = DeviceHelper.getInstance(MobSDK.getContext()).checkPermission(
					"android.permission.WRITE_EXTERNAL_STORAGE");
		} catch (Throwable t) {
//			t.printStackTrace();
		}

		final Runnable rGranted = new Runnable() {
			public void run() {
				PhotoCropPage.this.cameraType = cameraType;
				showForResult(MobSDK.getContext(), null, new FakeActivity() {
					public void onResult(HashMap<String, Object> data) {
						if (listener != null && data != null) {
							String photoId = (String) data.get("id");
							Object urls = data.get("avatar");
							if (urls != null && urls instanceof ArrayList) {
								@SuppressWarnings("unchecked")
								ArrayList<String> list = (ArrayList<String>) urls;
								String[] photoUrl = list.toArray(new String[list.size()]);
								listener.onOmageGot(photoId, photoUrl);
							}
						}
					}
				});
			}
		};

		if (permissionGranted) {
			rGranted.run();
		} else if (Build.VERSION.SDK_INT >= 23) {
			FakeActivity page = new FakeActivity() {
				public void onCreate() {
					requestPermissions(new String[]{
							"android.permission.WRITE_EXTERNAL_STORAGE"
					}, 1);
				}

				public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
					if (grantResults != null && grantResults.length >= 1 && grantResults[0] != -1) {
						rGranted.run();
					}
					finish();
				}
			};
			page.show(MobSDK.getContext(), null);
		}
	}

	public void onCreate() {
		repick();
	}

	public void repick() {
		pickResult = null;
		output = (ResHelper.getCachePath(activity, "images") + System.currentTimeMillis() + ".jpg");
		if (cameraType) {
			Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
			Uri uri;
			if (Build.VERSION.SDK_INT >= 24) {
				ContentValues values = new ContentValues(1);
				values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
				uri = activity.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				output = ResHelper.contentUriToPath(activity, uri);
			} else {
				uri = Uri.fromFile(new File(output));
			}
			i.putExtra("output", uri);
			i.putExtra("android.intent.extra.videoQuality", 1);
			startActivityForResult(i, REQ_PICK_IMAGE_FROM_CAMERA);
		} else {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setFlags(0x00080000); // 0x00080000 == FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET或者FLAG_ACTIVITY_NEW_DOCUMENT
			intent.setType("image/*");
			startActivityForResult(intent, REQ_PICK_IMAGE_FROM_ALBUM);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_PICK_IMAGE_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
			afterCamera(data);
		} else if (requestCode == REQ_PICK_IMAGE_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
			afterAlbum(data);
		} else {
			finish();
		}
	}

	private void afterCamera(Intent data) {
		String tmpOutput = this.output;
		if (tmpOutput == null || !new File(tmpOutput).exists()) {
			if (data != null && data.getData() != null) {
				tmpOutput = ResHelper.contentUriToPath(activity, data.getData());
				if (tmpOutput != null) {
					pickResult = tmpOutput;
					onCreateOfSuper();
				} else {
					finish();
				}
			}
		} else {
			pickResult = tmpOutput;
			onCreateOfSuper();
		}
	}

	private void afterAlbum(Intent data) {
		if (data != null) {
			String path = ResHelper.contentUriToPath(activity, data.getData());
			if (path != null && new File(path).exists()) {
				pickResult = path;
				onCreateOfSuper();
				return;
			}
		}

		ErrorDialog.Builder builder = new ErrorDialog.Builder(getContext(), getTheme());
		int resId = ResHelper.getStringRes(getContext(), "umssdk_operation_faield");
		builder.setTitle(getContext().getString(resId));
		resId = ResHelper.getStringRes(getContext(), "umssdk_image_not_exists");
		builder.setMessage(getContext().getString(resId));
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}

	private void onCreateOfSuper() {
		requestPortraitOrientation();
		super.onCreate();
	}

	public String getPickResult() {
		return pickResult;
	}

	public String getOutput() {
		return output;
	}

}
