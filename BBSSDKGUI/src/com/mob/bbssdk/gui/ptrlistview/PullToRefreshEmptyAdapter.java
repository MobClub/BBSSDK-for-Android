package com.mob.bbssdk.gui.ptrlistview;

import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.mob.bbssdk.gui.views.EmptyView;
import com.mob.tools.gui.PullToRequestListAdapter;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.gui.Scrollable;

public abstract class PullToRefreshEmptyAdapter extends PullToRequestListAdapter {
	private ScrollableRelativeLayout bodyView;
	private EmptyView emptyView;

	public PullToRefreshEmptyAdapter(PullToRequestView view) {
		super(view);
		bodyView = new ScrollableRelativeLayout(view.getContext());

		ListView lv = getListView();
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		bodyView.addView(lv, lp);

		emptyView = new EmptyView(view.getContext());
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		bodyView.addView(emptyView, lp);
		emptyView.setVisibility(View.INVISIBLE);

		emptyView.setEmptyImage(getEmptyViewDrawableId(), getErrorViewDrawableId());
		emptyView.setEmptyText(getEmptyViewStrId(), getErrorViewStrId());
		emptyView.setOnRetryClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onRefreshClick();
			}
		});
	}

	public Scrollable getBodyView() {
		return bodyView;
	}

	public void notifyDataSetChanged() {
		if (emptyView != null) {
			if (getCount() > 0) {
				emptyView.setVisibility(View.INVISIBLE);
			} else {
				setLoadEmpty(false);
			}
		}
		super.notifyDataSetChanged();
	}

	/*
	 * 设置没有加载到数据的界面
	 *
	 * @param isError 如果是加载出错,则显示加载出错的界面,否则显示加载数据为空的界面
	 */
	public void setLoadEmpty(boolean isError) {
		emptyView.setVisibility(View.VISIBLE);
		emptyView.setEmpty(isError);
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		getListView().setOnItemClickListener(listener);
	}

	protected abstract int getEmptyViewDrawableId();

	protected abstract int getErrorViewDrawableId();

	protected abstract int getEmptyViewStrId();

	protected abstract int getErrorViewStrId();

	protected abstract void onRefreshClick();

	protected static class ViewHolder {
		private SparseArray<View> views = new SparseArray<View>();
		private View contentView;

		public ViewHolder(View view) {
			contentView = view;
		}

		public <T extends View> T getView(int vId) {
			View view = views.get(vId);
			if (view == null) {
				view = contentView.findViewById(vId);
				views.put(vId, view);
			}
			return (T) view;
		}
	}
}
