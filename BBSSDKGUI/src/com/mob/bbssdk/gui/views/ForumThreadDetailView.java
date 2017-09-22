package com.mob.bbssdk.gui.views;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.ForumAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.dialog.DefaultChooserDialog;
import com.mob.bbssdk.gui.dialog.ReplyEditorPopWindow;
import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.utils.ImageDownloader;
import com.mob.bbssdk.gui.utils.ImageUtils;
import com.mob.bbssdk.gui.utils.SendForumPostManager;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.webview.BaseWebView;
import com.mob.bbssdk.gui.webview.JsInterfaceForumThread;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumPost;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.bbssdk.model.User;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 帖子详情的View
 */
public class ForumThreadDetailView extends BaseView {
	protected BaseWebView webView;
	private ProgressBar progressBar;
	private ViewGroup rlReply;
	private ViewGroup rlReplyIng;
	private ViewGroup rlReplyFailed;
	private ViewGroup rlReplySuccess;
	private TextView tvOwner;

	protected ForumThread forumThread;
	private JsViewClient innerJsViewClient;
	private JsViewClient outJsViewClient;
	protected JsInterfaceForumThread jsInterfaceForumThread;
	protected RelativeLayout rlContainer;
	private int keyBoardHeight = 0;
	private int realBottom = 0;

	private ReplyEditorPopWindow replyEditorPopWindow;
	private ReplyEditorPopWindow.OnConfirmClickListener onConfirmClickListener;
	private ChoosePicClickListener choosePicClickListener;

	private ForumAPI forumAPI;
	private ForumPost tmpPrePost = null;
	private BroadcastReceiver sendPostReceiver;
	private boolean showOwner = false;//只看楼主和

	public ForumThreadDetailView(Context context) {
		super(context);
	}

	public ForumThreadDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumThreadDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected View buildContentView(Context context) {
		return null;
	}

