package com.mob.bbssdk.gui.other.ums;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.dialog.ModelLoadingDialog;
import com.mob.bbssdk.gui.other.ErrorDialog;
import com.mob.bbssdk.gui.other.PhotoCropPage;
import com.mob.tools.gui.ScaledImageView;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PhotoCropPageAdapter extends DefaultThemePageAdapter<PhotoCropPage> {
	private static final double MAX_INPUT_BOUND_LEN = 1280;
	private Activity activityContext = null;
	private ScaledImageView ivPhoto;
	private Rect cropRect;
	private boolean saving;
	private ModelLoadingDialog modelLoadingDialog;

	public void onCreate(PhotoCropPage page, Activity activity) {
		super.onCreate(page, activity);
		this.activityContext = activity;
		initPage(activity);
		loadImage();
	}

	private void initPage(Activity activity) {
		LinearLayout llPage = new LinearLayout(activity);
		llPage.setOrientation(LinearLayout.VERTICAL);
		activity.setContentView(llPage);

		// image
		RelativeLayout rl = new RelativeLayout(activity);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.weight = 1;
		llPage.addView(rl, lp);

		ivPhoto = new ScaledImageView(activity);
		ivPhoto.setBackgroundColor(0xff000000);
		ivPhoto.setScaleType(ScaleType.MATRIX);
		RelativeLayout.LayoutParams lprl = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rl.addView(ivPhoto, lprl);

		View vCrop = new View(activity);
		vCrop.setId(View.generateViewId());
		int screenWidth = ResHelper.getScreenWidth(getPage().getContext());
		lprl = new RelativeLayout.LayoutParams(screenWidth, screenWidth);
		lprl.addRule(RelativeLayout.CENTER_IN_PARENT);
		rl.addView(vCrop, lprl);

		View v = new View(activity);
		v.setBackgroundColor(0x7f000000);
		lprl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lprl.addRule(RelativeLayout.ABOVE, vCrop.getId());
		rl.addView(v, lprl);

		v = new View(activity);
		v.setBackgroundColor(0xff8d8d8d);
		lprl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
		lprl.addRule(RelativeLayout.ABOVE, vCrop.getId());
		rl.addView(v, lprl);

		v = new View(activity);
		v.setBackgroundColor(0x7f000000);
		lprl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lprl.addRule(RelativeLayout.BELOW, vCrop.getId());
		rl.addView(v, lprl);

		v = new View(activity);
		v.setBackgroundColor(0xff8d8d8d);
		lprl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
		lprl.addRule(RelativeLayout.BELOW, vCrop.getId());
		rl.addView(v, lprl);

		// tool bar
		FrameLayout fl = new FrameLayout(activity);
		fl.setBackgroundColor(0xff141414);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, ResHelper.dipToPx(activity, 43));
		llPage.addView(fl, lp);

		TextView tv = new TextView(activity);
		int dp15 = ResHelper.dipToPx(activity, 15);
		tv.setPadding(dp15, 0, dp15, 0);
		tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		tv.setTextColor(0xffffffff);
		FrameLayout.LayoutParams lpfl = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		lpfl.gravity = Gravity.LEFT;
		tv.setText(ResHelper.getStringRes(activity, "umssdk_default_repick"));
		tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getPage().repick();
			}
		});
		fl.addView(tv, lpfl);

		tv = new TextView(activity);
		tv.setPadding(dp15, 0, dp15, 0);
		tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		tv.setTextColor(0xffe4554c);
		lpfl = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		lpfl.gravity = Gravity.RIGHT;
		tv.setText(ResHelper.getStringRes(activity, "umssdk_default_use_this_picture"));
		tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveImage();
			}
		});
		fl.addView(tv, lpfl);
	}

	private void loadImage() {
		new Thread() {
			public void run() {
				try {
					BitmapFactory.Options oriOpt = new BitmapFactory.Options();
					oriOpt.inJustDecodeBounds = true;
					String original = getPage().getPickResult();
					BitmapFactory.decodeFile(original, oriOpt);
					int oriMaxBound = oriOpt.outWidth > oriOpt.outHeight ? oriOpt.outWidth : oriOpt.outHeight;
					double sampleSize = oriMaxBound / MAX_INPUT_BOUND_LEN;
					Bitmap bitmap;
					if (sampleSize > 1) {
						Options bmOpt = new Options();
						bmOpt.inSampleSize = (int)(sampleSize + 0.5);
						bitmap = BitmapFactory.decodeFile(original, bmOpt);
					} else {
						bitmap = BitmapFactory.decodeFile(original);
					}

					Message msg = new Message();
					msg.obj = bitmap;
					UIHandler.sendMessage(msg, new Callback() {
						public boolean handleMessage(Message msg) {
							Bitmap bitmap = (Bitmap) msg.obj;
							if (bitmap == null || bitmap.isRecycled()) {
								Context cxt = getPage().getContext();
								ErrorDialog.Builder builder = new ErrorDialog.Builder(cxt, getPage().getTheme());
								int resId = ResHelper.getStringRes(cxt, "umssdk_default_pick_image");
								builder.setTitle(cxt.getString(resId));
								resId = ResHelper.getStringRes(cxt, "umssdk_default_pick_image_failed");
								builder.setMessage(cxt.getString(resId));
								builder.setOnDismissListener(new OnDismissListener() {
									public void onDismiss(DialogInterface dialog) {
										finish();
									}
								});
								builder.show();
							} else {
								getCropRect();
								ivPhoto.setBitmap(bitmap);
							}
							return false;
						}
					});
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}.start();
	}

	private void getCropRect() {
		int screenWidth = ResHelper.getScreenWidth(getPage().getContext());
		int top = (ivPhoto.getHeight() - screenWidth) / 2;
		int bottom = top + screenWidth;
		cropRect = new Rect(0, top, screenWidth, bottom);
	}

	protected void showLoadingDialog() {
		if (modelLoadingDialog != null && modelLoadingDialog.isShowing()) {
			return;
		}
		modelLoadingDialog = new ModelLoadingDialog(activityContext);
		modelLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if(modelLoadingDialog == null) {
			return;
		}
		modelLoadingDialog.dismiss();
	}

	private void saveImage() {
		if (saving) {
			return;
		}
		showLoadingDialog();

		saving = true;
		new Thread() {
			public void run() {
				try {
					Bitmap bmOrig = ivPhoto.getCropedBitmap(cropRect);
					Bitmap bm = Bitmap.createScaledBitmap(bmOrig, 2000, 2000, true);
					bmOrig.recycle();

					File file = new File(getPage().getOutput());
					File folder = file.getParentFile();
					if (!folder.exists()) {
						folder.mkdirs();
					}
					if (file.exists()) {
						file.delete();
					}
					file.createNewFile();

					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
					bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
					bos.flush();
					bos.close();
					bm.recycle();

					uploadImage();
				} catch (final Throwable t) {
//					t.printStackTrace();
					UIHandler.sendEmptyMessage(0, new Callback() {
						public boolean handleMessage(Message msg) {
							saving = false;
							Context cxt = getPage().getContext();
							ErrorDialog.Builder builder = new ErrorDialog.Builder(cxt, getPage().getTheme());
							int resId = ResHelper.getStringRes(cxt, "umssdk_default_pick_image");
							builder.setTitle(cxt.getString(resId));
							resId = ResHelper.getStringRes(cxt, "umssdk_default_save_image_failed");
							builder.setMessage(cxt.getString(resId));
							builder.setThrowable(t);
							builder.show();
							return false;
						}
					});
				}
			}
		}.start();
	}

	private void uploadImage() {
		final String userAvatar = getPage().getOutput();
		saving = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "0");
		ArrayList<String> list = new ArrayList<String>();
		list.add(userAvatar);
		map.put("avatar", list);
		getPage().setResult(map);
		finish();
	}

}
