package com.mob.bbssdk.theme1.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.mob.MobSDK;
import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ForumThreadHistoryManager;
import com.mob.bbssdk.gui.GUIManager;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageImageViewer;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.gui.views.GlideImageView;
import com.mob.bbssdk.gui.views.pullrequestview.BBSPullToRequestView;
import com.mob.bbssdk.model.FavoriteThread;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.model.UserOperations;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class Theme1UserProfilePullRequestView extends BBSPullToRequestView<Object> {
	public enum TAB {
		FAVORITE,
		THREAD,
		HISTORY
	}

	protected GlideImageView aivAvatar;
	protected ViewGroup layoutMyFollowing;
	protected ViewGroup layoutMyFollowers;
	protected TextView textViewName;
	protected TextView textViewLocation;
	protected TextView textViewSignature;
	protected ImageView imageViewEditProfile;
	protected TextView textViewFollowing;
	protected TextView textViewFollowers;
	protected ViewGroup layoutFavorite;
	protected ViewGroup layoutThread;
	protected ViewGroup layoutHistory;
	TextView textViewFavoriteCount;
	TextView textViewThreadCount;
	TextView textViewHistoryCount;
	ImageView imageViewBlur;
	ImageView imageViewFavorite;
	ImageView imageViewThread;
	ImageView imageViewHistory;
	ImageView imageViewLocationMark;
	public ViewGroup layoutTab;
	View viewFavoriteMark;
	View viewThreadMark;
	View viewHistoryMark;

	private TAB currentTab = TAB.FAVORITE;
	private UserProfileUpdatedListener userProfileUpdatedListener;
	private User userInfo;
	private UserOperations userOperations;
	private boolean isClickTabEvent;


	public Theme1UserProfilePullRequestView(Context context) {
		super(context);
	}

	public Theme1UserProfilePullRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1UserProfilePullRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setHaveContentHeader(true);
		setOnRequestListener(new BBSPullToRequestView.OnRequestListener() {
			@Override
			public void onRequest(int page, final BasePagedItemAdapter.RequestCallback callback) {
				UserAPI userapi = BBSSDK.getApi(UserAPI.class);
				if (currentTab == TAB.FAVORITE) {
					userapi.getFavoritePostList(page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<FavoriteThread>>() {
						@Override
						public void onSuccess(API api, int action, ArrayList<FavoriteThread> result) {
							callback.onFinished(true, hasMoreData(result), result);
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {
							ErrorCodeHelper.toastError(getContext(), errorCode, details);
						}
					});
				} else if (currentTab == TAB.THREAD) {
					userapi.getPersonalPostList(null, page, nDefaultLoadOnceCount, false, new APICallback<ArrayList<ForumThread>>() {
						@Override
						public void onSuccess(API api, int action, ArrayList<ForumThread> result) {
							callback.onFinished(true, hasMoreData(result), result);
						}

						@Override
						public void onError(API api, int action, int errorCode, Throwable details) {

						}
					});
				} else if (currentTab == TAB.HISTORY) {
					List<ForumThread> list = ForumThreadHistoryManager.getInstance().getReadedThread();
					callback.onFinished(true, false, list);
				}
				if (isClickTabEvent) {
					isClickTabEvent = false;
				} else {
					updateInfoFromServer();
				}
			}
		});
	}

	@Override
	protected View getContentHeader(ViewGroup viewGroup, View viewprev) {
		if(viewprev != null) {
			return viewprev;
		}
		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_header_userprofile"), viewGroup, false);
		layoutTab = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutTab"));
		aivAvatar = (GlideImageView) view.findViewById(ResHelper.getIdRes(getContext(), "aivAvatar"));
		aivAvatar.setExecuteRound();
		aivAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userInfo == null || StringUtils.isEmpty(userInfo.avatar)) {
					return;
				}
				PageImageViewer page = BBSViewBuilder.getInstance().buildPageImageViewer();
				String[] array = new String[1];
				array[0] = userInfo.avatar;
				page.setImageUrlsAndIndex(array, 0);
				page.show(getContext());
			}
		});

		textViewName = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewName"));
		imageViewLocationMark = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewLocationMark"));
		textViewLocation = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewLocation"));
		textViewSignature = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewSignature"));
		imageViewEditProfile = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewEditProfile"));
		textViewFollowing = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowing"));
		textViewFollowers = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFollowers"));
		imageViewFavorite = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewFavorite"));
		imageViewThread = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewThread"));
		imageViewHistory = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewHistory"));
		viewFavoriteMark = view.findViewById(ResHelper.getIdRes(getContext(), "viewFavoriteMark"));
		viewThreadMark = view.findViewById(ResHelper.getIdRes(getContext(), "viewThreadMark"));
		viewHistoryMark = view.findViewById(ResHelper.getIdRes(getContext(), "viewHistoryMark"));

		layoutMyFollowing = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowing"));
		layoutMyFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageFollowings().show(getContext());
			}
		});
		layoutMyFollowers = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutMyFollowers"));
		layoutMyFollowers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageFollowers().show(getContext());
			}
		});
		imageViewEditProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BBSViewBuilder.getInstance().buildPageUserProfileDetails().show(getContext());
			}
		});
		layoutFavorite = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutFavorite"));
		layoutThread = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutThread"));
		layoutHistory = (ViewGroup) view.findViewById(ResHelper.getIdRes(getContext(), "layoutHistory"));
		textViewFavoriteCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewFavoriteCount"));
		textViewThreadCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewThreadCount"));
		textViewHistoryCount = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewHistoryCount"));
		imageViewBlur = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewBlur"));
		layoutFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(TAB.FAVORITE);
			}
		});
		layoutThread.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(TAB.THREAD);
			}
		});
		layoutHistory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickTab(TAB.HISTORY);
			}
		});
		updateViews(view);
		return view;
	}

	public void clickTab(TAB tab) {
		//todo fix the scroll error caused by rapid click.
		if (tab == null || tab == currentTab) {
			return;
		}
		isClickTabEvent = true;
		currentTab = tab;
		updateTabView();
		getBasePagedItemAdapter().getDataSet().clear();
		refreshQuiet();
		try {
			//取消加载效果，防止界面闪动
			ReflectHelper.invokeInstanceMethod(this,"reversePulling");
		} catch (Throwable throwable) {
		}
	}

	public void updateTabView() {
		updateTabStatus(imageViewFavorite, imageViewThread, imageViewHistory, viewFavoriteMark, viewThreadMark, viewHistoryMark);
		if (userProfileUpdatedListener != null) {
			userProfileUpdatedListener.OnTabUpdated(currentTab);
		}
	}

	public void updateTabStatus(ImageView favorite, ImageView thread, ImageView history, View favmark, View threadmark, View historymark) {
		if (currentTab == TAB.FAVORITE) {
			favorite.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_favorite_active"));
			thread.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_post_inactive"));
			history.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_history_inactive"));
			favmark.setVisibility(VISIBLE);
			threadmark.setVisibility(INVISIBLE);
			historymark.setVisibility(INVISIBLE);
		} else if (currentTab == TAB.THREAD) {
			favorite.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_favorite_inactive"));
			thread.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_post_active"));
			history.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_history_inactive"));
			favmark.setVisibility(INVISIBLE);
			threadmark.setVisibility(VISIBLE);
			historymark.setVisibility(INVISIBLE);
		} else if (currentTab == TAB.HISTORY) {
			favorite.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_favorite_inactive"));
			thread.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_post_inactive"));
			history.setImageResource(ResHelper.getBitmapRes(getContext(), "bbs_theme1_history_active"));
			favmark.setVisibility(INVISIBLE);
			threadmark.setVisibility(INVISIBLE);
			historymark.setVisibility(VISIBLE);
		}
	}

	public void setOnUserProfileUpdatedListener(UserProfileUpdatedListener listener) {
		this.userProfileUpdatedListener = listener;
	}

	public interface UserProfileUpdatedListener {
		void OnTabUpdated(TAB tab);
		void OnUserInfoUpdated(User user);
		void OnUserOperationUpdated(UserOperations useroper);
	}

	protected void updateViews() {
		updateViews(null);
	}

	protected void updateViews(View view) {
		//make sure the view was created, and the date got from server.
		if(view == null) {
			view = viewHeader;
		}
		if (view != null) {
			if (userInfo != null) {
				aivAvatar.setImageBitmap(GUIManager.getInstance().getCurrentUserAvatar());
				textViewName.setText(userInfo.userName);
				textViewLocation.setText(DataConverterHelper.getLocationText(userInfo));
				if(StringUtils.isEmpty(textViewLocation.getText().toString().trim())) {
					imageViewLocationMark.setVisibility(INVISIBLE);
				} else {
					imageViewLocationMark.setVisibility(VISIBLE);
				}
				textViewSignature.setText(userInfo.sightml == null ? "" : Html.fromHtml(userInfo.sightml));

				//set blur background
				if (!StringUtils.isEmpty(userInfo.avatar)) {
					Glide.with(MobSDK.getContext())
							.load(userInfo.avatar)
							.asBitmap()
							.signature(new StringSignature("" + System.currentTimeMillis()))
							.into(new SimpleTarget<Bitmap>() {
								@Override
								public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
									Blurry.with(getContext()).from(resource).into(imageViewBlur);
								}
							});
				}
			}
			if (userOperations != null) {
				textViewFollowing.setText("" + userOperations.firends);
				textViewFollowers.setText("" + userOperations.followers);
				textViewFavoriteCount.setText("" + userOperations.favorites);
				textViewThreadCount.setText("" + userOperations.threads);
				textViewHistoryCount.setText("" + ForumThreadHistoryManager.getInstance().getReadedThreadCount());
			}
		}
	}

	protected void updateInfoFromServer() {
		final User user = BBSViewBuilder.getInstance().ensureLogin(true);
		if (user == null) {
			return;
		}
		BBSSDK.getApi(UserAPI.class).getUserInfo(null, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				if (result != null) {
					userInfo = result;
					updateViews();
				}
				if (userProfileUpdatedListener != null) {
					userProfileUpdatedListener.OnUserInfoUpdated(userInfo);
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});

		UserAPI api = BBSSDK.getApi(UserAPI.class);
		api.getUserOperations(null, false, new APICallback<UserOperations>() {
			@Override
			public void onSuccess(API api, int action, UserOperations result) {
				if (result != null) {
					userOperations = result;
					updateViews();
				}
				if (userProfileUpdatedListener != null) {
					userProfileUpdatedListener.OnUserOperationUpdated(userOperations);
				}
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	@Override
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		Object item = getItem(position);
		if (item instanceof FavoriteThread) {
			Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_favoritethread");
			final FavoriteThread thread = (FavoriteThread) item;
			View view = ListViewItemBuilder.getInstance().buildLayoutFavoriteView(thread, convertView, parent, layout);
			TextView textviewComment = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_item_forumpost_textViewPageComment"));
			TextView textViewPageLike = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewPageLike"));
			TextView textViewPageView = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "bbs_item_forumpost_textViewPageView"));
			if(textviewComment != null) {
				textviewComment.setText("" + thread.phoneReplies);
			}
			if(textViewPageLike != null) {
				textViewPageLike.setText("" + thread.recommendadd);
			}
			if(textViewPageView != null) {
				textViewPageView.setText("" + thread.phoneViews);
			}
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (thread != null) {
						PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
						pageForumThreadDetail.setForumThread(thread.fid, thread.tid, thread.author);
						pageForumThreadDetail.show(getContext());
					}
				}
			});
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if(currentTab != TAB.FAVORITE) {
						return true;
					}
					AlertDialog dialog = new AlertDialog.Builder(getContext())
							.setTitle(ResHelper.getStringRes(getContext(), "bbs_unfavorite_title"))
							.setMessage(ResHelper.getStringRes(getContext(), "bbs_unfavorite_body"))
							.setPositiveButton(ResHelper.getStringRes(getContext(), "bbs_unfavorite_btn_pos"), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									UserAPI api = BBSSDK.getApi(UserAPI.class);
									api.unfavoritePost(thread.favid, false, new APICallback<Boolean>() {
										@Override
										public void onSuccess(API api, int action, Boolean result) {
											Theme1UserProfilePullRequestView.this.getBasePagedItemAdapter().getDataSet().remove(position);
											Theme1UserProfilePullRequestView.this.getBasePagedItemAdapter().notifyDataSetChanged();
											ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_unfavorite_success"));
										}
										@Override
										public void onError(API api, int action, int errorCode, Throwable details) {
											ErrorCodeHelper.toastError(getContext(), errorCode, details);
										}
									});
								}
							})
							.setNegativeButton(ResHelper.getStringRes(getContext(), "bbs_unfavorite_btn_neg"), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							})
							.show();
					return true;
				}
			});
			return view;
		} else if (item instanceof ForumThread) {
			Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_otheruserprofile");
			final ForumThread thread = (ForumThread) item;
			View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(thread, convertView, parent, layout);
			TextView textViewPageLike = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewPageLike"));
			textViewPageLike.setText("" + thread.recommendadd);
			view.findViewById(ResHelper.getIdRes(getContext(), "bbs_item_forumpost_textViewSubject")).setVisibility(GONE);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (thread != null) {
						PageForumThreadDetail pageForumThreadDetail = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
						pageForumThreadDetail.setForumThread(thread);
						pageForumThreadDetail.show(getContext());
					}
				}
			});
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if(currentTab != TAB.HISTORY) {
						return true;
					}
					AlertDialog dialog = new AlertDialog.Builder(getContext())
							.setTitle(ResHelper.getStringRes(getContext(), "bbs_delhistory_title"))
							.setMessage(ResHelper.getStringRes(getContext(), "bbs_delhistory_body"))
							.setPositiveButton(ResHelper.getStringRes(getContext(), "bbs_btn_pos"), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									ForumThreadHistoryManager.getInstance().removeReadeThread(thread);
									Theme1UserProfilePullRequestView.this.getBasePagedItemAdapter().getDataSet().remove(position);
									Theme1UserProfilePullRequestView.this.getBasePagedItemAdapter().notifyDataSetChanged();
									ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "bbs_delhistory_success"));
								}
							})
							.setNegativeButton(ResHelper.getStringRes(getContext(), "bbs_btn_neg"), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							})
							.show();
					return true;
				}
			});
			return view;
		} else {
			throw new IllegalStateException("Error State");
		}
	}
}
