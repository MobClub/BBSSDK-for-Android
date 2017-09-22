package com.mob.bbssdk.gui.ptrlistview;

import android.view.View;
import android.widget.TextView;

import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;

public abstract class PullToRefreshHeaderFooterAdapter extends PullToRefreshEmptyAdapter {
	private ChrysanthemumView cvHeaderProgress;
	private RotateImageView ivHeaderPointer;
	private TextView tvHeaderMessage;
	protected boolean refreshType;
	private ChrysanthemumView cvFooterProgress;
	private RotateImageView ivFooterPointer;
	private TextView tvFooterMessage;

	public PullToRefreshHeaderFooterAdapter(PullToRequestView view) {
		super(view);
	}

	public View getHeaderView() {
		View headerView = View.inflate(getContext(), ResHelper.getLayoutRes(getContext(), "bbs_pulltorefresh_header"), null);
		cvHeaderProgress = (ChrysanthemumView) headerView.findViewById(ResHelper.getIdRes(getContext(), "cvHeaderProgress"));
		ivHeaderPointer = (RotateImageView) headerView.findViewById(ResHelper.getIdRes(getContext(), "ivHeaderPointer"));
		tvHeaderMessage = (TextView) headerView.findViewById(ResHelper.getIdRes(getContext(), "tvHeaderMessage"));
		return headerView;
	}

	public void onPullDown(int percent) {
		if (percent <= 80) {
			ivHeaderPointer.setRotation(0);
			tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_pull_to_refresh"));
		} else if (percent < 100) {
			ivHeaderPointer.setRotation((percent - 80) * 9);
			tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_pull_to_refresh"));
		} else {
			ivHeaderPointer.setRotation(180);
			tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_release_to_refresh"));
		}
	}

	public void onRefresh() {
		tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_requesting"));
		ivHeaderPointer.setVisibility(View.INVISIBLE);
		cvHeaderProgress.setVisibility(View.VISIBLE);
		refreshType = true;
		requestRefresh();
	}

	public void onRequestNext() {
		tvFooterMessage.setText(ResHelper.getStringRes(getContext(), "bbs_requesting"));
		ivFooterPointer.setVisibility(View.INVISIBLE);
		cvFooterProgress.setVisibility(View.VISIBLE);
		refreshType = false;
		requestNextPage();
	}

	protected abstract void requestRefresh();
	protected abstract void requestNextPage();

	public View getFooterView() {
		View headerView = View.inflate(getContext(), ResHelper.getLayoutRes(getContext(), "bbs_pulltorefresh_footer"), null);
		cvFooterProgress = (ChrysanthemumView) headerView.findViewById(ResHelper.getIdRes(getContext(), "cvFooterProgress"));
		ivFooterPointer = (RotateImageView) headerView.findViewById(ResHelper.getIdRes(getContext(), "ivFooterPointer"));
		tvFooterMessage = (TextView) headerView.findViewById(ResHelper.getIdRes(getContext(), "tvFooterMessage"));
		return headerView;
	}

	public void onPullUp(int percent) {
		if (percent <= 80) {
			ivFooterPointer.setRotation(0);
			tvFooterMessage.setText(ResHelper.getStringRes(getContext(), "bbs_pull_to_request"));
		} else if (percent < 100) {
			ivFooterPointer.setRotation((percent - 80) * 9);
			tvFooterMessage.setText(ResHelper.getStringRes(getContext(), "bbs_pull_to_request"));
		} else {
			ivFooterPointer.setRotation(180);
			tvFooterMessage.setText(ResHelper.getStringRes(getContext(), "bbs_release_to_request"));
		}
	}

	public void notifyFreshDataError() {
		if (refreshType) {
			cvHeaderProgress.showFinish(new Runnable() {
				public void run() {
					tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_load_finished"));
				}
			}, new Runnable() {
				public void run() {
					superNotifyDataSetChanged();
					setLoadEmpty(true);
				}
			});
		}
	}

	public void notifyDataSetChanged() {
		if (refreshType) {
			cvHeaderProgress.showFinish(new Runnable() {
				public void run() {
					tvHeaderMessage.setText(ResHelper.getStringRes(getContext(), "bbs_load_finished"));
				}
			}, new Runnable() {
				public void run() {
					superNotifyDataSetChanged();
				}
			});
		} else {
			cvFooterProgress.showFinish(new Runnable() {
				public void run() {
					tvFooterMessage.setText(ResHelper.getStringRes(getContext(), "bbs_load_finished"));
				}
			}, new Runnable() {
				public void run() {
					superNotifyDataSetChanged();
				}
			});
		}
	}

	protected void superNotifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void onReversed() {
		ivHeaderPointer.setVisibility(View.VISIBLE);
		cvHeaderProgress.setVisibility(View.INVISIBLE);
		cvHeaderProgress.reset();

		ivFooterPointer.setVisibility(View.VISIBLE);
		cvFooterProgress.setVisibility(View.INVISIBLE);
		cvFooterProgress.reset();
	}

	protected void onRefreshClick() {
		getParent().performPullingDown(true);
	}
}
