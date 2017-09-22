package com.mob.bbssdk.gui.views;


import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.datadef.ThreadListSelectType;
import com.mob.tools.gui.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ForumThreadListPageAdapter extends ViewPagerAdapter {
	private PageSwitchListener pageSwitchListener;
	private ArrayList<ThreadListSelectType> threadListType;
	//缓存已经产生的view
	private Map<Integer, View> mapViewContent = new HashMap<Integer, View>();

	public ForumThreadListPageAdapter(PageSwitchListener listener) {
		this.pageSwitchListener = listener;
		this.threadListType = new ArrayList<ThreadListSelectType>();
	}

	public void setThreadListSelectType(ArrayList<ThreadListSelectType> list) {
		this.threadListType.clear();
		this.threadListType.addAll(list);
	}

	public void onScreenChange(int currentScreen, int lastScreen) {
		super.onScreenChange(currentScreen, lastScreen);
		pageSwitchListener.onPageChanged(currentScreen);
	}

	public int getCount() {
		return threadListType.size();
	}

	public ThreadListSelectType getItem(int position) {
		return (position >= 0 && position < threadListType.size()) ? threadListType.get(position) : null;
	}

	public Map<Integer, View> getViewMap() {
		return mapViewContent;
	}

	public View getViewAt(int pos) {
		return mapViewContent.get(pos);
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
		View item = mapViewContent.get(position);
		if (item != null) {
			return item;
		}

		ThreadListSelectType type = getItem(position);
		View itemView = getView(type, position);
		mapViewContent.put(position, itemView);
		return itemView;
	}

	protected abstract View getView(ThreadListSelectType filtertype, int position);

	public interface PageSwitchListener {
		void onPageChanged(int page);
	}
}
