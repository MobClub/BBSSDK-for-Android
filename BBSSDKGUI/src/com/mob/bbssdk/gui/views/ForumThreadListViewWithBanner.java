package com.mob.bbssdk.gui.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.other.BannerLayout;
import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.model.Banner;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;

public class ForumThreadListViewWithBanner extends ForumThreadListView {
	protected BannerLayout bannerLayout;
	protected ImageView imageViewFakeBanner;
	protected List<Banner> listBanner = new ArrayList<Banner>();
	private int viewBannerHeight = 0;

	public ForumThreadListViewWithBanner(Context context) {
		super(context);
	}

	public ForumThreadListViewWithBanner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ForumThreadListViewWithBanner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();
		setHaveContentHeader(true);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (checkIsTouchHeaderView(ev.getY())) {
			return viewHeader.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (checkIsTouchHeaderView(ev.getY())) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	protected void setBannerLayoutViews(BannerLayout bannerlayout, ImageView fakebanner) {
		this.bannerLayout = bannerLayout;
		this.imageViewFakeBanner = fakebanner;
	}

	protected int getHeaderViewHeight() {
		if (viewHeader == null) {
			throw new IllegalStateException("No bannerLayout set!");
		}
		return viewHeader.getHeight();
	}

	protected int getHeaderViewY() {
		if (viewHeader == null) {
			throw new IllegalStateException("No bannerLayout set!");
		}
		return (int) viewHeader.getY();
	}

	private boolean checkIsTouchHeaderView(float eventY) {
		if (!basePagedItemAdapter.haveContentHeader()) {
			return false;
		}
		if (viewHeader != null && viewHeader.isShown()) {
			float headerViewY = getHeaderViewY();
			if (viewBannerHeight == 0) {
				viewBannerHeight = getHeaderViewHeight();
			}
			return eventY < viewBannerHeight + headerViewY && Math.abs(headerViewY) < viewBannerHeight;
		}
		return false;
	}

	@Override
	protected void OnRefresh() {
		super.OnRefresh();
		getBanner();
	}

	protected void getBanner() {
		UserAPI userapi = BBSSDK.getApi(UserAPI.class);
		userapi.getBannerList(false, new APICallback<List<Banner>>() {
			@Override
			public void onSuccess(API api, int action, List<Banner> result) {
				listBanner = result;
				OnBannerGot(listBanner);
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void OnBannerGot(List<Banner> list) {
		updateBanner(list);
	}

	public void updateBanner(List<Banner> l) {
		if (imageViewFakeBanner == null || bannerLayout == null) {
			return;
		}
		final List<Banner> banners;
		if (l == null) {
			banners = listBanner;
		} else {
			banners = l;
		}
		if (banners == null || banners.size() == 0) {
			imageViewFakeBanner.setVisibility(VISIBLE);
			bannerLayout.setVisibility(GONE);
			return;
		} else {
			imageViewFakeBanner.setVisibility(GONE);
			bannerLayout.setVisibility(VISIBLE);
		}
		if (bannerLayout != null && banners != null) {
			List<BannerLayout.Item> list = new ArrayList<BannerLayout.Item>();
			for (Banner banner : banners) {
				BannerLayout.Item item = new BannerLayout.Item();
				item.strUrl = banner.picture;
				item.strTitle = banner.title;
				list.add(item);
			}
			bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
				@Override
				public void onItemClick(int position) {
					if (banners != null) {
						Banner banner = banners.get(position);
						if (banner != null && banner.btype != null) {
							if (banner.btype.equals("thread")) {
								PageForumThreadDetail page = BBSViewBuilder.getInstance().buildPageForumThreadDetail();
								ForumThread thread = new ForumThread();
								thread.tid = Long.valueOf(banner.tid);
								thread.fid = Long.valueOf(banner.fid);
								page.setForumThread(thread);
								page.show(getContext());
							} else if (banner.btype.equals("link")) {
								String url = banner.link;
								if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
									PageWeb pageWeb = BBSViewBuilder.getInstance().buildPageWeb();
									pageWeb.setLink(url);
									pageWeb.show(getContext());
								}
							}
						}
					}
				}
			});
			bannerLayout.setViewItems(list);
		}
	}

	@Override
	public View getContentHeader(ViewGroup viewGroup, View viewprev) {
		View view = viewprev;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme0_layout_banner"), viewGroup, false);
		}
		bannerLayout = (BannerLayout) view.findViewById(ResHelper.getIdRes(getContext(), "bannerLayout"));
		imageViewFakeBanner = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewFakeBanner"));
//		List<BannerLayout.Item> list = new ArrayList<BannerLayout.Item>();
//		list.add(new BannerLayout.Item("http://static.699pic.com/images/activeimg/598190a32dc45.jpg", "11"));
//		list.add(new BannerLayout.Item("http://static.699pic.com/images/activeimg/595de1af2632f.jpg", "2"));
//		list.add(new BannerLayout.Item("http://static.699pic.com/images/activeimg/597ed0755b2cc.jpg", "3"));
//		bannerLayout.setViewItems(list);
		getBanner();
		return view;
	}
}
