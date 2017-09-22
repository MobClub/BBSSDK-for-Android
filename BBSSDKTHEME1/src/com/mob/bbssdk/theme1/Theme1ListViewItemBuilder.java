package com.mob.bbssdk.theme1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.tools.utils.ResHelper;


public class Theme1ListViewItemBuilder extends ListViewItemBuilder {
	@Override
	public View buildFollowerItemView(View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		return LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(parent.getContext(), "bbs_theme1_item_follower"), parent, false);
	}

	@Override
	public View buildFollowingsItemView(View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		return LayoutInflater.from(context).inflate(ResHelper.getLayoutRes(parent.getContext(), "bbs_theme1_item_following"), parent, false);
	}
}
