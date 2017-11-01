package com.mob.bbssdk.gui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mob.bbssdk.gui.utils.SendForumPostManager;
import com.mob.bbssdk.gui.views.EmojiPagerAdapter;
import com.mob.bbssdk.gui.views.EmojiTab;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.ReplyInputEditText;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReplyEditorPopWindow implements View.OnClickListener {
	private Context context;
	private PopupWindow popupWindow;
	private InputMethodManager imm;
	private OnConfirmClickListener onConfirmClickListener;
	private OnImgAddClickListener onImgAddClickListener;
	private ReplyInputEditText replyInputEditText;
	private GridView gvImg;
	private ViewPager emojiViewPager;
	private TextView tvImgCount;
	private ImageView ivImg;
	private TextView tvSend;
	private View viewFiller;
	private ImageView imageViewAddEmoji;
	private ImageView imageShowKeyboard;
	private LinearLayout layoutBottomContainer;
	private LinearLayout layoutEmojiTab;
	private ImageView imageViewEmojiGeneral;
	private ImageView imageViewEmojiGrapeman;
	private ImageView imageViewEmojiCoolMonkey;

	private PopupWindow.OnDismissListener dismissListener;
	private boolean isImgOrEmojiClicked = false;
	private int gvHeight = 0;
	private List<String> imgList;
	private BaseAdapter adapter = null;
	private WarningDialog warningDialog;
	private EmojiPagerAdapter emojiPagerAdapter;

	public ReplyEditorPopWindow(Context context, OnConfirmClickListener listener, OnImgAddClickListener addlistener) {
		this.context = context;
		onConfirmClickListener = listener;
		onImgAddClickListener = addlistener;
		this.imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		init();
	}

	protected Context getContext() {
		return context;
	}

	protected View getContentView() {
		return null;
	}

	private void init() {
		View contentView = getContentView();
		if (contentView == null) {
			contentView = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_view_replyeditor"), null);
		}
		replyInputEditText = (ReplyInputEditText) contentView.findViewById(ResHelper.getIdRes(context, "replyInputEditText"));
		gvImg = (GridView) contentView.findViewById(ResHelper.getIdRes(context, "gvImg"));
		tvImgCount = (TextView) contentView.findViewById(ResHelper.getIdRes(context, "tvImgCount"));
		ivImg = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "ivImg"));
		tvSend = (TextView) contentView.findViewById(ResHelper.getIdRes(context, "tvSend"));
		viewFiller = contentView.findViewById(ResHelper.getIdRes(context, "viewFiller"));
		imageViewAddEmoji = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "imageViewAddEmoji"));
		imageShowKeyboard = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "imageShowKeyboard"));
		imageViewEmojiGeneral = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "imageViewEmojiGeneral"));
		imageViewEmojiGrapeman = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "imageViewEmojiGrapeman"));
		imageViewEmojiCoolMonkey = (ImageView) contentView.findViewById(ResHelper.getIdRes(context, "imageViewEmojiCoolMonkey"));
		emojiViewPager = (ViewPager) contentView.findViewById(ResHelper.getIdRes(context, "emojiViewPager"));
		layoutBottomContainer = (LinearLayout) contentView.findViewById(ResHelper.getIdRes(context, "layoutBottomContainer"));
		layoutEmojiTab = (LinearLayout) contentView.findViewById(ResHelper.getIdRes(context, "layoutEmojiTab"));

		imageShowKeyboard.setOnClickListener(this);
		imageViewEmojiGeneral.setOnClickListener(this);
		imageViewEmojiGrapeman.setOnClickListener(this);
		imageViewEmojiCoolMonkey.setOnClickListener(this);

		emojiPagerAdapter = new EmojiPagerAdapter(replyInputEditText);
		emojiViewPager.setAdapter(emojiPagerAdapter);
		emojiPagerAdapter.notifyDataSetChanged();
		emojiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				onEmojiPageSelected(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		viewFiller.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReplyEditorPopWindow.this.dismiss();
			}
		});

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
		imageViewAddEmoji.setOnClickListener(this);

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				gvImg.setVisibility(View.GONE);
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
		if (replyInputEditText != null && !TextUtils.isEmpty(content)) {
			replyInputEditText.setText(content);
		}
		if (imgList != null && imgList.size() > 0 && tvImgCount != null) {
			tvImgCount.setVisibility(View.VISIBLE);
			tvImgCount.setText(String.valueOf(imgList.size()));
		}
		replyInputEditText.setOnKeyPreImeListener(new ReplyInputEditText.KeyPreImeListener() {
			@Override
			public void OnKeyPreImeBack() {
				//当返回时直接关闭整个回复窗口
				ReplyEditorPopWindow.this.dismiss();
			}
		});
	}

	private void hideSoftInput() {
		if (imm == null) {
			return;
		}
		imm.hideSoftInputFromWindow(replyInputEditText.getWindowToken(), 0);
	}

	private void showSoftInput() {
		if (replyInputEditText == null) {
			return;
		}
		replyInputEditText.requestFocus();
		imm.showSoftInput(replyInputEditText, 0);
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
		if (replyInputEditText == null) {
			return null;
		}
		return replyInputEditText.getText().toString();
	}

	public void setBottomLayoutHeight(int height) {
		if (isImgOrEmojiClicked) {
			isImgOrEmojiClicked = false;
			return;
		}
		if (height > 0 && gvImg.getVisibility() == View.GONE) {
			gvHeight = height;
			ViewGroup.LayoutParams p = gvImg.getLayoutParams();
			p.height = height;
			layoutBottomContainer.setLayoutParams(p);
		} else if (height == 0 && gvImg.getVisibility() == View.VISIBLE) {
			dismiss();
		}
	}

	public void show(View v, String authorName) {
		if (isShowing()) {
			return;
		}
		if (replyInputEditText != null) {
			replyInputEditText.setHint(context.getResources().getString(ResHelper.getStringRes(context, "bbs_viewthreaddetail_btn_reply"))
					+ authorName);
		}
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				showSoftInput();
			}
		}, 500);
		layoutBottomContainer.setVisibility(View.INVISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				layoutBottomContainer.setVisibility(View.VISIBLE);
			}
		}, 800);
	}

	public void onClick(View v) {
		if (v == ivImg) {
			isImgOrEmojiClicked = true;
			hideSoftInput();
			if (imgList == null || imgList.size() == 0) {
				if (onImgAddClickListener != null) {
					onImgAddClickListener.onClick();
				}
			}
			layoutBottomContainer.setVisibility(View.VISIBLE);
			layoutEmojiTab.setVisibility(View.GONE);
			showImageGridView();
			emojiViewPager.setVisibility(View.GONE);
		} else if (v == tvSend) {
			String content = replyInputEditText.getText().toString();
			if (TextUtils.isEmpty(content) && (imgList == null || imgList.size() == 0)) {
				showWarningDialog(context.getString(ResHelper.getStringRes(context, "bbs_pagewritethread_tip_content_null")));
				return;
			}
			if (onConfirmClickListener != null) {
				onConfirmClickListener.onConfirm(replyInputEditText.getText().toString(), imgList);
			}
			hideSoftInput();
			dismiss();
		} else if (v == imageViewAddEmoji) {
			isImgOrEmojiClicked = true;
			hideSoftInput();
			layoutBottomContainer.setVisibility(View.VISIBLE);
			layoutEmojiTab.setVisibility(View.VISIBLE);
			gvImg.setVisibility(View.GONE);
			emojiViewPager.setVisibility(View.VISIBLE);
		} else if (v == imageShowKeyboard) {
			replyInputEditText.requestFocus();
			showSoftInput();
		} else if (v == imageViewEmojiGeneral) {
			clickOnEmojiTab(EmojiTab.General);
		} else if (v == imageViewEmojiCoolMonkey) {
			clickOnEmojiTab(EmojiTab.CoolMonkey);
		} else if (v == imageViewEmojiGrapeman) {
			clickOnEmojiTab(EmojiTab.Grapeman);
		}
	}

	protected void clickOnEmojiTab(EmojiTab tab) {
		if (tab == null) {
			return;
		}
		int selectedcolor = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_emoji_selected"));
		int unselectedcolor = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_emoji_unselected"));
		if (tab == EmojiTab.General) {
			emojiViewPager.setCurrentItem(0);
			imageViewEmojiGeneral.setBackgroundColor(selectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(unselectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(unselectedcolor);
		} else if (tab == EmojiTab.Grapeman) {
			emojiViewPager.setCurrentItem(1);
			imageViewEmojiGeneral.setBackgroundColor(unselectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(selectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(unselectedcolor);
		} else if (tab == EmojiTab.CoolMonkey) {
			emojiViewPager.setCurrentItem(2);
			imageViewEmojiGeneral.setBackgroundColor(unselectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(unselectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(selectedcolor);
		}
	}

	protected void onEmojiPageSelected(int position) {
		EmojiTab tab = EmojiTab.fromPosition(position);
		int selectedcolor = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_emoji_selected"));
		int unselectedcolor = getContext().getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_emoji_unselected"));
		if (tab == EmojiTab.General) {
			emojiViewPager.setCurrentItem(0);
			imageViewEmojiGeneral.setBackgroundColor(selectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(unselectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(unselectedcolor);
		} else if (tab == EmojiTab.Grapeman) {
			emojiViewPager.setCurrentItem(1);
			imageViewEmojiGeneral.setBackgroundColor(unselectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(selectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(unselectedcolor);
		} else if (tab == EmojiTab.CoolMonkey) {
			emojiViewPager.setCurrentItem(2);
			imageViewEmojiGeneral.setBackgroundColor(unselectedcolor);
			imageViewEmojiGrapeman.setBackgroundColor(unselectedcolor);
			imageViewEmojiCoolMonkey.setBackgroundColor(selectedcolor);
		}
	}

	private void showWarningDialog(String warningStr) {
		if (warningDialog == null) {
			warningDialog = new WarningDialog(context);
		}
		warningDialog.setWarningText(warningStr);
		warningDialog.show();
	}

	/**
	 * 回复成功后，需要清空上次编辑缓存
	 */
	public void resetUI() {
		if (replyInputEditText != null) {
			replyInputEditText.setText("");
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
		showImageGridView();
	}

	private void showImageGridView() {
		if (gvImg != null) {
			if (gvImg.getVisibility() == View.GONE) {
				gvImg.setVisibility(View.VISIBLE);
			}
			gvImg.setAdapter(initGvAdapter());
		}
	}

	protected Integer getAddPicImageId() {
		return null;
	}

	protected Integer getReplyAddPicLayoutId() {
		return null;
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
					Integer layout = getReplyAddPicLayoutId();
					if (layout == null) {
						layout = ResHelper.getLayoutRes(getContext(), "bbs_reply_addpic");
					}
					ViewGroup view;
					view = (ViewGroup) LayoutInflater.from(getContext()).inflate(layout, parent, false);
//					view = new RelativeLayout(context);
//					int padding = ResHelper.dipToPx(context, 10);
//					final int ivHeight = (gvHeight - padding * 2) / 2;
//					view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ivHeight));
//					final ImageView ivImg = new ImageView(context);
//					ivImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//					RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//							ViewGroup.LayoutParams.MATCH_PARENT);
//					rlp.bottomMargin = padding;
//					rlp.topMargin = ResHelper.dipToPx(context, 5);
//					rlp.rightMargin = padding;
//					view.addView(ivImg, rlp);
//					ImageView ivDelete = new ImageView(context);
//					ivDelete.setImageResource(ResHelper.getBitmapRes(context, "bbs_reply_addpic_remove"));
//					rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//					rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//					view.addView(ivDelete, rlp);

					int padding = ResHelper.dipToPx(context, 10);
					final int ivHeight = (gvHeight - padding * 2) / 2;
					view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ivHeight));
					final GlideImageView ivImg = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "ivImg"));
					ImageView ivDelete = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "ivDelete"));
					final String path = getItem(position);
					if (path == null) {
						ivDelete.setVisibility(View.GONE);
						ivImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						Integer id = getAddPicImageId();
						if (id == null) {
							id = ResHelper.getBitmapRes(context, "bbs_reply_addpicitem");
						}
						ivImg.setImageResource(id);
					} else {
						ivDelete.setVisibility(View.VISIBLE);
						Glide.with(context).load(path).into(ivImg);
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
						if (onImgAddClickListener != null) {
							onImgAddClickListener.onClick();
						}
					}
				}
			});
		} else {
			adapter.notifyDataSetChanged();
		}
		return adapter;
	}

	public interface OnImgAddClickListener {
		void onClick();
	}

	public interface OnConfirmClickListener {
		void onConfirm(String content, List<String> imgList);
	}
}
