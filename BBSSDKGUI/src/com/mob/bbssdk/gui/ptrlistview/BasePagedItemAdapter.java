package com.mob.bbssdk.gui.ptrlistview;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;

/* 处理分页的adapter */
public abstract class BasePagedItemAdapter<T> extends PullToRefreshHeaderFooterAdapter {
	private int currentPage = 1;
	private boolean hasMoreData = true;
	private boolean showPageFooter = true;
	private ArrayList<T> dataSet = new ArrayList<T>();

	public BasePagedItemAdapter(PullToRequestView view) {
		super(view);
		getListView().setSelector(new ColorDrawable(0));
		getListView().setDivider(new ColorDrawable(view.getResources().getColor(ResHelper.getColorRes(getContext(), "bbs_list_divider_bg"))));
		getListView().setDividerHeight(ResHelper.dipToPx(getContext(), 1));
	}

	public long getItemId(int position) {
		return position;
	}

	public T getItem(int position) {
		if (dataSet == null || position >= dataSet.size()) {
			return null;
		}
		return dataSet.get(position);
	}

	public int getCount() {
		if (dataSet == null) {
			return 0;
		}
		return dataSet.size();
	}

	protected void requestRefresh() {
		currentPage = 1;
		hasMoreData = true;
		onRequest(currentPage, new RequestCallback<T>() {
			public void onFinished(boolean success, boolean hasMoreData, List<T> data) {
				BasePagedItemAdapter.this.hasMoreData = hasMoreData;
				dataSet.clear();
				if (success) {
					if (data != null) {
						dataSet.addAll(data);
					}
					if (hasMoreData) {
						//允许上拉加载更多
						getParent().releasePullingUpLock();
					} else {
						//禁止上拉加载更多
						if (dataSet.size() > 0 && showPageFooter) {
							dataSet.add(null);
						}
						getParent().lockPullingUp();
					}
					notifyDataSetChanged();
				} else {
					getParent().lockPullingUp();
					notifyFreshDataError();
				}
			}
		});
	}

	protected void requestNextPage() {
		onRequest(currentPage + 1, new RequestCallback<T>() {
			public void onFinished(boolean success, boolean hasMoreData, List<T> data) {
				BasePagedItemAdapter.this.hasMoreData = hasMoreData;
				if (success && data != null) {
					dataSet.addAll(data);
				}
				if (hasMoreData) {
					getParent().releasePullingUpLock();
					currentPage += 1;
				} else {
					if (showPageFooter) {
						dataSet.add(null);
					}
					getParent().lockPullingUp();
				}
				notifyDataSetChanged();
			}
		});
	}

	public int getItemViewType(int position) {
		if (showPageFooter && dataSet.size() > 0 && !hasMoreData && position == dataSet.size() - 1) {
			T t = dataSet.get(position);
			if (t == null) {
				return 1;
			}
		}
		return 0;
	}

	public int getViewTypeCount() {
		return 2;
	}

	/** 设置当翻到最后一页时，是否显示页脚"以上已为全部内容"*/
	public void setShowPageFooter(boolean show) {
		showPageFooter = show;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 1) {
			return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_list_page_footer"), parent, false);
		}
		return getContentView(position, convertView, parent);
	}

	protected abstract View getContentView(int position, View convertView, ViewGroup parent);

	protected abstract void onRequest(int page, RequestCallback callback);

	public interface RequestCallback<T> {
		void onFinished(boolean success, boolean hasMoreData, List<T> data);
	}

}
