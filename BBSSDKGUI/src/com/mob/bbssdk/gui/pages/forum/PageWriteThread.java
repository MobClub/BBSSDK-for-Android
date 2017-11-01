package com.mob.bbssdk.gui.pages.forum;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.dialog.WarningDialog;
import com.mob.bbssdk.gui.pages.SelectPicBasePageWithTitle;
import com.mob.bbssdk.gui.utils.SendForumThreadManager;
import com.mob.bbssdk.gui.views.EmojiPagerAdapter;
import com.mob.bbssdk.gui.views.EmojiTab;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.RichEditor;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.model.ForumForum;
import com.mob.bbssdk.model.User;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 发帖界面
 */
public class PageWriteThread extends SelectPicBasePageWithTitle implements View.OnClickListener {
	private GlideImageView aivAvatar;
	private TextView textViewTitle;
	protected TextView textViewChooseCat;
	private EditText editTextTitle;
	private RichEditor richEditor;
	private ImageView toggleBold;
	private ImageView toggleItalic;
	private ImageView toggleStrikeThrough;
	private ImageView toggleAddPic;
	private ImageView toggleHeader1;
	private ImageView toggleHeader2;
	private ImageView toggleHeader3;
	private ImageView toggleHeader4;
	private ImageView toggleQuote;
	private ImageView imageViewKeyboard;
	private ImageView imageViewAddEmoji;
	private ViewGroup layoutEmojiContainer;
	private View llEditorBar;
	private EmojiPagerAdapter emojiPagerAdapter;
	private ViewPager emojiViewPager;
	private Handler mHandler = new Handler();
	private ImageView imageViewEmojiGeneral;
	private ImageView imageViewEmojiGrapeman;
	private ImageView imageViewEmojiCoolMonkey;

	private View.OnClickListener toggleListener;
	protected ForumForum selectedForum = null;

	private WarningDialog warningDialog;
	private BroadcastReceiver sendThreadReceiver;
	InputMethodManager inputMethodManager;
	private boolean isKeyboardShown = false;

	/**
	 * 设置论坛版块
	 *
	 * @param forum 论坛版块
	 */
	public void setForum(ForumForum forum) {
		selectedForum = forum;
	}

	protected View onCreateContentView(final Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_forum_writethread"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View view) {
		super.onViewCreated(view);
		final Context context = getContext();
		titleBar.setTitle(getStringRes("bbs_pagewritepost_title"));
		titleBar.setTvLeft(getStringRes("bbs_pagewritepost_title_left"));
		titleBar.setTvRight(getStringRes("bbs_pagewritepost_title_right"));
		titleBar.getRightTextView().setTextColor(getContext().getResources().getColor(getColorId("bbs_blue")));
		aivAvatar = (GlideImageView) view.findViewById(getIdRes("bbs_writepost_aivAvatar"));
		int avatarSize = ResHelper.dipToPx(getContext(), 25);
		aivAvatar.setExecuteRound(avatarSize / 2);
		textViewTitle = (TextView) view.findViewById(getIdRes("bbs_writepost_textViewTitle"));
		textViewChooseCat = (TextView) view.findViewById(getIdRes("bbs_writepost_textViewChooseCat"));
		editTextTitle = (EditText) view.findViewById(getIdRes("bbs_writepost_editTextTitle"));
		richEditor = (RichEditor) view.findViewById(getIdRes("richEditor"));
		toggleBold = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleBold"));
		toggleItalic = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleItalic"));
		toggleStrikeThrough = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleDelline"));
		toggleAddPic = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleAddPic"));
		toggleHeader1 = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleHeader1"));
		toggleHeader2 = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleHeader2"));
		toggleHeader3 = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleHeader3"));
		toggleHeader4 = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleHeader4"));
		toggleQuote = (ImageView) view.findViewById(getIdRes("bbs_writepost_toggleQuote"));
		imageViewAddEmoji = (ImageView) view.findViewById(getIdRes("imageViewAddEmoji"));
		layoutEmojiContainer = (ViewGroup) view.findViewById(getIdRes("layoutEmojiContainer"));
		layoutEmojiContainer.setVisibility(View.GONE);
		llEditorBar = view.findViewById(getIdRes("llEditorBar"));

		imageViewEmojiGeneral = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewEmojiGeneral"));
		imageViewEmojiGrapeman = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewEmojiGrapeman"));
		imageViewEmojiCoolMonkey = (ImageView) view.findViewById(ResHelper.getIdRes(context, "imageViewEmojiCoolMonkey"));
		imageViewEmojiGeneral.setOnClickListener(this);
		imageViewEmojiGrapeman.setOnClickListener(this);
		imageViewEmojiCoolMonkey.setOnClickListener(this);

		UserAPI userAPI = BBSSDK.getApi(UserAPI.class);
		User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			finish();
			return;
		}
		aivAvatar.execute(user.avatar, null);
		textViewTitle.setText(user.userName);

		inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

		titleBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Rect r = new Rect();
				titleBar.getWindowVisibleDisplayFrame(r);
				int screenHeight = titleBar.getRootView().getHeight();
				// r.bottom is the position above soft keypad or device button.
				// if keypad is shown, the r.bottom is smaller than that before.
				int keypadHeight = screenHeight - r.bottom;
				if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
					isKeyboardShown = true;
					//键盘展示时会隐藏emoji输入框
					layoutEmojiContainer.setVisibility(View.GONE);
				} else {
					isKeyboardShown = false;
				}
			}
		});

		imageViewKeyboard = (ImageView) view.findViewById(getIdRes("bbs_writepost_imageViewKeyboard"));
		imageViewKeyboard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isKeyboardShown) {
					hideSoftInput();
				} else {
					showSoftInput();
				}
			}
		});
		imageViewAddEmoji.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isKeyboardShown) {
					hideSoftInput();
				}
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						layoutEmojiContainer.setVisibility(View.VISIBLE);
					}
				}, 500);
			}
		});

		emojiViewPager = (ViewPager) view.findViewById(getIdRes("emojiViewPager"));
		emojiPagerAdapter = new EmojiPagerAdapter(getContext(), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String key = (String) v.getTag();
				key = htmlReplace(key);
				richEditor.insertHTML(key);
			}
		});
		emojiViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
		emojiViewPager.setAdapter(emojiPagerAdapter);
		emojiPagerAdapter.notifyDataSetChanged();

		textViewChooseCat.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ChooseForum();
			}
		});

		richEditor.setPlaceholder(getStringRes("bbs_pagewritethread_edit_content_hint"));
		int padding = ResHelper.dipToPx(getContext(), 5);
		richEditor.setPadding(padding, padding, padding, padding);
		richEditor.setEditorFontSize(15);
		richEditor.setEditorFontColor(0x3A4045);
		richEditor.setBackgroundColor(0x00000000);

		richEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
			public void onStateChangeListener(String text, List<RichEditor.Type> types) {
				boolean isBold = false;
				boolean isItalic = false;
				if (types != null && types.size() > 0) {
					for (RichEditor.Type type : types) {
						if (type == RichEditor.Type.BOLD) {
							isBold = true;
						} else if (type == RichEditor.Type.ITALIC) {
							isItalic = true;
						}
					}
				}
				if (toggleBold != null) {
					toggleBold.setSelected(isBold);
				}
				if (toggleItalic != null) {
					toggleItalic.setSelected(isItalic);
				}
			}
		});
		initOnPressListener();
		toggleBold.setOnClickListener(toggleListener);
		toggleItalic.setOnClickListener(toggleListener);
		toggleStrikeThrough.setOnClickListener(toggleListener);
		toggleAddPic.setOnClickListener(toggleListener);
		toggleHeader1.setOnClickListener(toggleListener);
		toggleHeader2.setOnClickListener(toggleListener);
		toggleHeader3.setOnClickListener(toggleListener);
		toggleHeader4.setOnClickListener(toggleListener);
		toggleQuote.setOnClickListener(toggleListener);

		llEditorBar.setVisibility(View.GONE);
		richEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					llEditorBar.setVisibility(View.VISIBLE);
				} else {
					llEditorBar.setVisibility(View.GONE);
				}
			}
		});

		richEditor.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//当弹出emoji界面，又点击了详情页时，因为会弹出键盘，所以关闭掉emoji页面的展示。
				if (event.getAction() == MotionEvent.ACTION_UP && layoutEmojiContainer.getVisibility() == View.VISIBLE) {
					layoutEmojiContainer.setVisibility(View.GONE);
				}
				return false;
			}
		});
		restoreCache(context);
	}

	public static String htmlReplace(String str){
		str = str.replace("'", "&apos;");
		str = str.replace("—", "&mdash;");
		str = str.replace("–", "&ndash;");
		str = str.replace("\"", "&quot;");
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace("&", "&amp;");
		return str;
	}

	@Override
	public void onClick(View v) {
		if (v == imageViewEmojiGeneral) {
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


	private void hideSoftInput() {
		if (inputMethodManager == null) {
			return;
		}
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	private void showSoftInput() {
		if (activity.getCurrentFocus() == null) {
			return;
		}
		activity.getCurrentFocus().requestFocus();
		inputMethodManager.showSoftInput(activity.getCurrentFocus(), 0);
	}

	protected void ChooseForum() {
		PageSelectForum selectforum = BBSViewBuilder.getInstance().buildPageSelectForum();
		selectforum.showForResult(getContext(), new FakeActivity() {
			public void onResult(HashMap<String, Object> data) {
				if (data != null) {
					ForumForum forumForum = ResHelper.forceCast(data.get("ForumForum"));
					if (forumForum != null) {
						if (forumForum.fid < 1) {
							textViewChooseCat.setText(getStringRes("bbs_pagewritethread_choose_category"));
						} else {
							selectedForum = forumForum;
							textViewChooseCat.setText(selectedForum.name);
						}
					}
					OnForumChoosed(forumForum);
				}
			}
		});
	}

	protected void OnForumChoosed(ForumForum forum) {

	}

	private void initOnPressListener() {
		if (toggleListener == null) {
			toggleListener = new View.OnClickListener() {
				public void onClick(View view) {
					if (richEditor == null || !richEditor.hasFocus()) {
						return;
					}
					if (view == toggleAddPic) {
						choose();
						return;
					}
					boolean isSelected = view.isSelected();
					view.setSelected(!isSelected);
					if (view == toggleBold) {
						richEditor.setBold();
					} else if (view == toggleItalic) {
						richEditor.setItalic();
					} else if (view == toggleStrikeThrough) {
						richEditor.setStrikeThrough();
					} else if (view == toggleHeader1) {
						if (isSelected) {
							richEditor.setEndFormat();
						} else {
							richEditor.setHeading(1);
						}
					} else if (view == toggleHeader2) {
						if (isSelected) {
							richEditor.setEndFormat();
						} else {
							richEditor.setHeading(2);
						}
					} else if (view == toggleHeader3) {
						if (isSelected) {
							richEditor.setEndFormat();
						} else {
							richEditor.setHeading(3);
						}
					} else if (view == toggleHeader4) {
						if (isSelected) {
							richEditor.setEndFormat();
						} else {
							richEditor.setHeading(4);
						}
					} else if (view == toggleQuote) {
						if (isSelected) {
							richEditor.setEndFormat();
						} else {
							richEditor.setBlockquote();
						}
					}
				}
			};
		}
	}

	protected void onTitleLeftClick(TitleBar titleBar) {
		finish();
	}

	protected void onTitleRightClick(TitleBar titleBar) {
		//send post
		if (selectedForum == null || selectedForum.fid < 1) {
			showWarningDialog(getStringRes("bbs_pagewritethread_tip_forum_invalid"));
			return;
		}
		final String subject = editTextTitle.getText().toString();
		if (TextUtils.isEmpty(subject) || subject.length() < 2) {
			showWarningDialog(getStringRes("bbs_pagewritethread_tip_subject_invalid"));
			return;
		}
		String body = richEditor.getHtml();
		if (TextUtils.isEmpty(body) || body.length() < 5) {
			showWarningDialog(getStringRes("bbs_pagewritethread_tip_content_invalid"));
			return;
		}
		final String message = body;
		//1. 调用js获取所有的img标签的src值
		richEditor.getImages(new RichEditor.OnJsCallbackListener() {
			public void onGotImages(String[] imgList) {
				//2. 发送请求
				new SendForumThreadManager(activity.getApplicationContext(), selectedForum.fid, subject, message, imgList).sendThread();
				finish();
			}
		});
	}

	private void showWarningDialog(String warningStr) {
		if (warningDialog == null) {
			warningDialog = new WarningDialog(activity);
		}
		warningDialog.setWarningText(warningStr);
		warningDialog.show();
	}

	protected void onPicGot(Uri source, String realpath) {
		File file = new File(realpath);
		if (file.exists()) {
			if (file.length() > 10 * 1024 * 1024) {
				showWarningDialog(getStringRes("bbs_tip_upload_img_oversize"));
				return;
			}
			if (realpath.startsWith("/")) {
				richEditor.insertImage("file://" + realpath, "");
			} else {
				richEditor.insertImage("file:///" + realpath, "");
			}
		}
	}

	public void onResume() {
		super.onResume();
		activity.registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumThreadManager.BROADCAST_SEND_THREAD));
	}

	public void onPause() {
		super.onPause();
		saveCache(getContext());
		if (sendThreadReceiver != null) {
			activity.unregisterReceiver(sendThreadReceiver);
		}
	}

	private BroadcastReceiver initSendThreadReceiver() {
		if (sendThreadReceiver == null) {
			sendThreadReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					if (intent == null) {
						return;
					}
					int status = intent.getIntExtra("status", SendForumThreadManager.STATUS_SEND_IDLE);
					//发帖成功，重置发帖界面
					if (status == SendForumThreadManager.STATUS_SEND_SUCCESS) {
						resetUI();
					}
				}
			};
		}
		return sendThreadReceiver;
	}

	protected void restoreCache(Context context) {
		HashMap<String, Object> map = SendForumThreadManager.getThreadCache(context);
		//1. 版块、主题、内容（内容中需要替换已经上传的图片）
		if (selectedForum == null || selectedForum.fid < 1) {
			selectedForum = ResHelper.forceCast(map.get("ForumForum"));
		}
		if (selectedForum != null && selectedForum.fid > 0) {
			textViewChooseCat.setText(selectedForum.name);
		} else {
			selectedForum = null;
		}
		String subject = ResHelper.forceCast(map.get("subject"));
		if (!TextUtils.isEmpty(subject)) {
			editTextTitle.setText(subject);
		}
		String message = ResHelper.forceCast(map.get("message"));
		if (!TextUtils.isEmpty(message)) {
			richEditor.setHtml(message);
		}
	}

	private void saveCache(final Context context) {
		//1. 版块、主题、内容（内容中需要替换已经上传的图片）
		final String subject = editTextTitle.getText().toString();
		final String message = richEditor.getHtml();
		richEditor.getImages(new RichEditor.OnJsCallbackListener() {
			public void onGotImages(String[] imgList) {
				SendForumThreadManager.saveCache(context, selectedForum, subject, message, imgList);
			}
		});
	}

	/**
	 * 回复成功后刷新UI
	 */
	protected void resetUI() {
		try {
			if (textViewChooseCat != null) {
				selectedForum = null;
				textViewChooseCat.setText(getStringRes("bbs_pagewritethread_choose_category"));
			}
			if (editTextTitle != null) {
				editTextTitle.setText("");
			}
			if (richEditor != null) {
				richEditor.setHtml(null);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
