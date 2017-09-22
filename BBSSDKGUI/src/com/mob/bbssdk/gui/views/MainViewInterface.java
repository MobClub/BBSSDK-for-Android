package com.mob.bbssdk.gui.views;


public interface MainViewInterface {
	void loadData();

	void onCreate();

	void onDestroy();

	void setThreadItemClickListener(ForumThreadListView.OnItemClickListener listener);

	void setForumItemClickListener(MainView.ForumItemClickListener listener);

	void updateTitleUserAvatar();
}
