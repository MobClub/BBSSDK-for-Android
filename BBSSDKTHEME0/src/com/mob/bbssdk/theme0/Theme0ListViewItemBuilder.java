package com.mob.bbssdk.theme0;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

public class Theme0ListViewItemBuilder extends ListViewItemBuilder {
	@Override
	protected Integer getThreadItemLayoutId(Context context) {
		return ResHelper.getLayoutRes(context, "bbs_theme0_item_forumthread");
	}

	@Override
	protected Integer getFavoriteItemLayoutId(Context context) {
		return ResHelper.getLayoutRes(context, "bbs_theme0_item_favorite");
	}

	@Override
	public View buildThreadView(ForumThread thread, View convertview, ViewGroup viewgroup) {
		View view = super.buildThreadView(thread, convertview, viewgroup);
		ThreadViewHolder viewholder = (ThreadViewHolder) view.getTag();
		viewholder.aivAvatar.setExecuteRound(ResHelper.dipToPx(view.getContext(), 11));
		return view;
	}
}
