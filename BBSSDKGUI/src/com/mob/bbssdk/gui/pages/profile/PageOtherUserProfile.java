package com.mob.bbssdk.gui.pages.profile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.gui.pages.BasePageWithTitle;
import com.mob.bbssdk.gui.views.pullrequestview.OtherUserProfilePullRequestView;
import com.mob.bbssdk.model.User;

public class PageOtherUserProfile extends BasePageWithTitle {
	protected OtherUserProfilePullRequestView pullRequestView;
	protected int nUid;
	protected User userOther;

	public void initPage(User user){
		this.userOther = user;
	}

	public void initPage(Integer uid){
		this.nUid = uid;
	}

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_page_profile_otheruserprofile"), null);
		return view;
	}


	@Override
	protected void onViewCreated(View contentView) {
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("bbs_userprofile_title"));
		pullRequestView = (OtherUserProfilePullRequestView) contentView.findViewById(getIdRes("pullRequestView"));
		if (userOther != null) {
			pullRequestView.initUser(userOther);
		} else {
			pullRequestView.initUser(nUid);
		}
		pullRequestView.updateDataFromServer();
	}
}
