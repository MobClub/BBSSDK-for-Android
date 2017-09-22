package com.mob.bbssdk.theme0;


import android.content.Context;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.dialog.ReplyEditorPopWindow;
import com.mob.bbssdk.gui.pages.BasePage;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.pages.account.PageReactiveConfirm;
import com.mob.bbssdk.gui.pages.account.PageRegister;
import com.mob.bbssdk.gui.pages.account.PageRegisterConfirm;
import com.mob.bbssdk.gui.pages.account.PageRetrievePassword;
import com.mob.bbssdk.gui.pages.account.PageRetrievePasswordConfirm;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageReportAccusation;
import com.mob.bbssdk.gui.pages.forum.PageSelectForum;
import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.pages.misc.PageFavorites;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.pages.misc.PageHistory;
import com.mob.bbssdk.gui.pages.misc.PageMessages;
import com.mob.bbssdk.gui.pages.misc.PagePosts;
import com.mob.bbssdk.gui.pages.profile.PageInitProfile;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.pages.profile.PageUserProfileDetails;
import com.mob.bbssdk.gui.views.ForumThreadDetailView;
import com.mob.bbssdk.theme0.page.Theme0ReplyEditorPopWindow;
import com.mob.bbssdk.theme0.page.forum.Theme0PageForumThread;
import com.mob.bbssdk.theme0.page.forum.Theme0PageForumThreadDetail;
import com.mob.bbssdk.theme0.page.forum.Theme0PageSelectForum;
import com.mob.bbssdk.theme0.page.forum.Theme0PageWriteThread;
import com.mob.bbssdk.theme0.page.user.Theme0PageFavorites;
import com.mob.bbssdk.theme0.page.user.Theme0PageFollowers;
import com.mob.bbssdk.theme0.page.user.Theme0PageFollowings;
import com.mob.bbssdk.theme0.page.user.Theme0PageHistory;
import com.mob.bbssdk.theme0.page.user.Theme0PageInitProfile;
import com.mob.bbssdk.theme0.page.user.Theme0PageLogin;
import com.mob.bbssdk.theme0.page.user.Theme0PageMessages;
import com.mob.bbssdk.theme0.page.user.Theme0PageOtherUserProfile;
import com.mob.bbssdk.theme0.page.user.Theme0PagePosts;
import com.mob.bbssdk.theme0.page.user.Theme0PageReactiveConfirm;
import com.mob.bbssdk.theme0.page.user.Theme0PageRegister;
import com.mob.bbssdk.theme0.page.user.Theme0PageRegisterConfirm;
import com.mob.bbssdk.theme0.page.user.Theme0PageReportAccusation;
import com.mob.bbssdk.theme0.page.user.Theme0PageRetrievePassword;
import com.mob.bbssdk.theme0.page.user.Theme0PageRetrievePasswordConfirm;
import com.mob.bbssdk.theme0.page.user.Theme0PageUserProfile;
import com.mob.bbssdk.theme0.page.user.Theme0PageUserProfileDetails;
import com.mob.bbssdk.theme0.view.Theme0ForumThreadDetailView;
import com.mob.tools.utils.ResHelper;

public class BBSTheme0ViewBuilder extends BBSViewBuilder {

	public BBSTheme0ViewBuilder() {

	}

	public static BBSTheme0ViewBuilder buildInstance() {
		return new BBSTheme0ViewBuilder();
	}

	public Integer getMainActivityStatusBarColor(Context context) {
		return context.getResources().getColor(ResHelper.getColorRes(context, "bbs_theme0_blue"));
	}

	public Integer getMainActivityLayout(Context context) {
		return ResHelper.getLayoutRes(context, "bbs_theme0_activity_main");
	}

	@Override
	public PageLogin buildPageLogin() {
		return new Theme0PageLogin();
	}

	@Override
	public PageInitProfile buildPageInitProfile() {
		return new Theme0PageInitProfile();
	}

	@Override
	public PageInitProfile buildPageEditProfile() {
		return new Theme0PageInitProfile();
	}

	@Override
	public PageRegister buildPageRegister() {
		return new Theme0PageRegister();
	}

	@Override
	public PageRetrievePassword buildPageRetrievePassword() {
		return new Theme0PageRetrievePassword();
	}

	@Override
	public PageReactiveConfirm buildPageReactiveConfirm() {
		return new Theme0PageReactiveConfirm();
	}

	@Override
	public PageRegisterConfirm buildPageRegisterConfirm() {
		return new Theme0PageRegisterConfirm();
	}

	@Override
	public PageRetrievePasswordConfirm buildPageRetrievePasswordConfirm() {
		return new Theme0PageRetrievePasswordConfirm();
	}

	@Override
	public PageOtherUserProfile buildPageOtherUserProfile() {
		return new Theme0PageOtherUserProfile();
	}

	@Override
	public BasePage buildPageUserProfile() {
		//先跳转到个人中心Theme0PagePersonalInfo 然后可以再跳转到Theme0PageUserProfile
		return new Theme0PageUserProfile();
	}

	@Override
	public PageWriteThread buildPageWriteThread() {
		return new Theme0PageWriteThread();
	}

	@Override
	public PageSelectForum buildPageSelectForum() {
		return new Theme0PageSelectForum();
	}

	@Override
	public PageForumThread buildPageForumThread() {
		return new Theme0PageForumThread();
	}

	@Override
	public PageForumThreadDetail buildPageForumThreadDetail() {
		return new Theme0PageForumThreadDetail();
	}

	@Override
	public ReplyEditorPopWindow buildReplyEditorPopWindow(Context context
			, ReplyEditorPopWindow.OnConfirmClickListener listener
			, ReplyEditorPopWindow.OnImgAddClickListener clicklistener) {
		return new Theme0ReplyEditorPopWindow(context, listener, clicklistener);
	}

	@Override
	public ForumThreadDetailView buildForumThreadView(Context context) {
		return new Theme0ForumThreadDetailView(context);
	}

	@Override
	public PageReportAccusation buildPageReportAccusation() {
		return new Theme0PageReportAccusation();
	}

	@Override
	public PageFollowings buildPageFollowings() {
		return new Theme0PageFollowings();
	}

	@Override
	public PageFollowers buildPageFollowers() {
		return new Theme0PageFollowers();
	}

	@Override
	public PageUserProfileDetails buildPageUserProfileDetails() {
		return new Theme0PageUserProfileDetails();
	}

	@Override
	public PageFavorites buildPageFavorites() {
		return new Theme0PageFavorites();
	}

	@Override
	public PagePosts buildPagePosts() {
		return new Theme0PagePosts();
	}

	@Override
	public PageHistory buildPageHistory() {
		return new Theme0PageHistory();
	}

	@Override
	public PageMessages buildPageMessages() {
		return new Theme0PageMessages();
	}

	@Override
	public int getStatusBarColor(Context context) {
		return context.getResources().getColor(ResHelper.getColorRes(context, "bbs_theme0_statusbar_grey"));
	}
}
