package com.mob.bbssdk.gui.pages;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;

import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;

/**
 * 可以选择图片或者拍照的页面
 */
public abstract class SelectPicBasePageWithTitle extends BasePageWithTitle {
	public static final int REQUEST_PICK = 9500;
	public static final int REQUEST_CAPTURE = 9501;

	private File filePhotoDir;
	private Uri outputFileUriByCamera;

	private boolean crop = false;
	private int cropSquareWidth = 0;

	@Override
	protected void onViewCreated(View contentView) {
		filePhotoDir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "bbs" + File.separator);
	}

	private String getPhotoFileName() {
		return "" + System.currentTimeMillis() + ".jpg";
	}

	protected void choose() {
		choose(false, 0, 0);
	}

	protected void choose(boolean crop, int cropWidth, int cropHeight) {
		this.crop = crop;
		this.cropSquareWidth = cropWidth;
		if (cropHeight > cropWidth) {
			cropSquareWidth = cropHeight;
		}
		String[] strarray = getContext().getResources().getStringArray(
				ResHelper.getStringArrayRes(getContext(), "bbs_chooserpic_items"));
		final DefaultChooserDialog dialog = new DefaultChooserDialog(getContext(), strarray);
		dialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				if (position == 0) {
					dialog.dismiss();
				} else if (position == 2) { //从相册选择
					final Intent galleryIntent = new Intent(Intent.ACTION_PICK);
					galleryIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(galleryIntent, REQUEST_PICK);
					dialog.dismiss();
				} else if (position == 1) {
					if (!filePhotoDir.exists()) {
						filePhotoDir.mkdir();
					}
					final String fname = getPhotoFileName();
					final File sdImageMainDirectory = new File(filePhotoDir, fname);
					outputFileUriByCamera = Uri.fromFile(sdImageMainDirectory);

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUriByCamera); // set the image file name
					// start the image capture Intent
					startActivityForResult(intent, REQUEST_CAPTURE);
					dialog.dismiss();
				} else {
					dialog.dismiss();
				}
			}
		});
		dialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_PICK) {
				Uri uri = intent.getData();
				if (crop) {
					cropSquareBitmap(getRealPathFromURI(intent), cropSquareWidth);
				} else {
					onPicGot(uri, getRealPathFromURI(intent));
				}
			} else if (requestCode == REQUEST_CAPTURE) {
				if (crop) {
					cropSquareBitmap(outputFileUriByCamera.getPath(), cropSquareWidth);
				} else {
					onPicGot(outputFileUriByCamera, outputFileUriByCamera.getPath());
				}
			}
		}
	}

	private void cropSquareBitmap(final String imgPath, final int sideWidth) {
		new Thread() {
			public void run() {
				try {
					Bitmap bitmap = BitmapHelper.getBitmapByCompressSize(imgPath, sideWidth, sideWidth);
					int bWidth = bitmap.getWidth();
					int bHeight = bitmap.getHeight();
					Bitmap scaledBitmap;
					if (bWidth < sideWidth && bHeight < sideWidth) {
						if (bWidth > bHeight) {
							bWidth = bWidth * sideWidth / bHeight;
							bHeight = sideWidth;
						} else {
							bHeight = bHeight * sideWidth / bWidth;
							bWidth = sideWidth;
						}
						scaledBitmap = Bitmap.createScaledBitmap(bitmap, bWidth, bHeight, false);
					} else if (bWidth <= sideWidth) {
						scaledBitmap = Bitmap.createScaledBitmap(bitmap, sideWidth, bHeight * sideWidth / bWidth, false);
					} else if (bHeight <= sideWidth) {
						scaledBitmap = Bitmap.createScaledBitmap(bitmap, bWidth * sideWidth / bHeight, sideWidth, false);
					} else {
						scaledBitmap = bitmap;
					}
					int scaledWidth = scaledBitmap.getWidth();
					int scaledHeight = scaledBitmap.getHeight();
					int x = 0;
					int y = 0;
					if (scaledWidth > sideWidth) {
						x = (scaledWidth - sideWidth) / 2;
					}
					if (scaledHeight > sideWidth) {
						y = (scaledHeight - sideWidth) / 2;
					}
					Bitmap cropBitmap = Bitmap.createBitmap(scaledBitmap, x, y, sideWidth, sideWidth);
					sendPicCrop(cropBitmap);
					return;
				} catch (Throwable t) {
					t.printStackTrace();
				}
				sendPicCrop(null);
			}
		}.start();
	}

	private void sendPicCrop(final Bitmap newBitmap) {
		UIHandler.sendEmptyMessage(0, new Handler.Callback() {
			public boolean handleMessage(Message msg) {
				onPicCrop(newBitmap);
				return false;
			}
		});
	}

	public String getRealPathFromURI(Intent intent) {
		try {
			Uri originalUri = intent.getData();
			if (originalUri.getScheme().equals("file")) {//如果是绝对路径，则直接获取;小米4图库中拍摄的照片即是这样的
				return originalUri.getPath();
			}
			String[] proj = {MediaStore.Images.Media.DATA};
			Cursor cursor = activity.managedQuery(originalUri, proj, null, null, null);
			int filepathindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(filepathindex);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	protected abstract void onPicGot(Uri source, String realpath);

	protected void onPicCrop(Bitmap bitmap) {

	}
}
