package com.mob.bbssdk.gui.views.pullrequestview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.gui.Scrollable;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public abstract class BBSPullToRequestView<T> extends PullToRequestView {
	protected int nDefaultLoadOnceCount = 20;
	protected View viewHeader = null;
	protected BasePagedItemAdapter<T> basePagedItemAdapter;
	protected LayoutInflater layoutInflater;
	private OnRequestListener onRequestListener;
	private OnScrollListener onScrollListener;
	private Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<Integer, Integer>();

	public BBSPullToRequestView(Context context) {
		super(context);
		init();
	}

	public BBSPullToRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BBSPullToRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected boolean hasMoreData(List list) {
		if (list == null || list.size() < nDefaultLoadOnceCount) {
			return false;
		}
		return true;
	}

	protected Integer getEmptyViewDrawableId() {
		return BBSViewBuilder.getInstance().getBBSPullToRequestView_EmptyViewDrawableId(getContext());
	}

	protected Integer getErrorViewDrawableId() {
		return BBSViewBuilder.getInstance().getBBSPullToRequestView_getErrorViewDrawableId(getContext());
	}

	protected Integer getEmptyViewStrId() {
		return BBSViewBuilder.getInstance().getBBSPullToRequestView_getEmptyViewStrId(getContext());
	}

	protected Integer getErrorViewStrId() {
		return BBSViewBuilder.getInstance().getBBSPullToRequestView_getErrorViewStrId(getContext());
	}

	public boolean isRefreshing() {
		if(basePagedItemAdapter != null) {
			return basePagedItemAdapter.isRefreshing();
		}
		return false;
	}

	protected void init() {
		basePagedItemAdapter = new BasePagedItemAdapter<T>(this) {

			public int getItemViewType(int position) {
				if (position == 0 && haveContentHeader()) {
					return 2;
				}
				return super.getItemViewType(position);
			}

			public int getViewTypeCount() {
				return 3;
			}

			@Override
			protected View getContentView(int position, View convertView, ViewGroup parent) {
				if (haveContentHeader() && position == 0) {
					viewHeader = getContentHeader(parent, viewHeader);
					return viewHeader;
				}
				return BBSPullToRequestView.this.getContentView(position, convertView, parent);
			}

			@Override
			public View getContentHeader(ViewGroup viewGroup, View viewprev) {
				return BBSPullToRequestView.this.getContentHeader(viewGroup, viewprev);
			}

			@Override
			protected void onRequest(int page, RequestCallback callback) {
				BBSPullToRequestView.this.onRequest(page, callback);
			}

			@Override
			protected int getEmptyViewDrawableId() {
				Integer id = BBSPullToRequestView.this.getEmptyViewDrawableId();
				if(id == null) {
					return super.getEmptyViewDrawableId();
				} else {
					return id;
				}
			}

			@Override
			protected int getErrorViewDrawableId() {
				Integer id = BBSPullToRequestView.this.getErrorViewDrawableId();
				if(id == null) {
					return super.getErrorViewDrawableId();
				} else {
					return id;
				}
			}

			@Override
			protected int getEmptyViewStrId() {
				Integer id = BBSPullToRequestView.this.getEmptyViewStrId();
				if(id == null) {
					return super.getEmptyViewStrId();
				} else {
					return id;
				}
			}

			@Override
			protected int getErrorViewStrId() {
				Integer id = BBSPullToRequestView.this.getErrorViewStrId();
				if(id == null) {
					return super.getErrorViewStrId();
				} else {
					return id;
				}
			}

			@Override
			public void onScroll(Scrollable scrollable, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				BBSPullToRequestView.this.onScroll(scrollable, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		};
		setAdapter(basePagedItemAdapter);
		layoutInflater = LayoutInflater.from(getContext());
	}

	public BasePagedItemAdapter<T> getBasePagedItemAdapter() {
		return basePagedItemAdapter;
	}

	public void setHaveContentHeader(boolean flag) {
		basePagedItemAdapter.setHasContentHeader(flag);
	}

	public T getItem(int postion) {
		return basePagedItemAdapter.getItem(postion);
	}


	protected View getContentHeader(ViewGroup viewGroup,View viewprev) {
		//return LayoutInflater.from(getContext()).inflate(R.layout.bbs_layout_test, viewGroup, false);
		return null;
	}

	public void setOnRequestListener(OnRequestListener listener) {
		onRequestListener = listener;
	}

	protected abstract View getContentView(int position, View convertView, ViewGroup parent);

	protected void onRequest(int page, BasePagedItemAdapter.RequestCallback callback) {
		if (onRequestListener == null) {
			throw new IllegalStateException("No OnRequestListener has been set!");
		}
		onRequestListener.onRequest(page, callback);
	}

	public interface OnRequestListener {
		void onRequest(int page, BasePagedItemAdapter.RequestCallback callback);
	}

//	@Override
//	public void scrollTo(int x, int y) {
//		new Throwable().printStackTrace();
//		super.scrollTo(x, y);
//	}
//
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(isRefreshing()) {
			return false;
		}
		return super.dispatchTouchEvent(ev);
	}

	public void refreshQuiet() {
		if(isRefreshing()) {
			return;
		}
		performFresh();
	}

	protected void OnRefresh() {

	}

	protected void performFresh() {
		super.performFresh();
		OnRefresh();
	}

	public static void setAlphaByScrollY(View viewbg, int height, int opacityheight) {
		if(viewbg == null) {
			return;
		}
		float alpha = (float) height / opacityheight;
		if (alpha <= 1) {
			viewbg.setAlpha(alpha);
		} else {
			if (viewbg.getAlpha() < 1) {
				viewbg.setAlpha(1);
			}
		}
	}

	private int getScroll() {
		ListView listview = basePagedItemAdapter.getListView();
		View c = listview.getChildAt(0); //this is the first visible row
		int scrollY = -c.getTop();
		listViewItemHeights.put(listview.getFirstVisiblePosition(), c.getHeight());
		for (int i = 0; i < listview.getFirstVisiblePosition(); i ++) {
			if (listViewItemHeights.get(i) != null) { // (this is a sanity check)
				scrollY += listViewItemHeights.get(i); //add all heights of the views that are gone
			}
		}
		return scrollY;
	}

	protected void OnScrolledToY(int height) {

	}

	public void onScroll(Scrollable scrollable, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(scrollable != null && scrollable instanceof ListView) {
			final ListView listview = (ListView) scrollable;
			View c = listview.getChildAt(0);
			if (c != null) {
				int scrollheight = getScroll();
				OnScrolledToY(scrollheight);
				if (BBSPullToRequestView.this.onScrollListener != null) {
					BBSPullToRequestView.this.onScrollListener.OnScrolledTo(scrollheight);
				}
			}
		}
	}

	public void setOnScrollListener(ForumThreadListView.OnScrollListener listener) {
		this.onScrollListener = listener;
	}

	public interface OnScrollListener {
		void OnScrolledTo(int ypos);
	}
}
