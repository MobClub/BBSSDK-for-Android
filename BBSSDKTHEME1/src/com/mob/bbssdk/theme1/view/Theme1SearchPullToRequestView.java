package com.mob.bbssdk.theme1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.bbssdk.gui.builder.ListViewItemBuilder;
import com.mob.bbssdk.gui.views.pullrequestview.SearchPullToRequestView;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.utils.ResHelper;

/**
 * Created by pgao on 2017/9/20.
 */

public class Theme1SearchPullToRequestView extends SearchPullToRequestView {

	public Theme1SearchPullToRequestView(Context context) {
		super(context);
	}

	public Theme1SearchPullToRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Theme1SearchPullToRequestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View getContentView(final int position, View convertView, ViewGroup parent) {
		Integer layout = ResHelper.getLayoutRes(getContext(), "bbs_theme1_item_searchthread");
		Object obj = getItem(position);
		if(obj instanceof ForumThread) {
			final ForumThread forumthread = (ForumThread) obj;
			final View view = ListViewItemBuilder.getInstance().buildLayoutThreadView(forumthread, convertView, parent, layout);
			TextView textViewTime = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewMainViewThreadTime"));
			if (textViewTime != null) {
				textViewTime.setText(com.mob.bbssdk.gui.utils.TimeUtils.timeDiff(getContext(), forumthread.createdOn));
			}
			TextView textViewPageLike = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewPageLike"));
			if(textViewPageLike != null) {
				textViewPageLike.setText("" + forumthread.recommendadd);
			}
			ListViewItemBuilder.getInstance().setThreadViewClickListener(forumthread, view);
			return view;
		}
		return null;
	}
}