	protected View initContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		View view = buildContentView(context);
		if(view == null) {
			view = LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(context, "bbs_layout_forumthreadview"), this, false);
		}
		rlContainer = (RelativeLayout) view.findViewById(ResHelper.getIdRes(context, "rlContainer"));
		webView = (BaseWebView) view.findViewById(ResHelper.getIdRes(context, "webView"));
		progressBar = (ProgressBar) view.findViewById(ResHelper.getIdRes(context, "progressBar"));
		tvOwner = (TextView) view.findViewById(ResHelper.getIdRes(context, "tvOwner"));
		rlReply = (ViewGroup) view.findViewById(ResHelper.getIdRes(context, "rlReply"));
		rlReplySuccess = (ViewGroup) view.findViewById(ResHelper.getIdRes(context, "rlReplySuccess"));
		rlReplyFailed = (ViewGroup) view.findViewById(ResHelper.getIdRes(context, "rlReplyFailed"));
		rlReplyIng = (ViewGroup) view.findViewById(ResHelper.getIdRes(context, "rlReplyIng"));

		initInnerJsViewClient();
		tvOwner.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onOwnerClick();
			}
		});

		rlReply.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onReplyClick(null);
			}
		});

		rlReplyFailed.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendForumPostManager.showFailedDialog(getContext());
			}
		});

		((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		rlContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					public void onGlobalLayout() {
						Rect r = new Rect();
						rlContainer.getWindowVisibleDisplayFrame(r);
						if (realBottom == 0) {
							realBottom = r.bottom;
						}
						int newKeyboardHeight = realBottom - r.bottom;
						if (keyBoardHeight == newKeyboardHeight) {
							return;
						}
						keyBoardHeight = newKeyboardHeight;
						if (replyEditorPopWindow != null) {
							replyEditorPopWindow.setBottomLayoutHeight(keyBoardHeight);
						}
					}
				});

		updateSendButton(SendForumPostManager.getStatus(context), null);
		return rlContainer;
	}

	private void initInnerJsViewClient() {
		if (innerJsViewClient == null) {
			innerJsViewClient = new JsViewClient(getContext()) {
				public boolean onReplyPostClick(ForumPost prePost) {
					if (outJsViewClient == null || !outJsViewClient.onReplyPostClick(prePost)) {
						onReplyClick(prePost);
					}
					return true;
				}

				public void onItemAttachmentClick(ForumThreadAttachment attachment) {
					if (outJsViewClient != null) {
						outJsViewClient.onItemAttachmentClick(attachment);
					} else {
						super.onItemAttachmentClick(attachment);
					}
				}

				public void onItemImageClick(String[] imageUrls, int index) {
					if (outJsViewClient != null) {
						outJsViewClient.onItemImageClick(imageUrls, index);
					} else {
						super.onItemImageClick(imageUrls, index);
					}
				}

				public void downloadImages(String[] imageUrls, ImageDownloader.ImageDownloaderListener downloaderListener) {
					if (outJsViewClient != null) {
						outJsViewClient.downloadImages(imageUrls, downloaderListener);
					} else {
						super.downloadImages(imageUrls, downloaderListener);
					}
				}

				public void showImage(boolean success, int index, String imageUrl, String imagePath, Bitmap bitmap) {
					if (outJsViewClient != null) {
						outJsViewClient.showImage(success, index, imageUrl, imagePath, bitmap);
					} else {
						super.showImage(success, index, imageUrl, imagePath, bitmap);
					}
				}

				public boolean onImageLongPressed(String imagePath) {
					if (outJsViewClient == null || !outJsViewClient.onImageLongPressed(imagePath)) {
						ForumThreadDetailView.this.onImageLongPressed(imagePath);
					}
					return true;
				}

				public void evaluateJavascript(String javascript) {
					if (outJsViewClient != null) {
						outJsViewClient.evaluateJavascript(javascript);
					} else {
						super.evaluateJavascript(javascript);
					}
				}

				public ImageDownloader.ImageDownloaderListener getImageDownloadListener() {
					if (outJsViewClient != null) {
						return outJsViewClient.getImageDownloadListener();
					} else {
						return super.getImageDownloadListener();
					}
				}
			};
			innerJsViewClient.setWebView(webView);
		}
	}


	private void initWebView() {
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://") || url.startsWith("https://")) {
					PageWeb pageWeb = BBSViewBuilder.getInstance().buildPageWeb();
					pageWeb.setLink(url);
					pageWeb.show(getContext());
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress < 10) {
					newProgress = 10;
				}
				progressBar.setProgress(newProgress);
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else if (progressBar.getVisibility() == View.GONE) {
					progressBar.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}

			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				if (jsInterfaceForumThread != null && Build.VERSION.SDK_INT < 17) {
					if ("getForumThreadDetails".equals(message)) {
						result.confirm(jsInterfaceForumThread.getForumThreadDetails());
						return true;
					} else if ("getPosts".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						long fid = ResHelper.forceCast(map.get("fid"), 0L);
						long tid = ResHelper.forceCast(map.get("tid"), 0L);
						int page = ResHelper.forceCast(map.get("page"), 0);
						int pageSize = ResHelper.forceCast(map.get("pageSize"), 0);
						long authorId = ResHelper.forceCast(map.get("authorId"), 0L);
						String callback = ResHelper.forceCast(map.get("callback"));
						jsInterfaceForumThread.getPosts(fid, tid, page, pageSize, authorId, callback);
						result.confirm();
						return true;
					} else if ("openImage".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						ArrayList<String> imageUrls = ResHelper.forceCast(map.get("imageUrls"));
						if (imageUrls != null && imageUrls.size() > 0) {
							int index = ResHelper.forceCast(map.get("index"));
							jsInterfaceForumThread.openImage(imageUrls.toArray(new String[imageUrls.size()]), index);
						}
						result.confirm();
						return true;
					} else if ("openAttachment".equals(message)) {
						jsInterfaceForumThread.openAttachment(defaultValue);
						result.confirm();
						return true;
					} else if ("downloadImages".equals(message)) {
						HashMap<String, Object> map = jsInterfaceForumThread.parseJsonToMap(defaultValue);
						ArrayList<String> imageUrls = ResHelper.forceCast(map.get("imageUrls"));
						if (imageUrls != null && imageUrls.size() > 0) {
							jsInterfaceForumThread.downloadImages(imageUrls.toArray(new String[imageUrls.size()]));
						}
						result.confirm();
						return true;
					} else if ("replyPost".equalsIgnoreCase(message)) {
						jsInterfaceForumThread.replyPost(defaultValue);
						result.confirm();
						return true;
					} else if ("onImageLongPressed".equalsIgnoreCase(message)) {
						jsInterfaceForumThread.onImageLongPressed(defaultValue);
						result.confirm();
						return true;
					}
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
	}

	public void setJsViewClient(JsViewClient jsViewClient) {
		jsViewClient.setWebView(webView);
		this.outJsViewClient = jsViewClient;
	}

	public void setLoadingFailed() {
		progressBar.setProgress(100);
		progressBar.setVisibility(View.GONE);
		setLoadingStatus(RequestLoadingView.LOAD_STATUS_FAILED);
	}

	/**
	 * 设置主题帖内容
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	public void setForumThread(int fid, int tid, String author) {
		if(fid <= 0 || tid <= 0) {
			this.forumThread = null;
			return;
		}
		this.forumThread = new ForumThread();
		this.forumThread.fid = fid;
		this.forumThread.tid = tid;
		this.forumThread.author = author;
	}

	protected void forumThreadUpdated() {

	}

	/**
	 * 加载帖子详情，加载前请调用{@link #setForumThread(ForumThread)}设置主题帖内容
	 */
	public void loadData() {
		if (forumThread == null) {
			setLoadingFailed();
			return;
		}
		setLoadingStatus(RequestLoadingView.LOAD_STATUS_ING);
		//加载帖子详情
		initForumAPI();
		forumAPI.getThreadDetailsByThreadId(forumThread.fid, forumThread.tid, false, new APICallback<ForumThread>() {
			public void onSuccess(API api, int action, ForumThread result) {
				if (result == null) {
					setLoadingFailed();
					return;
				}
				forumThread = result;
				setLoadingStatus(RequestLoadingView.LOAD_STATUS_SUCCESS);
				loadHtml(forumThread);
				forumThreadUpdated();
			}

			public void onError(API api, int action, int errorCode, Throwable details) {
				setLoadingFailed();
			}
		});
	}

	private void initForumAPI() {
		if (forumAPI == null) {
			forumAPI = BBSSDK.getApi(ForumAPI.class);
		}
	}

	protected void loadHtml(ForumThread detail) {
		progressBar.setProgress(0);
		progressBar.setVisibility(View.VISIBLE);
		jsInterfaceForumThread = new JsInterfaceForumThread(getContext(), innerJsViewClient, detail);
		if (Build.VERSION.SDK_INT >= 17) {
			webView.addJavascriptInterface(jsInterfaceForumThread, "forumThread");
		}
		loadNativeHtml();
	}

	protected void loadNativeHtml() {
		webView.loadUrl("file:///android_asset/html/details/index.html");
	}

	private void onOwnerClick() {
		if (jsInterfaceForumThread != null && tvOwner != null) {
			if(showOwner) {
				jsInterfaceForumThread.getAllPosts();
				tvOwner.setText(getStringRes("bbs_viewthreaddetail_btn_owner"));
			} else {
				jsInterfaceForumThread.getOwnerPosts();
				tvOwner.setText(getStringRes("bbs_viewthreaddetail_btn_all"));
			}
			showOwner = !showOwner;
		}
	}

	public void onReplyClick(ForumPost prePost) {
		if (!checkIsAllowToReply(true, true)) {
			return;
		}
		int status = SendForumPostManager.getStatus(getContext());
		if (status != SendForumPostManager.STATUS_SEND_IDLE && status != SendForumPostManager.STATUS_SEND_SUCCESS
				&& status != SendForumPostManager.STATUS_SEND_CACHED) {
			Toast.makeText(getContext(), ResHelper.getStringRes(getContext(), "bbs_writepost_send_ing_waiting"), Toast.LENGTH_SHORT).show();
			return;
		}
		tmpPrePost = prePost;
		if (replyEditorPopWindow == null) {
			if (onConfirmClickListener == null) {
				onConfirmClickListener = new ReplyEditorPopWindow.OnConfirmClickListener() {
					public void onConfirm(final String content, final List<String> imgList) {
						if (forumThread == null) {
							return;
						}
						String[] tmpImgList = null;
						if (imgList != null && imgList.size() > 0) {
							int size = imgList.size();
							tmpImgList = imgList.toArray(new String[size]);
						}
						new SendForumPostManager(getContext(), forumThread.fid, forumThread.tid, tmpPrePost, content, tmpImgList).sendPost();
					}
				};
			}

			replyEditorPopWindow = BBSViewBuilder.getInstance().buildReplyEditorPopWindow(getContext(), onConfirmClickListener,
					new ReplyEditorPopWindow.OnImgAddClickListener() {
						@Override
						public void onClick() {
							if (choosePicClickListener != null) {
								choosePicClickListener.onChooseClick();
							}
						}
					});

			replyEditorPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
				public void onDismiss() {
					if (forumThread == null) {
						return;
					}
					SendForumPostManager.saveCache(getContext(), forumThread.fid, forumThread.tid, tmpPrePost, replyEditorPopWindow.getEditorContent(),
							replyEditorPopWindow.getImgList());
				}
			});
		}
		String author;
		if (tmpPrePost != null) {
			author = tmpPrePost.author;
		} else {
			if(forumThread == null || forumThread.author == null) {
				author = "";
			} else {
				author = forumThread.author;
			}
		}
		replyEditorPopWindow.show(rlContainer, author);
	}

	public void onImageLongPressed(final String imagePath) {
		if (imagePath != null && !imagePath.startsWith("file:///")) {
			Toast.makeText(getContext().getApplicationContext(), ResHelper.getStringRes(getContext(), "bbs_toast_save_pic_failed"),
					Toast.LENGTH_SHORT).show();
			return;
		}
		DefaultChooserDialog chooserDialog = new DefaultChooserDialog(getContext(),
				new int[]{ResHelper.getStringRes(getContext(), "bbs_save_photo_to_local")});
		chooserDialog.setOnItemClickListener(new DefaultChooserDialog.OnItemClickListener() {
			public void onItemClick(View v, int position) {
				if (position == 1) {
					ImageUtils.savePhotoToNative(getContext(), imagePath.substring(8));
				}
			}
		});
		chooserDialog.show();
	}

	private boolean checkIsAllowToReply(boolean gotoLogin, boolean toast) {
		User user = BBSViewBuilder.getInstance().ensureLogin(false);
		boolean isLogin = (user != null);
		if (isLogin) {
			if (user.allowReply == 1) {
				return true;
			} else if (toast){//不允许发帖
				ToastUtils.showToast(getContext(),
						getResources().getString(ResHelper.getStringRes(getContext(), "bbs_dont_allowreply")));
				return false;
			}
		} else if (gotoLogin) {
			PageLogin pagelogin = BBSViewBuilder.getInstance().buildPageLogin();
			pagelogin.showForResult(getContext(), new FakeActivity() {
				public void onResult(HashMap<String, Object> data) {
					super.onResult(data);
				}
			});
		}
		return false;
	}

	public void setChoosePicListener(ChoosePicClickListener listener) {
		this.choosePicClickListener = listener;
	}

	public void setSelectedPicPath(String path) {
		if (replyEditorPopWindow != null) {
			replyEditorPopWindow.setSelectedPicPath(path);
		}
	}

	protected void updateSendButton(int status, ForumPost forumPost) {
		if (!checkIsAllowToReply(false, false)) {
			rlReply.setVisibility(View.VISIBLE);
			rlReplyIng.setVisibility(View.GONE);
			rlReplyFailed.setVisibility(View.GONE);
			rlReplySuccess.setVisibility(View.GONE);
			return;
		}
		if (status == SendForumPostManager.STATUS_SEND_ING) {
			rlReply.setVisibility(View.GONE);
			rlReplyIng.setVisibility(View.VISIBLE);
			rlReplyFailed.setVisibility(View.GONE);
			rlReplySuccess.setVisibility(View.GONE);
		} else if (status == SendForumPostManager.STATUS_SEND_FAILED) {
			rlReply.setVisibility(View.GONE);
			rlReplyIng.setVisibility(View.GONE);
			rlReplyFailed.setVisibility(View.VISIBLE);
			rlReplySuccess.setVisibility(View.GONE);
		} else if (status == SendForumPostManager.STATUS_SEND_SUCCESS) {
			rlReply.setVisibility(View.GONE);
			rlReplyIng.setVisibility(View.GONE);
			rlReplyFailed.setVisibility(View.GONE);
			rlReplySuccess.setVisibility(View.VISIBLE);
			if (jsInterfaceForumThread != null && forumPost != null && forumThread != null && forumPost.fid == forumThread.fid
					&& forumPost.tid == forumThread.tid) {
				//如果是当前帖子的回帖，则添加评论
				jsInterfaceForumThread.addPost(forumPost);
			}
			UIHandler.sendEmptyMessageDelayed(0, 2000, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					rlReply.setVisibility(View.VISIBLE);
					rlReplyIng.setVisibility(View.GONE);
					rlReplyFailed.setVisibility(View.GONE);
					rlReplySuccess.setVisibility(View.GONE);
					return false;
				}
			});
		} else {
			rlReply.setVisibility(View.VISIBLE);
			rlReplyIng.setVisibility(View.GONE);
			rlReplyFailed.setVisibility(View.GONE);
			rlReplySuccess.setVisibility(View.GONE);
		}
	}

	public void onCreate() {
		getContext().registerReceiver(initSendThreadReceiver(), new IntentFilter(SendForumPostManager.BROADCAST_SEND_POST));
	}

	public void onDestroy() {
		SendForumPostManager.clearPostStatus(getContext());
		if (sendPostReceiver != null) {
			getContext().unregisterReceiver(sendPostReceiver);
		}
	}

	private BroadcastReceiver initSendThreadReceiver() {
		if (sendPostReceiver == null) {
			sendPostReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					if (intent == null) {
						return;
					}
					int status = intent.getIntExtra("status", SendForumPostManager.STATUS_SEND_IDLE);
					if (status == SendForumPostManager.STATUS_SEND_SUCCESS && replyEditorPopWindow != null) {
						replyEditorPopWindow.resetUI();
					}
					ForumPost forumPost = ResHelper.forceCast(intent.getSerializableExtra("forumPost"));
					updateSendButton(status, forumPost);
				}
			};
		}
		return sendPostReceiver;
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public interface ChoosePicClickListener {
		void onChooseClick();
	}

}
