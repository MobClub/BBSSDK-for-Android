package com.mob.bbssdk.gui.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.bbssdk.gui.utils.SendForumPostManager;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReplyEditorPopWindow implements View.OnClickListener {
	private HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();
	private Context context;
	private PopupWindow popupWindow;
	private InputMethodManager imm;
	private OnConfirmClickListener onConfirmClickListener;
	private EditText etContent;
	private GridView gvImg;
	private TextView tvImgCount;
	private ImageView ivImg;
	private TextView tvSend;

	private PopupWindow.OnDismissListener dismissListener;
	private boolean isImgClick = false;
	private int gvHeight = 0;
	private List<String> imgList;
	private BaseAdapter adapter = null;
	private WarningDialog warningDialog;

	public ReplyEditorPopWindow(Context context, OnConfirmClickListener listener) {
		this.context = context;
		onConfirmClickListener = listener;
		this.imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		init();
	}

	private void init() {
		View contentView = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_reply_editor"), null);
		etContent = (EditText) contentView.findViewById(ResHelper.getIdRes(context, "etContent"));
		gvImg = (GridView) contentView.findViewById(ResHelper.getIdRes(context, "gvImg"));
		tvImgCount = (TextView) contentView.findViewById(ResHelper.getIdRes(context, "tvImgCount"));
		ivImg = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "ivImg"));
		tvSend = (TextView) contentView.findViewById(ResHelper.getIdRes(context, "tvSend"));

		popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0xa1000000));
		popupWindow.setAnimationStyle(ResHelper.getStyleRes(context, "BBS_AnimUpDown"));

		ivImg.setOnClickListener(this);
		tvSend.setOnClickListener(this);
		tvImgCount.setVisibility(View.GONE);

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				gvImg.setVisibility(View.GONE);
				bitmapCache.clear();
				if (dismissListener != null) {
					dismissListener.onDismiss();
				}
			}
		});

		//恢复缓存数据
		HashMap<String, Object> map = SendForumPostManager.getPostCache(context);
		String content = ResHelper.forceCast(map.get("message"));
		String[] tempImgArray = ResHelper.forceCast(map.get("imgList"));
		if (tempImgArray != null && tempImgArray.length > 0) {
			imgList = new ArrayList<String>(Arrays.asList(tempImgArray));
		}
		if (etContent != null && !TextUtils.isEmpty(content)) {
			etContent.setText(content);
		}
		if (imgList != null && imgList.size() > 0 && tvImgCount != null) {
			tvImgCount.setVisibility(View.VISIBLE);
			tvImgCount.setText(String.valueOf(imgList.size()));
		}
	}

	private void hideSoftInput() {
		if (imm == null) {
			return;
		}
		imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
	}

	private void showSoftInput() {
		if (etContent == null) {
			return;
		}
		etContent.requestFocus();
		imm.showSoftInput(etContent, 0);
	}

	public void dismiss() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

	public boolean isShowing() {
		if (popupWindow == null) {
			return false;
		}
		return popupWindow.isShowing();
	}

	public void setOnDismissListener(PopupWindow.OnDismissListener dismissListener) {
		this.dismissListener = dismissListener;
	}

	public String[] getImgList() {
		if (imgList == null || imgList.isEmpty()) {
			return null;
		}
		int size = imgList.size();
		return imgList.toArray(new String[size]);
	}

	public String getEditorContent() {
		if (etContent == null) {
			return null;
		}
		return etContent.getText().toString();
	}

	public void setBottomLayoutHeight(int height) {
		if (isImgClick) {
			isImgClick = false;
			return;
		}
		if (height > 0 && gvImg.getVisibility() == View.GONE) {
			gvHeight = height;
			ViewGroup.LayoutParams p = gvImg.getLayoutParams();
			p.height = height;
			gvImg.setLayoutParams(p);
			int padding = ResHelper.dipToPx(context, 10);
			gvImg.setPadding(padding, padding, 0, padding);
			gvImg.setVisibility(View.VISIBLE);
		} else if (height == 0 && gvImg.getVisibility() == View.VISIBLE) {
			gvImg.setVisibility(View.GONE);
			dismiss();
		}
	}

	public void show(View v, String authorName) {
		if (isShowing()) {
			return;
		}
		if (etContent != null) {
			etContent.setHint(context.getResources().getString(ResHelper.getStringRes(context, "bbs_viewthreaddetail_btn_reply"))
					+ authorName);
		}
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				showSoftInput();
			}
		}, 500);
	}

	public void onClick(View v) {
		if (v == ivImg) {
			isImgClick = true;
			hideSoftInput();
			if (imgList == null || imgList.size() == 0) {
				onImgAddClick();
			}
			updateImgListView();
		} else if (v == tvSend) {
			String content = etContent.getText().toString();
			if (TextUtils.isEmpty(content) && (imgList == null || imgList.size() == 0)) {
				showWarningDialog(context.getString(ResHelper.getStringRes(context, "bbs_pagewritethread_tip_content_null")));
				return;
			}
			if (onConfirmClickListener != null) {
				onConfirmClickListener.onConfirm(etContent.getText().toString(), imgList);
			}
			hideSoftInput();
			dismiss();
		}
	}

	private void showWarningDialog(String warningStr) {
		if (warningDialog == null) {
			warningDialog = new WarningDialog(context);
		}
		warningDialog.setWarningText(warningStr);
		warningDialog.show();
	}

	protected void onImgAddClick() {

	}

	/**
	 * 回复成功后，需要清空上次编辑缓存
	 */
	public void resetUI() {
		if (etContent != null) {
			etContent.setText("");
		}
		if (tvImgCount != null) {
			tvImgCount.setVisibility(View.GONE);
		}
		if (imgList != null) {
			imgList.clear();
		}
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public void setSelectedPicPath(String path) {
		if (imgList == null) {
			imgList = new ArrayList<String>();
		}
		imgList.add(path);
		if (imgList.size() > 0 && tvImgCount != null) {
			tvImgCount.setVisibility(View.VISIBLE);
			tvImgCount.setText(String.valueOf(imgList.size()));
		}
		updateImgListView();
	}

	private void updateImgListView() {
		if (gvImg != null) {
			if (gvImg.getVisibility() == View.GONE) {
				gvImg.setVisibility(View.VISIBLE);
			}
			gvImg.setAdapter(initGvAdapter());
		}
	}

	private BaseAdapter initGvAdapter() {
		if (adapter == null) {
			adapter = new BaseAdapter() {
				public int getCount() {
					if (imgList == null) {
						return 1;
					}
					int imgSize = imgList.size();
					if (imgSize < 8) {
						return imgSize + 1;
					}
					return 8;
				}

				public String getItem(int position) {
					return (imgList == null || position >= imgList.size()) ? null : imgList.get(position);
				}

				public long getItemId(int position) {
					return position;
				}

				public View getView(final int position, View convertView, ViewGroup parent) {
					RelativeLayout view;
					view = new RelativeLayout(context);
					int padding = ResHelper.dipToPx(context, 10);
					final int ivHeight = (gvHeight - padding * 2) / 2;
					view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ivHeight));
					final ImageView ivImg = new ImageView(context);
					ivImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
					RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT);
					rlp.bottomMargin = padding;
					rlp.topMargin = ResHelper.dipToPx(context, 5);
					rlp.rightMargin = padding;
					view.addView(ivImg, rlp);
					ImageView ivDelete = new ImageView(context);
					ivDelete.setImageResource(ResHelper.getBitmapRes(context, "bbs_reply_addpic_remove"));
					rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
					view.addView(ivDelete, rlp);

					final String path = getItem(position);
					if (path == null) {
						ivDelete.setVisibility(View.GONE);
						ivImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
						ivImg.setImageResource(ResHelper.getBitmapRes(context, "bbs_reply_addpicitem"));
					} else {
						ivDelete.setVisibility(View.VISIBLE);
						Bitmap cachedBitmap = bitmapCache.get(path);
						if (cachedBitmap != null) {
							ivImg.setImageBitmap(cachedBitmap);
						} else {
							try {
								new Thread() {
									public void run() {
										try {
											final Bitmap bitmap = BitmapHelper.getBitmapByCompressQuality(path, ivHeight, ivHeight, 70, 0);
											bitmapCache.put(path, bitmap);
											UIHandler.sendEmptyMessage(0, new Handler.Callback() {
												public boolean handleMessage(Message msg) {
													ivImg.setImageBitmap(bitmap);
													return false;
												}
											});
										} catch (Throwable t) {
											t.printStackTrace();
										}
									}
								}.start();
							} catch (Throwable t) {
								t.printStackTrace();
							}
						}
					}

					ivDelete.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (imgList != null && position < imgList.size()) {
								imgList.remove(position);
								if (tvImgCount != null) {
									if (imgList.size() > 0) {
										tvImgCount.setText(String.valueOf(imgList.size()));
									} else {
										tvImgCount.setVisibility(View.GONE);
									}
								}
							}
							notifyDataSetChanged();
						}
					});

					return view;
				}
			};
			gvImg.setSelector(new ColorDrawable(0x00000000));
			gvImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Object object = adapter.getItem(position);
					if (object == null) {
						onImgAddClick();
					}
				}
			});
		} else {
			adapter.notifyDataSetChanged();
		}
		return adapter;
	}

	public interface OnConfirmClickListener {
		void onConfirm(String content, List<String> imgList);
	}
}
