package com.mob.bbssdk.theme0;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.views.ForumThreadListPageAdapter;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.gui.ViewPagerAdapter;

public class HeaderMobViewPager extends MobViewPager {

	ForumThreadListPageAdapter viewPagerAdapter;
	public HeaderMobViewPager(Context context) {
		super(context);
	}

	public HeaderMobViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HeaderMobViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setAdapter(ViewPagerAdapter adapter) {
		if(adapter instanceof ForumThreadListPageAdapter) {
			this.viewPagerAdapter = (ForumThreadListPageAdapter) adapter;
		} else {
			throw new IllegalArgumentException("adapter is not instance of ForumThreadListPageAdapter");
		}
		super.setAdapter(adapter);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
//			ViewGroup childView = (ViewGroup) getChildAt(getCurrentScreen());
			ViewGroup childView  = (ViewGroup) viewPagerAdapter.getViewAt(getCurrentScreen());
			if (childView.onInterceptTouchEvent(ev)) {
				return false;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}
}
